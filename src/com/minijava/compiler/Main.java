package com.minijava.compiler;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.LexicalAnalyzer;
import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;
import com.minijava.compiler.lexicalanalyzer.Token;

import java.io.FileNotFoundException;
import java.io.IOException;

class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Debe ingresar exactamente un argumento: la ruta del archivo fuente.");
            return;
        }

        String path = args[0];
        try (FileManager fileManager = new FileManager(path)) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

            Token currentToken;
            do {
                currentToken = lexicalAnalyzer.nextToken();
                System.out.println(currentToken.toString());
            } while (!currentToken.getName().equals("EOF"));

            System.out.println("El análisis léxico terminó con éxito.");
        } catch (FileNotFoundException exception) {
            System.err.println("No fue posible abrir el archivo fuente indicado.");
        } catch (IOException exception) {
            System.err.println("Ocurrió un error durante la lectura del archivo fuente.");
        } catch (LexicalException exception) {
            System.err.println(exception.toString());
        }
    }
}
