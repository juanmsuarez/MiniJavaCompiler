package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

import java.util.ArrayList;
import java.util.List;

public abstract class CallableAccess extends Access {
    protected List<Expression> arguments = new ArrayList<>();

    public CallableAccess(Lexeme lexeme) {
        super(lexeme);
    }

    public void add(Expression argument) {
        arguments.add(argument);
    }
}
