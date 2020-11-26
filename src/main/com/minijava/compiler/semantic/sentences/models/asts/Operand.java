package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class Operand extends Expression {
    public Operand(Lexeme leftLexeme, Lexeme lexeme) {
        super(leftLexeme, lexeme);
    }
}
