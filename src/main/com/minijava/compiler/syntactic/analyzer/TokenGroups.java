package com.minijava.compiler.syntactic.analyzer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;

public class TokenGroups {
    // NAMES
    public static final String TYPE = "type";
    public static final String METHOD_TYPE = "method_type";
    public static final String ASSIGNMENT_OR_SENTENCE_END = "assignment_or_sentence_end";

    // FIRSTS
    static final Set<String> FIRST_VISIBILITY = buildSet(PUBLIC_KW, PRIVATE_KW);
    static final Set<String> FIRST_ATTRIBUTE = FIRST_VISIBILITY;

    private static final Set<String> FIRST_CONSTRUCTOR = buildSet(CLASS_ID);

    static final Set<String> FIRST_METHOD_FORM = buildSet(STATIC_KW, DYNAMIC_KW);
    static final Set<String> FIRST_METHOD = FIRST_METHOD_FORM;

    static final Set<String> FIRST_MEMBER = buildSet(FIRST_ATTRIBUTE, FIRST_CONSTRUCTOR, FIRST_METHOD);

    static final Set<String> FIRST_PRIMITIVE_TYPE = buildSet(BOOLEAN_KW, CHAR_KW, INT_KW, STRING_KW);
    private static final Set<String> FIRST_CLASS_TYPE = buildSet(CLASS_ID);
    static final Set<String> FIRST_TYPE = buildSet(FIRST_PRIMITIVE_TYPE, FIRST_CLASS_TYPE);

    static final Set<String> FIRST_FORMAL_ARGS = FIRST_TYPE;

    private static final Set<String> FIRST_EMPTY_SENTENCE = buildSet(SEMICOLON);
    static final Set<String> FIRST_ACCESS = buildSet(THIS_KW, VAR_MET_ID, STATIC_KW, NEW_KW, OPEN_PARENTHESIS);
    static final Set<String> FIRST_CALL_OR_ASSIGNMENT = FIRST_ACCESS;
    static final Set<String> FIRST_DECLARATION = FIRST_TYPE;
    private static final Set<String> FIRST_IF = buildSet(IF_KW);
    private static final Set<String> FIRST_WHILE = buildSet(WHILE_KW);
    private static final Set<String> FIRST_BLOCK = buildSet(OPEN_BRACE);
    private static final Set<String> FIRST_RETURN = buildSet(RETURN_KW);
    static final Set<String> FIRST_SENTENCE = buildSet(FIRST_EMPTY_SENTENCE, FIRST_CALL_OR_ASSIGNMENT, FIRST_DECLARATION,
            FIRST_IF, FIRST_WHILE, FIRST_BLOCK, FIRST_RETURN);

    static final Set<String> FIRST_ASSIGNMENT_TYPE = buildSet(ASSIGN, ADD_ASSIGN, SUB_ASSIGN);

    static final Set<String> FIRST_UNARY_OPERATOR = buildSet(ADD, SUB, NOT);
    static final Set<String> FIRST_LITERAL = buildSet(NULL_KW, TRUE_KW, FALSE_KW, INT_LITERAL, CHAR_LITERAL, STRING_LITERAL);
    static final Set<String> FIRST_OPERAND = buildSet(FIRST_LITERAL, FIRST_ACCESS);
    static final Set<String> FIRST_EXPRESSION = buildSet(FIRST_UNARY_OPERATOR, FIRST_OPERAND);

    // HELPERS
    private static Set<String> buildSet(String... tokens) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(tokens)));
    }

    @SafeVarargs
    private static Set<String> buildSet(Set<String>... tokenSets) {
        return Collections.unmodifiableSet(new HashSet<String>() {{
            for (Set<String> set : tokenSets) {
                addAll(set);
            }
        }});
    }
}
