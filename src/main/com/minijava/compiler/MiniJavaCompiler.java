package com.minijava.compiler;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.semantic.symbols.SymbolTable;
import com.minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class MiniJavaCompiler {
    public static SymbolTable symbolTable;
    private static final Logger logger = Logger.getLogger(MiniJavaCompiler.class.getName());

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
            symbolTable = new SymbolTable();
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);

            syntacticAnalyzer.analyze();
            boolean syntacticSuccess = syntacticAnalyzer.getExceptions().isEmpty();

            if (syntacticSuccess) {
                logger.log(INFO, symbolTable.toString()); // TODO: remove in final version

                symbolTable.consolidate();
                boolean semanticSuccess = symbolTable.getExceptions().isEmpty();

                if (semanticSuccess) {
                    finishSuccessfully();
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

    private static void finishWithErrors(List<Exception> exceptions) {
        System.out.println(ERROR_MESSAGE);
        for (Exception exception : exceptions) {
            System.out.println(exception.toString());
        }
    }
}
