package com.minijava.compiler.semantic.sentences;

import com.minijava.compiler.CompilerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

class SemanticSentencesExceptionTests extends SemanticSentencesTests {
    private static final String DIR = "semantic/sentences/exception_tests/";

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("dynamic_in_static_context", Arrays.asList("this", "x", "g")),
                Arguments.of("return", Arrays.asList("return", "return", "return", "return", "return", "return", "return", "return", "return", "return", "return", "return", "return")),
                Arguments.of("if_while", Arrays.asList("if", "y", "if", "if", "y", "y", "while", "while")),
                Arguments.of("calls", Arrays.asList("f", "x", "C", "h", "y")),
                Arguments.of("declarations", Arrays.asList("x", "x", "y", "z", "C")),
                Arguments.of("assignment", Arrays.asList("x", "y", "=", "=", "=", "+=", "-=", "+=")),
                Arguments.of("arguments", Arrays.asList("f", "g", "g", "a", "h", "1", "new", "new")),
                Arguments.of("binary_operators", Arrays.asList("+", "-", "*", "/", "%", "&&", "||", "==", "==", "!=", "<=", "<", "=")),
                Arguments.of("variables_resolution", Arrays.asList("a1", "a2", "a3", "a3", "a1")),
                Arguments.of("unary_operators", Arrays.asList("-", "-", "=", "!", "!")),
                Arguments.of("subtypes", Arrays.asList("=", "=", "=", "=", "=", "=", "=", "=", "=", "=", "=", "=")),
                Arguments.of("static_access", Arrays.asList("a1", "a2", "f", "f", "C", "C", "a1", "a2", "f", "f", "C", "C")),
                Arguments.of("constructors", Arrays.asList("a2", "C", "B", "B")),
                Arguments.of("chained_variables", Arrays.asList("a1", "b1", "b2", "b2", "b1", "a1", "a3", "a1", "b2")),
                Arguments.of("variables", Arrays.asList("=", "a4", "a3", "=")),
                Arguments.of("chained_methods", Arrays.asList("=", "b2", "a", "f", "b1", "b1", "b1", "a3", "=")),
                Arguments.of("methods", Arrays.asList("b1", "a3", "b3", "a3"))
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