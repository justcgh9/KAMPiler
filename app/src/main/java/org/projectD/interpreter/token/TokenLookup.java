package org.projectD.interpreter.token;
import java.util.HashMap;
import java.util.Map;

public class TokenLookup {

    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("func", TokenType.FUNCTION);
        keywords.put("var", TokenType.VAR);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("return", TokenType.RETURN);
        keywords.put("is", TokenType.IS);
        keywords.put("in", TokenType.IN);
        keywords.put("loop", TokenType.LOOP);
        keywords.put("while", TokenType.WHILE);
        keywords.put("for", TokenType.FOR);
        keywords.put("do", TokenType.DO);
        keywords.put("end", TokenType.END);
        keywords.put("int", TokenType.TYPE);
        keywords.put("str", TokenType.TYPE);
        keywords.put("real", TokenType.TYPE);
        keywords.put("bool", TokenType.TYPE);
        keywords.put("array", TokenType.TYPE);
        keywords.put("tuple", TokenType.TYPE);
        keywords.put("empty", TokenType.TYPE);
        keywords.put("print", TokenType.PRINT);
    }

    public static TokenType lookupIdent(String ident) {
        TokenType tokenType = keywords.get(ident);
        return (tokenType != null) ? tokenType : TokenType.IDENT;
    }

    public static void main(String[] args) {
        System.out.println(lookupIdent("func")); // Output: FUNCTION
        System.out.println(lookupIdent("var")); // Output: VAR
        System.out.println(lookupIdent("foobar")); // Output: IDENT
    }
}
