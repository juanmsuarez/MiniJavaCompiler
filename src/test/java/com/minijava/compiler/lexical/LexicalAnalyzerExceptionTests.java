package com.minijava.compiler.lexical;

import com.minijava.compiler.lexical.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LexicalAnalyzerExceptionTests extends LexicalAnalyzerTests {
    private static final String DIR = "lexical/exception_tests/";

    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("invalid_symbol", InvalidSymbolException.class),
                Arguments.of("invalid_symbol_misc", InvalidSymbolException.class),
                Arguments.of("malformed_char", MalformedCharException.class),
                Arguments.of("malformed_char_misc", MalformedCharException.class),
                Arguments.of("malformed_operator", MalformedOperatorException.class),
                Arguments.of("malformed_operator_misc", MalformedOperatorException.class),
                Arguments.of("malformed_text_block", MalformedTextBlockException.class),
                Arguments.of("unclosed_char", UnclosedCharException.class),
                Arguments.of("unclosed_char_misc", UnclosedCharException.class),
                Arguments.of("unclosed_comment", UnclosedCommentException.class),
                Arguments.of("unclosed_comment_misc", UnclosedCommentException.class),
                Arguments.of("unclosed_string", UnclosedStringException.class),
                Arguments.of("unclosed_text_block", UnclosedTextBlockException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    <T extends Throwable> void fileShouldProduceException(String fileName, Class<T> exception) {
        String path = DIR + fileName + ".java";

        System.out.println("Running test. File: " + path + " should produce " + exception.toString() + ".");

        Assertions.assertThrows(exception, () -> runAnalyzer(getPath(path)));
    }
}