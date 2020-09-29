package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;
import org.junit.jupiter.api.Test;

class LexicalAnalyzerSuccessfulTests extends LexicalAnalyzerTests {
    @Test
    void keywords() throws LexicalException {
        runAnalyzer(getPath("successful_tests/keywords.java"));
    }

    @Test
    void identifiers() throws LexicalException {
        runAnalyzer(getPath("successful_tests/identifiers.java"));
    }

    @Test
    void comments() throws LexicalException {
        runAnalyzer(getPath("successful_tests/comments.java"));
    }

    @Test
    void literals() throws LexicalException {
        runAnalyzer(getPath("successful_tests/literals.java"));
    }

    @Test
    void punctuation() throws LexicalException {
        runAnalyzer(getPath("successful_tests/punctuation.java"));
    }

    @Test
    void operators() throws LexicalException {
        runAnalyzer(getPath("successful_tests/operators.java"));
    }

    @Test
    void assignments() throws LexicalException {
        runAnalyzer(getPath("successful_tests/assignments.java"));
    }

    @Test
    void emptyFile() throws LexicalException {
        runAnalyzer(getPath("successful_tests/empty_file.java"));
    }

    @Test
    void helloWorld() throws LexicalException {
        runAnalyzer(getPath("successful_tests/hello_world.java"));
    }

    @Test
    void misc() throws LexicalException {
        runAnalyzer(getPath("successful_tests/misc.java"));
    }
}