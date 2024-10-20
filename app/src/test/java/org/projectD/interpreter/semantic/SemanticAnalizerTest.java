package org.projectD.interpreter.semantic;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.projectD.interpreter.token.TokenType;
import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.ast.Ast;
import org.projectD.interpreter.lexer.ParserLexer;
import org.projectD.interpreter.parser.Parser;

class SemanticAnalizerTest {
    public Ast.Program getRoot(String input) throws IOException {
        var lexer = new ParserLexer(input);
        var parser = new Parser(lexer);
        parser.parse();
		return (Ast.Program)parser.getRoot();
	}

    @Test
    void testVarWhenVariableExists() throws IOException {
        var root = this.getRoot("var a := 1 + 2; var a := 1 + 2;");
        var analyzer = new SemanticAnalyzer();

        var exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(root);
        });
        Assertions.assertEquals("a is redefined in this scope", exp.getMessage());
    }

    @Test
    void testVarWhenVariableExistsInFunction() throws IOException {
        var root = this.getRoot("func () is var a := 1 + 2; var a := 1 + 2; return; end;");
        var analyzer = new SemanticAnalyzer();

        var exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(root);
        });
        Assertions.assertEquals("a is redefined in this scope", exp.getMessage());
    }

    @Test
    void testDivisionByZero() throws IOException {
        var root = this.getRoot("1 / 0;");
        var analyzer = new SemanticAnalyzer();

        var exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(root);
        });
        Assertions.assertEquals("Division by 0", exp.getMessage());
    }

    @Test
    void testUnsuppStringOperation() throws IOException {
        var root = this.getRoot("\"test\" - \"test\";");
        var analyzer = new SemanticAnalyzer();

        var exp = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            analyzer.analyze(root);
        });
        Assertions.assertEquals("Illigal operation for strings", exp.getMessage());
    }

    @Test
    void testUnsuppBoolOperation() throws IOException {
        var root = this.getRoot("true - true;");
        var analyzer = new SemanticAnalyzer();

        var exp = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            analyzer.analyze(root);
        });
        Assertions.assertEquals("Illigal operation for bools", exp.getMessage());
    }

    @Test
    void testUsedVariablesRemoved() throws IOException {
        var root = this.getRoot("var a := true;");
        var analyzer = new SemanticAnalyzer();

        analyzer.analyze(root);

        Assertions.assertEquals(0, root.getStatements().size());
    }

    @Test
    void testSimplifyBoolOperation() throws IOException {
        var root = this.getRoot("var a := true = true; a = false;");
        var expectedExpr = new Ast.BooleanLiteral(new Token("true", TokenType.TRUE), "true");
        var expectedStmt = new Ast.VarStatement(new Ast.Identifier(new Token("a", TokenType.IDENT), "a"), expectedExpr);
        var analyzer = new SemanticAnalyzer();

        analyzer.analyze(root);

        var varStmt = (Ast.VarStatement)(root.getStatements().get(0));
        Assertions.assertEquals(expectedStmt, varStmt);
    }

    @Test
    void testSimplifyStringConcat() throws IOException {
        var root = this.getRoot("var a := \"one\" + \"two\" ; a = \"igmored\";");
        var expectedExpr = new Ast.StringLiteral(new Token("\"onetwo\"", TokenType.STRING), "\"onetwo\"");
        var expectedStmt = new Ast.VarStatement(new Ast.Identifier(new Token("a", TokenType.IDENT), "a"), expectedExpr);
        var analyzer = new SemanticAnalyzer();

        analyzer.analyze(root);

        var varStmt = (Ast.VarStatement)(root.getStatements().get(0));
        Assertions.assertEquals(expectedStmt, varStmt);
    }

    @Test
    void testSimplifyRealArithmetic() throws IOException {
        var root = this.getRoot("var a := 1 + 2.3; a = 1;");
        var expectedExpr = new Ast.RealLiteral(new Token("3.3", TokenType.REAL), "3.3");
        var expectedStmt = new Ast.VarStatement(new Ast.Identifier(new Token("a", TokenType.IDENT), "a"), expectedExpr);
        var analyzer = new SemanticAnalyzer();

        analyzer.analyze(root);

        var varStmt = (Ast.VarStatement)(root.getStatements().get(0));
        Assertions.assertEquals(expectedStmt, varStmt);
    }

    @Test
    void testSimplifyIntegerArithmetic() throws IOException {
        var root = this.getRoot("var a := 1 + 2; a = 1;");
        var expectedExpr = new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3");
        var expectedStmt = new Ast.VarStatement(new Ast.Identifier(new Token("a", TokenType.IDENT), "a"), expectedExpr);
        var analyzer = new SemanticAnalyzer();

        analyzer.analyze(root);

        var varStmt = (Ast.VarStatement)(root.getStatements().get(0));
        Assertions.assertEquals(expectedStmt, varStmt);
    }
}