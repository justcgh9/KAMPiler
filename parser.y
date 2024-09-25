%language "Java"

%define api.package {org.projectD.interpreter.parser}
%define api.parser.public
%define api.parser.class {Parser}
%define api.value.type {Ast.Node}

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
		ParserLexer l = new ParserLexer("1 + 3");
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
<<<<<<< HEAD
	: %empty
	| AddUnit { 
		List<Ast.Statement> statements = new ArrayList<>();
		statements.add((Ast.ExpressionStatement)$1);
=======
	: %empty {$$ = null;}
	| AddUnit { 
		List<Ast.Statement> statements = new ArrayList<>();
		statements.add((Ast.ExpressionStatement)$1);
		
		// TODO: remove, added for debugging
		System.out.println(statements);
>>>>>>> 9dcbd93 (add addition grammar)
		$$ = new Ast.Program(statements);
	}
	;

AddUnit
	: INT PLUS INT {
		((Ast.InfixExpression)$2).setLeft((Ast.IntegerLiteral)$1);
		((Ast.InfixExpression)$2).setRight((Ast.IntegerLiteral)$3);

		var token = new Token($1.tokenLiteral(), TokenType.INT);
        Ast.ExpressionStatement expr = new Ast.ExpressionStatement(token, (Ast.InfixExpression)$2);
    
        $$ = expr;
	}

%%

class ParserLexer implements Parser.Lexer {
	private Lexer lexer;
	private Ast.Node value;

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
				this.value = new Ast.InfixExpression("+");
				return Parser.Lexer.PLUS;
			case TokenType.INT:
				this.value = new Ast.IntegerLiteral(tok, literal);
				return Parser.Lexer.INT;
		}

		return Parser.Lexer.YYerror;
	}

	@Override
	public Ast.Node getLVal() {
		return this.value;
	}

	@Override
	public void yyerror(String msg) {
		System.err.println(msg);	
	}
}
