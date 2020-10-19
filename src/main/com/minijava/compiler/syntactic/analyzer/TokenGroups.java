package com.minijava.compiler.syntactic.analyzer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;

public class TokenGroups {
    // NAMES
    public static final String CLASS_OR_INTERFACE = "class_or_interface";
    public static final String TYPE = "type";
    public static final String METHOD_TYPE = "method_type";
    public static final String ASSIGNMENT_OR_SENTENCE_END = "assignment_or_sentence_end";
    public static final String EXPRESSION = "expression";
    public static final String OPERAND = "operand";

    // FIRSTS
    static final Set<String> FIRST_CLASS_OR_INTERFACE = buildSet(CLASS_KW, INTERFACE_KW);

    static final Set<String> FIRST_VISIBILITY = buildSet(PUBLIC_KW, PRIVATE_KW);
    static final Set<String> FIRST_ATTRIBUTE = FIRST_VISIBILITY;

    private static final Set<String> FIRST_CONSTRUCTOR = buildSet(CLASS_ID);

    static final Set<String> FIRST_METHOD_FORM = buildSet(STATIC_KW, DYNAMIC_KW);
    static final Set<String> FIRST_METHOD = FIRST_METHOD_FORM;

    static final Set<String> FIRST_MEMBER = buildSet(FIRST_ATTRIBUTE, FIRST_CONSTRUCTOR, FIRST_METHOD);
    static final Set<String> FIRST_INTERFACE_MEMBER = FIRST_METHOD;

    static final Set<String> FIRST_PRIMITIVE_TYPE = buildSet(BOOLEAN_KW, CHAR_KW, INT_KW, STRING_KW);
    private static final Set<String> FIRST_CLASS_TYPE = buildSet(CLASS_ID);
    static final Set<String> FIRST_TYPE = buildSet(FIRST_PRIMITIVE_TYPE, FIRST_CLASS_TYPE);

    static final Set<String> FIRST_FORMAL_ARGS = FIRST_TYPE;

    private static final Set<String> FIRST_EMPTY_SENTENCE = buildSet(SEMICOLON);

    static final String FIRST_IMPLICIT_STATIC_ACCESS = CLASS_ID; // TODO: mejorar organización
    static final String SECOND_IMPLICIT_STATIC_ACCESS = DOT;
    static final String FIRST_EXPLICIT_STATIC_ACCESS = STATIC_KW;
    static final Set<String> FIRST_STATIC_ACCESS = buildSet(FIRST_IMPLICIT_STATIC_ACCESS, FIRST_EXPLICIT_STATIC_ACCESS);

    static final String FIRST_IMPLICIT_ACCESS = FIRST_IMPLICIT_STATIC_ACCESS;
    static final String SECOND_IMPLICIT_ACCESS = SECOND_IMPLICIT_STATIC_ACCESS;
    static final Set<String> FIRST_EXPLICIT_ACCESS = buildSet(THIS_KW, VAR_MET_ID, FIRST_EXPLICIT_STATIC_ACCESS, NEW_KW, OPEN_PARENTHESIS);
    static final Set<String> FIRST_ACCESS = buildSet(buildSet(FIRST_IMPLICIT_ACCESS), FIRST_EXPLICIT_ACCESS);

    static final Set<String> FIRST_EXPLICIT_CALL_OR_ASSIGNMENT = FIRST_EXPLICIT_ACCESS;
    static final String FIRST_IMPLICIT_CALL_OR_ASSIGNMENT = FIRST_IMPLICIT_ACCESS;
    static final String SECOND_IMPLICIT_CALL_OR_ASSIGNMENT = SECOND_IMPLICIT_ACCESS;
    static final Set<String> FIRST_CALL_OR_ASSIGNMENT = buildSet(FIRST_EXPLICIT_CALL_OR_ASSIGNMENT, buildSet(FIRST_IMPLICIT_CALL_OR_ASSIGNMENT));

    static final Set<String> FIRST_DECLARATION = FIRST_TYPE;
    private static final Set<String> FIRST_IF = buildSet(IF_KW);
    private static final Set<String> FIRST_WHILE = buildSet(WHILE_KW);
    private static final Set<String> FIRST_BLOCK = buildSet(OPEN_BRACE);
    private static final Set<String> FIRST_RETURN = buildSet(RETURN_KW);
    static final Set<String> FIRST_SENTENCE = buildSet(FIRST_EMPTY_SENTENCE, FIRST_CALL_OR_ASSIGNMENT, FIRST_DECLARATION,
            FIRST_IF, FIRST_WHILE, FIRST_BLOCK, FIRST_RETURN);

    static final Set<String> FIRST_ASSIGNMENT_TYPE = buildSet(ASSIGN, ADD_ASSIGN, SUB_ASSIGN);

    static final Set<String> FIRST_UNARY_OPERATOR = buildSet(ADD, SUB, NOT);
    static final Set<String> FIRST_BINARY_OPERATOR = buildSet(OR, AND, EQUALS, NOT_EQUALS, LESS, GREATER, LESS_OR_EQ,
            GREATER_OR_EQ, ADD, SUB, MUL, DIV, MOD);
    static final Set<String> FIRST_LITERAL = buildSet(NULL_KW, TRUE_KW, FALSE_KW, INT_LITERAL, CHAR_LITERAL, STRING_LITERAL);
    static final Set<String> FIRST_OPERAND = buildSet(FIRST_LITERAL, FIRST_ACCESS);
    static final Set<String> FIRST_EXPRESSION = buildSet(FIRST_UNARY_OPERATOR, FIRST_OPERAND);

    // RECOVERY
    static final Set<String> LAST_INITIAL = buildSet(EOF);

    static final Set<String> LAST_CLASS_OR_INTERFACE = LAST_INITIAL;
    static final Set<String> NEXT_CLASS_OR_INTERFACE = buildSet(FIRST_CLASS_OR_INTERFACE, buildSet(EOF));

    static final Set<String> LAST_CLASS_OR_INTERFACE_SIGNATURE = buildSet(LAST_INITIAL, buildSet(OPEN_BRACE));
    static final Set<String> NEXT_CLASS_SIGNATURE = buildSet(FIRST_MEMBER, buildSet(CLOSE_BRACE));
    static final Set<String> NEXT_INTERFACE_SIGNATURE = buildSet(FIRST_INTERFACE_MEMBER, buildSet(CLOSE_BRACE));

    static final Set<String> LAST_CLASS_OR_INTERFACE_BODY = buildSet(LAST_INITIAL, buildSet(CLOSE_BRACE));
    static final Set<String> NEXT_CLASS_OR_INTERFACE_BODY = NEXT_CLASS_OR_INTERFACE;

    static final Set<String> LAST_ATTRIBUTE = buildSet(LAST_CLASS_OR_INTERFACE_BODY, buildSet(SEMICOLON));
    static final Set<String> NEXT_ATTRIBUTE = buildSet(FIRST_MEMBER, buildSet(CLOSE_BRACE));

    static final Set<String> LAST_CLASS_METHOD_SIGNATURE = buildSet(LAST_CLASS_OR_INTERFACE_BODY, buildSet(CLOSE_PARENTHESIS));
    static final Set<String> LAST_INTERFACE_METHOD_SIGNATURE = buildSet(LAST_CLASS_OR_INTERFACE_BODY, buildSet(SEMICOLON));
    static final Set<String> NEXT_CLASS_METHOD_SIGNATURE = FIRST_BLOCK;
    static final Set<String> NEXT_INTERFACE_METHOD_SIGNATURE = FIRST_INTERFACE_MEMBER;

    static final Set<String> LAST_BLOCK = buildSet(LAST_CLASS_OR_INTERFACE_BODY, buildSet(CLOSE_BRACE));
    static final Set<String> NEXT_BLOCK = FIRST_MEMBER;

    static final Set<String> LAST_CONTROL_STRUCTURE = buildSet(LAST_BLOCK, buildSet(CLOSE_PARENTHESIS));
    static final Set<String> NEXT_CONTROL_STRUCTURE = FIRST_SENTENCE;

    static final Set<String> LAST_SENTENCE = buildSet(LAST_BLOCK, buildSet(SEMICOLON));
    static final Set<String> NEXT_SENTENCE = buildSet(FIRST_SENTENCE, buildSet(CLOSE_BRACE));

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
