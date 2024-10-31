package org.projectD.interpreter.evaluator;

import org.projectD.interpreter.object.ObjectTypeDemo;
import org.projectD.interpreter.object.ObjectTypeDemo.ObjectType;
import org.projectD.interpreter.object.Environment;
import org.projectD.interpreter.ast.Ast;

public class Evaluator {
    
    final ObjectTypeDemo.Null NULL = new ObjectTypeDemo.Null();
    final ObjectTypeDemo.Boolean TRUE = new ObjectTypeDemo.Boolean(true);
    final ObjectTypeDemo.Boolean FALSE = new ObjectTypeDemo.Boolean(false);

    // Общая функция для Эвала объекта, когда мы не знаем его тип заранее
    // TODO: реализовать.
    public ObjectTypeDemo.Object eval(Ast.Node node, Environment environment) {
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
            return evalNumericInfixExpression(operator, left,right);
        }
        
        return NULL;
    }

    private ObjectTypeDemo.Object evalNumericInfixExpression(String operator, ObjectTypeDemo.Object left, ObjectTypeDemo.Object right) {
        boolean isDouble = left.getType() == ObjectType.DOUBLE_OBJ || right.getType() == ObjectType.DOUBLE_OBJ;
        
        Number leftVal, rightVal;
        leftVal = left.getType() == ObjectType.DOUBLE_OBJ ? ((ObjectTypeDemo.Double) left).getValue() : ((ObjectTypeDemo.Integer) left).getValue();
        rightVal = right.getType() == ObjectType.DOUBLE_OBJ ? ((ObjectTypeDemo.Double) right).getValue() : ((ObjectTypeDemo.Integer) right).getValue();

        switch (operator) {
            case "+":
                return isDouble
                ? new ObjectTypeDemo.Double((double) leftVal + (double) rightVal)
                : new ObjectTypeDemo.Integer((long) leftVal + (long) rightVal);
            case "-":
                return isDouble
                ? new ObjectTypeDemo.Double((double) leftVal - (double) rightVal)
                : new ObjectTypeDemo.Integer((long) leftVal - (long) rightVal);
            case "*":
                return isDouble
                ? new ObjectTypeDemo.Double((double) leftVal * (double) rightVal)
                : new ObjectTypeDemo.Integer((long) leftVal * (long) rightVal);
            case "/":
                if ((right.getType() == ObjectType.DOUBLE_OBJ && (double) rightVal == 0.0) ||
                    (right.getType() == ObjectType.INTEGER_OBJ && (long) rightVal == 0)) {
                    newError("Division by zero is not allowed");
                }
                return isDouble
                    ? new ObjectTypeDemo.Double((double) leftVal / (double) rightVal)
                    : new ObjectTypeDemo.Integer((long) leftVal / (long) rightVal);
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

    private ObjectTypeDemo.Error newError(String format, Object... args) {
        String message = String.format(format, args);
        return new ObjectTypeDemo.Error(message);
    }
}