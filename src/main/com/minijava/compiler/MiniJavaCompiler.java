package com.minijava.compiler;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.semantic.SymbolTable;
import com.minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MiniJavaCompiler {
    public static SymbolTable symbolTable;

    private static final String SUCCESS_MESSAGE = "El análisis finalizó: no se encontraron errores.";
    private static final String SUCCESS_CODE = "[SinErrores]";

    private static final String ERROR_MESSAGE = "El análisis finalizó: se encontraron errores.";
    private static final String ARGS_ERROR = "Debe ingresar exactamente un argumento: la ruta del archivo fuente.";
    private static final String FILE_ERROR = "No pudo realizarse el análisis: no fue posible abrir el archivo fuente indicado.";
    private static final String IO_ERROR = "No pudo realizarse el análisis: ocurrió un error durante la lectura del archivo fuente.";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(ARGS_ERROR);
            return;
        }

        String path = args[0];
        try (FileManager fileManager = new FileManager(path)) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);
            symbolTable = new SymbolTable();

            syntacticAnalyzer.analyze();
            boolean syntacticSuccess = syntacticAnalyzer.getExceptions().isEmpty();

            if (syntacticSuccess) {
                symbolTable.checkDeclarations();
                symbolTable.consolidate();
                boolean declarationCheckSuccess = symbolTable.getExceptions().isEmpty();

                if (declarationCheckSuccess) {
                    symbolTable.checkSentences();
                    boolean sentenceCheckSuccess = symbolTable.getExceptions().isEmpty();

                    if (sentenceCheckSuccess) {
                        finishSuccessfully();
                    } else {
                        finishWithErrors(symbolTable.getExceptions());
                    }
                } else {
                    finishWithErrors(symbolTable.getExceptions());
                }
            } else {
                finishWithErrors(syntacticAnalyzer.getExceptions());
            }
        } catch (FileNotFoundException exception) {
            System.out.println(FILE_ERROR);
        } catch (IOException exception) {
            System.out.println(IO_ERROR);
        }
    }

    private static void finishSuccessfully() {
        System.out.println(SUCCESS_MESSAGE);
        System.out.println(SUCCESS_CODE);
    }

    private static void finishWithErrors(List<? extends CompilerException> exceptions) {
        System.out.println(ERROR_MESSAGE);
        for (Exception exception : exceptions) {
            System.out.println(exception.toString());
        }
    }
}
