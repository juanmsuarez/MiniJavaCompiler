package com.minijava.compiler.lexical;

import com.minijava.compiler.ResourceReader;
import com.minijava.compiler.filemanagers.InputFileManager;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.exceptions.LexicalException;
import com.minijava.compiler.lexical.models.Token;

import java.io.FileNotFoundException;
import java.io.IOException;

class LexicalAnalyzerTests extends ResourceReader {
    protected void runAnalyzer(String path) throws LexicalException {
        try (InputFileManager inputFileManager = new InputFileManager(path)) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(inputFileManager);

            Token currentToken;
            do {
                try {
                    currentToken = lexicalAnalyzer.nextToken();
                    System.out.println(currentToken.toString());
                } catch (LexicalException exception) {
                    System.err.println(exception.toString());
                    throw exception;
                }
            } while (!currentToken.getName().equals("EOF"));
        } catch (FileNotFoundException exception) {
            System.err.println("No fue posible abrir el archivo fuente indicado.");
        } catch (IOException exception) {
            System.err.println("Ocurrió un error durante la lectura del archivo fuente.");
        }
    }
}
