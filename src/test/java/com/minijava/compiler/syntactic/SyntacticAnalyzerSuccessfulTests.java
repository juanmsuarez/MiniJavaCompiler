package com.minijava.compiler.syntactic;

import com.minijava.compiler.CompilerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class SyntacticAnalyzerSuccessfulTests extends SyntacticAnalyzerTests {
    private static final String DIR = "syntactic/successful_tests/";

    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("empty_class"),
                Arguments.of("multiple_classes"),
                Arguments.of("class_inheritance"),
                Arguments.of("attributes"),
                Arguments.of("methods"),
                Arguments.of("constructors"),
                Arguments.of("types"),
                Arguments.of("basic_sentences"),
                Arguments.of("declarations"),
                Arguments.of("assignments"),
                Arguments.of("access_types"),
                Arguments.of("control_structures"),
                Arguments.of("literals"),
                Arguments.of("expressions"),
                Arguments.of("misc_hello_world"),
                Arguments.of("full_static"),
                Arguments.of("interfaces"),
                Arguments.of("generics"),
                Arguments.of("inline_assignment")
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    void fileShouldBeProcessedSuccessfully(String fileName) {
        String path = DIR + fileName + ".java";
        System.out.println("Running test. File: " + path + " should be processed successfully.");

        runAnalyzer(getPath(path));

        List<CompilerException> occurredExceptions = syntacticAnalyzer.getExceptions();
        Assertions.assertTrue(occurredExceptions.isEmpty());
    }
}