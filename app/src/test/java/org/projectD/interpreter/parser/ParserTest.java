package org.projectD.interpreter.parser;

import java.util.stream.Stream;
import java.util.Arrays;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.projectD.interpreter.token.TokenType;
import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.ast.Ast;
import org.projectD.interpreter.lexer.ParserLexer;

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
            ),
            arguments(
                "func (a) is 1 + 2; return; end;", 
                "func(a) {\n(1 + 2)return ;\n}",
                new Ast.FunctionLiteral(
                    Arrays.asList(
                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a")
                    ),
                    new Ast.BlockStatement(
                        Arrays.asList(
                            new Ast.ExpressionStatement(
                                new Ast.InfixExpression(
                                    "+", 
                                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                                ) 
                            ),
                            new Ast.ReturnStatement(null)
                        )
                    )
                )
            ),
            arguments(
                "func (a) is 1 + 2; return 1 + 2; end;", 
                "func(a) {\n(1 + 2)return (1 + 2);\n}",
                new Ast.FunctionLiteral(
                    Arrays.asList(
                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a")
                    ),
                    new Ast.BlockStatement(
                        Arrays.asList(
                            new Ast.ExpressionStatement(
                                new Ast.InfixExpression(
                                    "+", 
                                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                                ) 
                            ),
                            new Ast.ReturnStatement(
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
                "func (a) => 1 + 2;;", 
                "func(a) {\nreturn (1 + 2);\n}",
                new Ast.FunctionLiteral(
                    Arrays.asList(
                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a")
                    ),
                    new Ast.BlockStatement(
                        Arrays.asList(
                            new Ast.ReturnStatement(
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
            arguments("true;", "true", (new Ast.BooleanLiteral(new Token("true", TokenType.TRUE), "true"))),
            arguments("false;", "false", (new Ast.BooleanLiteral(new Token("false", TokenType.FALSE), "false")))
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
            ),
            arguments(
                "print 1 + 2;",
                "print (1 + 2);",
                new Ast.PrintLiteral(
                    Arrays.asList(
                        new Ast.InfixExpression(
                            "+", 
                            new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                            new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                        ) 
                    )
                )
            ),
            arguments(
                "var a := 2;",
                "var a = 2;",
                new Ast.VarStatement(
                    new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2"))
            ),
            arguments(
                "while 3 = 2 loop 1 + 2; end;", 
                "while(3 = 2)loop{\n(1 + 2)\n}end",
                new Ast.WhileStatement(
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
            )
        );
    }

    static Stream<Arguments> badSyntaxProvider() {
        return Stream.of(
            // no expression
            arguments("1 g 2;"), 
            // no semicolumn
            arguments("1 + 2")
        );
    }

    @ParameterizedTest
    @MethodSource("badSyntaxProvider")
    void testBadSyntax(String badSyntax) throws IOException {
        var lexer = new ParserLexer(badSyntax);
        var parser = new Parser(lexer);
        
        Assertions.assertEquals(parser.parse(), false);
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
