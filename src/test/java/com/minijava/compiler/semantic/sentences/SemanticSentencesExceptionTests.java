package com.minijava.compiler.semantic.sentences;

import com.minijava.compiler.CompilerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

class SemanticSentencesExceptionTests extends SemanticSentencesTests {
    private static final String DIR = "semantic/sentences/exception_tests/";

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("declarations", new ArrayList<>())
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void fileShouldProduceExceptions(String fileName, List<String> expectedExceptions) {
        String path = DIR + fileName + ".java";
        System.out.println("Running test. File: " + path + " should produce " + expectedExceptions + ".");

        runAnalyzer(getPath(path));

        exceptions.sort(Comparator.comparingInt(a -> a.getLexeme().getLineNumber()));
        List<String> occurredExceptions = new ArrayList<>();
        for (CompilerException exception : exceptions) {
            occurredExceptions.add(exception.getLexeme().getString());
        }

        Assertions.assertEquals(expectedExceptions, occurredExceptions);
    }
}