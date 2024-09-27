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
	Ast.Node root;

	public void setRoot(Ast.Node root) {
		this.root = root;
	}
	
	public Ast.Node getRoot() {
		return this.root;
	}

	// TODO: remove main function.
	// This function must be used only for debugging purposes.
	// Run command:
	// java app/src/main/java/org/projectD/interpreter/parser/Parser.java 
	public static void main (String args[]) throws IOException {
		ParserLexer l = new ParserLexer("(1.1 + 2) / 3 * (4 + 5);");
		Parser p = new Parser(l);
		p.parse();

		System.out.println(p.getRoot());
	}
}

// Tokens must be declared in the TokenType enum!

// literals
%token INT
%token REAL

// operators
%token PLUS
%token MINUS
%token MULTIPLY
%token DIVIDE

// delimeters
%token SEMICOLON
%token NEWLINE
%token LPAREN
%token RPAREN

%start CompilationUnit

%%

CompilationUnit
	: %empty {$$ = null;}
	| Statements {
		var prog = (Ast.Program)$1;
		this.setRoot(prog);
	}
	;

Statements
	: Statement {
		var statements = new ArrayList<Ast.Statement>();
		var exprStmt = (Ast.Statement)$1;
		statements.add(exprStmt);

		$$ = new Ast.Program(statements);
	}
	| Statements Statement {
		var program = (Ast.Program)$1;
		var statements = program.getStatements();              
		var exprStmt = (Ast.Statement)$2; 
		statements.add(exprStmt);                                  

		$$ = new Ast.Program(statements);
	}
	;

Statement
	: ExpressionStatement
	;
	
ExpressionStatement
	: Expression SEMICOLON { $$ = new Ast.ExpressionStatement((Ast.Expression)$1); }
	| Expression NEWLINE { $$ = new Ast.ExpressionStatement((Ast.Expression)$1); }
	;

Expression
	: AddExpression
	;

AddExpression
	: MultExpression
	| AddExpression PLUS MultExpression {
		var expr = (Ast.InfixExpression)$2;

		expr.setLeft((Ast.Expression)$1);
		expr.setRight((Ast.Expression)$3);

		$$ = expr;

	}
	| AddExpression MINUS MultExpression {
		var expr = (Ast.InfixExpression)$2;

		expr.setLeft((Ast.Expression)$1);
		expr.setRight((Ast.Expression)$3);

		$$ = expr;
	}
	;

MultExpression
	: UnaryExpression 
	| MultExpression MULTIPLY UnaryExpression {
		var expr = (Ast.InfixExpression)$2;

		expr.setLeft((Ast.Expression)$1);
		expr.setRight((Ast.Expression)$3);

		$$ = expr;
	}
	| MultExpression DIVIDE UnaryExpression {
		var expr = (Ast.InfixExpression)$2;

		expr.setLeft((Ast.Expression)$1);
		expr.setRight((Ast.Expression)$3);

		$$ = expr;
	}
	;

UnaryExpression
	: Term 
	| ParenthesisExpression
	| MINUS Term {
		var expr = (Ast.PrefixExpression) $1;

		expr.setRight((Ast.Expression)$2);
		$$ = expr;
	}
	;

ParenthesisExpression
	: LPAREN Expression RPAREN {$$ = $2;}
	;

Term
	: INT
	| REAL
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
			// literals
			case TokenType.INT:
				this.value = new Ast.IntegerLiteral(tok, literal);
				return Parser.Lexer.INT;
			case TokenType.REAL:
				this.value = new Ast.RealLiteral(tok, literal);
				return Parser.Lexer.REAL;
			
			// operators
			case TokenType.PLUS:
				this.value = new Ast.InfixExpression("+");
				return Parser.Lexer.PLUS;
			case TokenType.MINUS:
				if (this.value instanceof Ast.IntegerLiteral)
					this.value = new Ast.InfixExpression("-");
				else 
					this.value = new Ast.PrefixExpression("-");
				return Parser.Lexer.MINUS;
			case TokenType.ASTERISK:
				this.value = new Ast.InfixExpression("*");
				return Parser.Lexer.MULTIPLY;
			case TokenType.SLASH:
				this.value = new Ast.InfixExpression("/");
				return Parser.Lexer.DIVIDE;
			
			// delimiters
			case TokenType.SEMICOLON:
				return Parser.Lexer.SEMICOLON;
			case TokenType.NEWLINE:
				return Parser.Lexer.NEWLINE;
			case TokenType.LPAREN:
				return Parser.Lexer.LPAREN;
			case TokenType.RPAREN:
				return Parser.Lexer.RPAREN;
			
			case TokenType.EOF:
				return Parser.Lexer.EOF;

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
