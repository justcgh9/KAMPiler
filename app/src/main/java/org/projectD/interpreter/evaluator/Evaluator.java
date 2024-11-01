package org.projectD.interpreter.evaluator;

import org.projectD.interpreter.object.ObjectTypeDemo;
import org.projectD.interpreter.object.ObjectTypeDemo.ObjectType;
import org.projectD.interpreter.object.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.projectD.interpreter.ast.Ast;

public class Evaluator {
    
    final ObjectTypeDemo.Null NULL = new ObjectTypeDemo.Null();
    final ObjectTypeDemo.Boolean TRUE = new ObjectTypeDemo.Boolean(true);
    final ObjectTypeDemo.Boolean FALSE = new ObjectTypeDemo.Boolean(false);

    // Общая функция для Эвала объекта, когда мы не знаем его тип заранее
    public ObjectTypeDemo.Object eval(Ast.Node node, Environment environment) {
        
        if (node instanceof Ast.Program) return evalProgram((Ast.Program) node, environment);

        //Statements
        if (node instanceof Ast.BlockStatement) return evalBlockStatement((Ast.BlockStatement) node, environment);

        if (node instanceof Ast.ExpressionStatement) return eval(((Ast.ExpressionStatement) node).getExpression(), environment);
    
        if (node instanceof Ast.ReturnStatement) {
            var val = eval(((Ast.ReturnStatement) node).getExpression(), environment);
            return isError(val) ? val : new ObjectTypeDemo.ReturnValue(val);
        }

        if (node instanceof Ast.VarStatement) {
            var stmt = (Ast.VarStatement) node;
            var val = eval(stmt.getExpression(), environment);
            if (isError(val)) {
                return val;
            }
            environment.set(stmt.getIdentifier().getName(), val);
            return null;
        }

        if (node instanceof Ast.IfStatement) {
            return evalIfStatement((Ast.IfStatement) node, environment);
        }

        if (node instanceof Ast.PrintLiteral) {
            var stmt = (Ast.PrintLiteral) node;
            var args = evalExpressions(stmt.getArguments(), environment);
            StringBuilder out = new StringBuilder();
            for(var arg: args) {
                out.append(arg.toString());
                out.append(" ");
            }
            System.out.println(out.toString());
            return NULL;
        }

        if (node instanceof Ast.ForLiteral) {
            var stmt = (Ast.ForLiteral) node;
            var loopVariable = stmt.getLoopVariable();
            var typeInd = stmt.getTypeIndicator();
            if(!(typeInd instanceof Ast.InfixExpression)) {return newError("Invalid type indicator: %s", typeInd);}

            var typeIndicator = (Ast.InfixExpression) typeInd;
            if(!typeIndicator.getOperator().equals("..")) {return newError("Invalid operator: %s",typeIndicator.getOperator());}
            
            var left = eval(typeIndicator.getLeft(), environment);
            if (isError(left)) {
                return left;
            }

            if (left.getType() != ObjectType.INTEGER_OBJ) {
                return newError("Invalid type indicator start: %s", left);
            }

            var right = eval(typeIndicator.getRight(), environment);
            if (isError(right)) {
                return right;
            }

            if (right.getType() != ObjectType.INTEGER_OBJ) {
                return newError("Invalid type indicator start: %s", right);
            }

            var env = new Environment(environment);
            for(long i = ((ObjectTypeDemo.Integer) left).getValue(); i < ((ObjectTypeDemo.Integer) right).getValue(); i++) {
                env.set(loopVariable.getName(), new ObjectTypeDemo.Integer(i));
                var result = eval(stmt.getBody(), env);

                if (result != null) {
                    var resultType = result.getType();
                    if (resultType == ObjectType.RETURN_VALUE_OBJ || resultType == ObjectType.ERROR_OBJ) {
                        return result;
                    } 
                }
            }

            return NULL;
        }

        // TODO: For, While

        //Expressions
        if (node instanceof Ast.IntegerLiteral) {
            return new ObjectTypeDemo.Integer((long)((Ast.IntegerLiteral) node).getValue());
        }

        if (node instanceof Ast.RealLiteral) {
            return new ObjectTypeDemo.Double((double)((Ast.RealLiteral) node).getValue());
        }

        if (node instanceof Ast.StringLiteral) {
            return new ObjectTypeDemo.StringObject(((Ast.StringLiteral) node).getValue());
        }

        if (node instanceof Ast.BooleanLiteral) {
            return nativeBooleanToBooleanObject(((Ast.BooleanLiteral) node).getValue());
        }

        if (node instanceof Ast.ArrayLiteral) {
            var elements = evalExpressions(((Ast.ArrayLiteral) node).getElements(), environment);
            if(elements.size() == 1 && isError(elements.get(0))) {
                return elements.get(0);
            }
            return new ObjectTypeDemo.ArrayObject(elements);
        }

        if (node instanceof Ast.TupleLiteral) {
            var expr = (Ast.TupleLiteral) node;
            Map<ObjectTypeDemo.HashKey, ObjectTypeDemo.HashPair> pairs = new HashMap<>();

            for(var entry: expr.getPairs().entrySet()) {
                var gotKey = entry.getKey();
                if (!(gotKey instanceof Ast.IntegerLiteral || gotKey instanceof Ast.Identifier)) {
                    return newError("Invalid key type: %s", gotKey.toString());
                }
                
                var key = (gotKey instanceof Ast.IntegerLiteral) 
                ? new ObjectTypeDemo.Integer((long) ((Ast.IntegerLiteral) gotKey).getValue()) 
                : new ObjectTypeDemo.StringObject(((Ast.Identifier) gotKey).getName());

                var value = eval(entry.getValue(), environment);
                if (isError(value)) {
                    return value;
                }

                var hashed = ((ObjectTypeDemo.Hashable) key).hashKey();
                pairs.put(hashed, new ObjectTypeDemo.HashPair(key, value));
            }

            return new ObjectTypeDemo.TupleObject(pairs);
        }

        if (node instanceof Ast.IndexLiteral) {
            var left = eval(((Ast.IndexLiteral) node).getLeft(), environment);
            if(isError(left)) {
                return left;
            }

            if(left.getType() == ObjectType.TUPLE_OBJ) {
                var tuple = (ObjectTypeDemo.TupleObject) left;

                var index = ((Ast.IndexLiteral) node).getIndex();
                if(!(index instanceof Ast.IntegerLiteral || index instanceof Ast.Identifier)) {
                    return newError("key is unusable as hash: %s", index.toString());
                }

                var key = (index instanceof Ast.IntegerLiteral) 
                ? new ObjectTypeDemo.Integer((long) ((Ast.IntegerLiteral) index).getValue()) 
                : new ObjectTypeDemo.StringObject(((Ast.Identifier) index).getName());
                
                var pair = tuple.getPairs().get(((ObjectTypeDemo.Hashable) key).hashKey());
                if (pair == null) {
                    return NULL;
                }

                return pair.getValue();
            }

            var index = eval(((Ast.IndexLiteral) node).getIndex(), environment);
            if(isError(index)) {
                return index;
            }

            if (left.getType() == ObjectType.ARRAY_OBJ && index.getType() == ObjectType.INTEGER_OBJ) {
                var arr = (ObjectTypeDemo.ArrayObject) left;
                var idx = ((ObjectTypeDemo.Integer) index).getValue();
                long max = (long) arr.getValue().size();
                if (idx < 0 || idx > max) {
                    return NULL;
                }

                return arr.getValue().get((int)idx);
            }

        }

        if (node instanceof Ast.PrefixExpression) {
            Ast.PrefixExpression stmt = (Ast.PrefixExpression) node;
            var right = eval(stmt.getRight(), environment);
            if (isError(right)) {
                return right;
            }
            return evalPrefixExpression(stmt.getOperator(), right);
        }

        if (node instanceof Ast.InfixExpression) {
            Ast.InfixExpression stmt = (Ast.InfixExpression) node;
            
            var left = eval(stmt.getLeft(), environment);
            if (isError(left)) {
                return left;
            }
            
            var right = eval(stmt.getRight(), environment);
            if (isError(right)) {
                return right;
            }

            return evalInfixExpression(stmt.getOperator(), left, right);
        }

        if (node instanceof Ast.Identifier) {
            Ast.Identifier expr = (Ast.Identifier) node;
            var value = environment.get(expr.getName());
            if (value == null) return newError("identifier not found: " + expr.getName());
            return value;
        }

        if (node instanceof Ast.FunctionLiteral) {
            Ast.FunctionLiteral expr = (Ast.FunctionLiteral) node;
            var params = expr.getParameters();
            var body = expr.getBody();
            return new ObjectTypeDemo.FunctionObject(params, body, environment);
        }

        if (node instanceof Ast.CallExpression) {
            Ast.CallExpression expr = (Ast.CallExpression) node;
            var function = eval(expr.getFunction(), environment);
            
            if(isError(function)) {
                return function;
            }

            var args = evalExpressions(expr.getArguments(), environment);
            if(args.size() == 1 && isError(args.get(0))) {
                return args.get(0);
            }

            return applyFunction(function, args);
        }

        return NULL;
    }

    private ObjectTypeDemo.Object evalProgram(Ast.Program program, Environment environment) {
        /**
         * Функция для оценки программы.
         * Проходимся и оцениваем каждое утверждение;
         * Если встречаем ошибку или ретурн - выходим.
         */
        ObjectTypeDemo.Object result = NULL;

        for(Ast.Statement stmt: program.getStatements()) {
            
            result = eval(stmt, environment);

            if (result instanceof ObjectTypeDemo.ReturnValue) {
                return ((ObjectTypeDemo.ReturnValue) result).getValue();
            } else if (result instanceof ObjectTypeDemo.Error) {
                return result;
            }
        }

        return result;
    }

    private ObjectTypeDemo.Object evalBlockStatement(Ast.BlockStatement block, Environment environment) {
        /**
         * Функция для оценки blockStatement.
         * Проходимся и оцениваем каждое утверждение;
         * Если встречаем ошибку или ретурн - выходим.
         */
        ObjectTypeDemo.Object result = NULL;
        
        for(Ast.Statement stmt: block.getStatements()) {
            result = eval(stmt, environment);

            if (result != null) {
                var resultType = result.getType();
                if (resultType == ObjectType.RETURN_VALUE_OBJ || resultType == ObjectType.ERROR_OBJ) {
                    return result;
                } 
            }
        }
        
        return result;
    }

    private ObjectTypeDemo.Object evalIfStatement(Ast.IfStatement stmt, Environment environment) {
        var predicate = eval(stmt.getCondition(), environment);
        if (isError(predicate)) {
            return predicate;
        }

        if (isTruthy(predicate)) {
            return eval(stmt.getThenBlock(), new Environment(environment));
        } 

        var elseBlock = stmt.getElseBlock();
        if (elseBlock != null) {
            return eval(elseBlock, new Environment(environment));
        }

        return NULL;
    }

    private List<ObjectTypeDemo.Object> evalExpressions(List<Ast.Expression> exps, Environment environment) {
        List<ObjectTypeDemo.Object> result = new ArrayList<>();

        for(Ast.Expression expr: exps) {
            var evaluated = eval(expr, environment);

            if (isError(evaluated)) {
                return new ArrayList<ObjectTypeDemo.Object>(List.of(evaluated));
            }

            result.add(evaluated);
        }

        return result;
    }

    private ObjectTypeDemo.Object applyFunction(ObjectTypeDemo.Object function, List<ObjectTypeDemo.Object> args) {
        if (function instanceof ObjectTypeDemo.FunctionObject) {
            var func = (ObjectTypeDemo.FunctionObject) function;
            Environment environment = new Environment(func.getEnvironment(), true);

            var params = func.getParameters();
            for (int i = 0; i < params.size(); i++) {
                environment.set(params.get(i).getName(), args.get(i));
            }

            var evaluated = eval(func.getBody(), environment);

            if (evaluated instanceof ObjectTypeDemo.ReturnValue) {
                return ((ObjectTypeDemo.ReturnValue) evaluated).getValue();
            }

            return evaluated;
        }
        return newError("Not a function: %s", function.getType());
    }

    private ObjectTypeDemo.Boolean nativeBooleanToBooleanObject(boolean inp) {
        /**
         * Конвертим булы в булевые объекты
         */
        return inp ? TRUE : FALSE;
    }

    private int compare(Object leftVal, Object rightVal) {
        if (leftVal instanceof Double || rightVal instanceof Double) {
            double left = ((Number) leftVal).doubleValue();
            double right = ((Number) rightVal).doubleValue();
            return Double.compare(left, right);
        } else if (leftVal instanceof Long && rightVal instanceof Long) {
            return Long.compare((long) leftVal, (long) rightVal);
        } else {
            throw new IllegalArgumentException("Unsupported types for comparison");
        }
    }
    

    private ObjectTypeDemo.Object evalInfixExpression(String operator, ObjectTypeDemo.Object left, ObjectTypeDemo.Object right) {
        if (
            (left.getType() == ObjectType.INTEGER_OBJ || left.getType() == ObjectType.DOUBLE_OBJ)
            &&
            (right.getType() == ObjectType.INTEGER_OBJ || right.getType() == ObjectType.DOUBLE_OBJ)

        ) {
            return evalNumericInfixExpression(operator, left, right);
        }

        if (left.getType() == ObjectType.STRING_OBJ && right.getType() == ObjectType.STRING_OBJ) {
            return evalStringInfixExpression(operator, left, right);
        }

        if (left.getType() == ObjectType.ARRAY_OBJ && right.getType() == ObjectType.ARRAY_OBJ) {
            return evalArrayInfixExpression(operator, left, right);
        }
        //TODO: refine the tuple specifics, implement tuple operations.

        //TODO: equals method for all the object types
        if (operator.equals("==")) {
            return nativeBooleanToBooleanObject(left.equals(right));
        }

        if (operator.equals("!=")) {
            return nativeBooleanToBooleanObject(!left.equals(right));
        }
        
        return newError("unknown operator: %s %s %s", operator, right.getType());
    }

    private ObjectTypeDemo.Object evalNumericInfixExpression(String operator, ObjectTypeDemo.Object left, ObjectTypeDemo.Object right) {
        boolean isDouble = left.getType() == ObjectType.DOUBLE_OBJ || right.getType() == ObjectType.DOUBLE_OBJ;
        
        Number leftVal, rightVal;
        leftVal = left.getType() == ObjectType.DOUBLE_OBJ ? ((ObjectTypeDemo.Double) left).getValue() : ((ObjectTypeDemo.Integer) left).getValue();
        rightVal = right.getType() == ObjectType.DOUBLE_OBJ ? ((ObjectTypeDemo.Double) right).getValue() : ((ObjectTypeDemo.Integer) right).getValue();

        switch (operator) {
            case "+":
                return isDouble
                ? new ObjectTypeDemo.Double(leftVal.doubleValue() + rightVal.doubleValue())
                : new ObjectTypeDemo.Integer(leftVal.longValue() + rightVal.longValue());
            case "-":
                return isDouble
                ? new ObjectTypeDemo.Double(leftVal.doubleValue() - rightVal.doubleValue())
                : new ObjectTypeDemo.Integer(leftVal.longValue() - rightVal.longValue());
            case "*":
                return isDouble
                ? new ObjectTypeDemo.Double(leftVal.doubleValue() * rightVal.doubleValue())
                : new ObjectTypeDemo.Integer(leftVal.longValue() * rightVal.longValue());
            case "/":
                if ((right.getType() == ObjectType.DOUBLE_OBJ && rightVal.doubleValue() == 0.0) ||
                    (right.getType() == ObjectType.INTEGER_OBJ && rightVal.longValue() == 0)) {
                    newError("Division by zero is not allowed");
                }
                return isDouble
                    ? new ObjectTypeDemo.Double(leftVal.doubleValue() / rightVal.doubleValue())
                    : new ObjectTypeDemo.Integer(leftVal.longValue() / rightVal.longValue());
                    case "=":
                    return nativeBooleanToBooleanObject(leftVal == rightVal);
                
            case "!=":
                return nativeBooleanToBooleanObject(leftVal != rightVal);
            
            case ">":
                try {return nativeBooleanToBooleanObject(compare(leftVal, rightVal) > 0);}
                catch(IllegalArgumentException e) {
                    return newError(e.getMessage() + " %s%s", left.getType(), right.getType());
                }
            
            case ">=":
                try {return nativeBooleanToBooleanObject(compare(leftVal, rightVal) >= 0);}
                catch(IllegalArgumentException e) {
                    return newError(e.getMessage() + " %s%s", left.getType(), right.getType());
                }
            
            case "<":
                try {return nativeBooleanToBooleanObject(compare(leftVal, rightVal) < 0);}
                catch(IllegalArgumentException e) {
                    return newError(e.getMessage() + " %s%s", left.getType(), right.getType());
                }
            
            case "<=":
                try {return nativeBooleanToBooleanObject(compare(leftVal, rightVal) <= 0);}
                catch(IllegalArgumentException e) {
                    return newError(e.getMessage() + " %s%s", left.getType(), right.getType());
                }
                
        
        
            default:
                return newError("Unknown operator: %s %s %s", left.getType(), operator, right.getType());
        }
    }
    
    private ObjectTypeDemo.Object evalStringInfixExpression(String operator, ObjectTypeDemo.Object left, ObjectTypeDemo.Object right) {
        String leftValue = ((ObjectTypeDemo.StringObject) left).getValue();
        String rightValue = ((ObjectTypeDemo.StringObject) right).getValue();
        var result = new StringBuilder();

         switch (operator) {
            case "+":
                if (leftValue.equals("") && rightValue.equals("")){
                    break;
                }
                
                if (leftValue.equals("")) {
                    result.append(rightValue);
                    break;
                }

                if (rightValue.equals("")) {
                    result.append(leftValue);
                    break;
                }

                // remove quates to concatinate
                var leftTrancated = leftValue.substring(0, leftValue.length() - 1);
                var rightTrancated = rightValue.substring(1, rightValue.length());
                result.append(leftTrancated);
                result.append(rightTrancated);
                break;
            default:
                return newError("Unknown operator: %s %s %s", left.getType(), operator, right.getType());
            }
        return new ObjectTypeDemo.StringObject(result.toString());

    }

    private ObjectTypeDemo.Object evalArrayInfixExpression(String operator, ObjectTypeDemo.Object left, ObjectTypeDemo.Object right) {
        switch (operator) {
            case "+":
                var leftVal = ((ObjectTypeDemo.ArrayObject) left).getValue();
                leftVal.addAll(((ObjectTypeDemo.ArrayObject) right).getValue());
                return new ObjectTypeDemo.ArrayObject(leftVal);
            default:
                return newError("Unknown operator: %s %s %s", left.getType(), operator, right.getType());
        }
    }

    private ObjectTypeDemo.Object evalPrefixExpression(String operator, ObjectTypeDemo.Object right) {
        switch (operator) {
            case "-":
                return evalAlgebraicNegateOperation(right);
            case "not":
                return evalBooleanNegateOperation(right);
            default:
                return newError("unknown operator: %s%s", operator, right.getType());
        }
    }

    
    private ObjectTypeDemo.Object evalAlgebraicNegateOperation(ObjectTypeDemo.Object right) {
        if (right.getType() != ObjectType.DOUBLE_OBJ && right.getType() != ObjectType.INTEGER_OBJ) {
            return newError("unknown operator: -%s", right.getType());
        }
        
        if (right.getType() == ObjectType.INTEGER_OBJ) {
            return new ObjectTypeDemo.Integer(
                -((ObjectTypeDemo.Integer) right).getValue()
                );
        }
        
        if (right.getType() == ObjectType.DOUBLE_OBJ) {
            return new ObjectTypeDemo.Double(
                -((ObjectTypeDemo.Double) right).getValue()
                );
            }

            return NULL;
        }
            
    private ObjectTypeDemo.Object evalBooleanNegateOperation(ObjectTypeDemo.Object right) {
        if (right == FALSE || right == NULL) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    private boolean isTruthy(ObjectTypeDemo.Object obj) {
        if (obj == NULL || obj == FALSE) {
            return false;
        }
        return true;
    }

    private ObjectTypeDemo.Error newError(String format, Object... args) {
        String message = String.format(format, args);
        return new ObjectTypeDemo.Error(message);
    }

    private boolean isError(ObjectTypeDemo.Object obj) {
        return obj != null ? obj.getType() == ObjectType.ERROR_OBJ : false;
    }
}