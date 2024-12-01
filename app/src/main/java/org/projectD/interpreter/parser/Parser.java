/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

package org.projectD.interpreter.parser;



import java.text.MessageFormat;
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "parser.y":10  */
 
	import java.io.IOException;
	import java.util.List;    
	import java.util.ArrayList;

	import org.projectD.interpreter.ast.Ast;
	import org.projectD.interpreter.lexer.Lexer;
	import org.projectD.interpreter.lexer.ParserLexer;
	import org.projectD.interpreter.token.Token;	
	import org.projectD.interpreter.token.TokenType;
	import java.lang.IllegalArgumentException;

/* "app/src/main/java/org/projectD/interpreter/parser/Parser.java":58  */

/**
 * A Bison parser, automatically generated from <tt>parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class Parser
{
  /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.8.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";



  /**
   * True if verbose error messages are enabled.
   */
  private boolean yyErrorVerbose = true;

  /**
   * Whether verbose error messages are enabled.
   */
  public final boolean getErrorVerbose() { return yyErrorVerbose; }

  /**
   * Set the verbosity of error messages.
   * @param verbose True to request verbose error messages.
   */
  public final void setErrorVerbose(boolean verbose)
  { yyErrorVerbose = verbose; }




  public enum SymbolKind
  {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_INT(3),                      /* INT  */
    S_REAL(4),                     /* REAL  */
    S_STRING(5),                   /* STRING  */
    S_IDENT(6),                    /* IDENT  */
    S_TYPE(7),                     /* TYPE  */
    S_PLUS(8),                     /* PLUS  */
    S_MINUS(9),                    /* MINUS  */
    S_MULTIPLY(10),                /* MULTIPLY  */
    S_DIVIDE(11),                  /* DIVIDE  */
    S_ASSIGN(12),                  /* ASSIGN  */
    S_AND(13),                     /* AND  */
    S_OR(14),                      /* OR  */
    S_XOR(15),                     /* XOR  */
    S_GT(16),                      /* GT  */
    S_LT(17),                      /* LT  */
    S_GEQ(18),                     /* GEQ  */
    S_LEQ(19),                     /* LEQ  */
    S_EQ(20),                      /* EQ  */
    S_NOT_EQ(21),                  /* NOT_EQ  */
    S_SEMICOLON(22),               /* SEMICOLON  */
    S_NEWLINE(23),                 /* NEWLINE  */
    S_LPAREN(24),                  /* LPAREN  */
    S_RPAREN(25),                  /* RPAREN  */
    S_COMMA(26),                   /* COMMA  */
    S_DOT(27),                     /* DOT  */
    S_DOTDOT(28),                  /* DOTDOT  */
    S_LBRACE(29),                  /* LBRACE  */
    S_RBRACE(30),                  /* RBRACE  */
    S_LBRACKET(31),                /* LBRACKET  */
    S_RBRACKET(32),                /* RBRACKET  */
    S_VAR(33),                     /* VAR  */
    S_RETURN(34),                  /* RETURN  */
    S_FUNCTION(35),                /* FUNCTION  */
    S_ARROW(36),                   /* ARROW  */
    S_IS(37),                      /* IS  */
    S_END(38),                     /* END  */
    S_PRINT(39),                   /* PRINT  */
    S_IF(40),                      /* IF  */
    S_THEN(41),                    /* THEN  */
    S_ELSE(42),                    /* ELSE  */
    S_TRUE(43),                    /* TRUE  */
    S_FALSE(44),                   /* FALSE  */
    S_WHILE(45),                   /* WHILE  */
    S_FOR(46),                     /* FOR  */
    S_LOOP(47),                    /* LOOP  */
    S_IN(48),                      /* IN  */
    S_NOT(49),                     /* NOT  */
    S_YYACCEPT(50),                /* $accept  */
    S_CompilationUnit(51),         /* CompilationUnit  */
    S_Statements(52),              /* Statements  */
    S_Statement(53),               /* Statement  */
    S_IfStatement(54),             /* IfStatement  */
    S_AssignmentStatement(55),     /* AssignmentStatement  */
    S_WhileStatement(56),          /* WhileStatement  */
    S_ForStatement(57),            /* ForStatement  */
    S_VarStatement(58),            /* VarStatement  */
    S_PrintStatement(59),          /* PrintStatement  */
    S_PrintArgs(60),               /* PrintArgs  */
    S_ExpressionStatement(61),     /* ExpressionStatement  */
    S_LineBreak(62),               /* LineBreak  */
    S_Expression(63),              /* Expression  */
    S_Relation(64),                /* Relation  */
    S_Factor(65),                  /* Factor  */
    S_FuncLiteral(66),             /* FuncLiteral  */
    S_FuncDeclarationParameters(67), /* FuncDeclarationParameters  */
    S_BlockStatement(68),          /* BlockStatement  */
    S_ReturnStatement(69),         /* ReturnStatement  */
    S_AddExpression(70),           /* AddExpression  */
    S_MultExpression(71),          /* MultExpression  */
    S_UnaryExpression(72),         /* UnaryExpression  */
    S_Term(73),                    /* Term  */
    S_TypeLiteral(74),             /* TypeLiteral  */
    S_Reference(75),               /* Reference  */
    S_Tail(76),                    /* Tail  */
    S_ArrayTail(77),               /* ArrayTail  */
    S_TupleTail(78),               /* TupleTail  */
    S_FuncTail(79),                /* FuncTail  */
    S_CallArgs(80),                /* CallArgs  */
    S_Array(81),                   /* Array  */
    S_ArrayContent(82),            /* ArrayContent  */
    S_Tuple(83),                   /* Tuple  */
    S_TupleContent(84),            /* TupleContent  */
    S_BoolOp(85),                  /* BoolOp  */
    S_Compare(86);                 /* Compare  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
      SymbolKind.S_INT,
      SymbolKind.S_REAL,
      SymbolKind.S_STRING,
      SymbolKind.S_IDENT,
      SymbolKind.S_TYPE,
      SymbolKind.S_PLUS,
      SymbolKind.S_MINUS,
      SymbolKind.S_MULTIPLY,
      SymbolKind.S_DIVIDE,
      SymbolKind.S_ASSIGN,
      SymbolKind.S_AND,
      SymbolKind.S_OR,
      SymbolKind.S_XOR,
      SymbolKind.S_GT,
      SymbolKind.S_LT,
      SymbolKind.S_GEQ,
      SymbolKind.S_LEQ,
      SymbolKind.S_EQ,
      SymbolKind.S_NOT_EQ,
      SymbolKind.S_SEMICOLON,
      SymbolKind.S_NEWLINE,
      SymbolKind.S_LPAREN,
      SymbolKind.S_RPAREN,
      SymbolKind.S_COMMA,
      SymbolKind.S_DOT,
      SymbolKind.S_DOTDOT,
      SymbolKind.S_LBRACE,
      SymbolKind.S_RBRACE,
      SymbolKind.S_LBRACKET,
      SymbolKind.S_RBRACKET,
      SymbolKind.S_VAR,
      SymbolKind.S_RETURN,
      SymbolKind.S_FUNCTION,
      SymbolKind.S_ARROW,
      SymbolKind.S_IS,
      SymbolKind.S_END,
      SymbolKind.S_PRINT,
      SymbolKind.S_IF,
      SymbolKind.S_THEN,
      SymbolKind.S_ELSE,
      SymbolKind.S_TRUE,
      SymbolKind.S_FALSE,
      SymbolKind.S_WHILE,
      SymbolKind.S_FOR,
      SymbolKind.S_LOOP,
      SymbolKind.S_IN,
      SymbolKind.S_NOT,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_CompilationUnit,
      SymbolKind.S_Statements,
      SymbolKind.S_Statement,
      SymbolKind.S_IfStatement,
      SymbolKind.S_AssignmentStatement,
      SymbolKind.S_WhileStatement,
      SymbolKind.S_ForStatement,
      SymbolKind.S_VarStatement,
      SymbolKind.S_PrintStatement,
      SymbolKind.S_PrintArgs,
      SymbolKind.S_ExpressionStatement,
      SymbolKind.S_LineBreak,
      SymbolKind.S_Expression,
      SymbolKind.S_Relation,
      SymbolKind.S_Factor,
      SymbolKind.S_FuncLiteral,
      SymbolKind.S_FuncDeclarationParameters,
      SymbolKind.S_BlockStatement,
      SymbolKind.S_ReturnStatement,
      SymbolKind.S_AddExpression,
      SymbolKind.S_MultExpression,
      SymbolKind.S_UnaryExpression,
      SymbolKind.S_Term,
      SymbolKind.S_TypeLiteral,
      SymbolKind.S_Reference,
      SymbolKind.S_Tail,
      SymbolKind.S_ArrayTail,
      SymbolKind.S_TupleTail,
      SymbolKind.S_FuncTail,
      SymbolKind.S_CallArgs,
      SymbolKind.S_Array,
      SymbolKind.S_ArrayContent,
      SymbolKind.S_Tuple,
      SymbolKind.S_TupleContent,
      SymbolKind.S_BoolOp,
      SymbolKind.S_Compare
    };

    static final SymbolKind get(int code) {
      return values_[code];
    }

    public final int getCode() {
      return this.yycode_;
    }

    /* YYNAMES_[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
       First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yynames_ = yynames_init();
  private static final String[] yynames_init()
  {
    return new String[]
    {
  "end of file", "error", "invalid token", "INT", "REAL", "STRING",
  "IDENT", "TYPE", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "ASSIGN", "AND",
  "OR", "XOR", "GT", "LT", "GEQ", "LEQ", "EQ", "NOT_EQ", "SEMICOLON",
  "NEWLINE", "LPAREN", "RPAREN", "COMMA", "DOT", "DOTDOT", "LBRACE",
  "RBRACE", "LBRACKET", "RBRACKET", "VAR", "RETURN", "FUNCTION", "ARROW",
  "IS", "END", "PRINT", "IF", "THEN", "ELSE", "TRUE", "FALSE", "WHILE",
  "FOR", "LOOP", "IN", "NOT", "$accept", "CompilationUnit", "Statements",
  "Statement", "IfStatement", "AssignmentStatement", "WhileStatement",
  "ForStatement", "VarStatement", "PrintStatement", "PrintArgs",
  "ExpressionStatement", "LineBreak", "Expression", "Relation", "Factor",
  "FuncLiteral", "FuncDeclarationParameters", "BlockStatement",
  "ReturnStatement", "AddExpression", "MultExpression", "UnaryExpression",
  "Term", "TypeLiteral", "Reference", "Tail", "ArrayTail", "TupleTail",
  "FuncTail", "CallArgs", "Array", "ArrayContent", "Tuple", "TupleContent",
  "BoolOp", "Compare", null
    };
  }

    /* The user-facing name of this symbol.  */
    public final String getName() {
      return yynames_[yycode_];
    }
  };


  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Parser</tt>.
   */
  public interface Lexer {
    /* Token kinds.  */
    /** Token "end of file", to be returned by the scanner.  */
    static final int YYEOF = 0;
    /** Token error, to be returned by the scanner.  */
    static final int YYerror = 256;
    /** Token "invalid token", to be returned by the scanner.  */
    static final int YYUNDEF = 257;
    /** Token INT, to be returned by the scanner.  */
    static final int INT = 258;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 259;
    /** Token STRING, to be returned by the scanner.  */
    static final int STRING = 260;
    /** Token IDENT, to be returned by the scanner.  */
    static final int IDENT = 261;
    /** Token TYPE, to be returned by the scanner.  */
    static final int TYPE = 262;
    /** Token PLUS, to be returned by the scanner.  */
    static final int PLUS = 263;
    /** Token MINUS, to be returned by the scanner.  */
    static final int MINUS = 264;
    /** Token MULTIPLY, to be returned by the scanner.  */
    static final int MULTIPLY = 265;
    /** Token DIVIDE, to be returned by the scanner.  */
    static final int DIVIDE = 266;
    /** Token ASSIGN, to be returned by the scanner.  */
    static final int ASSIGN = 267;
    /** Token AND, to be returned by the scanner.  */
    static final int AND = 268;
    /** Token OR, to be returned by the scanner.  */
    static final int OR = 269;
    /** Token XOR, to be returned by the scanner.  */
    static final int XOR = 270;
    /** Token GT, to be returned by the scanner.  */
    static final int GT = 271;
    /** Token LT, to be returned by the scanner.  */
    static final int LT = 272;
    /** Token GEQ, to be returned by the scanner.  */
    static final int GEQ = 273;
    /** Token LEQ, to be returned by the scanner.  */
    static final int LEQ = 274;
    /** Token EQ, to be returned by the scanner.  */
    static final int EQ = 275;
    /** Token NOT_EQ, to be returned by the scanner.  */
    static final int NOT_EQ = 276;
    /** Token SEMICOLON, to be returned by the scanner.  */
    static final int SEMICOLON = 277;
    /** Token NEWLINE, to be returned by the scanner.  */
    static final int NEWLINE = 278;
    /** Token LPAREN, to be returned by the scanner.  */
    static final int LPAREN = 279;
    /** Token RPAREN, to be returned by the scanner.  */
    static final int RPAREN = 280;
    /** Token COMMA, to be returned by the scanner.  */
    static final int COMMA = 281;
    /** Token DOT, to be returned by the scanner.  */
    static final int DOT = 282;
    /** Token DOTDOT, to be returned by the scanner.  */
    static final int DOTDOT = 283;
    /** Token LBRACE, to be returned by the scanner.  */
    static final int LBRACE = 284;
    /** Token RBRACE, to be returned by the scanner.  */
    static final int RBRACE = 285;
    /** Token LBRACKET, to be returned by the scanner.  */
    static final int LBRACKET = 286;
    /** Token RBRACKET, to be returned by the scanner.  */
    static final int RBRACKET = 287;
    /** Token VAR, to be returned by the scanner.  */
    static final int VAR = 288;
    /** Token RETURN, to be returned by the scanner.  */
    static final int RETURN = 289;
    /** Token FUNCTION, to be returned by the scanner.  */
    static final int FUNCTION = 290;
    /** Token ARROW, to be returned by the scanner.  */
    static final int ARROW = 291;
    /** Token IS, to be returned by the scanner.  */
    static final int IS = 292;
    /** Token END, to be returned by the scanner.  */
    static final int END = 293;
    /** Token PRINT, to be returned by the scanner.  */
    static final int PRINT = 294;
    /** Token IF, to be returned by the scanner.  */
    static final int IF = 295;
    /** Token THEN, to be returned by the scanner.  */
    static final int THEN = 296;
    /** Token ELSE, to be returned by the scanner.  */
    static final int ELSE = 297;
    /** Token TRUE, to be returned by the scanner.  */
    static final int TRUE = 298;
    /** Token FALSE, to be returned by the scanner.  */
    static final int FALSE = 299;
    /** Token WHILE, to be returned by the scanner.  */
    static final int WHILE = 300;
    /** Token FOR, to be returned by the scanner.  */
    static final int FOR = 301;
    /** Token LOOP, to be returned by the scanner.  */
    static final int LOOP = 302;
    /** Token IN, to be returned by the scanner.  */
    static final int IN = 303;
    /** Token NOT, to be returned by the scanner.  */
    static final int NOT = 304;

    /** Deprecated, use YYEOF instead.  */
    public static final int EOF = YYEOF;


    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Ast.Node getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex() throws java.io.IOException;

    /**
     * Emit an errorin a user-defined way.
     *
     *
     * @param msg The string for the error message.
     */
     void yyerror(String msg);


  }


  /**
   * The object doing lexical analysis for us.
   */
  private Lexer yylexer;





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser(Lexer yylexer)
  {

    this.yylexer = yylexer;

  }



  private int yynerrs = 0;

  /**
   * The number of syntax errors so far.
   */
  public final int getNumberOfErrors() { return yynerrs; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror(String msg) {
      yylexer.yyerror(msg);
  }



  private final class YYStack {
    private int[] stateStack = new int[16];
    private Ast.Node[] valueStack = new Ast.Node[16];

    public int size = 16;
    public int height = -1;

    public final void push(int state, Ast.Node value) {
      height++;
      if (size == height) {
        int[] newStateStack = new int[size * 2];
        System.arraycopy(stateStack, 0, newStateStack, 0, height);
        stateStack = newStateStack;

        Ast.Node[] newValueStack = new Ast.Node[size * 2];
        System.arraycopy(valueStack, 0, newValueStack, 0, height);
        valueStack = newValueStack;

        size *= 2;
      }

      stateStack[height] = state;
      valueStack[height] = value;
    }

    public final void pop() {
      pop(1);
    }

    public final void pop(int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (0 < num) {
        java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt(int i) {
      return stateStack[height - i];
    }

    public final Ast.Node valueAt(int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print(java.io.PrintStream out) {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++) {
        out.print(' ');
        out.print(stateStack[i]);
      }
      out.println();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yyLRGotoState(int yystate, int yysym) {
    int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
    if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - YYNTOKENS_];
  }

  private int yyaction(int yyn, YYStack yystack, int yylen)
  {
    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    Ast.Node yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

    switch (yyn)
      {
          case 2: /* CompilationUnit: %empty  */
  if (yyn == 2)
    /* "parser.y":108  */
                 {yyval = null;};
  break;


  case 3: /* CompilationUnit: Statements  */
  if (yyn == 3)
    /* "parser.y":109  */
                     {
		var prog = (Ast.Program)yystack.valueAt (0);
		this.setRoot(prog);
	};
  break;


  case 4: /* Statements: Statement  */
  if (yyn == 4)
    /* "parser.y":116  */
                    {
		var statements = new ArrayList<Ast.Statement>();
		var exprStmt = (Ast.Statement)yystack.valueAt (0);
		statements.add(exprStmt);

		yyval = new Ast.Program(statements);
	};
  break;


  case 5: /* Statements: Statements Statement  */
  if (yyn == 5)
    /* "parser.y":123  */
                               {
		var program = (Ast.Program)yystack.valueAt (1);
		var statements = program.getStatements();              
		var exprStmt = (Ast.Statement)yystack.valueAt (0); 
		statements.add(exprStmt);                                  

		yyval = new Ast.Program(statements);
	};
  break;


  case 14: /* IfStatement: IF Expression THEN BlockStatement END LineBreak  */
  if (yyn == 14)
    /* "parser.y":145  */
                                                          {
		yyval = new Ast.IfStatement((Ast.Expression)yystack.valueAt (4), (Ast.BlockStatement)yystack.valueAt (2));
	};
  break;


  case 15: /* IfStatement: IF Expression THEN BlockStatement ELSE BlockStatement END LineBreak  */
  if (yyn == 15)
    /* "parser.y":148  */
                                                                              {
		yyval = new Ast.IfStatement((Ast.Expression)yystack.valueAt (6), (Ast.BlockStatement)yystack.valueAt (4), (Ast.BlockStatement)yystack.valueAt (2));
	};
  break;


  case 16: /* AssignmentStatement: Reference ASSIGN Expression LineBreak  */
  if (yyn == 16)
    /* "parser.y":154  */
                                                {
		if (yystack.valueAt (3) instanceof Ast.CallExpression) throw new IllegalArgumentException("Cannot Assign to a function call");
		if (yystack.valueAt (3) instanceof Ast.Identifier){ yyval = new Ast.ExpressionStatement(new Ast.InfixExpression(":=", (Ast.Identifier) yystack.valueAt (3), (Ast.Expression) yystack.valueAt (1)));}
		else {
			var ref = (Ast.IndexLiteral) yystack.valueAt (3);
			try {
				if (ref.tokenLiteral().equals("[]")) { yyval = new Ast.ExpressionStatement(new Ast.InfixExpression(":=", (Ast.Expression) yystack.valueAt (3), (Ast.Expression) yystack.valueAt (1)));}
				else {throw new IllegalArgumentException();}
			} catch (Exception e) {
				throw new IllegalArgumentException("Assignment is only available to variables and array elements");
			}
	}
	};
  break;


  case 17: /* WhileStatement: WHILE Expression LOOP BlockStatement END LineBreak  */
  if (yyn == 17)
    /* "parser.y":169  */
                                                             {
		yyval = new Ast.WhileStatement((Ast.Expression)yystack.valueAt (4), (Ast.BlockStatement)yystack.valueAt (2));
	};
  break;


  case 18: /* ForStatement: FOR IDENT IN Expression DOTDOT Expression LOOP BlockStatement END LineBreak  */
  if (yyn == 18)
    /* "parser.y":175  */
                                                                                      {
		yyval = new Ast.ForLiteral((Ast.Identifier) yystack.valueAt (8), new Ast.InfixExpression("..", (Ast.Expression) yystack.valueAt (6), (Ast.Expression) yystack.valueAt (4)), (Ast.BlockStatement) yystack.valueAt (2));
	};
  break;


  case 19: /* ForStatement: FOR IDENT IN Expression LOOP BlockStatement END LineBreak  */
  if (yyn == 19)
    /* "parser.y":178  */
                                                                    {
		yyval = new Ast.ForLiteral((Ast.Identifier) yystack.valueAt (6), (Ast.Expression) yystack.valueAt (4), (Ast.BlockStatement) yystack.valueAt (2));
	};
  break;


  case 20: /* VarStatement: VAR IDENT LineBreak  */
  if (yyn == 20)
    /* "parser.y":184  */
                              {
		yyval = new Ast.VarStatement((Ast.Identifier) yystack.valueAt (1), null);
		};
  break;


  case 21: /* VarStatement: VAR IDENT ASSIGN Expression LineBreak  */
  if (yyn == 21)
    /* "parser.y":187  */
                                                {yyval = new Ast.VarStatement((Ast.Identifier) yystack.valueAt (3), (Ast.Expression) yystack.valueAt (1));};
  break;


  case 22: /* PrintStatement: PRINT PrintArgs LineBreak  */
  if (yyn == 22)
    /* "parser.y":191  */
                                    {yyval = yystack.valueAt (1);};
  break;


  case 23: /* PrintArgs: Expression  */
  if (yyn == 23)
    /* "parser.y":195  */
                     {
		var args = new ArrayList<Ast.Expression>();
		args.add((Ast.Expression) yystack.valueAt (0));
		yyval = new Ast.PrintLiteral(args);
	};
  break;


  case 24: /* PrintArgs: PrintArgs COMMA Expression  */
  if (yyn == 24)
    /* "parser.y":200  */
                                     {
		var stmt = (Ast.PrintLiteral) yystack.valueAt (2);
		stmt.addArgument((Ast.Expression) yystack.valueAt (0));
		yyval = stmt;
	};
  break;


  case 25: /* ExpressionStatement: Expression LineBreak  */
  if (yyn == 25)
    /* "parser.y":208  */
                               { yyval = new Ast.ExpressionStatement((Ast.Expression)yystack.valueAt (1)); };
  break;


  case 28: /* Expression: Relation BoolOp Relation  */
  if (yyn == 28)
    /* "parser.y":217  */
                                   {var exp = (Ast.InfixExpression) yystack.valueAt (1); exp.setLeft((Ast.Expression) yystack.valueAt (2)); exp.setRight((Ast.Expression) yystack.valueAt (0)); yyval = exp;};
  break;


  case 31: /* Relation: Factor Compare Factor  */
  if (yyn == 31)
    /* "parser.y":223  */
                                {var exp = (Ast.InfixExpression) yystack.valueAt (1); exp.setLeft((Ast.Expression) yystack.valueAt (2)); exp.setRight((Ast.Expression) yystack.valueAt (0)); yyval = exp;};
  break;


  case 33: /* FuncLiteral: FUNCTION LPAREN FuncDeclarationParameters RPAREN IS BlockStatement END  */
  if (yyn == 33)
    /* "parser.y":231  */
                                                                                 {
		var func = (Ast.FunctionLiteral) yystack.valueAt (4);
		var body = (Ast.BlockStatement) yystack.valueAt (1);
		func.setBody(body);
		yyval = func;
	};
  break;


  case 34: /* FuncLiteral: FUNCTION LPAREN FuncDeclarationParameters RPAREN ARROW Expression  */
  if (yyn == 34)
    /* "parser.y":237  */
                                                                            {
		var func = (Ast.FunctionLiteral) yystack.valueAt (3);
		var statements = new ArrayList<Ast.Statement>();
		statements.add(new Ast.ReturnStatement((Ast.Expression) yystack.valueAt (0)));
		func.setBody(new Ast.BlockStatement(statements));
		yyval = func;
	};
  break;


  case 35: /* FuncDeclarationParameters: %empty  */
  if (yyn == 35)
    /* "parser.y":247  */
                 {yyval = new Ast.FunctionLiteral(new ArrayList<Ast.Identifier>());};
  break;


  case 36: /* FuncDeclarationParameters: IDENT  */
  if (yyn == 36)
    /* "parser.y":248  */
                {
		var idt = new ArrayList<Ast.Identifier>();
		idt.add((Ast.Identifier) yystack.valueAt (0));
		yyval = new Ast.FunctionLiteral(idt);
	};
  break;


  case 37: /* FuncDeclarationParameters: FuncDeclarationParameters COMMA IDENT  */
  if (yyn == 37)
    /* "parser.y":253  */
                                                {
		var func = (Ast.FunctionLiteral) yystack.valueAt (2);
		func.addParameter((Ast.Identifier) yystack.valueAt (0));
		yyval = func;
	};
  break;


  case 38: /* BlockStatement: Statement  */
  if (yyn == 38)
    /* "parser.y":262  */
                    {
		var statements = new ArrayList<Ast.Statement>();
		var stmt = (Ast.Statement) yystack.valueAt (0);
		statements.add(stmt);

		yyval = new Ast.BlockStatement(statements);
	};
  break;


  case 39: /* BlockStatement: BlockStatement Statement  */
  if (yyn == 39)
    /* "parser.y":269  */
                                   {
		var blockStatement = (Ast.BlockStatement) yystack.valueAt (1);
		blockStatement.addStatement((Ast.Statement) yystack.valueAt (0));

		yyval = blockStatement;
	};
  break;


  case 40: /* ReturnStatement: RETURN LineBreak  */
  if (yyn == 40)
    /* "parser.y":278  */
                           {yyval = new Ast.ReturnStatement(null);};
  break;


  case 41: /* ReturnStatement: RETURN Expression LineBreak  */
  if (yyn == 41)
    /* "parser.y":279  */
                                      {yyval = new Ast.ReturnStatement((Ast.Expression) yystack.valueAt (1));};
  break;


  case 43: /* AddExpression: AddExpression PLUS MultExpression  */
  if (yyn == 43)
    /* "parser.y":284  */
                                            {
		var expr = (Ast.InfixExpression)yystack.valueAt (1);

		expr.setLeft((Ast.Expression)yystack.valueAt (2));
		expr.setRight((Ast.Expression)yystack.valueAt (0));

		yyval = expr;
	};
  break;


  case 44: /* AddExpression: AddExpression MINUS MultExpression  */
  if (yyn == 44)
    /* "parser.y":292  */
                                             {
		var expr = (Ast.InfixExpression)yystack.valueAt (1);

		expr.setLeft((Ast.Expression)yystack.valueAt (2));
		expr.setRight((Ast.Expression)yystack.valueAt (0));

		yyval = expr;
	};
  break;


  case 46: /* MultExpression: MultExpression MULTIPLY UnaryExpression  */
  if (yyn == 46)
    /* "parser.y":304  */
                                                  {
		var expr = (Ast.InfixExpression)yystack.valueAt (1);

		expr.setLeft((Ast.Expression)yystack.valueAt (2));
		expr.setRight((Ast.Expression)yystack.valueAt (0));

		yyval = expr;
	};
  break;


  case 47: /* MultExpression: MultExpression DIVIDE UnaryExpression  */
  if (yyn == 47)
    /* "parser.y":312  */
                                                {
		var expr = (Ast.InfixExpression)yystack.valueAt (1);

		expr.setLeft((Ast.Expression)yystack.valueAt (2));
		expr.setRight((Ast.Expression)yystack.valueAt (0));

		yyval = expr;
	};
  break;


  case 49: /* UnaryExpression: LPAREN AddExpression RPAREN  */
  if (yyn == 49)
    /* "parser.y":324  */
                                      {yyval = yystack.valueAt (1);};
  break;


  case 50: /* UnaryExpression: MINUS Term  */
  if (yyn == 50)
    /* "parser.y":325  */
                     {
		var expr = (Ast.PrefixExpression) yystack.valueAt (1);

		expr.setRight((Ast.Expression)yystack.valueAt (0));
		yyval = expr;
	};
  break;


  case 51: /* UnaryExpression: NOT Term  */
  if (yyn == 51)
    /* "parser.y":331  */
                   {
		yyval = new Ast.PrefixExpression("not", ((Ast.Expression) yystack.valueAt (0)));
	};
  break;


  case 55: /* Term: Reference IS TypeLiteral  */
  if (yyn == 55)
    /* "parser.y":340  */
                                   {yyval = new Ast.InfixExpression("is", (Ast.Expression) yystack.valueAt (2), (Ast.TypeLiteral) yystack.valueAt (0));};
  break;


  case 62: /* TypeLiteral: FUNCTION  */
  if (yyn == 62)
    /* "parser.y":350  */
                   {yyval = new Ast.TypeLiteral(new Token("func", TokenType.TYPE), "func");};
  break;


  case 63: /* TypeLiteral: LBRACKET RBRACKET  */
  if (yyn == 63)
    /* "parser.y":351  */
                            {yyval = new Ast.TypeLiteral(new Token("[]", TokenType.TYPE), "array");};
  break;


  case 64: /* TypeLiteral: LBRACE RBRACE  */
  if (yyn == 64)
    /* "parser.y":352  */
                        {yyval = new Ast.TypeLiteral(new Token("{}", TokenType.TYPE), "tuple");};
  break;


  case 66: /* Reference: Reference Tail  */
  if (yyn == 66)
    /* "parser.y":356  */
                         {
		try {
			var idx = (Ast.IndexLiteral) yystack.valueAt (0);
			idx.setLeft((Ast.Expression) yystack.valueAt (1));
			yyval = idx; 
		} catch(Exception e) {
			try {
				var callExp = (Ast.CallExpression) yystack.valueAt (0);
				callExp.addFunction((Ast.Expression) yystack.valueAt (1));
				yyval = callExp;
			} catch (Exception f) {
				yyval = yystack.valueAt (1);
			}
		}
	};
  break;


  case 70: /* ArrayTail: LBRACKET INT RBRACKET  */
  if (yyn == 70)
    /* "parser.y":379  */
                                {var idx = new Ast.IndexLiteral((Ast.IntegerLiteral) yystack.valueAt (1)); idx.setToken(new Token("[]", TokenType.LBRACKET)); yyval = idx;};
  break;


  case 71: /* TupleTail: DOT INT  */
  if (yyn == 71)
    /* "parser.y":383  */
                  {yyval = new Ast.IndexLiteral((Ast.IntegerLiteral) yystack.valueAt (0));};
  break;


  case 72: /* TupleTail: DOT IDENT  */
  if (yyn == 72)
    /* "parser.y":384  */
                    {yyval = new Ast.IndexLiteral((Ast.Identifier) yystack.valueAt (0));};
  break;


  case 73: /* FuncTail: LPAREN CallArgs RPAREN  */
  if (yyn == 73)
    /* "parser.y":388  */
                                 {yyval = (Ast.CallExpression) yystack.valueAt (1);};
  break;


  case 74: /* CallArgs: %empty  */
  if (yyn == 74)
    /* "parser.y":392  */
                 {yyval = new Ast.CallExpression();};
  break;


  case 75: /* CallArgs: Expression  */
  if (yyn == 75)
    /* "parser.y":393  */
                     {
		var callExp = new Ast.CallExpression();
		callExp.addArgument((Ast.Expression) yystack.valueAt (0));
		yyval = callExp;
	};
  break;


  case 76: /* CallArgs: CallArgs COMMA Expression  */
  if (yyn == 76)
    /* "parser.y":398  */
                                    {
		var callExp = (Ast.CallExpression) yystack.valueAt (2);
		callExp.addArgument((Ast.Expression) yystack.valueAt (0));
		yyval = callExp;
	};
  break;


  case 77: /* Array: LBRACKET RBRACKET  */
  if (yyn == 77)
    /* "parser.y":406  */
                            {yyval = new Ast.ArrayLiteral();};
  break;


  case 78: /* Array: LBRACKET ArrayContent RBRACKET  */
  if (yyn == 78)
    /* "parser.y":407  */
                                         {yyval = (Ast.ArrayLiteral) yystack.valueAt (1);};
  break;


  case 79: /* ArrayContent: Expression  */
  if (yyn == 79)
    /* "parser.y":411  */
                     {
		var arr = new Ast.ArrayLiteral();
		arr.addExpression((Ast.Expression) yystack.valueAt (0));
		yyval = arr;
	};
  break;


  case 80: /* ArrayContent: ArrayContent COMMA Expression  */
  if (yyn == 80)
    /* "parser.y":416  */
                                        {
		var arr = (Ast.ArrayLiteral) yystack.valueAt (2);
		arr.addExpression((Ast.Expression) yystack.valueAt (0));
		yyval = arr;
	};
  break;


  case 81: /* Tuple: LBRACE RBRACE  */
  if (yyn == 81)
    /* "parser.y":424  */
                        {yyval = new Ast.TupleLiteral();};
  break;


  case 82: /* Tuple: LBRACE TupleContent RBRACE  */
  if (yyn == 82)
    /* "parser.y":425  */
                                     {yyval = (Ast.TupleLiteral) yystack.valueAt (1);};
  break;


  case 83: /* TupleContent: Expression  */
  if (yyn == 83)
    /* "parser.y":429  */
                     {
		var tpl = new Ast.TupleLiteral();
		tpl.addExpression((Ast.Expression) yystack.valueAt (0));
		yyval = tpl;
	};
  break;


  case 84: /* TupleContent: IDENT ASSIGN Expression  */
  if (yyn == 84)
    /* "parser.y":434  */
                                  {
		var tpl = new Ast.TupleLiteral();
		tpl.addAssignment((Ast.Identifier) yystack.valueAt (2), (Ast.Expression) yystack.valueAt (0));
		yyval = tpl;
	};
  break;


  case 85: /* TupleContent: TupleContent COMMA Expression  */
  if (yyn == 85)
    /* "parser.y":439  */
                                        {
		var tpl = (Ast.TupleLiteral) yystack.valueAt (2);
		tpl.addExpression((Ast.Expression) yystack.valueAt (0));
		yyval = tpl;
	};
  break;


  case 86: /* TupleContent: TupleContent COMMA IDENT ASSIGN Expression  */
  if (yyn == 86)
    /* "parser.y":444  */
                                                     {
		var tpl = (Ast.TupleLiteral) yystack.valueAt (4);
		if (tpl.getPairs().get((Ast.Identifier) yystack.valueAt (2)) != null) {
			throw new IllegalArgumentException("duplicate key in tuple literal" + ((Ast.Identifier) yystack.valueAt (2)).toString());
		}
		tpl.addAssignment((Ast.Identifier) yystack.valueAt (2), (Ast.Expression) yystack.valueAt (0));
		yyval = tpl;
	};
  break;


  case 87: /* BoolOp: AND  */
  if (yyn == 87)
    /* "parser.y":455  */
              {yyval = new Ast.InfixExpression("and");};
  break;


  case 88: /* BoolOp: OR  */
  if (yyn == 88)
    /* "parser.y":456  */
             {yyval = new Ast.InfixExpression("or");};
  break;


  case 89: /* BoolOp: XOR  */
  if (yyn == 89)
    /* "parser.y":457  */
              {yyval = new Ast.InfixExpression("xor");};
  break;


  case 90: /* BoolOp: NOT_EQ  */
  if (yyn == 90)
    /* "parser.y":458  */
                 {yyval = new Ast.InfixExpression("!=");};
  break;


  case 91: /* Compare: LT  */
  if (yyn == 91)
    /* "parser.y":462  */
             {yyval = new Ast.InfixExpression("<");};
  break;


  case 92: /* Compare: GT  */
  if (yyn == 92)
    /* "parser.y":463  */
             {yyval = new Ast.InfixExpression(">");};
  break;


  case 93: /* Compare: LEQ  */
  if (yyn == 93)
    /* "parser.y":464  */
              {yyval = new Ast.InfixExpression("<=");};
  break;


  case 94: /* Compare: GEQ  */
  if (yyn == 94)
    /* "parser.y":465  */
              {yyval = new Ast.InfixExpression(">=");};
  break;


  case 95: /* Compare: EQ  */
  if (yyn == 95)
    /* "parser.y":466  */
             {yyval = new Ast.InfixExpression("=");};
  break;



/* "app/src/main/java/org/projectD/interpreter/parser/Parser.java":1243  */

        default: break;
      }

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }




  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse() throws java.io.IOException

  {


    /* Lookahead token kind.  */
    int yychar = YYEMPTY_;
    /* Lookahead symbol kind.  */
    SymbolKind yytoken = null;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;



    /* Semantic value of the lookahead.  */
    Ast.Node yylval = null;



    yyerrstatus_ = 0;
    yynerrs = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval);



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:

        /* Accept?  */
        if (yystate == YYFINAL_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyPactValueIsDefault (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == YYEMPTY_)
          {

            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);

        if (yytoken == SymbolKind.S_YYerror)
          {
            // The scanner already issued an error message, process directly
            // to error recovery.  But do not keep the error token as
            // lookahead, it is too special and may lead us to an endless
            // loop in error recovery. */
            yychar = Lexer.YYUNDEF;
            yytoken = SymbolKind.S_YYUNDEF;
            label = YYERRLAB1;
          }
        else
          {
            /* If the proper action on seeing token YYTOKEN is to reduce or to
               detect an error, take that action.  */
            yyn += yytoken.getCode();
            if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
              label = YYDEFAULT;
            }

            /* <= 0 means reduce or error.  */
            else if ((yyn = yytable_[yyn]) <= 0)
              {
                if (yyTableValueIsError(yyn)) {
                  label = YYERRLAB;
                } else {
                  yyn = -yyn;
                  label = YYREDUCE;
                }
              }

            else
              {
                /* Shift the lookahead token.  */
                /* Discard the token being shifted.  */
                yychar = YYEMPTY_;

                /* Count tokens shifted since error; after three, turn off error
                   status.  */
                if (yyerrstatus_ > 0)
                  --yyerrstatus_;

                yystate = yyn;
                yystack.push(yystate, yylval);
                label = YYNEWSTATE;
              }
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction(yyn, yystack, yylen);
        yystate = yystack.stateAt(0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs;
            if (yychar == YYEMPTY_)
              yytoken = null;
            yyreportSyntaxError(new Context(this, yystack, yytoken));
          }

        if (yyerrstatus_ == 3)
          {
            /* If just tried and failed to reuse lookahead token after an
               error, discard it.  */

            if (yychar <= Lexer.YYEOF)
              {
                /* Return failure if at end of input.  */
                if (yychar == Lexer.YYEOF)
                  return false;
              }
            else
              yychar = YYEMPTY_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt(0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        // Pop stack until we find a state that shifts the error token.
        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yyPactValueIsDefault (yyn))
              {
                yyn += SymbolKind.S_YYerror.getCode();
                if (0 <= yyn && yyn <= YYLAST_
                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode())
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;


            yystack.pop ();
            yystate = yystack.stateAt(0);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  /**
   * Information needed to get the list of expected tokens and to forge
   * a syntax error diagnostic.
   */
  public static final class Context {
    Context(Parser parser, YYStack stack, SymbolKind token) {
      yyparser = parser;
      yystack = stack;
      yytoken = token;
    }

    private Parser yyparser;
    private YYStack yystack;


    /**
     * The symbol kind of the lookahead token.
     */
    public final SymbolKind getToken() {
      return yytoken;
    }

    private SymbolKind yytoken;
    static final int NTOKENS = Parser.YYNTOKENS_;

    /**
     * Put in YYARG at most YYARGN of the expected tokens given the
     * current YYCTX, and return the number of tokens stored in YYARG.  If
     * YYARG is null, return the number of expected tokens (guaranteed to
     * be less than YYNTOKENS).
     */
    int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
      return getExpectedTokens (yyarg, 0, yyargn);
    }

    int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
      int yycount = yyoffset;
      int yyn = yypact_[this.yystack.stateAt(0)];
      if (!yyPactValueIsDefault(yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative
             indexes in YYCHECK.  In other words, skip the first
             -YYN actions for this state because they are default
             actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST_ - yyn + 1;
          int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
          for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck_[yyx + yyn] == yyx && yyx != SymbolKind.S_YYerror.getCode()
                && !yyTableValueIsError(yytable_[yyx + yyn]))
              {
                if (yyarg == null)
                  yycount += 1;
                else if (yycount == yyargn)
                  return 0; // FIXME: this is incorrect.
                else
                  yyarg[yycount++] = SymbolKind.get(yyx);
              }
        }
      if (yyarg != null && yycount == yyoffset && yyoffset < yyargn)
        yyarg[yycount] = null;
      return yycount - yyoffset;
    }
  }




  private int yysyntaxErrorArguments(Context yyctx, SymbolKind[] yyarg, int yyargn) {
    /* There are many possibilities here to consider:
       - If this state is a consistent state with a default action,
         then the only way this function was invoked is if the
         default action is an error action.  In that case, don't
         check for expected tokens because there are none.
       - The only way there can be no lookahead present (in tok) is
         if this state is a consistent state with a default action.
         Thus, detecting the absence of a lookahead is sufficient to
         determine that there is no unexpected or expected token to
         report.  In that case, just report a simple "syntax error".
       - Don't assume there isn't a lookahead just because this
         state is a consistent state with a default action.  There
         might have been a previous inconsistent state, consistent
         state with a non-default action, or user semantic action
         that manipulated yychar.  (However, yychar is currently out
         of scope during semantic actions.)
       - Of course, the expected token list depends on states to
         have correct lookahead information, and it depends on the
         parser not to perform extra reductions after fetching a
         lookahead from the scanner and before detecting a syntax
         error.  Thus, state merging (from LALR or IELR) and default
         reductions corrupt the expected token list.  However, the
         list is correct for canonical LR with one exception: it
         will still contain any token that will not be accepted due
         to an error action in a later state.
    */
    int yycount = 0;
    if (yyctx.getToken() != null)
      {
        if (yyarg != null)
          yyarg[yycount] = yyctx.getToken();
        yycount += 1;
        yycount += yyctx.getExpectedTokens(yyarg, 1, yyargn);
      }
    return yycount;
  }


  /**
   * Build and emit a "syntax error" message in a user-defined way.
   *
   * @param ctx  The context of the error.
   */
  private void yyreportSyntaxError(Context yyctx) {
      if (yyErrorVerbose) {
          final int argmax = 5;
          SymbolKind[] yyarg = new SymbolKind[argmax];
          int yycount = yysyntaxErrorArguments(yyctx, yyarg, argmax);
          String[] yystr = new String[yycount];
          for (int yyi = 0; yyi < yycount; ++yyi) {
              yystr[yyi] = yyarg[yyi].getName();
          }
          String yyformat;
          switch (yycount) {
              default:
              case 0: yyformat = "syntax error"; break;
              case 1: yyformat = "syntax error, unexpected {0}"; break;
              case 2: yyformat = "syntax error, unexpected {0}, expecting {1}"; break;
              case 3: yyformat = "syntax error, unexpected {0}, expecting {1} or {2}"; break;
              case 4: yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3}"; break;
              case 5: yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3} or {4}"; break;
          }
          yyerror(new MessageFormat(yyformat).format(yystr));
      } else {
          yyerror("syntax error");
      }
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yyPactValueIsDefault(int yyvalue) {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yyTableValueIsError(int yyvalue) {
    return yyvalue == yytable_ninf_;
  }

  private static final short yypact_ninf_ = -103;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
     402,  -103,  -103,  -103,  -103,   569,   561,   449,   459,    11,
     501,     9,   511,   511,  -103,  -103,   511,    29,   569,    50,
     402,  -103,  -103,  -103,  -103,  -103,  -103,  -103,  -103,    31,
      70,    81,  -103,  -103,     6,    59,  -103,  -103,    28,  -103,
    -103,  -103,   -19,    -6,    49,  -103,  -103,    21,  -103,  -103,
     -16,    34,  -103,  -103,    31,    57,    32,  -103,    47,    27,
      42,  -103,  -103,  -103,  -103,  -103,  -103,  -103,  -103,   561,
    -103,  -103,  -103,  -103,  -103,   561,   561,   561,   561,   561,
     511,   511,     3,    91,    14,  -103,  -103,  -103,  -103,  -103,
     511,   553,  -103,   511,  -103,   511,  -103,  -103,  -103,    55,
     511,  -103,   402,   402,   511,  -103,  -103,    59,    59,  -103,
    -103,    31,  -103,    61,  -103,  -103,    75,  -103,    79,    82,
    -103,  -103,  -103,   101,  -103,  -103,    31,    56,   112,  -103,
    -103,    33,   167,   -17,  -103,  -103,   511,  -103,  -103,  -103,
     511,  -103,   511,   402,  -103,    31,   402,  -103,    31,   511,
     402,  -103,  -103,  -103,   214,  -103,   261,  -103,    73,   308,
    -103,    31,   402,    31,  -103,   355,  -103,    31,  -103
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final byte[] yydefact_ = yydefact_init();
  private static final byte[] yydefact_init()
  {
    return new byte[]
    {
       2,    52,    53,    56,    65,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    57,    58,     0,     0,     0,     0,
       3,     4,     9,    13,    10,    12,     7,     8,     6,     0,
      27,    30,    29,    11,    32,    42,    45,    48,    54,    59,
      60,    50,    54,     0,    65,    81,    83,     0,    77,    79,
       0,     0,    26,    40,     0,    35,     0,    23,     0,     0,
       0,    51,     1,     5,    25,    87,    88,    89,    90,     0,
      92,    91,    94,    93,    95,     0,     0,     0,     0,     0,
       0,    74,     0,     0,     0,    66,    67,    68,    69,    49,
       0,     0,    82,     0,    78,     0,    20,    41,    36,     0,
       0,    22,     0,     0,     0,    28,    31,    43,    44,    46,
      47,     0,    75,     0,    71,    72,     0,    61,     0,     0,
      62,    55,    84,    65,    85,    80,     0,     0,     0,    24,
      38,     0,     0,     0,    16,    73,     0,    70,    64,    63,
       0,    21,     0,     0,    37,     0,     0,    39,     0,     0,
       0,    76,    86,    34,     0,    14,     0,    17,     0,     0,
      33,     0,     0,     0,    15,     0,    19,     0,    18
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte[] yypgoto_ = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
    -103,  -103,  -103,     4,  -103,  -103,  -103,  -103,  -103,  -103,
    -103,  -103,   -22,    15,    52,    53,  -103,  -103,  -102,  -103,
     116,    35,    38,     8,  -103,     0,  -103,  -103,  -103,  -103,
    -103,  -103,  -103,  -103,  -103,  -103,  -103
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,    19,    20,   130,    22,    23,    24,    25,    26,    27,
      56,    28,    53,    29,    30,    31,    32,    99,   131,    33,
      34,    35,    36,    37,   121,    42,    85,    86,    87,    88,
     113,    39,    50,    40,    47,    69,    75
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final short[] yytable_ = yytable_init();
  private static final short[] yytable_init()
  {
    return new short[]
    {
      38,   132,    76,    77,    21,    81,   114,    64,    82,   115,
      93,   149,    83,    41,    76,    77,    94,    51,    84,    89,
      38,   117,    46,    49,    63,    54,    61,    57,    58,    96,
     150,    59,    97,    55,   101,    60,     1,     2,     3,     4,
      80,   154,     5,   118,   156,   119,    95,    91,   159,   120,
      62,    92,    81,    52,    52,    82,    52,     6,   100,    83,
     165,    90,     7,    98,     8,    84,     9,    10,    11,    78,
      79,   145,    12,    13,   103,   146,    14,    15,    16,    17,
     127,   128,    18,    65,    66,    67,   135,   136,   102,   134,
     104,    68,   142,   143,   116,   111,   112,    70,    71,    72,
      73,    74,    38,    38,   141,   122,   124,   137,   125,   138,
     126,   107,   108,   140,   139,   129,   109,   110,   144,   133,
     162,   105,    43,   155,     0,     0,   157,     0,   106,     0,
       0,    38,    38,     0,     0,   147,   147,     0,     0,   164,
       0,   166,     0,    38,     0,   168,    38,     0,     0,     0,
      38,   151,     0,     0,    38,   152,    38,   153,   147,    38,
     147,     0,    38,   147,   158,    38,     0,     0,     0,   147,
       1,     2,     3,     4,     0,     0,     5,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     6,     0,     0,     0,     0,     7,     0,     8,     0,
       9,    10,    11,     0,     0,   148,    12,    13,     0,     0,
      14,    15,    16,    17,     0,     0,    18,     1,     2,     3,
       4,     0,     0,     5,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     6,     0,
       0,     0,     0,     7,     0,     8,     0,     9,    10,    11,
       0,     0,   160,    12,    13,     0,     0,    14,    15,    16,
      17,     0,     0,    18,     1,     2,     3,     4,     0,     0,
       5,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     6,     0,     0,     0,     0,
       7,     0,     8,     0,     9,    10,    11,     0,     0,   161,
      12,    13,     0,     0,    14,    15,    16,    17,     0,     0,
      18,     1,     2,     3,     4,     0,     0,     5,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     6,     0,     0,     0,     0,     7,     0,     8,
       0,     9,    10,    11,     0,     0,   163,    12,    13,     0,
       0,    14,    15,    16,    17,     0,     0,    18,     1,     2,
       3,     4,     0,     0,     5,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     6,
       0,     0,     0,     0,     7,     0,     8,     0,     9,    10,
      11,     0,     0,   167,    12,    13,     0,     0,    14,    15,
      16,    17,     0,     0,    18,     1,     2,     3,     4,     0,
       0,     5,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     6,     0,     0,     0,
       0,     7,     0,     8,     0,     9,    10,    11,     0,     0,
       0,    12,    13,     0,     0,    14,    15,    16,    17,     0,
       0,    18,     1,     2,     3,    44,     0,     0,     5,     0,
       0,     0,     1,     2,     3,     4,     0,     0,     5,     0,
       0,     0,     0,     6,     0,     0,     0,     0,     7,    45,
       8,     0,     0,     6,    11,     0,     0,     0,     7,     0,
       8,    48,    14,    15,    11,     0,     0,     0,    18,     0,
       0,     0,    14,    15,     1,     2,     3,     4,    18,     0,
       5,     0,     0,     0,     1,     2,     3,     4,     0,     0,
       5,     0,     0,    52,     0,     6,     0,     0,     0,     0,
       7,     0,     8,     0,     0,     6,    11,     0,     0,     0,
       7,     0,     8,     0,    14,    15,    11,     0,     0,     0,
      18,     0,     0,     0,    14,    15,     1,     2,     3,   123,
      18,     0,     5,     0,     1,     2,     3,     4,     0,     0,
       5,     0,     1,     2,     3,     4,     0,     6,     0,     0,
       0,     0,     7,     0,     8,     6,     0,     0,    11,     0,
       7,     0,     8,     0,     0,     0,    14,    15,     7,     0,
       8,     0,    18,     0,    14,    15,     0,     0,     0,     0,
      18,     0,    14,    15
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
       0,   103,     8,     9,     0,    24,     3,    29,    27,     6,
      26,    28,    31,     5,     8,     9,    32,     6,    37,    25,
      20,     7,     7,     8,    20,    10,    18,    12,    13,    51,
      47,    16,    54,    24,    56,     6,     3,     4,     5,     6,
      12,   143,     9,    29,   146,    31,    12,    26,   150,    35,
       0,    30,    24,    22,    22,    27,    22,    24,    26,    31,
     162,    12,    29,     6,    31,    37,    33,    34,    35,    10,
      11,    38,    39,    40,    47,    42,    43,    44,    45,    46,
      25,    26,    49,    13,    14,    15,    25,    26,    41,   111,
      48,    21,    36,    37,     3,    80,    81,    16,    17,    18,
      19,    20,   102,   103,   126,    90,    91,    32,    93,    30,
      95,    76,    77,    12,    32,   100,    78,    79,     6,   104,
      47,    69,     6,   145,    -1,    -1,   148,    -1,    75,    -1,
      -1,   131,   132,    -1,    -1,   131,   132,    -1,    -1,   161,
      -1,   163,    -1,   143,    -1,   167,   146,    -1,    -1,    -1,
     150,   136,    -1,    -1,   154,   140,   156,   142,   154,   159,
     156,    -1,   162,   159,   149,   165,    -1,    -1,    -1,   165,
       3,     4,     5,     6,    -1,    -1,     9,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    24,    -1,    -1,    -1,    -1,    29,    -1,    31,    -1,
      33,    34,    35,    -1,    -1,    38,    39,    40,    -1,    -1,
      43,    44,    45,    46,    -1,    -1,    49,     3,     4,     5,
       6,    -1,    -1,     9,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    24,    -1,
      -1,    -1,    -1,    29,    -1,    31,    -1,    33,    34,    35,
      -1,    -1,    38,    39,    40,    -1,    -1,    43,    44,    45,
      46,    -1,    -1,    49,     3,     4,     5,     6,    -1,    -1,
       9,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    24,    -1,    -1,    -1,    -1,
      29,    -1,    31,    -1,    33,    34,    35,    -1,    -1,    38,
      39,    40,    -1,    -1,    43,    44,    45,    46,    -1,    -1,
      49,     3,     4,     5,     6,    -1,    -1,     9,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    24,    -1,    -1,    -1,    -1,    29,    -1,    31,
      -1,    33,    34,    35,    -1,    -1,    38,    39,    40,    -1,
      -1,    43,    44,    45,    46,    -1,    -1,    49,     3,     4,
       5,     6,    -1,    -1,     9,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    24,
      -1,    -1,    -1,    -1,    29,    -1,    31,    -1,    33,    34,
      35,    -1,    -1,    38,    39,    40,    -1,    -1,    43,    44,
      45,    46,    -1,    -1,    49,     3,     4,     5,     6,    -1,
      -1,     9,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    24,    -1,    -1,    -1,
      -1,    29,    -1,    31,    -1,    33,    34,    35,    -1,    -1,
      -1,    39,    40,    -1,    -1,    43,    44,    45,    46,    -1,
      -1,    49,     3,     4,     5,     6,    -1,    -1,     9,    -1,
      -1,    -1,     3,     4,     5,     6,    -1,    -1,     9,    -1,
      -1,    -1,    -1,    24,    -1,    -1,    -1,    -1,    29,    30,
      31,    -1,    -1,    24,    35,    -1,    -1,    -1,    29,    -1,
      31,    32,    43,    44,    35,    -1,    -1,    -1,    49,    -1,
      -1,    -1,    43,    44,     3,     4,     5,     6,    49,    -1,
       9,    -1,    -1,    -1,     3,     4,     5,     6,    -1,    -1,
       9,    -1,    -1,    22,    -1,    24,    -1,    -1,    -1,    -1,
      29,    -1,    31,    -1,    -1,    24,    35,    -1,    -1,    -1,
      29,    -1,    31,    -1,    43,    44,    35,    -1,    -1,    -1,
      49,    -1,    -1,    -1,    43,    44,     3,     4,     5,     6,
      49,    -1,     9,    -1,     3,     4,     5,     6,    -1,    -1,
       9,    -1,     3,     4,     5,     6,    -1,    24,    -1,    -1,
      -1,    -1,    29,    -1,    31,    24,    -1,    -1,    35,    -1,
      29,    -1,    31,    -1,    -1,    -1,    43,    44,    29,    -1,
      31,    -1,    49,    -1,    43,    44,    -1,    -1,    -1,    -1,
      49,    -1,    43,    44
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,     3,     4,     5,     6,     9,    24,    29,    31,    33,
      34,    35,    39,    40,    43,    44,    45,    46,    49,    51,
      52,    53,    54,    55,    56,    57,    58,    59,    61,    63,
      64,    65,    66,    69,    70,    71,    72,    73,    75,    81,
      83,    73,    75,    70,     6,    30,    63,    84,    32,    63,
      82,     6,    22,    62,    63,    24,    60,    63,    63,    63,
       6,    73,     0,    53,    62,    13,    14,    15,    21,    85,
      16,    17,    18,    19,    20,    86,     8,     9,    10,    11,
      12,    24,    27,    31,    37,    76,    77,    78,    79,    25,
      12,    26,    30,    26,    32,    12,    62,    62,     6,    67,
      26,    62,    41,    47,    48,    64,    65,    71,    71,    72,
      72,    63,    63,    80,     3,     6,     3,     7,    29,    31,
      35,    74,    63,     6,    63,    63,    63,    25,    26,    63,
      53,    68,    68,    63,    62,    25,    26,    32,    30,    32,
      12,    62,    36,    37,     6,    38,    42,    53,    38,    28,
      47,    63,    63,    63,    68,    62,    68,    62,    63,    68,
      38,    38,    47,    38,    62,    68,    62,    38,    62
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    50,    51,    51,    52,    52,    53,    53,    53,    53,
      53,    53,    53,    53,    54,    54,    55,    56,    57,    57,
      58,    58,    59,    60,    60,    61,    62,    63,    63,    63,
      64,    64,    65,    66,    66,    67,    67,    67,    68,    68,
      69,    69,    70,    70,    70,    71,    71,    71,    72,    72,
      72,    72,    73,    73,    73,    73,    73,    73,    73,    73,
      73,    74,    74,    74,    74,    75,    75,    76,    76,    76,
      77,    78,    78,    79,    80,    80,    80,    81,    81,    82,
      82,    83,    83,    84,    84,    84,    84,    85,    85,    85,
      85,    86,    86,    86,    86,    86
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     0,     1,     1,     2,     1,     1,     1,     1,
       1,     1,     1,     1,     6,     8,     4,     6,    10,     8,
       3,     5,     3,     1,     3,     2,     1,     1,     3,     1,
       1,     3,     1,     7,     6,     0,     1,     3,     1,     2,
       2,     3,     1,     3,     3,     1,     3,     3,     1,     3,
       2,     2,     1,     1,     1,     3,     1,     1,     1,     1,
       1,     1,     1,     2,     2,     1,     2,     1,     1,     1,
       3,     2,     2,     3,     0,     1,     3,     2,     3,     1,
       3,     2,     3,     1,     3,     3,     5,     1,     1,     1,
       1,     1,     1,     1,     1,     1
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 304;
    if (t <= 0)
      return SymbolKind.S_YYEOF;
    else if (t <= code_max)
      return SymbolKind.get(yytranslate_table_[t]);
    else
      return SymbolKind.S_YYUNDEF;
  }
  private static final byte[] yytranslate_table_ = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49
    };
  }


  private static final int YYLAST_ = 613;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 62;
  private static final int YYNTOKENS_ = 50;

/* Unqualified %code blocks.  */
/* "parser.y":23  */

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

/* "app/src/main/java/org/projectD/interpreter/parser/Parser.java":2033  */

}
/* "parser.y":469  */



