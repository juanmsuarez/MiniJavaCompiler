package com.minijava.compiler.semantic.declarations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SemanticDeclarationsSuccessfulTests extends SemanticDeclarationsTests {
    private static final String DIR = "semantic/declarations/successful_tests/";

    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("classes"),
                Arguments.of("attributes"),
                Arguments.of("methods"),
                Arguments.of("main_methods"),
                Arguments.of("predefined_entities"),
                Arguments.of("constructors"),
                Arguments.of("redefinition_and_hiding"),
                Arguments.of("inheritance"),
                Arguments.of("interfaces"),
                Arguments.of("generics")
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    void fileShouldBeProcessedSuccessfully(String fileName) {
        String path = DIR + fileName + ".java";
        System.out.println("Running test. File: " + path + " should be processed successfully.");

        runAnalyzer(getPath(path));

        Assertions.assertTrue(exceptions.isEmpty());
    }
}