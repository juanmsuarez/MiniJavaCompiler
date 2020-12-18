package com.minijava.compiler.semantic.sentences;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.ResourceReader;
import com.minijava.compiler.filemanagers.InputFileManager;
import com.minijava.compiler.filemanagers.OutputFileManager;
import com.minijava.compiler.generation.CodeGenerator;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.semantic.SymbolTable;
import com.minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

class SemanticSentencesTests extends ResourceReader {
    protected List<? extends CompilerException> exceptions = new ArrayList<>();

    private static final String SUCCESS_MESSAGE = "El análisis finalizó: no se encontraron errores.";
    private static final String SUCCESS_CODE = "[SinErrores]";

    private static final String ERROR_MESSAGE = "El análisis finalizó: se encontraron errores.";
    private static final String FILE_ERROR = "No pudo realizarse el análisis: no fue posible abrir el archivo fuente indicado.";
    private static final String IO_ERROR = "No pudo realizarse el análisis: ocurrió un error durante la lectura del archivo fuente.";

    protected void runAnalyzer(String path) {
        try (
                InputFileManager inputFileManager = new InputFileManager(path);
                OutputFileManager outputFileManager = new OutputFileManager(".temp.out");
        ) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(inputFileManager);
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);
            symbolTable = new SymbolTable();
            codeGenerator = new CodeGenerator(outputFileManager);

            syntacticAnalyzer.analyze();
            boolean syntacticSuccess = syntacticAnalyzer.getExceptions().isEmpty();

            if (syntacticSuccess) {
                symbolTable.checkDeclarations();
                symbolTable.consolidate();
                boolean declarationCheckSuccess = symbolTable.getExceptions().isEmpty();

                if (declarationCheckSuccess) {
                    System.out.println(symbolTable.toString());
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

    private void finishSuccessfully() {
        System.out.println(SUCCESS_MESSAGE);
        System.out.println(SUCCESS_CODE);
    }

    private void finishWithErrors(List<? extends CompilerException> exceptions) {
        System.out.println(ERROR_MESSAGE);
        for (Exception exception : exceptions) {
            System.out.println(exception.toString());
        }

        this.exceptions = exceptions;
    }
}
