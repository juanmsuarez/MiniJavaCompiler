package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class Operand extends Expression {
    public Operand(Lexeme lexeme) {
        super(lexeme);
    }
}
