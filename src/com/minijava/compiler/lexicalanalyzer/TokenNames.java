package com.minijava.compiler.lexicalanalyzer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TokenNames {
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

    public static final String CLASS_ID = "classId";
    public static final String VAR_MET_ID = "varMetId";
    public static final String INT_LIT = "intLit";
    public static final String CHAR_LIT = "charLit";
    public static final String STRING_LIT = "stringLit";
    public static final String EOF = "EOF";
}
