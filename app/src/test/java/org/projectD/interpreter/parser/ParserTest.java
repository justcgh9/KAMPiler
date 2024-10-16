package org.projectD.interpreter.parser;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
            arguments("1;", (new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"))),
            arguments("1.1;", (new Ast.RealLiteral(new Token("1.1", TokenType.REAL), "1.1"))), 
            arguments(
                "\"string\";",
                new Ast.StringLiteral(new Token("\"string\"", TokenType.STRING), "\"string\"")
            ),
            arguments(
                "1 + 2;",
                new Ast.InfixExpression(
                    "+", 
                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                )
            ),
            arguments(
                "1 + 2.3;",
                new Ast.InfixExpression(
                    "+", 
                    new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                    new Ast.RealLiteral(new Token("2.3", TokenType.REAL), "2.3")
                )
            ),
            arguments(
                "1 + 2 * 3;", 
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
                "func (a) => 1 + 2;", 
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
            arguments("true;", (new Ast.BooleanLiteral(new Token("true", TokenType.TRUE), "true"))),
            arguments("false;", (new Ast.BooleanLiteral(new Token("false", TokenType.FALSE), "false"))),
            arguments(
                "[1, 2];", 
                new Ast.ArrayLiteral(
                    Arrays.asList(
                        new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                        new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                    )
                )
            )
        );
    }

    static Stream<Arguments> statementProvider() {
        return Stream.of(
            arguments(
                "if 3 = 2 then 1 + 2; end;", 
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
                new Ast.VarStatement(
                    new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                    new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2"))
            ),
            arguments(
                "while 3 = 2 loop 1 + 2; end;", 
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

    static Stream<Arguments> statementsProvider() {
        return Stream.of(
            arguments(
                "var f := func(x) => x; f(x);", 
                Arrays.asList(
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("f", TokenType.IDENT), "f"),
                        new Ast.FunctionLiteral(
                            Arrays.asList(
                                new Ast.Identifier(new Token("x", TokenType.IDENT), "x")
                            ),
                            new Ast.BlockStatement(
                                Arrays.asList(
                                    new Ast.ReturnStatement(
                                        new Ast.Identifier(new Token("x", TokenType.IDENT), "x")
                                    )
                                )
                            )
                        )
                    ),
                    new Ast.ExpressionStatement(
                        new Ast.CallExpression(
                            new Ast.Identifier(new Token("f", TokenType.IDENT), "f"),
                            Arrays.asList(
                                new Ast.Identifier(new Token("x", TokenType.IDENT), "x") 
                            )
                        )
                    )
                )
            ),
            arguments(
                "var a := 5; var b := 3; print a + b - b;",
                Arrays.asList(
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                        new Ast.IntegerLiteral(new Token("5", TokenType.INT), "5")
                    ),
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("b", TokenType.IDENT), "b"),
                        new Ast.IntegerLiteral(new Token("3", TokenType.INT), "3")
                    ),
                    new Ast.PrintLiteral(
                        Arrays.asList(
                            new Ast.InfixExpression(
                                "-", 
                                new Ast.InfixExpression(
                                    "+", 
                                    new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                                    new Ast.Identifier(new Token("b", TokenType.IDENT), "b")
                                ),
                                new Ast.Identifier(new Token("b", TokenType.IDENT), "b")
                            ) 
                        )
                    )            
                )
            ),
            arguments(
                "var f := func(a, b) is return a + b; end; print f(5, 3.2);", 
                Arrays.asList(
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("f", TokenType.IDENT), "f"),
                        new Ast.FunctionLiteral(
                            Arrays.asList(
                                new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                                new Ast.Identifier(new Token("b", TokenType.IDENT), "b")
                            ),
                            new Ast.BlockStatement(
                                Arrays.asList(
                                    new Ast.ReturnStatement(
                                        new Ast.InfixExpression(
                                            "+", 
                                            new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                                            new Ast.Identifier(new Token("b", TokenType.IDENT), "b")
                                        ) 
                                    )
                                )
                            )
                        )
                    ),
                    new Ast.PrintLiteral(
                        Arrays.asList(
                            new Ast.CallExpression(
                                new Ast.Identifier(new Token("f", TokenType.IDENT), "f"),
                                Arrays.asList(
                                    new Ast.IntegerLiteral(new Token("5", TokenType.INT), "5"),
                                    new Ast.RealLiteral(new Token("3.2", TokenType.REAL), "3.2")
                                )
                            )
                        )
                    ) 
                )
            ),
            arguments(
                "var f := func(a, b) => a + b; print f(\"Hello \", \"World!\");", 
                Arrays.asList(
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("f", TokenType.IDENT), "f"),
                        new Ast.FunctionLiteral(
                            Arrays.asList(
                                new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                                new Ast.Identifier(new Token("b", TokenType.IDENT), "b")
                            ),
                            new Ast.BlockStatement(
                                Arrays.asList(
                                    new Ast.ReturnStatement(
                                        new Ast.InfixExpression(
                                            "+", 
                                            new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                                            new Ast.Identifier(new Token("b", TokenType.IDENT), "b")
                                        ) 
                                    )
                                )
                            )
                        )
                    ),
                    new Ast.PrintLiteral(
                        Arrays.asList(
                            new Ast.CallExpression(
                                new Ast.Identifier(new Token("f", TokenType.IDENT), "f"),
                                Arrays.asList(
                                    new Ast.StringLiteral(new Token("\"Hello \"", TokenType.STRING), "\"Hello \""),
                                    new Ast.StringLiteral(new Token("\"World!\"", TokenType.STRING), "\"World!\"")
                                )
                            )
                        )
                    ) 
                )
            ),
            arguments(
                "var arr := [1, 2]; arr[1];", 
                Arrays.asList(
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("arr", TokenType.IDENT), "arr"),
                        new Ast.ArrayLiteral(
                            Arrays.asList(
                                new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                                new Ast.IntegerLiteral(new Token("2", TokenType.INT), "2")
                            )
                        )
                    ),
                    new Ast.ExpressionStatement(
                        new Ast.IndexLiteral(
                            new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                            new Ast.Identifier(new Token("arr", TokenType.IDENT), "arr")
                        )
                    )
                )
            ),
            arguments(
                "var a := 5 > 4.6;", 
                Arrays.asList(
                    new Ast.VarStatement(
                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                        new Ast.InfixExpression(
                            ">", 
                            new Ast.IntegerLiteral(new Token("5", TokenType.INT), "5"),
                            new Ast.RealLiteral(new Token("4.6", TokenType.REAL), "4.6")
                        )
                    )
                )
            ),
            arguments(
                "for a in 1 .. 5 loop var x := a - 1; end;", 
                Arrays.asList(
                    new Ast.ForLiteral(
                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                        new Ast.InfixExpression(
                            "..", 
                            new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1"),
                            new Ast.IntegerLiteral(new Token("5", TokenType.INT), "5") 
                        ),
                        new Ast.BlockStatement(
                            Arrays.asList(
                                new Ast.VarStatement(
                                    new Ast.Identifier(new Token("x", TokenType.IDENT), "x"),
                                    new Ast.InfixExpression(
                                        "-", 
                                        new Ast.Identifier(new Token("a", TokenType.IDENT), "a"),
                                        new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1")
                                    )
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
    void testExpression(String literal, Ast.Expression expectedExpr) throws IOException {
        var lexer = new ParserLexer(literal);
        var parser = new Parser(lexer);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(issuccess, true);
        Assertions.assertEquals(new Ast.Program(Arrays.asList(new Ast.ExpressionStatement(expectedExpr))), root);
    }

    @ParameterizedTest
    @MethodSource("statementProvider")
    void testStatement(String statement, Ast.Statement expectedStmt) throws IOException {
        var lexer = new ParserLexer(statement);
        var parser = new Parser(lexer);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(issuccess, true);
        Assertions.assertEquals(new Ast.Program(Arrays.asList(expectedStmt)), root);
    }

    @ParameterizedTest
    @MethodSource("statementsProvider")
    void testStatements(String statement, List<Ast.Statement> expectedStmts) throws IOException {
        var lexer = new ParserLexer(statement);
        var parser = new Parser(lexer);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(issuccess, true);
        Assertions.assertEquals(new Ast.Program(expectedStmts), root);
    }

    @Test
    void testTuple() throws IOException {
        var lexer = new ParserLexer("{a:=1};");
        var parser = new Parser(lexer);
        var identKey = new Ast.Identifier(new Token("a", TokenType.IDENT), "a");
        var indexKey = new Ast.IntegerLiteral(new Token("0", TokenType.INT), "0");
        var val = new Ast.IntegerLiteral(new Token("1", TokenType.INT), "1");
        var expectedMap = new HashMap<Ast.Expression, Ast.Expression>();
        expectedMap.put(identKey, val);
        expectedMap.put(indexKey, val);

        var expectedExpr = new Ast.TupleLiteral(expectedMap);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(issuccess, true);
        var tupleExpr = ((Ast.ExpressionStatement)((Ast.Program)root).getStatements().get(0)).getExpression();
        var tuple = (Ast.TupleLiteral)tupleExpr;
        var map = tuple.getPairs();
        Assertions.assertEquals(val, map.get(indexKey));
        Assertions.assertEquals(val, map.get(identKey));
    }

    @Test
    void testNestedTuple() throws IOException {
        var lexer = new ParserLexer("{a:={b:=\"c\"}};");
        var parser = new Parser(lexer);
        var indexKey = new Ast.IntegerLiteral(new Token("0", TokenType.INT), "0");
        // creating b tuple
        Ast.Expression bKey = new Ast.Identifier(new Token("b", TokenType.IDENT), "b");
        Ast.Expression bValue = new Ast.StringLiteral(new Token("\"c\"", TokenType.STRING), "\"c\"");
        var bMap = Map.of(indexKey, bValue, bKey, bValue);
        // creating a tuple
        var aKey = new Ast.Identifier(new Token("a", TokenType.IDENT), "a");
        var aValue = new Ast.TupleLiteral(bMap);
        Map<Ast.Expression, Ast.Expression> expectedMap = Map.of(indexKey, aValue, aKey, aValue);
        var expectedExpr = new Ast.TupleLiteral(expectedMap);
        
        var issuccess = parser.parse();
        var root = parser.getRoot();

        Assertions.assertEquals(issuccess, true);
        var tupleExpr = ((Ast.ExpressionStatement)((Ast.Program)root).getStatements().get(0)).getExpression();
        var tuple = (Ast.TupleLiteral)tupleExpr;
        var map = tuple.getPairs();
        Assertions.assertEquals(aValue, map.get(indexKey));
        Assertions.assertEquals(aValue, map.get(aKey));
    }
}
