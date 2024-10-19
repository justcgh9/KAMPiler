package org.projectD.interpreter.semantic;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.Arguments;
// import org.junit.jupiter.params.provider.MethodSource;
// import static org.junit.jupiter.params.provider.Arguments.arguments;

// import org.projectD.interpreter.token.TokenType;
// import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.ast.Ast;
import org.projectD.interpreter.lexer.ParserLexer;
import org.projectD.interpreter.parser.Parser;

class SemanticAnalizerTest {
    public Ast.Node getRoot(String input) throws IOException {
        var lexer = new ParserLexer(input);
        var parser = new Parser(lexer);
        parser.parse();
		return parser.getRoot();
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
}