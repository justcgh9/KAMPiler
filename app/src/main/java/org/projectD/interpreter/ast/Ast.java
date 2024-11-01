package org.projectD.interpreter.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.token.TokenType;

public class Ast {

    public interface Node {
        String tokenLiteral();
        void accept(TreePrinter visitor);
        String toString();
    }

    public interface Statement extends Node {
        void statementNode();
    }

    public interface Expression extends Node {
        void expressionNode();
    }

    public static class Program implements Node {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        List<Statement> statements;

        public Program(List<Statement> statements) {
            this.statements = statements;
        }

        public List<Statement> getStatements() {
            return this.statements;
        }

        public void addStatement(Statement statement) {
            this.statements.add(statement);
        }

        public void setStatements(List<Statement> statements) {
            this.statements = statements;
        }

        public void replaceWith(Statement stmt, List<Statement> statements) {
            for (int i = 0; i < this.statements.size(); i++ ) {
                
                if (this.statements.get(i) != stmt) continue;

                this.statements.remove(i);
                for (int j = 0; j < statements.size(); j++ ) {
                    this.statements.add(j + i, statements.get(j));
                }
            }
        }

        public String tokenLiteral() {
            return this.statements.size() > 0 ? this.statements.get(0).tokenLiteral() : "";
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            for (Statement s : statements) {
                out.append(s.toString());
            }

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var program = (Program) obj;
            return this.statements.equals(program.statements);
        }
    }

    public static class VarStatement implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        Identifier name;
        Expression value;

        public VarStatement(Identifier name, Expression value) {
            this.token = new Token("var", TokenType.VAR);
            this.name = name;
            this.value = value;
        }

        public void statementNode() {
        };

        public Identifier getIdentifier() {
            return this.name;
        }

        public Expression getExpression() {
            return this.value;
        }

        public void setExpression(Expression expr) {
            this.value = expr;
        }

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out.append(token.gLiteral()).append(" ");
            out.append(name.toString());
            
            if (Objects.nonNull(value)) {
                out.append(" = ");
                out.append(value.toString());
            }

            out.append(";\n");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var statement = (VarStatement) obj;
            return (this.name.equals(statement.name) && this.value.equals(statement.value));
        }
    }

    public static class ReturnStatement implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        Expression returnValue;

        public ReturnStatement(Expression returnValue) {
            this.token = new Token("return", TokenType.RETURN);
            this.returnValue = returnValue;
        }

        public void statementNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public void setExpression(Expression expression) {
            this.returnValue = expression;
        }

        public Expression getExpression() {
            return this.returnValue;
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out.append(token.gLiteral()).append(" ");

            if (Objects.nonNull(returnValue)) {
                out.append(returnValue.toString());
            }

            out.append(";\n");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var statement = (ReturnStatement) obj;

            if (this.returnValue == null) {
                return this.returnValue == null;
            }

            return this.returnValue.equals(statement.returnValue);
        }
    }

    public static class ExpressionStatement implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Expression expression;

        public ExpressionStatement(Expression expression) {
            this.expression = expression;
        }

        public void statementNode() {};
        
        public String tokenLiteral() {
            return "expression statement";
        }

        public Expression getExpression() {
            return this.expression;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }
        
        public String toString() {
            return Objects.nonNull(expression) ? this.expression.toString() + ";\n" : "";
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var statement = (ExpressionStatement) obj;
            return this.expression.equals(statement.expression);
        }
    }

    public static class BlockStatement implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        List<Statement> statements;
        
        public BlockStatement(List<Statement> statements) {
            this.statements = statements;
        }

        public void addStatement(Statement statement) {
            this.statements.add(statement);
        }

        public List<Statement> getStatements() {
            return this.statements;
        }

        public void setStatements(List<Statement> statements) {
            this.statements = statements;
        }

        public void statementNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public void replaceWith(Statement stmt, List<Statement> statements) {
            for (int i = 0; i < this.statements.size(); i++ ) {
                
                if (this.statements.get(i) != stmt) continue;

                this.statements.remove(i);
                for (int j = 0; j < statements.size(); j++ ) {
                    this.statements.add(j + i, statements.get(j));
                }
            }
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();

            out.append("{\n");
            
            for(Statement s: this.statements) {
                out.append("    ");
                out.append(s.toString());
            }

            out.append("} ");
            
            return out.toString();
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var statement = (BlockStatement) obj;
            return this.statements.equals(statement.statements);
        }
    }
    
    public static class ForLiteral implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        BlockStatement loopBody;
        Identifier  loopVariable;
        Expression typeIndicator;
        
        public ForLiteral(Identifier loopVariable, Expression typeIndicator, BlockStatement loopBody) {
            this.typeIndicator = typeIndicator;
            this.loopVariable = loopVariable;
            this.loopBody = loopBody;
        }

        public Identifier getLoopVariable() {
            return this.loopVariable;
        }

        public Expression getTypeIndicator() {
            return this.typeIndicator;
        }

        public BlockStatement getBody() {
            return this.loopBody;
        }

        public void statementNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();
            
            out
            .append("for ")
            .append(loopVariable.toString())
            .append(" in ")
            .append(typeIndicator.toString())
            .append(" loop ")
            .append(loopBody.toString())
            .append("end;\n");
            
            return out.toString();
        } 

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (ForLiteral) obj;
            return (
                this.loopBody.equals(literal.loopBody)
                && this.loopVariable.equals(literal.loopVariable)
                && this.typeIndicator.equals(literal.typeIndicator)
            );
        }
    }

    public static class WhileStatement implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Expression predicate;
        BlockStatement loopBody;

        public WhileStatement(Expression predicate, BlockStatement loopBody) {
            this.predicate = predicate;
            this.loopBody = loopBody;
        }

        public void statementNode() {};

        public String tokenLiteral() {
            return "while statement";
        }

        public void setCondition(Expression expression) {
            this.predicate = expression;
        }

        public Expression getCondition() {
            return this.predicate;
        }

        public BlockStatement getBody(){
            return this.loopBody;
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();
            
            out
            .append("while")
            .append(predicate.toString())
            .append("loop")
            .append(loopBody.toString())
            .append("end;\n");

            
            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (WhileStatement) obj;
            return this.loopBody.equals(literal.loopBody) && this.predicate.equals(literal.predicate);
        }
    }

    public static class Identifier implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String value;

        public Identifier(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public String getName() {
            return this.value;
        }

        public Token getToken() {
            return this.token;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var ident = (Identifier) obj;
            return this.value.equals(ident.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static class BooleanLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String value;

        public BooleanLiteral(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public boolean getValue() {
            return Boolean.parseBoolean(this.value);
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (BooleanLiteral) obj;
            return this.value.equals(literal.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static class IntegerLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String value;

        public IntegerLiteral(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public int getValue() {
            return Integer.parseInt(this.value);
        }
        

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (IntegerLiteral) obj;
            return this.value.equals(literal.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static class TypeLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String value;

        public TypeLiteral(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (TypeLiteral) obj;
            return this.value.equals(literal.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static class RealLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String value;

        public RealLiteral(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            return this.token.gLiteral();
        }

        public double getValue() {
            return Double.parseDouble(this.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (RealLiteral) obj;
            return this.value.equals(literal.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static class StringLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String value;

        public StringLiteral(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var literal = (StringLiteral) obj;
            return this.value.equals(literal.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
    }

    public static class PrefixExpression implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        String operator;
        Expression right;
        

        public PrefixExpression(String operator) {
            this.operator = operator;
        }

        public PrefixExpression(String operator, Expression right) {
            this.operator = operator;
            this.right = right;
        }

        
        public void expressionNode() {
        };
        
        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public Expression getRight() {
            return this.right;
        }

        public String getOperator() {
            return this.operator;
        }
        
        public void setRight(Expression right) {
            this.right = right;
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out
                    .append("(")
                    .append(this.operator)
                    .append(this.right.toString())
                    .append(")");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var prefix = (PrefixExpression) obj;
            return this.operator.equals(prefix.operator) && this.right.equals(prefix.right);
        }
    }

    public static class InfixExpression implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        String operator;
        Expression left, right;

        public Expression getLeft() {
            return this.left;
        }

        public Expression getRight() {
            return this.right;
        }

        public String getOperator() {
            return this.operator;
        }
        
        public InfixExpression(String operator) {
            this.operator = operator;
        }

        public InfixExpression(String operator, Expression left, Expression right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public void setLeft(Expression left) {
            this.left = left;
        }

        public void setRight(Expression right) {
            this.right = right;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return "expression prefix";
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out
                    .append("(")
                    .append(this.left.toString())
                    .append(" " + this.operator + " ")
                    .append(this.right.toString())
                    .append(")");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var infix = (InfixExpression) obj;
            return (
                this.operator.equals(infix.operator) 
                && this.right.equals(infix.right)
                && this.left.equals(infix.left)
            );
        }
    }

    public static class IfStatement implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Expression predicate;
        BlockStatement ifBlock, elseBlock;

        public IfStatement(Expression predicate, BlockStatement ifBlock) {
            this.predicate = predicate;
            this.ifBlock = ifBlock;
        }

        public IfStatement(Expression predicate, BlockStatement ifBlock, BlockStatement elseBlock) {
            this.predicate = predicate;
            this.ifBlock = ifBlock;
            this.elseBlock = elseBlock;
        }

        public void setCondition(Expression expression) {
            this.predicate = expression;
        }

        public Expression getCondition() {
            return this.predicate;
        }

        public BlockStatement getThenBlock() {
            return this.ifBlock;
        }

        public BlockStatement getElseBlock() {
            return this.elseBlock;
        }

        public void statementNode() {
        };

        public String tokenLiteral() {
            return "if statement";
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out
                    .append("if")
                    .append(this.predicate.toString())
                    .append(this.ifBlock.toString());

            if (Objects.nonNull(this.elseBlock)) {
                out
                        .append("else")
                        .append(this.elseBlock.toString());
            }

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var ifstmt = (IfStatement) obj;

            var predIfBlock = this.predicate.equals(ifstmt.predicate) && this.ifBlock.equals(ifstmt.ifBlock);
            
            if (this.elseBlock == null) {
                if (ifstmt.elseBlock != null) {
                    return false;
                }
                return predIfBlock;
            } 

            return predIfBlock && this.elseBlock.equals(ifstmt.elseBlock);
        }
    }

    public static class FunctionLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        BlockStatement body;
        List<Identifier> parameters;

        public FunctionLiteral(List<Identifier> parameters) {
            this.token = new Token("func", TokenType.FUNCTION);
            this.parameters = parameters;
        }

        public FunctionLiteral(List<Identifier> parameters, BlockStatement body) {
            this.token = new Token("func", TokenType.FUNCTION);
            this.parameters = parameters;
            this.body = body;
            
        }

        public List<Identifier> getParameters() {
            return this.parameters;
        }

        public BlockStatement getBody() {
            return this.body;
        }

        public void setBody(BlockStatement body) {
            this.body = body;
        }

        public void addParameter(Identifier param) {
            this.parameters.add(param);
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();

            List<String> params = new ArrayList<>();
            for (Identifier p : this.parameters) {
                params.add(p.toString());
            }

            out.append(token.gLiteral());
            out.append("(");

            StringJoiner joiner = new StringJoiner(", ");
            for (String param : params) {
                joiner.add(param);
            }

            out.append(joiner.toString());
            out.append(") ");
            out.append(body.toString());

            return out.toString();
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var funcLiteral = (FunctionLiteral) obj;
            return this.parameters.equals(funcLiteral.parameters) && this.body.equals(funcLiteral.body);
        }
    }

    public static class CallExpression implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        Expression function;
        List<Expression> arguments;

        public CallExpression() {
            this.arguments = new ArrayList<>();
            this.function = null;
        }

        public CallExpression(Expression function) {
            this.arguments = new ArrayList<>();
            this.function = function;
        }

        public CallExpression(Expression function, List<Expression> arguments) {
            this.arguments = arguments;
            this.function = function;
        }

        public void addArgument(Expression argument) {
            this.arguments.add(argument);
        }

        public void addFunction(Expression function) {
            this.function = function;
        }

        public void setArguments(List<Expression> arguments) {
            this.arguments = arguments;
        }

        public Expression getFunction() {
            return this.function;
        }

        public List<Expression> getArguments() {
            return this.arguments;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for (Expression a : this.arguments) {
                joiner.add(a.toString());
            }

            out
                    .append(this.function.toString())
                    .append("(")
                    .append(joiner.toString())
                    .append(")");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var callExpr = (CallExpression) obj;
            return this.function.equals(callExpr.function) && this.arguments.equals(callExpr.arguments);
        }
    }

    public static class ArrayLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        List<Expression> elements;

        public void expressionNode() {
        };

        public List<Expression> getElements() {
            return this.elements;
        }

        public ArrayLiteral() {
            this.elements = new ArrayList<>();
        }
        
        public ArrayLiteral(List<Expression> elements) {
            this.elements = elements;
        }

        public void addExpression(Expression exp) {
            this.elements.add(exp);
        }

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for (Expression a : this.elements) {
                joiner.add(a.toString());
            }

            out
                    .append("(")
                    .append(joiner.toString())
                    .append(")");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var arrayLiteral = (ArrayLiteral) obj;
            return this.elements.equals(arrayLiteral.elements);
        }
    }

    public static class IndexLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        Expression left, index;

        public void expressionNode() {
        };

        public IndexLiteral(Expression index) {
            this.index = index;
        }

        public IndexLiteral(Expression index, Expression left) {
            this.index = index;
            this.left = left;
        }

        public void setLeft(Expression left) {
            this.left = left;
        }

        public void setIndex(Expression index) {
            this.index = index;
        }

        public void setToken(Token token) {
            this.token = token;
        }

        public Expression getLeft() {
            return this.left;
        }

        public Expression getIndex() {
            return this.index;
        }

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out
                    .append("(")
                    .append(this.left.toString())
                    .append("[")
                    .append(this.index.toString())
                    .append("])");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var indexLiteral = (IndexLiteral) obj;
            return this.left.equals(indexLiteral.left) && this.index.equals(indexLiteral.index);
        }
    }

    public static class TupleLiteral implements Expression {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        Map<Expression, Expression> pairs;
        int size;

        public TupleLiteral() {
            this.pairs = new HashMap<>();
            this.size = 0;
        }

        public TupleLiteral(Map<Expression, Expression> pairs) {
            this.pairs = pairs;
            this.size = pairs.size();
        }

        public Map<Expression, Expression> getPairs() {
            return this.pairs;
        }

        public void addExpression(Expression exp) {
            pairs.put(new IntegerLiteral(new Token(String.valueOf(size), TokenType.INT), String.valueOf(this.size)), exp);
            this.size++;
        }

        public void addAssignment(Identifier name, Expression exp) {
            pairs.put(name, exp);
            pairs.put(new IntegerLiteral(new Token(String.valueOf(size), TokenType.INT), String.valueOf(this.size)), exp);
            this.size++;
        }

        public void expressionNode() {
        };

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for (Map.Entry<Expression, Expression> entry : this.pairs.entrySet()) {
                joiner.add(entry.getKey().toString() + ":" + entry.getValue().toString());
            }

            out
                    .append("{")
                    .append(joiner.toString())
                    .append("}");

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var tupleLiteral = (TupleLiteral) obj;
            return this.pairs.equals(tupleLiteral.pairs);
        }
    }

    public static class PrintLiteral implements Statement {
        @Override
        public void accept(TreePrinter visitor) {
            visitor.visit(this);
        }

        Token token;
        List<Expression> arguments;

        public PrintLiteral(List<Expression> arguments) {
            this.token = new Token("print", TokenType.PRINT);
            this.arguments = arguments;
        }

        public void addArgument(Expression arg) {
            this.arguments.add(arg);
        }

        public void statementNode() {};

        public List<Expression> getArguments() {
            return this.arguments;
        }

        public void setArguments(List<Expression> expressions) {
            this.arguments = expressions;
        }

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for (Expression argument: this.arguments) {
                joiner.add(argument.toString());
            }

            out
            .append("print ")
            .append(joiner.toString())
            .append(";\n");
            

            return out.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            var printLiteral = (PrintLiteral) obj;
            return this.arguments.equals(printLiteral.arguments);
        }
    }

    public static class Semicolon implements Node {
        @Override
        public void accept(TreePrinter visitor) {
            
        }


        public Semicolon(){}

        @Override
        public String tokenLiteral() {
            return ";";
        }

    }



}
