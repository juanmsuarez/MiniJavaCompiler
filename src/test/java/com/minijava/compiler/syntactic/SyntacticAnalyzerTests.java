package com.minijava.compiler.syntactic;

import com.minijava.compiler.MiniJavaCompiler;
import com.minijava.compiler.ResourceReader;
import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.semantic.symbols.SymbolTable;
import com.minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

class SyntacticAnalyzerTests extends ResourceReader {
    private static final String SUCCESS_CODE = "[SinErrores]";

    protected SyntacticAnalyzer syntacticAnalyzer;

    protected void runAnalyzer(String path) {
        try (FileManager fileManager = new FileManager(path)) {
            MiniJavaCompiler.symbolTable = new SymbolTable();
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);
            syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);

            syntacticAnalyzer.analyze();

            boolean success = syntacticAnalyzer.getExceptions().isEmpty();
            if (success) {
                System.out.println("El análisis finalizó: no se encontraron errores.");
                System.out.println(SUCCESS_CODE);
            } else {
                System.out.println("El análisis finalizó: se encontraron errores.");
                for (Exception exception : syntacticAnalyzer.getExceptions()) {
                    System.out.println(exception.toString());
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("No pudo realizarse el análisis: no fue posible abrir el archivo fuente indicado.");
        } catch (IOException exception) {
            System.out.println("No pudo realizarse el análisis: ocurrió un error durante la lectura del archivo fuente.");
        }
    }
}
