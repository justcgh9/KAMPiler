package org.projectD.interpreter.repl;

import org.projectD.interpreter.lexer.Lexer;
import org.projectD.interpreter.token.Token;
import org.projectD.interpreter.token.TokenType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Repl {

    private static final String PROMPT = ">> ";

    public static void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(PROMPT);
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (line == null) {
                return;
            }

            Lexer lexer = new Lexer(line);

            Token tok;
            while ((tok = lexer.nextToken()).gTokenType() != TokenType.EOF) {
                System.out.println(tok.gTokenType().getValue() + " " + tok.gLiteral());
            }
        }
    }

    public static void main(String[] args) {
        start();
    }
}
