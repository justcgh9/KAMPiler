%language "Java"

%define api.package {org.projectD.interpreter.parser}
%define api.parser.public
%define api.parser.class {Parser}

%define parse.error detailed

%code imports { 
	import java.io.IOException;
	import java.util.List;    
	import java.util.ArrayList;

	import org.projectD.interpreter.ast.Ast;
	import org.projectD.interpreter.lexer.Lexer;
	import org.projectD.interpreter.token.Token;	
	import org.projectD.interpreter.token.TokenType;
}

%code {
	// TODO: remove main function.
	// This function must be used only for debugging purposes.
	// Run command:
	// java app/src/main/java/org/projectD/interpreter/parser/Parser.java 
	public static void main (String args[]) throws IOException {
		ParserLexer l = new ParserLexer("1 + 1");
		Parser p = new Parser(l);
		p.parse();
	}
}

// Tokens must be declared in the TokenType enum!

// literals
%token INT

// operators
%token PLUS

%start CompilationUnit

%%

CompilationUnit
	: %empty
	| AddUnit { 
		List<Ast.Statement> statements = new ArrayList<>();
		statements.add((Ast.ExpressionStatement)$1);
		$$ = new Ast.Program(statements);
	}
	;

AddUnit
	: INT PLUS INT {
        Ast.IntegerLiteral left = new Ast.IntegerLiteral(new Token(String.valueOf($1), TokenType.INT), String.valueOf($1));
        Ast.IntegerLiteral right = new Ast.IntegerLiteral(new Token(String.valueOf($3), TokenType.INT), String.valueOf($3));

        Ast.InfixExpression addExpr = new Ast.InfixExpression(new Token(String.valueOf($1), TokenType.PLUS), "+", left, right);

        Ast.ExpressionStatement exprStmt = new Ast.ExpressionStatement(new Token(String.valueOf($1)), addExpr);
    
        $$ = exprStmt;
	}

%%

class ParserLexer implements Parser.Lexer {
	private Lexer lexer;
	private Object value;

	ParserLexer(String input) {
		this.lexer = new Lexer(input);
	}

	@Override
	public int yylex() throws java.io.IOException {
		Token tok = this.lexer.nextToken();
		
		String literal = tok.gLiteral();
		switch (tok.gTokenType()) {
			case TokenType.EOF:
				return Parser.Lexer.EOF;
			case TokenType.PLUS:
				this.value = literal;
				return Parser.Lexer.PLUS;
			case TokenType.INT:
				this.value = Integer.parseInt(literal);
				return Parser.Lexer.INT;
		}

		return Parser.Lexer.YYerror;
	}

	@Override
	public Object getLVal() {
		return this.value;
	}

	@Override
	public void yyerror(String msg) {
		System.err.println(msg);	
	}
}
