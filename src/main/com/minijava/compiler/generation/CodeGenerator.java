package com.minijava.compiler.generation;

import com.minijava.compiler.filemanagers.OutputFileManager;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class CodeGenerator {
    public static final String HELPER_HEAP_INIT = "HELPER_HEAP_INIT";
    public static final String HELPER_MALLOC = "HELPER_MALLOC";

    private int labelNumber = 0;
    private OutputFileManager outputFileManager;

    public CodeGenerator(OutputFileManager outputFileManager) {
        this.outputFileManager = outputFileManager;
    }

    public void translateProgram() throws IOException {
        initialize();

        symbolTable.translate();
    }

    private void initialize() throws IOException {
        generate(".CODE");

        String mainLabel = symbolTable.getMainMethod().getLabel();
        generate(
                "PUSH " + HELPER_HEAP_INIT,
                "CALL",
                "PUSH " + mainLabel,
                "CALL",
                "HALT"
        );

        generate(
                HELPER_HEAP_INIT + ":",
                "RET 0"
        );

        generate(
                HELPER_MALLOC + ":",
                "LOADFP",
                "LOADSP",
                "STOREFP",
                "LOADHL",
                "DUP",
                "PUSH 1",
                "ADD",
                "STORE 4",
                "LOAD 3",
                "ADD",
                "STOREHL",
                "STOREFP",
                "RET 1"
        );
    }

    public String newLabelId() {
        String label = "ID_" + labelNumber;
        labelNumber++;
        return label;
    }

    public void generate(String instruction) throws IOException {
        outputFileManager.writeLine(instruction);
    }

    public void generate(String... instructions) throws IOException {
        for (String instruction : instructions) {
            generate(instruction);
        }
    }
}
