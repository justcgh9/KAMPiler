package org.projectD.interpreter.ast;

import java.util.Map;

import org.projectD.interpreter.ast.Ast.ArrayLiteral;
import org.projectD.interpreter.ast.Ast.BlockStatement;
import org.projectD.interpreter.ast.Ast.BooleanLiteral;
import org.projectD.interpreter.ast.Ast.CallExpression;
import org.projectD.interpreter.ast.Ast.Expression;
import org.projectD.interpreter.ast.Ast.ExpressionStatement;
import org.projectD.interpreter.ast.Ast.ForLiteral;
import org.projectD.interpreter.ast.Ast.FunctionLiteral;
import org.projectD.interpreter.ast.Ast.IntegerLiteral;
import org.projectD.interpreter.ast.Ast.PrefixExpression;
import org.projectD.interpreter.ast.Ast.PrintLiteral;
import org.projectD.interpreter.ast.Ast.Program;
import org.projectD.interpreter.ast.Ast.RealLiteral;
import org.projectD.interpreter.ast.Ast.ReturnStatement;
import org.projectD.interpreter.ast.Ast.TypeLiteral;
import org.projectD.interpreter.ast.Ast.VarStatement;
import org.projectD.interpreter.ast.Ast.WhileStatement;
import org.projectD.interpreter.ast.Ast.Identifier;
import org.projectD.interpreter.ast.Ast.IfStatement;
import org.projectD.interpreter.ast.Ast.IndexLiteral;
import org.projectD.interpreter.ast.Ast.InfixExpression;
import org.projectD.interpreter.ast.Ast.Statement;
import org.projectD.interpreter.ast.Ast.StringLiteral;
import org.projectD.interpreter.ast.Ast.TupleLiteral;

interface NodeVisitor {
    void visit(Program program);
    void visit(VarStatement varStatement);
    void visit(ReturnStatement returnStatement);
    void visit(ExpressionStatement expressionStatement);
    void visit(BlockStatement blockStatement);
    void visit(ForLiteral forLiteral);
    void visit(WhileStatement whileStatement);
    void visit(Identifier identifier);
    void visit(BooleanLiteral booleanLiteral);
    void visit(IntegerLiteral integerLiteral);
    void visit(TypeLiteral typeLiteral);
    void visit(RealLiteral realLiteral);
    void visit(StringLiteral stringLiteral);
    void visit(PrefixExpression prefixExpression);
    void visit(InfixExpression infixExpression);
    void visit(IfStatement ifStatement);
    void visit(FunctionLiteral functionLiteral);
    void visit(CallExpression callExpression);
    void visit(ArrayLiteral arrayLiteral);
    void visit(IndexLiteral indexLiteral);
    void visit(TupleLiteral tupleLiteral);
    void visit(PrintLiteral printLiteral);
}

public class TreePrinter implements NodeVisitor{
    private int indentLevel = 0;

    private void printIndent() {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  "); // Indent by 2 spaces for each level
        }
    }

    private void printLine(String line) {
        printIndent();
        System.out.println(line);
    }

    @Override
    public void visit(Program program) {
        printLine("Program");
        indentLevel++;
        for (Statement s : program.getStatements()) {
            s.accept(this); // Each statement accepts the visitor
        }
        indentLevel--;
    }

    @Override
    public void visit(VarStatement varStatement) {
        printLine("VarStatement: " + varStatement.name.toString());
        indentLevel++;
        if (varStatement.value != null) {
            printLine("Value:");
            varStatement.value.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        printLine("ReturnStatement");
        indentLevel++;
        if (returnStatement.returnValue != null) {
            returnStatement.returnValue.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {
        printLine("ExpressionStatement");
        indentLevel++;
        expressionStatement.expression.accept(this);
        indentLevel--;
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        printLine("BlockStatement");
        indentLevel++;
        for (Statement s : blockStatement.statements) {
            s.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(ForLiteral forLiteral) {
        printLine("ForLiteral");
        indentLevel++;
        forLiteral.loopVariable.accept(this);
        forLiteral.typeIndicator.accept(this);
        forLiteral.loopBody.accept(this);
        indentLevel--;
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        printLine("WhileStatement");
        indentLevel++;
        whileStatement.predicate.accept(this);
        whileStatement.loopBody.accept(this);
        indentLevel--;
    }

    @Override
    public void visit(Identifier identifier) {
        printLine("Identifier: " + identifier.toString());
    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        printLine("BooleanLiteral: " + booleanLiteral.toString());
    }

    @Override
    public void visit(IntegerLiteral integerLiteral) {
        printLine("IntegerLiteral: " + integerLiteral.toString());
    }

    @Override
    public void visit(TypeLiteral typeLiteral) {
        printLine("TypeLiteral: " + typeLiteral.toString());
    }

    public void visit(RealLiteral realLiteral) {
        printLine("RealLiteral: " + realLiteral.toString());
    }

    @Override
    public void visit(StringLiteral stringLiteral) {
        printLine("StringLiteral: " + stringLiteral.toString());
    }

    @Override
    public void visit(PrefixExpression prefixExpression) {
        printLine("PrefixExpression: ");
        indentLevel++;
        if (prefixExpression.right != null) {
            prefixExpression.right.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(InfixExpression infixExpression) {
        printLine("InfixExpression: " );
        indentLevel++;
        if (infixExpression.left != null) {
            infixExpression.left.accept(this);
        }
        if (infixExpression.right != null) {
            infixExpression.right.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(IfStatement ifStatement) {
        printLine("IfStatement: ");
        ifStatement.predicate.accept(this);
        indentLevel++;
        ifStatement.ifBlock.accept(this);
        indentLevel--;
        if (ifStatement.elseBlock != null) {
            printLine("ElseBlock");
            indentLevel++;
            ifStatement.elseBlock.accept(this);
            indentLevel--;
        }
    }

    @Override
    public void visit(FunctionLiteral functionLiteral) {
        printLine("FunctionLiteral: ");
        indentLevel++;
        for (Identifier param : functionLiteral.parameters) {
            param.accept(this);
        }
        functionLiteral.body.accept(this);
        indentLevel--;
    }

    @Override
    public void visit(CallExpression callExpression) {
        printLine("CallExpression: ");
        indentLevel++;
        callExpression.function.accept(this);
        for (Expression arg : callExpression.arguments) {
            arg.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(ArrayLiteral arrayLiteral) {
        printLine("ArrayLiteral: ");
        indentLevel++;
        for (Expression element : arrayLiteral.elements) {
            element.accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(IndexLiteral indexLiteral) {
        printLine("IndexLiteral: ");
        indentLevel++;
        indexLiteral.left.accept(this);
        indexLiteral.index.accept(this);
        indentLevel--;
    }

    @Override
    public void visit(TupleLiteral tupleLiteral) {
        printLine("TupleLiteral: ");
        indentLevel++;
        for (Map.Entry<Expression, Expression> entry : tupleLiteral.getPairs().entrySet()) {
            entry.getKey().accept(this);
            entry.getValue().accept(this);
        }
        indentLevel--;
    }

    @Override
    public void visit(PrintLiteral printLiteral) {
        printLine("PrintLiteral: ");
        indentLevel++;
        for (Expression arg : printLiteral.arguments) {
            arg.accept(this);
        }
        indentLevel--;
    }
}
