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
		ParserLexer l = new ParserLexer("1 + 2 + 3 + 4 + 5");
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
	: %empty {$$ = null;}
	| AddUnit { 
		List<Ast.Statement> statements = new ArrayList<>();
		var exprStmt = new Ast.ExpressionStatement((Ast.Expression)$1);
		statements.add(exprStmt);
		
		// TODO: remove, added for debugging
		System.out.println(statements);
		$$ = new Ast.Program(statements);
	}
	;

AddUnit
	: AddUnit PLUS Term {
		var infix = (Ast.InfixExpression)$2;

		infix.setLeft((Ast.Expression)$1);
		infix.setRight((Ast.IntegerLiteral)$3);

        $$ = infix;
	}
	| Term 
	;

Term
	: INT
	;

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
