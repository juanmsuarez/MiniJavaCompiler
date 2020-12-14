package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

public abstract class Sentence {
    protected Lexeme lexeme; // podría pushearse hacia los hijos ya que no todos necesitan (ya está parcialmente hecho)

    public Sentence(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public abstract void check(Context context);

    public abstract void translate() throws IOException;

    @Override
    public abstract String toString();
}
