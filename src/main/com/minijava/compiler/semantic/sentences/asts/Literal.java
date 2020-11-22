package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.models.Token;

public class Literal extends Operand {
    private Token literal;

    public Literal(Token literal) {
        super(literal.getLexeme());
        this.literal = literal;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "literal=" + literal +
                '}';
    }
}
