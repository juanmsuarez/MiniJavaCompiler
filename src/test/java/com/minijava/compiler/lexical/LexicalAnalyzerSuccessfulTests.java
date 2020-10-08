package com.minijava.compiler.lexical;

import com.minijava.compiler.lexical.exceptions.LexicalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LexicalAnalyzerSuccessfulTests extends LexicalAnalyzerTests {
    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("successful_tests/keywords.java"),
                Arguments.of("successful_tests/identifiers.java"),
                Arguments.of("successful_tests/comments.java"),
                Arguments.of("successful_tests/literals.java"),
                Arguments.of("successful_tests/punctuation.java"),
                Arguments.of("successful_tests/operators.java"),
                Arguments.of("successful_tests/assignments.java"),
                Arguments.of("successful_tests/empty_file.java"),
                Arguments.of("successful_tests/hello_world.java"),
                Arguments.of("successful_tests/misc.java")
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    <T extends Throwable> void fileShouldBeProcessedSuccessfully(String path) throws LexicalException {
        System.out.println("Running test. File: " + path + " should be processed successfully.");

        runAnalyzer(getPath(path));
    }
}