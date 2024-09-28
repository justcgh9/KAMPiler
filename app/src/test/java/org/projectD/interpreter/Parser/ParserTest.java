package org.projectD.interpreter.parser;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.projectD.interpreter.token.TokenType;
import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.parser.ParserLexer;
import org.projectD.interpreter.parser.Parser;
import org.projectD.interpreter.ast.Ast;

public class ParserTest {
    static Stream<Arguments> expressionProvider() {
        return Stream.of(
            arguments("1;", "1", (new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"))),
            arguments("1.1;", "1.1", (new Ast.RealLiteral(new Token("1.1", TokenType.REAL), "1.1"))), 
            arguments(
                "\"string\";",
                "\"string\"", 
                new Ast.StringLiteral(new Token("\"string\"", TokenType.STRING), "\"string\"")
            ),
            arguments(
                "1 + 2;",
                "(1 + 2)",
                new Ast.InfixExpression(
                    "+", 
                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                )
            ),
            arguments(
                "1 + 2.3;",
                "(1 + 2.3)",
                new Ast.InfixExpression(
                    "+", 
                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                    new Ast.RealLiteral(new Token("2.3", TokenType.REAL), "2.3")
                )
            ),
            arguments(
                "1 + 2 * 3;", 
                "(1 + (2 * 3))",
                new Ast.InfixExpression(
                    "+", 
                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                    new Ast.InfixExpression(
                        "*", 
                        new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2"),
                        new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3")
                    )
                )
            ),
            arguments(
                "(1 + 2) * 3;", 
                "((1 + 2) * 3)",
                new Ast.InfixExpression(
                    "*",
                    new Ast.InfixExpression(
                        "+", 
                        new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                        new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                    ), 
                    new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3")
                )
            )
        );
    }

    static Stream<Arguments> statementProvider() {
        return Stream.of(
            arguments(
                "if 3 = 2 then 1 + 2; end;", 
                "if(3 = 2){\n(1 + 2)\n}",
                new Ast.IfStatement(
                    new Ast.InfixExpression(
                        "=",
                        new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3"),
                        new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                    ),
                    new Ast.BlockStatement(
                        Arrays.asList(
                            new Ast.ExpressionStatement(
                                new Ast.InfixExpression(
                                    "+", 
                                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                                ) 
                            )
                        )
                    )
                )
            ),
            arguments(
                "if 3 = 2 then 1 + 2; else 1 + 3; end;", 
                "if(3 = 2){\n(1 + 2)\n}else{\n(1 + 3)\n}",
                new Ast.IfStatement(
                    new Ast.InfixExpression(
                        "=",
                        new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3"),
                        new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                    ),
                    new Ast.BlockStatement(
                        Arrays.asList(
                            new Ast.ExpressionStatement(
                                new Ast.InfixExpression(
                                    "+", 
                                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                                ) 
                            )
                        )
                    ),
                    new Ast.BlockStatement(
                        Arrays.asList(
                            new Ast.ExpressionStatement(
                                new Ast.InfixExpression(
                                    "+", 
                                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                                    new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3")
                                ) 
                            )
                        )
                    )
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("expressionProvider")
    void testExpression(String literal, String expectedStr, Ast.Expression expectedExpr) throws IOException {
        var lexer = new ParserLexer(literal);
        var parser = new Parser(lexer);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(true, issuccess);
        Assertions.assertEquals(root.toString(), expectedStr);
        Assertions.assertEquals(root, new Ast.Program(Arrays.asList(new Ast.ExpressionStatement(expectedExpr))));
    }

    @ParameterizedTest
    @MethodSource("statementProvider")
    void testStatement(String statement, String expectedStr, Ast.Statement expectedStmt) throws IOException {
        var lexer = new ParserLexer(statement);
        var parser = new Parser(lexer);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(true, issuccess);
        Assertions.assertEquals(root.toString(), expectedStr);
        Assertions.assertEquals(root, new Ast.Program(Arrays.asList(expectedStmt)));
    }
}
