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
                Arguments.of("dynamic_in_static_context"),
                Arguments.of("return"),
                Arguments.of("if_while"),
                Arguments.of("calls"),
                Arguments.of("declarations"),
                Arguments.of("assignment"),
                Arguments.of("arguments"),
                Arguments.of("binary_operators"),
                Arguments.of("variables_resolution"),
                Arguments.of("unary_operators"),
                Arguments.of("subtypes"),
                Arguments.of("static_access"),
                Arguments.of("constructors"),
                Arguments.of("chained_variables"),
                Arguments.of("variables"),
                Arguments.of("chained_methods"),
                Arguments.of("methods")
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