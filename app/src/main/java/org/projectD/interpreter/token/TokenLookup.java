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
        keywords.put("end", TokenType.END);
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
