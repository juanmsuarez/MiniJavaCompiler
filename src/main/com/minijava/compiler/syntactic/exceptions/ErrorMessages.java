package com.minijava.compiler.syntactic.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.syntactic.models.TokenGroupNames.*;

class ErrorMessages {
    static final Map<String, String> TOKEN_DESCRIPTION = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put(EOF, "el fin de archivo");
                put(CLASS_OR_INTERFACE, "el comienzo de una clase o interfaz");
                put(TYPE, "un tipo");
                put(METHOD_TYPE, "un tipo de método");
                put(ASSIGNMENT_OR_SENTENCE_END, "una asignación o un finalizador de sentencia");
                put(EXPRESSION, "una expresión");
                put(OPERAND, "un operando");
                put(SEMICOLON, "un punto y coma");
                put(CLASS_ID, "un identificador de clase");
                put(VAR_MET_ID, "un identificador de método o variable");
                put(OPEN_BRACE, "una llave de apertura");
                put(CLOSE_BRACE, "una llave de cierre");
                put(OPEN_PARENTHESIS, "un paréntesis de apertura");
                put(CLOSE_PARENTHESIS, "un paréntesis de cierre");
                put(DOT, "un punto para realizar un acceso");
                put(GREATER, "un cierre de tipo genérico");
                put(SENTENCE, "una sentencia");
            }}
    );

    static final Map<String, String> LEXEME_DESCRIPTION = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put(EOF, "el fin de archivo");
            }}
    );
}
