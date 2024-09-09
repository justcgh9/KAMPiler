import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.projectD.interpreter.lexer.Lexer;
import org.projectD.interpreter.token.TokenType;


public class LexerTest {
    @Test
    void testNotAllowedSymbol() {
        var token = new Lexer("п").nextToken();
        Assertions.assertEquals(TokenType.ILLEGAL, token.gTokenType());
        Assertions.assertEquals("п", token.gLiteral());
    }

    @Test
    void testNotAllowedOperator() {
        var token = new Lexer("~").nextToken();
        Assertions.assertEquals(TokenType.ILLEGAL, token.gTokenType());
        Assertions.assertEquals("~", token.gLiteral());
    }

    @Test
    void testSignleCharIsIdentToken() {
        var token = new Lexer("a").nextToken();
        Assertions.assertEquals(TokenType.IDENT, token.gTokenType());
        Assertions.assertEquals("a", token.gLiteral());
    }

    @Test
    void testMultipleCharsIsIndetToken() {
        var token = new Lexer("abc").nextToken();
        Assertions.assertEquals(TokenType.IDENT, token.gTokenType());
        Assertions.assertEquals("abc", token.gLiteral());
    }

    @Test
    void testKeywordIsKeyrwordToken() {
        var token = new Lexer("func").nextToken();
        Assertions.assertEquals(TokenType.FUNCTION, token.gTokenType());
        Assertions.assertEquals("func", token.gLiteral());
    }

    @Test
    void testOperationIsOperationToken() {
        var token = new Lexer("+").nextToken();
        Assertions.assertEquals(TokenType.PLUS, token.gTokenType());
        Assertions.assertEquals("+", token.gLiteral());
    }

    @Test
    void testDelimiterIsDelimiterToken() {
        var token = new Lexer("(").nextToken();
        Assertions.assertEquals(TokenType.LPAREN, token.gTokenType());
        Assertions.assertEquals("(", token.gLiteral());
    }

    @Test
    void testLiteralReal() {
        var token = new Lexer("3.14").nextToken();
        Assertions.assertEquals(TokenType.REAL, token.gTokenType());
        Assertions.assertEquals("3.14", token.gLiteral());
    }

    @Test
    void testLiteralNumber() {
        var token = new Lexer("332").nextToken();
        Assertions.assertEquals(TokenType.INT, token.gTokenType());
        Assertions.assertEquals("332", token.gLiteral());
    }
    
    @Test
    void testLiteralString() {
        var token = new Lexer("\"str\"").nextToken();
        Assertions.assertEquals(TokenType.STRING, token.gTokenType());
        Assertions.assertEquals("str", token.gLiteral());
    }
}