package org.projectD.interpreter.lexer;

import org.projectD.interpreter.lexer.Lexer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.projectD.interpreter.token.TokenType;


public class LexerTest {
    static Stream<Arguments> keywordProvider() {
        return Stream.of(
            arguments("func", TokenType.FUNCTION),
            arguments("var", TokenType.VAR),
            arguments("true", TokenType.TRUE),
            arguments("false", TokenType.FALSE),
            arguments("if", TokenType.IF),
            arguments("else", TokenType.ELSE),
            arguments("return", TokenType.RETURN),
            arguments("end", TokenType.END),
            arguments("for", TokenType.FOR),
            arguments("while", TokenType.WHILE),
            arguments("do", TokenType.DO),
            arguments("loop", TokenType.LOOP),
            arguments("in", TokenType.IN),
            arguments("is", TokenType.IS)
        );
    }

    static Stream<Arguments> operationProvider() {
        return Stream.of(
            arguments(":=", TokenType.ASSIGN),
            arguments("+", TokenType.PLUS),
            arguments("-", TokenType.MINUS),
            arguments("!", TokenType.BANG),
            arguments("*", TokenType.ASTERISK),
            arguments("/", TokenType.SLASH),
            arguments(".", TokenType.DOT),
            arguments("<", TokenType.LT),
            arguments(">", TokenType.GT),
            arguments(">=", TokenType.GEQ),
            arguments("<=", TokenType.LEQ),
            arguments("=>", TokenType.ARROW),
            arguments("=", TokenType.EQ),
            arguments("!=", TokenType.NOT_EQ)
        );
    }

    static Stream<Arguments> delimiterProvider() {
        return Stream.of(
            arguments(",", TokenType.COMMA),
            arguments(";", TokenType.SEMICOLON),
            arguments(":", TokenType.COLON),
            arguments("\n", TokenType.NEWLINE),
            arguments("(", TokenType.LPAREN),
            arguments(")", TokenType.RPAREN),
            arguments("{", TokenType.LBRACE),
            arguments("}", TokenType.RBRACE),
            arguments("[", TokenType.LBRACKET),
            arguments("]", TokenType.RBRACKET)
        );
    }

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

    @ParameterizedTest
    @MethodSource("keywordProvider")
    void testKeywordIsKeyrwordToken(String value, TokenType expectedTokenType) {
        var token = new Lexer(value).nextToken();
        Assertions.assertEquals(expectedTokenType, token.gTokenType());
    }

    @ParameterizedTest
    @MethodSource("operationProvider")
    void testOperationIsOperationToken(String value, TokenType expectedTokenType) {
        var token = new Lexer(value).nextToken();
        Assertions.assertEquals(expectedTokenType, token.gTokenType());
    }

    @ParameterizedTest
    @MethodSource("delimiterProvider")
    void testDelimiterIsDelimiterToken(String value, TokenType expectedTokenType) {
        var token = new Lexer(value).nextToken();
        Assertions.assertEquals(expectedTokenType, token.gTokenType());
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