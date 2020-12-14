package com.minijava.compiler.generation;

import com.minijava.compiler.semantic.sentences.models.asts.Block;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;

public class PredefinedBlocks {
    public static class SystemRead extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "READ",
                    "STORE 3"
            );
        }
    }

    public static class SystemPrintB extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "BPRINT"
            );
        }
    }

    public static class SystemPrintC extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "CPRINT"
            );
        }
    }

    public static class SystemPrintI extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "IPRINT"
            );
        }
    }

    public static class SystemPrintS extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "SPRINT"
            );
        }
    }

    public static class SystemPrintln extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "PRNLN"
            );
        }
    }

    public static class SystemPrintBln extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "BPRINT",
                    "PRNLN"
            );
        }
    }

    public static class SystemPrintCln extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "CPRINT",
                    "PRNLN"
            );
        }
    }

    public static class SystemPrintIln extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "IPRINT",
                    "PRNLN"
            );
        }
    }

    public static class SystemPrintSln extends Block {
        @Override
        public void translate() throws IOException {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3",
                    "SPRINT",
                    "PRNLN"
            );
        }
    }
}
