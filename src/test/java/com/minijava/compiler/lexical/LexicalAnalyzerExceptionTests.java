package com.minijava.compiler.lexical;

import com.minijava.compiler.lexical.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LexicalAnalyzerExceptionTests extends LexicalAnalyzerTests {
    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("exception_tests/invalid_symbol.java", InvalidSymbolException.class),
                Arguments.of("exception_tests/invalid_symbol_misc.java", InvalidSymbolException.class),
                Arguments.of("exception_tests/malformed_char.java", MalformedCharException.class),
                Arguments.of("exception_tests/malformed_char_misc.java", MalformedCharException.class),
                Arguments.of("exception_tests/malformed_operator.java", MalformedOperatorException.class),
                Arguments.of("exception_tests/malformed_operator_misc.java", MalformedOperatorException.class),
                Arguments.of("exception_tests/malformed_text_block.java", MalformedTextBlockException.class),
                Arguments.of("exception_tests/unclosed_char.java", UnclosedCharException.class),
                Arguments.of("exception_tests/unclosed_char_misc.java", UnclosedCharException.class),
                Arguments.of("exception_tests/unclosed_comment.java", UnclosedCommentException.class),
                Arguments.of("exception_tests/unclosed_comment_misc.java", UnclosedCommentException.class),
                Arguments.of("exception_tests/unclosed_string.java", UnclosedStringException.class),
                Arguments.of("exception_tests/unclosed_text_block.java", UnclosedTextBlockException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    <T extends Throwable> void fileShouldProduceException(String path, Class<T> exception) {
        System.out.println("Running test. File: " + path + " should produce " + exception.toString() + ".");

        Assertions.assertThrows(exception, () -> runAnalyzer(getPath(path)));
    }
}