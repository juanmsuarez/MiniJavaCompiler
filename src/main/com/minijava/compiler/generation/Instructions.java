package com.minijava.compiler.generation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.minijava.compiler.lexical.models.TokenNames.*;

public class Instructions {
    public static final Map<String, String> UNARY_OPERATORS = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put(ADD, "NOP");
                put(SUB, "NEG");
                put(NOT, "NOT");
            }});

    public static final Map<String, String> BINARY_OPERATORS = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put(ADD, "ADD");
                put(SUB, "SUB");
                put(MUL, "MUL");
                put(DIV, "DIV");
                put(MOD, "MOD");
                put(AND, "AND");
                put(OR, "OR");
                put(EQUALS, "EQ");
                put(NOT_EQUALS, "NE");
                put(LESS, "LT");
                put(GREATER, "GT");
                put(LESS_OR_EQ, "LE");
                put(GREATER_OR_EQ, "GE");
            }});

    public static final Map<String, String> COMPOSITE_OPERATORS = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put(ADD_ASSIGN, "ADD");
                put(SUB_ASSIGN, "SUB");
            }});
}
