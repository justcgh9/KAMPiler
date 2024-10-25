package org.projectD.interpreter.semantic;

import org.projectD.interpreter.ast.*;
import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.token.TokenType;
import org.projectD.interpreter.lib.Pair;

import java.util.*;


public class SemanticAnalyzer {

    
    private final Map<String, Ast.VarStatement> symbolTable = new HashMap<>();
    
    private final Stack<Map<String, Pair<Ast.VarStatement, String>>> functionContext = new Stack<>();
    
    private final Set<String> usedVariables = new HashSet<>();
    
    
    public void analyze(Ast.Node node) {
        if (node instanceof Ast.Program) {
            analyzeProgram((Ast.Program) node);
        } else {
            throw new IllegalArgumentException("Invalid root node type for semantic analysis");
        }
    }

    
    private void analyzeProgram(Ast.Program program) {
        for (Ast.Statement stmt : program.getStatements()) {
            analyzeStatement(stmt);
        }
        removeUnusedVariables(program);
    }

    private Ast.Expression analyzeExpression(Ast.Expression expr) {
        if (expr instanceof Ast.Identifier) {
            Ast.Identifier ident = (Ast.Identifier) expr;
            checkExistence(ident.getName());
            return expr;
        } else if (expr instanceof Ast.InfixExpression) {
            Ast.InfixExpression infix = (Ast.InfixExpression) expr;
            infix.setLeft(analyzeExpression(infix.getLeft()));
            infix.setRight(analyzeExpression(infix.getRight()));
            var res = simplifyInfixExpression(infix);
            return res;
        } else if (expr instanceof Ast.PrefixExpression) {

        } else if (expr instanceof Ast.FunctionLiteral) {
            analyzeFunction((Ast.FunctionLiteral) expr);
            return expr;
        } else if (expr instanceof Ast.CallExpression) {
            // TODO: Check out this one, because I skipped it
            ((Ast.CallExpression) expr).addFunction(analyzeExpression(((Ast.CallExpression) expr).getFunction()));
            
            List<Ast.Expression> expressions = new ArrayList<>();
            for (Ast.Expression exp : ((Ast.CallExpression) expr).getArguments()) {
                expressions.add(analyzeExpression(exp));
            }
            ((Ast.CallExpression) expr).setArguments(expressions);
        }

        return expr;
    }

    private boolean isNumber(Ast.Expression expr) {
        return expr instanceof Ast.IntegerLiteral || expr instanceof Ast.RealLiteral;
    } 

    private boolean isArithmeticOperator(String operator) {
        return operator == "+" || operator == "-" || operator == "/" || operator == "*";
    }

    private boolean isBoolOperator(String operator) {
        return operator == ">" || operator == "<" || operator == "=" || operator == "!=";
    }

    private double convertDouple(Ast.Expression number) {
        if (number instanceof Ast.IntegerLiteral) {
            return ((Ast.IntegerLiteral) number).getValue(); 
        }
        return ((Ast.RealLiteral) number).getValue(); 
    }

    private Ast.Expression simplifyStrings(Ast.InfixExpression infix) {
        String left = ((Ast.StringLiteral)infix.getLeft()).getValue();
        String right = ((Ast.StringLiteral)infix.getRight()).getValue();
        var result = new StringBuilder();

         switch (infix.getOperator()) {
            case "+":
                if (left.equals("") && right.equals("")){
                    break;
                }
                
                if (left.equals("")) {
                    result.append(right);
                    break;
                }

                if (right.equals("")) {
                    result.append(left);
                    break;
                }

                // remove quates to concatinate
                var leftTrancated = left.substring(0, left.length() - 1);
                var rightTrancated = right.substring(1, right.length());
                result.append(leftTrancated);
                result.append(rightTrancated);
                break;
            default:
                throw new UnsupportedOperationException("Illigal operation for strings");
        }

        return new Ast.StringLiteral(new Token(result.toString(), TokenType.STRING), result.toString());
    }

    private Ast.Expression simplifyBools(Ast.InfixExpression infix) {
        boolean left = ((Ast.BooleanLiteral)infix.getLeft()).getValue();
        boolean right = ((Ast.BooleanLiteral)infix.getRight()).getValue();
        boolean result;

         switch (infix.getOperator()) {
            case "!=":
                result = left != right;
                break;
            case "=":
                result = left == right;
                break;
            default:
                throw new UnsupportedOperationException("Illigal operation for bools");
        }

        TokenType type = TokenType.FALSE;
        if (result) {
            type = TokenType.TRUE;
        }

        return new Ast.BooleanLiteral(new Token(String.valueOf(result), type), String.valueOf(result));
    }

    private Ast.Expression simplifyNumbers(Ast.InfixExpression infix) {
        double left = convertDouple(infix.getLeft());
        double right = convertDouple(infix.getRight());
        double result = 0;
        boolean boolRes = false;
        boolean isBooleanRes = false;

        switch (infix.getOperator()) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right == 0) {
                    throw new IllegalArgumentException("Division by 0");
                }; 
                result = left / right;
                break;
            case ">":
                boolRes = left > right;
                isBooleanRes = true;
                break;
            case "<":
                boolRes = left < right;
                isBooleanRes = true;
                break;
            case "!=":
                boolRes = left != right;
                isBooleanRes = true;
                break;
            case "=":
                boolRes = left == right;
                isBooleanRes = true;
                break;
            default:
                throw new UnsupportedOperationException("Illigal operation for numbers");
        }
        
        // for logical expression
        if (isBooleanRes) {
            TokenType boolType = TokenType.FALSE;
            if (boolRes) {
                boolType = TokenType.TRUE;
            }
            return new Ast.BooleanLiteral(new Token(String.valueOf(boolRes), boolType), String.valueOf(boolRes));
        }

        // for arithmetic experssion
        if (result % 1 == 0) { 
            return new Ast.IntegerLiteral(new Token(String.valueOf((int) result), TokenType.INT), String.valueOf((int) result));
        }
        return new Ast.RealLiteral(new Token(String.valueOf(result), TokenType.REAL), String.valueOf(result));
    }


    private Ast.Expression simplifyInfixExpression(Ast.InfixExpression infix) {
        if (isNumber(infix.getLeft()) && isNumber(infix.getRight())) {
            return simplifyNumbers(infix);
        } 

        if (infix.getLeft() instanceof Ast.StringLiteral && infix.getRight() instanceof Ast.StringLiteral) {
            return simplifyStrings(infix);
        }

        if (infix.getLeft() instanceof Ast.BooleanLiteral && infix.getRight() instanceof Ast.BooleanLiteral) {
            return simplifyBools(infix);
        }
        return infix;
    }

    private void analyzeStatement(Ast.Statement stmt) {
        if (stmt instanceof Ast.VarStatement) {
            analyzeVarStatement((Ast.VarStatement) stmt);
        } else if (stmt instanceof Ast.ExpressionStatement) {
            ((Ast.ExpressionStatement) stmt).setExpression(analyzeExpression(((Ast.ExpressionStatement) stmt).getExpression()));
        } else if (stmt instanceof Ast.IfStatement) {
            analyzeIfStatement((Ast.IfStatement) stmt);
        } else if (stmt instanceof Ast.WhileStatement) {
            analyzeWhileStatement((Ast.WhileStatement) stmt);
        } else if (stmt instanceof Ast.ReturnStatement) {
            analyzeReturnStatement((Ast.ReturnStatement) stmt);
        } else if (stmt instanceof Ast.PrintLiteral) {
            analyzePrintStatement((Ast.PrintLiteral) stmt);
        } else if (stmt instanceof Ast.ForLiteral) {
            analyzeForStatement((Ast.ForLiteral) stmt);
        }
    }
    
    private void analyzeVarStatement(Ast.VarStatement stmt) {
        String varName = stmt.getIdentifier().getName();
        if (functionContext.isEmpty()) {
            var val = symbolTable.get(varName);
            if(val != null) {
                throw new IllegalArgumentException(varName + " is redefined in this scope");
            }
            symbolTable.put(varName, stmt);
            usedVariables.remove(varName);  
        } else {
            var context = functionContext.peek();
            var val = context.get(varName);
            if(val != null) {
                throw new IllegalArgumentException(varName + " is redefined in this scope");
            }
            context.put(varName, new Pair<Ast.VarStatement,String>(stmt, "unused"));
        }
        if (stmt.getExpression() != null) {
            stmt.setExpression(analyzeExpression(stmt.getExpression()));
        }
    }

    private void analyzePrintStatement(Ast.PrintLiteral stmt) {
        List<Ast.Expression> newArgs = new ArrayList<>();
        for(Ast.Expression arg : stmt.getArguments()) {
            newArgs.add(analyzeExpression(arg));
        }
        stmt.setArguments(newArgs);
    }
    
    private void analyzeIfStatement(Ast.IfStatement stmt) {
        stmt.setCondition(analyzeExpression(stmt.getCondition()));
        analyzeBlock(stmt.getThenBlock());
        if (stmt.getElseBlock() != null) {
            analyzeBlock(stmt.getElseBlock());
        }
    }

    
    private void analyzeWhileStatement(Ast.WhileStatement stmt) {
        stmt.setCondition(analyzeExpression(stmt.getCondition()));
        analyzeBlock(stmt.getBody());
    }

    private void analyzeForStatement(Ast.ForLiteral stmt) {
        analyzeBlock(stmt.getBody());
    }

    
    private void analyzeReturnStatement(Ast.ReturnStatement stmt) {
        if (functionContext.isEmpty()) {
            throw new IllegalStateException("Return statement used outside of a function");
        }
        if (stmt.getExpression() != null) {
            stmt.setExpression(analyzeExpression(stmt.getExpression()));
        }
    }

    
    private void analyzeFunction(Ast.FunctionLiteral func) {
        Map<String, Pair<Ast.VarStatement, String>> context = new HashMap<>();
        functionContext.push(context);
        for (Ast.Identifier param : func.getParameters()) {
            context.put(param.getName(), new Pair<Ast.VarStatement,String>(new Ast.VarStatement(param, null), "unused"));
        }
        analyzeBlock(func.getBody());
        removeUnusedVariables(func.getBody());
        functionContext.pop();
    }

    
    private void analyzeBlock(Ast.BlockStatement block) {
        for (Ast.Statement stmt : block.getStatements()) {
            analyzeStatement(stmt);
        }
    }

    private void checkExistence(String name) {
        Stack<Map<String, Pair<Ast.VarStatement, String>>> newStack = new Stack<>();

        while (!functionContext.isEmpty()) {
            var context = functionContext.pop();
            newStack.push(context);
            var val = context.get(name);
            if (val != null) {
                context.put(name, new Pair<Ast.VarStatement,String>(val.getLeft(), "used"));
                putContextsBack(newStack);
                return;
            } 
        }
        
        putContextsBack(newStack);
        var val = symbolTable.get(name);
        if (val != null){
            usedVariables.add(name);
            return;
        }
        throw new IllegalArgumentException(name + " is not defined");
    }

    private void putContextsBack(Stack<Map<String, Pair<Ast.VarStatement, String>>> contexts) {
        while(!contexts.isEmpty()) {
            functionContext.push(contexts.pop());
        }
    }
    
    private void removeUnusedVariables(Ast.Program program) {
        List<Ast.Statement> newStatements = new ArrayList<>();
        for (Ast.Statement stmt : program.getStatements()) {
            if (stmt instanceof Ast.VarStatement) {
                Ast.VarStatement varStmt = (Ast.VarStatement) stmt;
                if (usedVariables.contains(varStmt.getIdentifier().getName())) {
                    newStatements.add(varStmt);
                }
            } else {
                newStatements.add(stmt);
            }
        }
        program.setStatements(newStatements);
    }

    private void removeUnusedVariables(Ast.BlockStatement block) {
        List<Ast.Statement> newStatements = new ArrayList<>();
        for (Ast.Statement stmt : block.getStatements()) {
            if (stmt instanceof Ast.VarStatement) {
                Ast.VarStatement varStmt = (Ast.VarStatement) stmt;
                if (!functionContext.isEmpty()) {
                    var context = functionContext.peek();
                    var val = context.get(varStmt.getIdentifier().getName());
                    if (val.getRight().equals("used")) {
                        newStatements.add(varStmt);
                    }
                } else if (usedVariables.contains(varStmt.getIdentifier().getName())) {
                    newStatements.add(varStmt);
                }
            } else {
                newStatements.add(stmt);
            }
        }
        block.setStatements(newStatements);
    }


    // Additional optimization: Inlining simple functions

    // Inline simple functions into the program
//     private void inlineFunctions(Ast.Program program) {
//         // Step 1: Identify simple functions that can be inlined
//         Map<String, Ast.FunctionLiteral> inlineableFunctions = new HashMap<>();
        
//         // Iterate over all statements in the program and collect inlineable functions
//         for (Ast.Statement stmt : program.getStatements()) {
//             if (stmt instanceof Ast.VarStatement) {
//                 Ast.VarStatement varStmt = (Ast.VarStatement) stmt;
//                 if (varStmt.getExpression() instanceof Ast.FunctionLiteral) {
//                     Ast.FunctionLiteral func = (Ast.FunctionLiteral) varStmt.getExpression();
//                     if (isInlineableFunction(func)) {
//                         inlineableFunctions.put(varStmt.getIdentifier().getName(), func);
//                     }
//                 }
//             }
//         }
        
//         // Step 2: Inline function calls with their body
//         for (Ast.Statement stmt : program.getStatements()) {
//             if (stmt instanceof Ast.ExpressionStatement) {
//                 Ast.ExpressionStatement exprStmt = (Ast.ExpressionStatement) stmt;
//                 inlineFunctionCalls(exprStmt.getExpression(), inlineableFunctions);
//             }
//         }
//     }

// // Check if a function is simple enough to be inlined (e.g., one return statement, no side effects)
// private boolean isInlineableFunction(Ast.FunctionLiteral func) {
//     List<Ast.Statement> bodyStatements = func.getBody().getStatements();
    
//     // Function can be inlined if it only has one return statement
//     return bodyStatements.size() == 1 && bodyStatements.get(0) instanceof Ast.ReturnStatement;
// }

// // Recursively inline function calls in expressions
// private void inlineFunctionCalls(Ast.Expression expr, Map<String, Ast.FunctionLiteral> inlineableFunctions) {
//     if (expr instanceof Ast.CallExpression) {
//         Ast.CallExpression callExpr = (Ast.CallExpression) expr;
//         String functionName = callExpr.getFunction().toString();
        
//         // Check if the function is inlineable
//         if (inlineableFunctions.containsKey(functionName)) {
//             Ast.FunctionLiteral func = inlineableFunctions.get(functionName);
//             Ast.ReturnStatement returnStmt = (Ast.ReturnStatement) func.getBody().getStatements().get(0);
            
//             // Inline the return expression from the function into the call
//             Ast.Expression inlinedExpr = returnStmt.getExpression();
            
//             // Replace the function call with the inlined expression
//             callExpr.replaceWith(inlinedExpr);
//         }
        
//         // Recursively inline arguments of the function call
//         for (Ast.Expression arg : callExpr.getArguments()) {
//             inlineFunctionCalls(arg, inlineableFunctions);
//         }
//     } else if (expr instanceof Ast.InfixExpression) {
//         // Recursively inline the left and right expressions in an infix operation
//         Ast.InfixExpression infixExpr = (Ast.InfixExpression) expr;
//         inlineFunctionCalls(infixExpr.getLeft(), inlineableFunctions);
//         inlineFunctionCalls(infixExpr.getRight(), inlineableFunctions);
//     } else if (expr instanceof Ast.PrefixExpression) {
//         // Recursively inline the operand in a prefix operation
//         Ast.PrefixExpression prefixExpr = (Ast.PrefixExpression) expr;
//         inlineFunctionCalls(prefixExpr.getOperand(), inlineableFunctions);
//     }
// }

}
