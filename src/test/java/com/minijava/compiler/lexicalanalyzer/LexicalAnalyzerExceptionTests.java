package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.lexicalanalyzer.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LexicalAnalyzerExceptionTests extends LexicalAnalyzerTests {
    @Test
    void invalidSymbol() {
        Assertions.assertThrows(InvalidSymbolException.class, () ->
                runAnalyzer(getPath("exception_tests/invalid_symbol.java"))
        );
    }

    @Test
    void invalidSymbolMisc() {
        Assertions.assertThrows(InvalidSymbolException.class, () ->
                runAnalyzer(getPath("exception_tests/invalid_symbol_misc.java"))
        );
    }

    @Test
    void malformedChar() {
        Assertions.assertThrows(MalformedCharException.class, () ->
                runAnalyzer(getPath("exception_tests/malformed_char.java"))
        );
    }

    @Test
    void malformedCharMisc() {
        Assertions.assertThrows(MalformedCharException.class, () ->
                runAnalyzer(getPath("exception_tests/malformed_char_misc.java"))
        );
    }

    @Test
    void malformedOperator() {
        Assertions.assertThrows(MalformedOperatorException.class, () ->
                runAnalyzer(getPath("exception_tests/malformed_operator.java"))
        );
    }

    @Test
    void malformedOperatorMisc() {
        Assertions.assertThrows(MalformedOperatorException.class, () ->
                runAnalyzer(getPath("exception_tests/malformed_operator_misc.java"))
        );
    }

    @Test
    void malformedTextBlock() {
        Assertions.assertThrows(MalformedTextBlockException.class, () ->
                runAnalyzer(getPath("exception_tests/malformed_text_block.java"))
        );
    }

    @Test
    void unclosedChar() {
        Assertions.assertThrows(UnclosedCharException.class, () ->
                runAnalyzer(getPath("exception_tests/unclosed_char.java"))
        );
    }

    @Test
    void unclosedCharMisc() {
        Assertions.assertThrows(UnclosedCharException.class, () ->
                runAnalyzer(getPath("exception_tests/unclosed_char_misc.java"))
        );
    }

    @Test
    void unclosedComment() {
        Assertions.assertThrows(UnclosedCommentException.class, () ->
                runAnalyzer(getPath("exception_tests/unclosed_comment.java"))
        );
    }


    @Test
    void unclosedCommentMisc() {
        Assertions.assertThrows(UnclosedCommentException.class, () ->
                runAnalyzer(getPath("exception_tests/unclosed_comment_misc.java"))
        );
    }

    @Test
    void unclosedString() {
        Assertions.assertThrows(UnclosedStringException.class, () ->
                runAnalyzer(getPath("exception_tests/unclosed_string.java"))
        );
    }

    @Test
    void unclosedTextBlock() {
        Assertions.assertThrows(UnclosedTextBlockException.class, () ->
                runAnalyzer(getPath("exception_tests/unclosed_text_block.java"))
        );
    }
}