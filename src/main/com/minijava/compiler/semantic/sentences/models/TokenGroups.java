package com.minijava.compiler.semantic.sentences.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;

public class TokenGroups {
    public static final Set<String> INT_OPERATORS = buildSet(ADD, SUB, MUL, DIV, MOD);
    public static final Set<String> BOOLEAN_OPERATORS = buildSet(AND, OR);
    public static final Set<String> EQUALITY_OPERATORS = buildSet(EQUALS, NOT_EQUALS);
    public static final Set<String> RELATIONAL_OPERATORS = buildSet(LESS, LESS_OR_EQ, GREATER, GREATER_OR_EQ);

    public static final Set<String> COMPOSITE_ASSIGNMENT = buildSet(ADD_ASSIGN, SUB_ASSIGN);

    // HELPERS
    private static Set<String> buildSet(String... tokens) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(tokens)));
    }
}
