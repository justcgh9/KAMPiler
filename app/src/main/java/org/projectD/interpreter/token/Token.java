package org.projectD.interpreter.token;

public class Token {

    private TokenType type;
    private String literal;

    public Token(String literal, TokenType type) {
        this.type = type;
        this.literal = literal;
    }

    public TokenType gTokenType() {
        return this.type;
    }

    public String gLiteral() {
        return this.literal;
    }
}

