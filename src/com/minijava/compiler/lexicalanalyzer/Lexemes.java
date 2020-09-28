package com.minijava.compiler.lexicalanalyzer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.minijava.compiler.lexicalanalyzer.TokenNames.*;

class Lexemes {
    // KEYWORDS
    static final Map<String, String> KEYWORDS = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put("class", CLASS_KW);
                put("extends", EXTENDS_KW);
                put("static", STATIC_KW);
                put("dynamic", DYNAMIC_KW);
                put("public", PUBLIC_KW);
                put("private", PRIVATE_KW);
                put("this", THIS_KW);
                put("new", NEW_KW);
                put("null", NULL_KW);
                put("void", VOID_KW);
                put("boolean", BOOLEAN_KW);
                put("char", CHAR_KW);
                put("int", INT_KW);
                put("String", STRING_KW);
                put("true", TRUE_KW);
                put("false", FALSE_KW);
                put("if", IF_KW);
                put("else", ELSE_KW);
                put("while", WHILE_KW);
                put("return", RETURN_KW);
            }});

    // PUNCTUATION
    static final Map<String, String> PUNCTUATION = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put("(", OPEN_PARENTHESIS);
                put(")", CLOSE_PARENTHESIS);
                put("{", OPEN_BRACES);
                put("}", CLOSE_BRACES);
                put(";", SEMICOLON);
                put(",", COMMA);
                put(".", DOT);
            }});

    // OPERATORS (AND ASSIGNMENT)
    static final Map<String, String> OPERATORS = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put("!", NOT);
                put("&&", AND);
                put("||", OR);
                put("==", EQUALS);
                put("!=", NOT_EQUALS);
                put("+", ADD);
                put("-", SUB);
                put("*", MUL);
                put("/", DIV);
                put("%", MOD);
                put(">", GREATER);
                put(">=", GREATER_OR_EQ);
                put("<", LESS);
                put("<=", LESS_OR_EQ);
                put("=", ASSIGN);
                put("+=", ADD_ASSIGN);
                put("-=", SUB_ASSIGN);
            }});

    // LITERALS
    static final String CHAR_DELIMITER = "'";
    static final String STRING_DELIMITER = "\"";
    static final String EMPTY_STRING_LITERAL = "\"\"";
    static final String TEXT_BLOCK_OPEN = "\"\"\"\n";
    static final String TEXT_BLOCK_CLOSE = "\n\"\"\"";
}
