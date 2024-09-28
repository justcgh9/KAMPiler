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
		ParserLexer l = new ParserLexer("print 1, a, b, c, \"cat\", 2 / 3  + 2 * 2;");
		Parser p = new Parser(l);
		p.parse();

		System.out.println(p.getRoot());
	}
}

// Tokens must be declared in the TokenType enum!

// literals
%token INT
%token REAL
%token STRING
%token IDENT

// operators
%token PLUS
%token MINUS
%token MULTIPLY
%token DIVIDE
%token ASSIGN
%token AND
%token OR
%token XOR
%token GT
%token LT
%token GEQ
%token LEQ
%token EQ

// delimeters
%token SEMICOLON
%token NEWLINE
%token LPAREN
%token RPAREN
%token COMMA

%token VAR
%token RETURN
%token FUNCTION
%token ARROW
%token IS
%token END
%token PRINT

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
	| VarStatement
	| PrintStatement
	;

VarStatement
	: VAR IDENT LineBreak {
		$$ = new Ast.VarStatement((Ast.Identifier) $2, null);
		}
	| VAR IDENT ASSIGN Expression LineBreak {$$ = new Ast.VarStatement((Ast.Identifier) $2, (Ast.Expression) $4);}
	;

PrintStatement
	: PRINT PrintArgs LineBreak {$$ = $2;}
	;

PrintArgs
	: Expression {
		var args = new ArrayList<Ast.Expression>();
		args.add((Ast.Expression) $1);
		$$ = new Ast.PrintLiteral(args);
	}
	| PrintArgs COMMA Expression {
		var stmt = (Ast.PrintLiteral) $1;
		stmt.addArgument((Ast.Expression) $3);
		$$ = stmt;
	}
	;

ExpressionStatement
	: Expression LineBreak { $$ = new Ast.ExpressionStatement((Ast.Expression)$1); }
	;

LineBreak
	: SEMICOLON
	;

Expression
	: Relation
	| Relation BoolOp Relation {var exp = (Ast.InfixExpression) $2; exp.setLeft((Ast.Expression) $1); exp.setRight((Ast.Expression) $3); $$ = exp;}
	;

Relation
	: Factor 
	| Factor Compare Factor {var exp = (Ast.InfixExpression) $2; exp.setLeft((Ast.Expression) $1); exp.setRight((Ast.Expression) $3); $$ = exp;}
	;

Factor
	: AddExpression
	| FuncLiteral
	;

FuncLiteral
	: FUNCTION LPAREN FuncDeclarationParameters RPAREN IS BlockStatement ReturnStatement END {
		var func = (Ast.FunctionLiteral) $3;
		var body = (Ast.BlockStatement) $6;
		body.addStatement((Ast.ReturnStatement) $7);
		func.setBody(body);
		$$ = func;
	}
	| FUNCTION LPAREN FuncDeclarationParameters RPAREN ARROW Expression LineBreak {
		var func = (Ast.FunctionLiteral) $3;
		var statements = new ArrayList<Ast.Statement>();
		statements.add(new Ast.ReturnStatement((Ast.Expression) $6));
		func.setBody(new Ast.BlockStatement(statements));
		$$ = func;
	}
	;

FuncDeclarationParameters
	: %empty {$$ = new Ast.FunctionLiteral(new ArrayList<Ast.Identifier>());}
	| IDENT	{
		var idt = new ArrayList<Ast.Identifier>();
		idt.add((Ast.Identifier) $1);
		$$ = new Ast.FunctionLiteral(idt);
	}
	| FuncDeclarationParameters COMMA IDENT {
		var func = (Ast.FunctionLiteral) $1;
		func.addParameter((Ast.Identifier) $3);
		$$ = func;
	}
	;


BlockStatement
	: Statement {
		var statements = new ArrayList<Ast.Statement>();
		var stmt = (Ast.Statement) $1;
		statements.add(stmt);

		$$ = new Ast.BlockStatement(statements);
	}
	| BlockStatement Statement {
		var blockStatement = (Ast.BlockStatement) $1;
		blockStatement.addStatement((Ast.Statement) $2);

		$$ = blockStatement;
	}
	;

ReturnStatement
	: RETURN LineBreak {$$ = new Ast.ReturnStatement(null);}
	| RETURN Expression LineBreak {$$ = new Ast.ReturnStatement((Ast.Expression) $2);}
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
	| LPAREN AddExpression RPAREN {$$ = $2;}
	| MINUS Term {
		var expr = (Ast.PrefixExpression) $1;

		expr.setRight((Ast.Expression)$2);
		$$ = expr;
	}
	;

Term
	: INT
	| REAL
	| IDENT
	| STRING
	;

BoolOp
	: AND {$$ = new Ast.InfixExpression("and");}
	| OR {$$ = new Ast.InfixExpression("or");}
	| XOR {$$ = new Ast.InfixExpression("xor");}
	;

Compare
	: LT {$$ = new Ast.InfixExpression("<");}
	| GT {$$ = new Ast.InfixExpression(">");}
	| LEQ {$$ = new Ast.InfixExpression("<=");}
	| GEQ {$$ = new Ast.InfixExpression(">=");}
	| EQ {$$ = new Ast.InfixExpression("=");}
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
			case TokenType.STRING:
				this.value = new Ast.StringLiteral(tok, literal);
				return Parser.Lexer.STRING;
			case TokenType.IDENT:
				this.value = new Ast.Identifier(tok, literal);
				return Parser.Lexer.IDENT;
			
			// operators
			case TokenType.PLUS:
				this.value = new Ast.InfixExpression("+");
				return Parser.Lexer.PLUS;
			case TokenType.MINUS:
				if (this.value instanceof Ast.Expression)
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
			case TokenType.ASSIGN:
				this.value = null;
				return Parser.Lexer.ASSIGN;
			case TokenType.PRINT:
				this.value = new Ast.PrintLiteral(new ArrayList<Ast.Expression>());
				return Parser.Lexer.PRINT;
			case TokenType.LT:
				this.value = null;
				return Parser.Lexer.LT;
			case TokenType.GT:
				this.value = null;
				return Parser.Lexer.GT;
			case TokenType.LEQ:
				this.value = null;
				return Parser.Lexer.LEQ;
			case TokenType.GEQ:
				this.value = null;
				return Parser.Lexer.GEQ;
			case TokenType.EQ:
				this.value = null;
				return Parser.Lexer.EQ;
			case TokenType.AND:
				this.value = null;
				return Parser.Lexer.AND;
			case TokenType.OR:
				this.value = null;
				return Parser.Lexer.OR;
			case TokenType.XOR:
				this.value = null;
				return Parser.Lexer.XOR;
			
			// delimiters
			case TokenType.SEMICOLON:
				this.value = new Ast.Semicolon();
				return Parser.Lexer.SEMICOLON;
			case TokenType.NEWLINE:
				if (!(this.value instanceof Ast.Semicolon)){
					this.value = new Ast.Semicolon();
					return Parser.Lexer.SEMICOLON;
				} else {
					return this.yylex();
				}
			case TokenType.LPAREN:
				this.value = null;
				return Parser.Lexer.LPAREN;
			case TokenType.RPAREN:
				this.value = null;
				return Parser.Lexer.RPAREN;
			case TokenType.COMMA:
				this.value = null;
				return Parser.Lexer.COMMA;

			case TokenType.VAR:
				this.value = null;
				return Parser.Lexer.VAR;
			case TokenType.RETURN:
				this.value = null;
				return Parser.Lexer.RETURN;
			case TokenType.FUNCTION:
				this.value = null;
				return Parser.Lexer.FUNCTION;
			case TokenType.ARROW:
				this.value = null;
				return Parser.Lexer.ARROW;
			case TokenType.IS:
				this.value = null;
				return Parser.Lexer.IS;
			case TokenType.END:
				this.value = null;
				return Parser.Lexer.END;
			
			case TokenType.EOF:
				this.value = null;
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
