package com.minijava.compiler.lexicalanalyzer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TokenNames {
    // KEYWORDS
    public static final String CLASS_KW = "classKw";
    public static final String EXTENDS_KW = "extendsKw";
    public static final String STATIC_KW = "static";
    public static final String DYNAMIC_KW = "dynamicKw";
    public static final String PUBLIC_KW = "publicKw";
    public static final String PRIVATE_KW = "privateKw";
    public static final String THIS_KW = "thisKw";
    public static final String NEW_KW = "newKw";
    public static final String NULL_KW = "nullKw";
    public static final String VOID_KW = "voidKw";
    public static final String BOOLEAN_KW = "booleanKw";
    public static final String CHAR_KW = "charKw";
    public static final String INT_KW = "intKw";
    public static final String STRING_KW = "stringKw";
    public static final String TRUE_KW = "trueKw";
    public static final String FALSE_KW = "falseKw";
    public static final String IF_KW = "ifKw";
    public static final String ELSE_KW = "elseKw";
    public static final String WHILE_KW = "whileKw";
    public static final String RETURN_KW = "returnKw";

    public static final Map<String, String> KEYWORDS = Collections.unmodifiableMap(
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

    // IDs
    public static final String CLASS_ID = "classId";
    public static final String VAR_MET_ID = "varMetId";

    // LITERALS
    public static final String INT_LITERAL = "intLiteral";
    public static final String CHAR_LITERAL = "charLiteral";
    public static final String STRING_LITERAL = "stringLiteral";

    // PUNCTUATION
    public static final String OPEN_PARENTHESIS = "openParenthesis";
    public static final String CLOSE_PARENTHESIS = "closeParenthesis";
    public static final String OPEN_BRACES = "openBraces";
    public static final String CLOSE_BRACES = "closeBraces";
    public static final String SEMICOLON = "semicolon";
    public static final String COMMA = "comma";
    public static final String DOT = "dot";

    public static final Map<String, String> PUNCTUATION = Collections.unmodifiableMap(
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
    public static final String NOT = "not";
    public static final String AND = "and";
    public static final String OR = "or";

    public static final String EQUALS = "equals";
    public static final String NOT_EQUALS = "notEquals";

    public static final String ADD = "add";
    public static final String SUB = "sub";
    public static final String MUL = "mul";
    public static final String DIV = "div";
    public static final String MOD = "mod";

    public static final String GREATER = "greater";
    public static final String GREATER_OR_EQ = "greaterEq";
    public static final String LESS = "less";
    public static final String LESS_OR_EQ = "lessEq";

    public static final String ASSIGN = "assign";
    public static final String ADD_ASSIGN = "addAssign";
    public static final String SUB_ASSIGN = "subAssign";

    public static final Map<String, String> OPERATORS = Collections.unmodifiableMap(
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

    // OTHERS
    public static final String EOF = "EOF";
}
