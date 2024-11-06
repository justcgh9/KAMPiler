package org.projectD.interpreter.evaluator;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.projectD.interpreter.ast.Ast;
import org.projectD.interpreter.lexer.ParserLexer;
import org.projectD.interpreter.parser.Parser;
import org.projectD.interpreter.object.Environment;
import org.projectD.interpreter.semantic.SemanticAnalyzer;
import org.projectD.interpreter.object.ObjectTypeDemo;

class EvaluatorTest {
    public Ast.Program getRoot(String input) throws IOException {
        var lexer = new ParserLexer(input);
        var parser = new Parser(lexer);
        var analyzer = new SemanticAnalyzer();
        
        parser.parse();
        Ast.Program root = (Ast.Program)parser.getRoot();

        analyzer.analyze(root);

		return root;
	}

    @Test
    void testEvalVar() throws IOException {
        var root = this.getRoot("var a := 1; a = false;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(1, ((ObjectTypeDemo.Integer) env.get("a")).getValue());
    }
    
    @Test
    void testEvalIngerExpression() throws IOException {
        var root = this.getRoot("1 + 3;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(4, ((ObjectTypeDemo.Integer) result).getValue());
    }

    @Test
    void testEvalStringExpression() throws IOException {
        var root = this.getRoot("\"1\" + \"3\";");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals("\"13\"", ((ObjectTypeDemo.StringObject) result).getValue());
    }

    @Test
    void testEvalRealExpression() throws IOException {
        var root = this.getRoot("1.1 + 3.3;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(4.4, ((ObjectTypeDemo.Double) result).getValue());
    }

    @Test
    void testEvalBoolExpression() throws IOException {
        var root = this.getRoot("true;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(true, ((ObjectTypeDemo.Boolean) result).getValue());
    }

    @Test
    void testEvalAddition() throws IOException {
        var root = this.getRoot("var a := 5; var b := 3; a + b - b ;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(5, ((ObjectTypeDemo.Integer) result).getValue());
    }

    @Test
    void testEvalFuncCall() throws IOException {
        var root = this.getRoot("var f := func(a, b) is return a + b; end; f(5, 3.2);");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(8.2, ((ObjectTypeDemo.Double) result).getValue());
    }

    @Test
    void testEvalIfElseIs() throws IOException {
        var root = this.getRoot("var integer := \"integer\"; if integer is int then \"int\"; else \"not int\"; end;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals("\"not int\"", ((ObjectTypeDemo.StringObject) result).getValue());
    }
 
    @Test
    void testEvalArrayOutOfIndex() throws IOException {
        var root = this.getRoot("var arr := []; arr[0];");
        var evaluator = new Evaluator();
        var env = new Environment();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            evaluator.eval(root, env);
        });
    }

    @Test
    void testEvalWhuke() throws IOException {
        var root = this.getRoot("var x := 0; while x < 10 loop x := x + 1; end; x;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(10, ((ObjectTypeDemo.Integer) result).getValue());
    }

    @Test
    void testEvalFor() throws IOException {
        var root = this.getRoot("var x := 0; for a in 1 .. 5 loop x := x + a; end; x;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(10, ((ObjectTypeDemo.Integer) result).getValue());
    }

    @Test
    void testEvalConcatArr() throws IOException {
        var root = this.getRoot("var arr := [1, 2, 3] + [4, 5, 6]; arr[5];");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(6, ((ObjectTypeDemo.Integer) result).getValue());
    }

    @Test
    void testEvalConcatTuple() throws IOException {
        var root = this.getRoot("var t := {1, 2, 3} + {4, 5, 6}; t.5;");
        var evaluator = new Evaluator();
        var env = new Environment();

        var result = evaluator.eval(root, env);

        Assertions.assertEquals(6, ((ObjectTypeDemo.Integer) result).getValue());
    }
}