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
	import org.projectD.interpreter.lexer.ParserLexer;
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
		ParserLexer l = new ParserLexer("if 1 + 2 then 1 + 3; end\n");
		Parser p = new Parser(l);
		p.parse();

		System.out.println(p.getRoot());
	}
}

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
%token DOT
%token DOTDOT
%token LBRACE
%token RBRACE
%token LBRACKET
%token RBRACKET


// keywords
%token VAR
%token RETURN
%token FUNCTION
%token ARROW
%token IS
%token END
%token PRINT
%token IF
%token THEN
%token ELSE
%token TRUE
%token FALSE
%token WHILE
%token FOR
%token LOOP
%token IN

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
	| IfStatement
	| WhileStatement
	| ReturnStatement
	| ForStatement
	;

IfStatement
	: IF Expression THEN BlockStatement END LineBreak {
		$$ = new Ast.IfStatement((Ast.Expression)$2, (Ast.BlockStatement)$4);
	}
	| IF Expression THEN BlockStatement ELSE BlockStatement END LineBreak {
		$$ = new Ast.IfStatement((Ast.Expression)$2, (Ast.BlockStatement)$4, (Ast.BlockStatement)$6);
	}
	;

WhileStatement
	: WHILE Expression LOOP BlockStatement END LineBreak {
		$$ = new Ast.WhileStatement((Ast.Expression)$2, (Ast.BlockStatement)$4);
	}
	;

ForStatement
	: FOR IDENT IN Expression DOTDOT Expression LOOP BlockStatement END LineBreak {
		$$ = new Ast.ForLiteral((Ast.Identifier) $2, new Ast.InfixExpression("..", (Ast.Expression) $4, (Ast.Expression) $6), (Ast.BlockStatement) $8);
	}
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
	| FuncLiteral
	;

Relation
	: Factor 
	| Factor Compare Factor {var exp = (Ast.InfixExpression) $2; exp.setLeft((Ast.Expression) $1); exp.setRight((Ast.Expression) $3); $$ = exp;}
	;

Factor
	: AddExpression
	;

FuncLiteral
	: FUNCTION LPAREN FuncDeclarationParameters RPAREN IS BlockStatement END {
		var func = (Ast.FunctionLiteral) $3;
		var body = (Ast.BlockStatement) $6;
		func.setBody(body);
		$$ = func;
	}
	| FUNCTION LPAREN FuncDeclarationParameters RPAREN ARROW Expression {
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
	| IDENT Tail {
		try {
			var idx = (Ast.IndexLiteral) $2;
			idx.setLeft((Ast.Identifier) $1);
			$$ = idx; 
		} catch(Exception e) {
			try {
				var callExp = (Ast.CallExpression) $2;
				callExp.addFunction((Ast.Identifier) $1);
				$$ = callExp;
			} catch (Exception f) {
				$$ = $1;
			}
		}
	}
	| STRING
	| TRUE
	| FALSE
	| Array
	| Tuple
	;

Tail 
	: %empty
	| ArrayTail
	| TupleTail
	| FuncTail
	;

ArrayTail
	: LBRACKET INT RBRACKET {$$ = new Ast.IndexLiteral((Ast.IntegerLiteral) $2);}
	;

TupleTail
	: DOT INT {$$ = new Ast.IndexLiteral((Ast.IntegerLiteral) $2);}
	| DOT IDENT {$$ = new Ast.IndexLiteral((Ast.Identifier) $2);}
	;

FuncTail
	: LPAREN CallArgs RPAREN {$$ = (Ast.CallExpression) $2;}
	;

CallArgs
	: %empty {$$ = new Ast.CallExpression();}
	| Expression {
		var callExp = new Ast.CallExpression();
		callExp.addArgument((Ast.Expression) $1);
		$$ = callExp;
	}
	| CallArgs COMMA Expression {
		var callExp = (Ast.CallExpression) $1;
		callExp.addArgument((Ast.Expression) $3);
		$$ = callExp;
	}
	;

Array
	: LBRACKET RBRACKET {$$ = new Ast.ArrayLiteral();}
	| LBRACKET ArrayContent RBRACKET {$$ = (Ast.ArrayLiteral) $2;}
	;

ArrayContent
	: Expression {
		var arr = new Ast.ArrayLiteral();
		arr.addExpression((Ast.Expression) $1);
		$$ = arr;
	}
	| ArrayContent COMMA Expression {
		var arr = (Ast.ArrayLiteral) $1;
		arr.addExpression((Ast.Expression) $3);
		$$ = arr;
	}
	;

Tuple
	: LBRACE RBRACE {$$ = new Ast.TupleLiteral();}
	| LBRACE TupleContent RBRACE {$$ = (Ast.TupleLiteral) $2;}
	;

TupleContent
	: Expression {
		var tpl = new Ast.TupleLiteral();
		tpl.addExpression((Ast.Expression) $1);
		$$ = tpl;
	}
	| IDENT ASSIGN Expression {
		var tpl = new Ast.TupleLiteral();
		tpl.addAssignment((Ast.Identifier) $1, (Ast.Expression) $3);
		$$ = tpl;
	}
	| TupleContent COMMA Expression {
		var tpl = (Ast.TupleLiteral) $1;
		tpl.addExpression((Ast.Expression) $3);
		$$ = tpl;
	}
	| TupleContent COMMA IDENT ASSIGN Expression {
		var tpl = (Ast.TupleLiteral) $1;
		tpl.addAssignment((Ast.Identifier) $3, (Ast.Expression) $5);
		$$ = tpl;
	}
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


