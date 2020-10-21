package com.minijava.compiler.syntactic.analyzer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;

public class TokenGroups {
    // FIRST
    static class Firsts {
        static final Set<String> CLASS_OR_INTERFACE = buildSet(CLASS_KW, INTERFACE_KW);

        static final Set<String> VISIBILITY = buildSet(PUBLIC_KW, PRIVATE_KW);
        static final Set<String> ATTRIBUTE = VISIBILITY;

        private static final Set<String> CONSTRUCTOR = buildSet(CLASS_ID);

        static final Set<String> METHOD_FORM = buildSet(STATIC_KW, DYNAMIC_KW);
        static final Set<String> METHOD = METHOD_FORM;

        static final Set<String> MEMBER = buildSet(ATTRIBUTE, CONSTRUCTOR, METHOD);
        static final Set<String> INTERFACE_MEMBER = METHOD;

        static final Set<String> PRIMITIVE_TYPE = buildSet(BOOLEAN_KW, CHAR_KW, INT_KW, STRING_KW);
        private static final Set<String> CLASS_TYPE = buildSet(CLASS_ID);
        static final Set<String> TYPE = buildSet(PRIMITIVE_TYPE, CLASS_TYPE);

        static final Set<String> FORMAL_ARGS = TYPE;

        static final String IMPLICIT_STATIC_ACCESS = CLASS_ID;
        static final String EXPLICIT_STATIC_ACCESS = STATIC_KW;
        static final Set<String> STATIC_ACCESS = buildSet(IMPLICIT_STATIC_ACCESS, EXPLICIT_STATIC_ACCESS);

        static final String IMPLICIT_ACCESS = IMPLICIT_STATIC_ACCESS;
        static final Set<String> EXPLICIT_ACCESS = buildSet(THIS_KW, VAR_MET_ID, EXPLICIT_STATIC_ACCESS, NEW_KW, OPEN_PARENTHESIS);
        static final Set<String> ACCESS = buildSet(buildSet(IMPLICIT_ACCESS), EXPLICIT_ACCESS);

        static final String IMPLICIT_CALL_OR_ASSIGNMENT = IMPLICIT_ACCESS;
        static final Set<String> EXPLICIT_CALL_OR_ASSIGNMENT = EXPLICIT_ACCESS;
        static final Set<String> CALL_OR_ASSIGNMENT = buildSet(EXPLICIT_CALL_OR_ASSIGNMENT, buildSet(IMPLICIT_CALL_OR_ASSIGNMENT));

        private static final Set<String> EMPTY_SENTENCE = buildSet(SEMICOLON);
        static final Set<String> DECLARATION = TYPE;
        private static final Set<String> IF = buildSet(IF_KW);
        private static final Set<String> WHILE = buildSet(WHILE_KW);
        private static final Set<String> BLOCK = buildSet(OPEN_BRACE);
        private static final Set<String> RETURN = buildSet(RETURN_KW);
        static final Set<String> SENTENCE = buildSet(EMPTY_SENTENCE, CALL_OR_ASSIGNMENT, DECLARATION, IF, WHILE,
                BLOCK, RETURN);

        static final Set<String> ASSIGNMENT_TYPE = buildSet(ASSIGN, ADD_ASSIGN, SUB_ASSIGN);

        static final Set<String> UNARY_OPERATOR = buildSet(ADD, SUB, NOT);
        static final Set<String> BINARY_OPERATOR_1 = buildSet(OR);
        static final Set<String> BINARY_OPERATOR_2 = buildSet(AND);
        static final Set<String> BINARY_OPERATOR_3 = buildSet(EQUALS, NOT_EQUALS);
        static final Set<String> BINARY_OPERATOR_4 = buildSet(LESS, GREATER, LESS_OR_EQ, GREATER_OR_EQ);
        static final Set<String> BINARY_OPERATOR_5 = buildSet(ADD, SUB);
        static final Set<String> BINARY_OPERATOR_6 = buildSet(MUL, DIV, MOD);

        static final Set<String> LITERAL = buildSet(NULL_KW, TRUE_KW, FALSE_KW, INT_LITERAL, CHAR_LITERAL, STRING_LITERAL);
        static final Set<String> OPERAND = buildSet(LITERAL, ACCESS);
        static final Set<String> EXPRESSION = buildSet(UNARY_OPERATOR, OPERAND);
    }

    // SECOND
    static class Seconds {
        static final String IMPLICIT_STATIC_ACCESS = DOT;
        static final String IMPLICIT_ACCESS = IMPLICIT_STATIC_ACCESS;
        static final String IMPLICIT_CALL_OR_ASSIGNMENT = IMPLICIT_ACCESS;
    }

    // RECOVERY
    static class Last {
        static final Set<String> INITIAL = buildSet(EOF);

        static final Set<String> CLASS_OR_INTERFACE = INITIAL;
        static final Set<String> CLASS_OR_INTERFACE_SIGNATURE = buildSet(INITIAL, buildSet(OPEN_BRACE));
        static final Set<String> CLASS_OR_INTERFACE_BODY = buildSet(INITIAL, buildSet(CLOSE_BRACE));

        static final Set<String> ATTRIBUTE = buildSet(CLASS_OR_INTERFACE_BODY, buildSet(SEMICOLON));

        static final Set<String> CLASS_METHOD_SIGNATURE = buildSet(CLASS_OR_INTERFACE_BODY, buildSet(CLOSE_PARENTHESIS));
        static final Set<String> INTERFACE_METHOD_SIGNATURE = buildSet(CLASS_OR_INTERFACE_BODY, buildSet(SEMICOLON));

        static final Set<String> BLOCK = buildSet(CLASS_OR_INTERFACE_BODY, buildSet(CLOSE_BRACE));
        static final Set<String> CONTROL_STRUCTURE = buildSet(BLOCK, buildSet(CLOSE_PARENTHESIS));
        static final Set<String> SENTENCE = buildSet(BLOCK, buildSet(SEMICOLON));
    }

    static class Next {
        static final Set<String> CLASS_OR_INTERFACE = buildSet(Firsts.CLASS_OR_INTERFACE, buildSet(EOF));
        static final Set<String> CLASS_SIGNATURE = buildSet(Firsts.MEMBER, buildSet(CLOSE_BRACE));
        static final Set<String> INTERFACE_SIGNATURE = buildSet(Firsts.INTERFACE_MEMBER, buildSet(CLOSE_BRACE));
        static final Set<String> CLASS_OR_INTERFACE_BODY = CLASS_OR_INTERFACE;

        static final Set<String> ATTRIBUTE = buildSet(Firsts.MEMBER, buildSet(CLOSE_BRACE));

        static final Set<String> CLASS_METHOD_SIGNATURE = Firsts.BLOCK;
        static final Set<String> INTERFACE_METHOD_SIGNATURE = Firsts.INTERFACE_MEMBER;

        static final Set<String> BLOCK = Firsts.MEMBER;
        static final Set<String> CONTROL_STRUCTURE = Firsts.SENTENCE;
        static final Set<String> SENTENCE = buildSet(Firsts.SENTENCE, buildSet(CLOSE_BRACE));
    }

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
