package org.projectD.interpreter.lexer;

import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.token.TokenLookup;
import org.projectD.interpreter.token.TokenType;

public class Lexer {

    private final String input;
    private int position;     // current position in input (points to current char)
    private int readPosition; // current reading position in input (after current char)
    private char ch;          // current char under examination

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.readPosition = 0;
        this.ch = '\0';
        readChar();
    }

    public Token nextToken() {
        Token tok;
        skipWhitespace();

        switch (ch) {
            case '=':
                if (peekChar() == '>') {
                    char currentChar = ch;
                    readChar();
                    String literal = "" + currentChar + ch;
                    tok = new Token(literal, TokenType.ARROW);
                } else {
                    tok = newToken(TokenType.EQ, ch);
                }
                break;
            case '+':
                tok = newToken(TokenType.PLUS, ch);
                break;
            case '-':
                tok = newToken(TokenType.MINUS, ch);
                break;
            case '!':
                if (peekChar() == '=') {
                    char currentChar = ch;
                    readChar();
                    String literal = "" + currentChar + ch;
                    tok = new Token(literal, TokenType.NOT_EQ);
                } else {
                    tok = newToken(TokenType.BANG, ch);
                }
                break;
            case '/':
                tok = newToken(TokenType.SLASH, ch);
                break;
            case '*':
                tok = newToken(TokenType.ASTERISK, ch);
                break;
            case '<':
                if (peekChar() == '=') {
                    char currentChar = ch;
                    readChar();
                    String literal = "" + currentChar + ch;
                    tok = new Token(literal, TokenType.LEQ);
                } else {
                    tok = newToken(TokenType.LT, ch);
                }
                break;
            case '>':
                if (peekChar() == '=') {
                    char currentChar = ch;
                    readChar();
                    String literal = "" + currentChar + ch;
                    tok = new Token(literal, TokenType.GEQ);
                } else {
                    tok = newToken(TokenType.GT, ch);
                }
                break;
            case ';':
                tok = newToken(TokenType.SEMICOLON, ch);
                break;
            case ':':
                if (peekChar() == '='){
                    char currentChar = ch;
                    readChar();
                    String literal = "" + currentChar + ch;
                    tok = new Token(literal, TokenType.ASSIGN);
                } else {
                    tok = newToken(TokenType.COLON, ch);
                }
                break;
            case ',':
                tok = newToken(TokenType.COMMA, ch);
                break;
            case '{':
                tok = newToken(TokenType.LBRACE, ch);
                break;
            case '}':
                tok = newToken(TokenType.RBRACE, ch);
                break;
            case '(':
                tok = newToken(TokenType.LPAREN, ch);
                break;
            case ')':
                tok = newToken(TokenType.RPAREN, ch);
                break;
            case '"':
                tok = new Token(readString(), TokenType.STRING);
                break;
            case '[':
                tok = newToken(TokenType.LBRACKET, ch);
                break;
            case ']':
                tok = newToken(TokenType.RBRACKET, ch);
                break;
            case '.':
                tok = newToken(TokenType.DOT, ch);
                break;
            case '\n':
                tok = newToken(TokenType.NEWLINE, ch);
                break;
            case '\0':
                tok = new Token("", TokenType.EOF);
                break;
            default:
                if (isLetter(ch)) {
                    String literal = readIdentifier();
                    TokenType type = TokenLookup.lookupIdent(literal);
                    return new Token(literal, type);
                } else if (isDigit(ch)) {
                    int startPos = position;
                    boolean isReal = false;
                    while (isDigit(ch)) {
                        if (peekChar() == '.') {
                            if (isReal) {
                                break;
                            }

                            isReal = true;
                            readChar();
                            if (!isDigit(peekChar())) {
                                readChar();
                                break;
                            }
                        }
                        readChar();
                    }
                    return new Token(input.substring(startPos, position), isReal ? TokenType.REAL: TokenType.INT);
                } else {
                    tok = newToken(TokenType.ILLEGAL, ch);
                }
                break;
        }

        readChar();
        return tok;
    }

    private void skipWhitespace() {
        while (ch == ' ' || ch == '\t' || ch == '\r') {
            readChar();
        }
    }

    private void readChar() {
        if (readPosition >= input.length()) {
            ch = '\0';
        } else {
            ch = input.charAt(readPosition);
        }
        position = readPosition;
        readPosition++;
    }

    private char peekChar() {
        if (readPosition >= input.length()) {
            return '\0';
        } else {
            return input.charAt(readPosition);
        }
    }

    private String readIdentifier() {
        int startPos = position;
        readChar();
        while (isLetter(ch) || isDigit(ch)) {
            readChar();
        }
        return input.substring(startPos, position);
    }

    // private String readNumber() {
    //     int startPos = position;
    //     while (isDigit(ch)) {
    //         readChar();
    //     }
    //     return input.substring(startPos, position);
    // }

    private String readString() {
        int startPos = position + 1;
        do {
            readChar();
        } while (ch != '"' && ch != '\0');
        return "\"" + input.substring(startPos, position) + ch;
    }

    private boolean isLetter(char ch) {
        return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ch == '_';
    }

    private boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    private Token newToken(TokenType tokenType, char ch) {
        return new Token(String.valueOf(ch), tokenType);
    }
}
