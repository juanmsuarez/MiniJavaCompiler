package com.minijava.compiler.lexical;

import com.minijava.compiler.lexical.exceptions.LexicalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LexicalAnalyzerSuccessfulTests extends LexicalAnalyzerTests {
    private static final String DIR = "lexical/successful_tests/";

    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("keywords"),
                Arguments.of("identifiers"),
                Arguments.of("comments"),
                Arguments.of("literals"),
                Arguments.of("punctuation"),
                Arguments.of("operators"),
                Arguments.of("assignments"),
                Arguments.of("empty_file"),
                Arguments.of("hello_world"),
                Arguments.of("misc")
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    <T extends Throwable> void fileShouldBeProcessedSuccessfully(String fileName) throws LexicalException {
        String path = DIR + fileName + ".java";

        System.out.println("Running test. File: " + path + " should be processed successfully.");

        runAnalyzer(getPath(path));
    }
}