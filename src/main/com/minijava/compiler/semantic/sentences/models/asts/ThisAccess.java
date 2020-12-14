package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.DynamicAccessInStaticContextException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

public class ThisAccess extends Access {
    public ThisAccess(Lexeme lexeme) {
        super(lexeme, lexeme);
    }

    @Override
    public void check(Context context) throws SemanticException {
        if (context.isStatic()) {
            throw new DynamicAccessInStaticContextException(lexeme);
        }

        type = new ReferenceType(context.getCurrentClass().getName());

        if (chainedAccess != null) {
            chainedAccess.check(context, type);
            type = chainedAccess.type;
        }
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public void translate() throws IOException {
        // TODO: pending
        if (chainedAccess != null) {
            chainedAccess.translate();
        }
    }

    @Override
    public String toString() {
        return "ThisAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
