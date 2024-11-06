package org.projectD.interpreter.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.projectD.interpreter.ast.Ast;
import org.projectD.interpreter.ast.TreePrinter;
import org.projectD.interpreter.evaluator.Evaluator;
import org.projectD.interpreter.lexer.ParserLexer;
import org.projectD.interpreter.object.Environment;
import org.projectD.interpreter.parser.Parser;
import org.projectD.interpreter.repl.Repl;
import org.projectD.interpreter.semantic.SemanticAnalyzer;

public class Main {

    // public static void printTree(Ast.Node node, int depth) {
    //     // Print indentation based on the depth of the node
    //     for (int i = 0; i < depth; i++) {
    //         System.out.print("  "); // 2 spaces per depth level
    //     }
        
    //     // Print the node value
    //     System.out.println(node.value);

    //     // Recursively print all children
    //     for (Ast.Node child : node.children) {
    //         printTree(child, depth + 1);
    //     }
    // }

    public static void main(String[] args) {
        // String fileName = "./examples/first.d";
           
        if (args.length >= 0) {
            for (String fileName : args) {
                if (fileName.endsWith(".d")) {
                    try {
                        String content = new String(Files.readAllBytes(Paths.get(fileName)));

                        Parser parser = new Parser(new ParserLexer(content));

                        parser.parse();
                        var parsedResult = parser.getRoot();
                        // System.out.println(parsedResult);
                        TreePrinter tp = new TreePrinter();
                        SemanticAnalyzer sm = new SemanticAnalyzer();
                        // sm.analyze(parsedResult);
                        var some = parsedResult.toString();
                        Evaluator evaluator = new Evaluator();
                        var result = evaluator.eval(parsedResult, new Environment());
                        // System.out.println(result);
                        // parsedResult.accept(tp);

                        String outputFileName = fileName.replace(".d", ".o");

                        try (FileWriter writer = new FileWriter(new File(outputFileName))) {
                            writer.write(some);
                        }

                        System.out.println("Parsed content written to: " + outputFileName);
                    } catch (IOException e) {
                        System.err.println("Error reading or writing files: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Error during parsing: " + e.getMessage());
                    }
                } else {
                    System.err.println("File does not have the .d extension: " + fileName);
                }
            }
        } else {
            Repl.start();
        }
    }
}