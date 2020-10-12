package com.minijava.compiler.syntactic.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class ErrorMessages {
    static final Map<String, String> ERROR_MESSAGES = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                // TODO: mensajes para cada token / token groups
                // put(EXPECTED_MEMBER_FIRST, "la declaración de ")
            }});
}
