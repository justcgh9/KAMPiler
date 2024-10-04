package org.projectD.interpreter.lexer;
import java.util.ArrayList;

import org.projectD.interpreter.ast.Ast;
import org.projectD.interpreter.parser.Parser;
import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.token.TokenType;

public class ParserLexer implements Parser.Lexer {
	private Lexer lexer;
	private Ast.Node value;

	public ParserLexer(String input) {
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
			case TokenType.DOT:
				this.value = null;
				return Parser.Lexer.DOT;
			case TokenType.LBRACE:
				this.value = null;
				return Parser.Lexer.LBRACE;
			case TokenType.RBRACE:
				this.value = null;
				return Parser.Lexer.RBRACE;
			case TokenType.LBRACKET:
				this.value = null;
				return Parser.Lexer.LBRACKET;
			case TokenType.RBRACKET:
				this.value = null;
				return Parser.Lexer.RBRACKET;

			// keywords
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
			case TokenType.IF:
				this.value = null;
				return Parser.Lexer.IF;
			case TokenType.THEN:
				this.value = null;
				return Parser.Lexer.THEN;
			case TokenType.ELSE:
				this.value = null;
				return Parser.Lexer.ELSE;
			case TokenType.TRUE:
				this.value = new Ast.BooleanLiteral(tok, literal);
				return Parser.Lexer.TRUE;
			case TokenType.FALSE:
				this.value = new Ast.BooleanLiteral(tok, literal);
				return Parser.Lexer.FALSE;
			case TokenType.WHILE:
				this.value = null;
				return Parser.Lexer.WHILE;
			case TokenType.LOOP:
				this.value = null;
				return Parser.Lexer.LOOP;
			
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