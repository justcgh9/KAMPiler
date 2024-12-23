package org.projectD.interpreter.token;

public enum TokenType {
    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    
    // Identifiers + literals
    IDENT("IDENT"),
    INT("INT"),
    STRING("STRING"),
    REAL("REAL"),

    TYPE("TYPE"),
    
    // Operators
    ASSIGN(":="),
    PLUS("+"),
    MINUS("-"),
    BANG("!"),
    ASTERISK("*"),
    SLASH("/"),
    DOT("."),
    DOTDOT(".."),
    
    LT("<"),
    GT(">"),
    LEQ(">="),
    GEQ("<="),
    ARROW("=>"),
    
    EQ("="),
    NOT_EQ("!="),
    
    // Delimiters
    COMMA(","),
    SEMICOLON(";"),
    COLON(":"),
    NEWLINE("\\n"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    
    // Keywords
    FUNCTION("FUNCTION"),
    VAR("VAR"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    ELSE("ELSE"),
    RETURN("RETURN"),
    END("END"),
    FOR("FOR"),
    WHILE("WHILE"),
    DO("DO"),
    LOOP("LOOP"),
    IN("IN"),
    IS("IS"),
    THEN("THEN"),
    AND("AND"),
    OR("OR"),
    XOR("XOR"),
    NOT("NOT"),
    PRINT("PRINT");
    
    private final String value;
    
    TokenType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
