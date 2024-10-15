package org.projectD.interpreter.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.projectD.interpreter.lexer.ParserLexer;
import org.projectD.interpreter.parser.Parser;
// import org.projectD.interpreter.repl.Repl;

public class Main {
    public static void main(String[] args) {

        String fileName = "./examples/first.d";
        try {
                            String content = new String(Files.readAllBytes(Paths.get(fileName)));
    
                            Parser parser = new Parser(new ParserLexer(content));
    
                            parser.parse();
                            var parsedResult = parser.getRoot();
    
                            String outputFileName = fileName.replace(".d", ".o");
    
                            // try (FileWriter writer = new FileWriter(new File(outputFileName))) {
                            //     writer.write(parsedResult);
                            // }
    
                            System.out.println("Parsed content written to: " + outputFileName);
                        } catch (IOException e) {
                            System.err.println("Error reading or writing files: " + e.getMessage());
                        } catch (Exception e) {
                            System.err.println("Error during parsing: " + e.getMessage());
                        }
                    
        // if (args.length > 0) {
        //     for (String fileName : args) {
        //         if (fileName.endsWith(".d")) {
        //             
        //         } else {
        //             System.err.println("File does not have the .d extension: " + fileName);
        //         }
        //     }
        // } else {
        //     Repl.start();
        // }
    }
}