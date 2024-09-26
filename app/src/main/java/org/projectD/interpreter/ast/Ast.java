package org.projectD.interpreter.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import org.projectD.interpreter.token.Token;

public class Ast {

    interface Node {
        String tokenLiteral();
        String toString();
    }

    public interface Statement extends Node {
        void statementNode();
    }

    public interface Expression extends Node {
        void expressionNode();
    }

    public class Program implements Node{
        List<Statement> statements;

        public Program(List<Statement> statements) {
            this.statements = statements;
        }

        public String tokenLiteral() {
            return this.statements.size() > 0 ? this.statements.get(0).tokenLiteral(): "";
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            for (Statement s : statements) {
                out.append(s.toString());
            }

            return out.toString();
        }
    }

    public class VarStatement implements Statement {
        Token token;
        Identifier name;
        Expression value;

        public void statementNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out.append(token.gLiteral()).append(" ");
            out.append(name.toString());
            out.append(" = ");

            if (Objects.nonNull(value)) {
                out.append(value.toString());
            }

            out.append(";");

            return out.toString();
        }

    }

    public class ReturnStatement implements Statement {
        Token token;
        Expression returnValue;

        public void statementNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out.append(token.gLiteral()).append(" ");

            if (Objects.nonNull(returnValue)) {
                out.append(returnValue.toString());
            }

            out.append(";");

            return out.toString();
        }

    }

    public class ExpressionStatement implements Statement {
        Token token; // I suggest to store the first token of the expression
        Expression expression;

        public ExpressionStatement(Token token, Expression expression) {
            this.token = token;
            this.expression = expression;
        }

        public void statementNode() {};
        
        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            return Objects.nonNull(expression) ? this.expression.toString(): "";
        }
        
    }

    public class BlockStatement implements Statement {
        Token token;
        List<Statement> statements;
        
        public void statementNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();
            
            for(Statement s: this.statements) {
                out.append(s.toString());
            }
            
            return out.toString();
        }
        
    }
    
    public class ForLiteral implements Statement {
        Token token;
        BlockStatement loopBody;
        Identifier  loopVariable;
        Expression typeIndicator;

        public void statementNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();
            
            out
            .append("for")
            .append(loopVariable.toString())
            .append("in")
            .append(typeIndicator.toString())
            .append("loop")
            .append(loopBody.toString())
            .append("end");

            
            return out.toString();
        }
    }

    public class WhileLiteral implements Statement {
        Token token;
        Expression predicate;
        BlockStatement loopBody;

        public void statementNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();
            
            out
            .append("while")
            .append(predicate.toString())
            .append("loop")
            .append(loopBody.toString())
            .append("end");

            
            return out.toString();
        }
    }

    public class Identifier implements Expression {
        Token token;
        String value;

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return value;
        }
    }

    public class BooleanLiteral implements Expression {
        Token token;
        String value;

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }
    }

    public class IntegerLiteral implements Expression {
        Token token;
        String value;

        public IntegerLiteral(Token token, String value) {
            this.token = token;
            this.value = value;
        }

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }
    }

    public class RealLiteral implements Expression {
        Token token;
        String value;

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            return this.token.gLiteral();
        }
    }

    public class StringLiteral implements Expression {
        Token token;
        String value;
        
        public void expressionNode() {};
        
        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            return this.token.gLiteral();
        }
    }

    public class PrefixExpression implements Expression {
        Token token;
        String operator;
        Expression right;


        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
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
    }

    public class InfixExpression implements Expression {
        Token token;
        String operator;
        Expression left, right;

        public InfixExpression(Token token, String operator, Expression left, Expression right) {
            this.token = token;
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
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
    }

    public class IfExpression implements Expression {
        Token token;
        Expression predicate;
        BlockStatement ifBlock, elseBlock;


        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();

            out
            .append("if")
            .append(this.predicate.toString())
            .append(" ")
            .append(this.ifBlock.toString());

            if(Objects.nonNull(this.elseBlock)) {
                out
                .append("else ")
                .append(this.elseBlock.toString());
            }

            return out.toString();
        }
    }

    public class FunctionLiteral implements Expression {
        Token token;
        BlockStatement body;
        List<Identifier> parameters;
        
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

        public void expressionNode() {};
    
        public String tokenLiteral() {
            return this.token.gLiteral();
        }
    }

    public class CallExpression implements Expression {
        Token token;
        Expression function;
        List<Expression> arguments;

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for(Expression a: this.arguments) {
                joiner.add(a.toString());
            }

            out
            .append(this.function.toString())
            .append("(")
            .append(joiner.toString())
            .append(")");

            return out.toString();
        }
    }

    public class ArrayLiteral implements Expression {
        Token token;
        List<Expression> elements;

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }
        
        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for(Expression a: this.elements) {
                joiner.add(a.toString());
            }

            out
            .append("(")
            .append(joiner.toString())
            .append(")");

            return out.toString();
        }
    }

    public class IndexLiteral implements Expression {
        Token token;
        Expression left, index;

        public void expressionNode() {};

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
    }

    public class TupleLiteral implements Expression {
        Token token;
        Map<Expression, Expression> pairs;

        public void expressionNode() {};

        public String tokenLiteral() {
            return this.token.gLiteral();
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            StringJoiner joiner = new StringJoiner(", ");

            for (Map.Entry<Expression, Expression> entry: this.pairs.entrySet()) {
                joiner.add(entry.getKey().toString() + ":" + entry.getValue().toString());
            }

            out
            .append("{")
            .append(joiner.toString())
            .append("}");
            

            return out.toString();
        }
    }

    public class PrintLiteral implements Expression {
        Token token;
        List<Expression> arguments;


        public void expressionNode() {};

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
            .append("print(")
            .append(joiner.toString())
            .append(")");
            

            return out.toString();
        }
    }

}
