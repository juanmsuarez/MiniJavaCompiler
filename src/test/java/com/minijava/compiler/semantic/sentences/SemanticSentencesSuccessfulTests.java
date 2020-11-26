package com.minijava.compiler.semantic.sentences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SemanticSentencesSuccessfulTests extends SemanticSentencesTests {
    private static final String DIR = "semantic/sentences/successful_tests/";

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("declarations")
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void fileShouldBeProcessedSuccessfully(String fileName) {
        String path = DIR + fileName + ".java";
        System.out.println("Running test. File: " + path + " should be processed successfully.");

        runAnalyzer(getPath(path));

        Assertions.assertTrue(exceptions.isEmpty());
    }
}