package com.minijava.compiler.semantic.declarations;

import com.minijava.compiler.CompilerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

class SemanticDeclarationsExceptionTests extends SemanticDeclarationsTests {
    private static final String DIR = "semantic/declarations/exception_tests/";

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("duplicate_classes", Arrays.asList("Object", "System", "A")),
                Arguments.of("duplicate_invalid_attributes", Arrays.asList("x", "y", "k")),
                Arguments.of("duplicate_invalid_methods", Arrays.asList("f", "f", "f", "f", "x")),
                Arguments.of("type_not_found", Arrays.asList("B", "f", "k", "k", "f")),
                Arguments.of("no_main_method", Collections.singletonList("main")),
                Arguments.of("duplicate_invalid_constructors", Arrays.asList("B", "B", "i", "i", "A", "A")),
                Arguments.of("invalid_redefinition", Arrays.asList("main", "g", "main", "f", "main", "main")),
                Arguments.of("cyclic_inheritance", Arrays.asList("A", "B", "C", "D", "E", "F")),
                Arguments.of("interfaces_duplicate_parent", Collections.singletonList("A")),
                Arguments.of("interfaces_duplicate_implementation", Collections.singletonList("A")),
                Arguments.of("interfaces_duplicate_units", Arrays.asList("A", "A", "B", "B")),
                Arguments.of("interfaces_collision", Arrays.asList("E", "G", "H", "J")),
                Arguments.of("interfaces_not_found", Arrays.asList("A", "A", "B", "B")),
                Arguments.of("interfaces_invalid_implementation", Collections.singletonList("g")),
                Arguments.of("interfaces_not_implemented", Collections.singletonList("B")),
                Arguments.of("interfaces_invalid_redefinition", Collections.singletonList("g")),
                Arguments.of("interfaces_cyclic_inheritance", Arrays.asList("F", "G", "I", "J", "M")),
                Arguments.of("generic_parent", Arrays.asList("D", "E", "F", "G", "H1", "J", "K", "L", "N", "O")),
                Arguments.of("generic_members", Arrays.asList("x2", "y1", "y2", "z", "h1", "x", "p2", "p3", "p5", "p1", "p2", "p1", "g2", "g3", "g5", "g6")),
                Arguments.of("generic_inheritance", Arrays.asList("h", "i", "f", "x", "g", "h", "g", "g", "h", "i", "j"))
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