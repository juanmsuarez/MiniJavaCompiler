package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.types.Type;

public abstract class Expression {
    protected Lexeme lexeme;

    protected Type type;

    public Expression(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public abstract String toString();
}
