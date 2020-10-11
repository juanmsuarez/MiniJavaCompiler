package com.minijava.compiler;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

class MiniJavaCompiler {
    private static final String SUCCESS_CODE = "[SinErrores]";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Debe ingresar exactamente un argumento: la ruta del archivo fuente.");
            return;
        }

        String path = args[0];
        try (FileManager fileManager = new FileManager(path)) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);

            boolean errorOccurred = false;
            try {
                syntacticAnalyzer.analyze();
            } catch (CompilerException exception) {
                System.out.println(exception.toString());
                errorOccurred = true;
            }

            if (!errorOccurred) {
                System.out.println("El análisis finalizó: no se encontraron errores.");
                System.out.println(SUCCESS_CODE);
            } else {
                System.out.println("El análisis finalizó: se encontraron errores.");
            }
        } catch (FileNotFoundException exception) {
            System.out.println("No pudo realizarse el análisis: no fue posible abrir el archivo fuente indicado.");
        } catch (IOException exception) {
            System.out.println("No pudo realizarse el análisis: ocurrió un error durante la lectura del archivo fuente.");
        }
    }
}
