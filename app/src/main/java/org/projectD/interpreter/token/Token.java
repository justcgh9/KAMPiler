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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        var token = (Token) obj;
        return this.type.equals(token.gTokenType()) && this.literal.equals(token.gLiteral());
    }
}

