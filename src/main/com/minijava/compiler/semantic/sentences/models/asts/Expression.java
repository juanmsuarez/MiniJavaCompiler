package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

public abstract class Expression {
    protected Lexeme leftLexeme;
    protected Lexeme lexeme;

    protected Type type;

    public Expression(Lexeme leftLexeme, Lexeme lexeme) {
        this.leftLexeme = leftLexeme;
        this.lexeme = lexeme;
    }

    public Type getType() {
        return type;
    }

    public abstract void check(Context context) throws SemanticException;

    public abstract void translate() throws IOException;

    @Override
    public abstract String toString();
}
