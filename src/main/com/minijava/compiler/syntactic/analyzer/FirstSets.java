package com.minijava.compiler.syntactic.analyzer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;

class FirstSets {
    static final Set<String> FIRST_ATTRIBUTE = buildSet(PUBLIC_KW, PRIVATE_KW);
    static final Set<String> FIRST_CONSTRUCTOR = buildSet(CLASS_ID);
    static final Set<String> FIRST_METHOD = buildSet(STATIC_KW, DYNAMIC_KW);
    static final Set<String> FIRST_MEMBER = buildSet(FIRST_ATTRIBUTE, FIRST_CONSTRUCTOR, FIRST_METHOD);

    static final Set<String> FIRST_PRIMITIVE_TYPE = buildSet(BOOLEAN_KW, CHAR_KW, INT_KW, STRING_KW);
    static final Set<String> FIRST_TYPE = buildSet(FIRST_PRIMITIVE_TYPE, buildSet(CLASS_ID));

    static final Set<String> FIRST_FORMAL_ARGS = FIRST_TYPE;

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
