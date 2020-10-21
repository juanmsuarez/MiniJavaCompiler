package com.minijava.compiler.syntactic;

import com.minijava.compiler.syntactic.exceptions.SyntacticException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.syntactic.models.TokenGroupNames.*;

class SyntacticAnalyzerExceptionTests extends SyntacticAnalyzerTests {
    private static final String DIR = "syntactic/exception_tests/";

    private static Stream<Arguments> provideExceptionArguments() {
        return Stream.of(
                Arguments.of("empty_file", Collections.singletonList(CLASS_OR_INTERFACE)),
                Arguments.of("unclosed_class", Collections.singletonList(CLOSE_BRACE)),
                Arguments.of("unopened_class", Collections.singletonList(OPEN_BRACE)),
                Arguments.of("tokens_before_class", Collections.singletonList(CLASS_OR_INTERFACE)),
                Arguments.of("tokens_after_class", Collections.singletonList(EOF)),
                Arguments.of("class_inheritance", Arrays.asList(CLASS_ID, CLASS_ID, OPEN_BRACE)),
                Arguments.of("wrong_class_signatures", Arrays.asList(CLASS_OR_INTERFACE, CLASS_ID, OPEN_BRACE)),
                Arguments.of("attributes", Arrays.asList(TYPE, SEMICOLON, VAR_MET_ID, TYPE, VAR_MET_ID, OPEN_PARENTHESIS)),
                Arguments.of("methods", Arrays.asList(METHOD_TYPE, METHOD_TYPE, VAR_MET_ID, OPEN_PARENTHESIS, CLOSE_PARENTHESIS, VAR_MET_ID, TYPE, OPEN_BRACE, OPEN_BRACE, CLOSE_BRACE)),
                Arguments.of("constructors", Arrays.asList(OPEN_PARENTHESIS, OPEN_BRACE, OPEN_PARENTHESIS, VAR_MET_ID, TYPE)),
                Arguments.of("types", Arrays.asList(TYPE, METHOD_TYPE)),
                Arguments.of("basic_sentences", Arrays.asList(CLOSE_BRACE, SEMICOLON)),
                Arguments.of("declarations", Arrays.asList(VAR_MET_ID, VAR_MET_ID, VAR_MET_ID, VAR_MET_ID, VAR_MET_ID, SEMICOLON)),
                Arguments.of("assignments", Arrays.asList(ASSIGNMENT_OR_SENTENCE_END, OPERAND, CLOSE_PARENTHESIS, ASSIGNMENT_OR_SENTENCE_END, EXPRESSION, EXPRESSION)),
                Arguments.of("access_types", Arrays.asList(ASSIGNMENT_OR_SENTENCE_END, VAR_MET_ID, CLASS_ID, DOT, VAR_MET_ID, ASSIGNMENT_OR_SENTENCE_END, CLOSE_PARENTHESIS, OPEN_PARENTHESIS, CLASS_ID, ASSIGNMENT_OR_SENTENCE_END, CLOSE_PARENTHESIS, EXPRESSION, VAR_MET_ID, CLOSE_PARENTHESIS, CLOSE_PARENTHESIS, VAR_MET_ID, VAR_MET_ID, CLOSE_PARENTHESIS)),
                Arguments.of("control_structures", Arrays.asList(OPEN_PARENTHESIS, EXPRESSION, EXPRESSION, ASSIGNMENT_OR_SENTENCE_END, CLOSE_BRACE, EXPRESSION, SENTENCE, EXPRESSION, EXPRESSION, SENTENCE, SENTENCE, CLOSE_BRACE, ASSIGNMENT_OR_SENTENCE_END, ASSIGNMENT_OR_SENTENCE_END, SENTENCE, EOF)),
                Arguments.of("expressions", Arrays.asList(OPERAND, SEMICOLON, SEMICOLON, EXPRESSION, OPERAND, EXPRESSION, EXPRESSION, EXPRESSION, SEMICOLON, CLOSE_PARENTHESIS, EXPRESSION, EXPRESSION, EXPRESSION)),
                Arguments.of("full_static", Arrays.asList(TYPE, VAR_MET_ID, DOT, VAR_MET_ID, VAR_MET_ID, VAR_MET_ID, VAR_MET_ID, DOT, CLOSE_PARENTHESIS, EXPRESSION)),
                Arguments.of("interfaces", Arrays.asList(CLASS_ID, METHOD_TYPE, SEMICOLON, VAR_MET_ID, OPEN_PARENTHESIS, CLOSE_BRACE, OPEN_BRACE, OPEN_BRACE, CLASS_ID, CLASS_ID)),
                Arguments.of("generics", Arrays.asList(CLASS_ID, CLASS_ID, CLASS_ID, OPEN_BRACE, CLASS_ID, GREATER, OPEN_PARENTHESIS, CLASS_ID, GREATER, DOT, VAR_MET_ID)),
                Arguments.of("inline_assignment", Arrays.asList(EXPRESSION, EXPRESSION, EXPRESSION, EXPRESSION, EXPRESSION, EXPRESSION))
        );
    }

    @ParameterizedTest
    @MethodSource("provideExceptionArguments")
    void fileShouldProduceExceptions(String fileName, List<String> expectedExceptions) {
        String path = DIR + fileName + ".java";
        System.out.println("Running test. File: " + path + " should produce " + expectedExceptions + ".");

        runAnalyzer(getPath(path));
        List<String> occurredExceptions = new ArrayList<>();
        for (Exception exception : syntacticAnalyzer.getExceptions()) {
            if (exception instanceof SyntacticException) {
                occurredExceptions.add(((SyntacticException) exception).getExpectedTokenName());
            }
        }

        Assertions.assertEquals(expectedExceptions, occurredExceptions);
    }
}