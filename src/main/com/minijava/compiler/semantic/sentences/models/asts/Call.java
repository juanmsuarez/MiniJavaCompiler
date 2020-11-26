package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidCallException;
import com.minijava.compiler.semantic.sentences.models.Context;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Call extends Sentence {
    private Access access;

    public Call(Access access) {
        super(access.getLastInChain().lexeme);
        this.access = access;
    }

    @Override
    public void check(Context context) {
        try {
            access.check(context);

            if (!access.getLastInChain().isCallable()) {
                throw new InvalidCallException(lexeme);
            }
        } catch (SemanticException exception) {
            symbolTable.throwLater(exception);
        }
    }

    @Override
    public String toString() {
        return "\nCall{" +
                "access=" + access +
                '}';
    }
}
