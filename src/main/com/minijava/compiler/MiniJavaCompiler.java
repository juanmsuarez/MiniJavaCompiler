package com.minijava.compiler;

import com.minijava.compiler.filemanagers.InputFileManager;
import com.minijava.compiler.filemanagers.OutputFileManager;
import com.minijava.compiler.generation.CodeGenerator;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.semantic.SymbolTable;
import com.minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MiniJavaCompiler {
    public static SymbolTable symbolTable;
    public static CodeGenerator codeGenerator;

    private static final String SUCCESS_MESSAGE = "La compilación finalizó: no se encontraron errores.";
    private static final String SUCCESS_CODE = "[SinErrores]";

    private static final String ERROR_MESSAGE = "El análisis finalizó: se encontraron errores.";
    private static final String ARGS_ERROR = "Debe ingresar exactamente dos argumentos: la ruta del archivo fuente y la ruta del archivo de salida.";
    private static final String FILE_ERROR = "El compilador no pudo ejecutarse correctamente: no fue posible abrir los archivos indicados.";
    private static final String IO_ERROR = "El compilador no pudo ejecutarse correctamente: ocurrió un error durante la lectura o escritura de los archivos.";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(ARGS_ERROR);
            return;
        }

        String inputPath = args[0], outputPath = args[1];
        try (
                InputFileManager inputFileManager = new InputFileManager(inputPath);
                OutputFileManager outputFileManager = new OutputFileManager(outputPath);
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
                    symbolTable.checkSentences();
                    boolean sentenceCheckSuccess = symbolTable.getExceptions().isEmpty();

                    if (sentenceCheckSuccess) {
                        codeGenerator.translateProgram();
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
