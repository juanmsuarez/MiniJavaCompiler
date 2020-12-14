package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidCallException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.types.VoidType.VOID;

public class CallSentence extends Sentence {
    private Access access;

    public CallSentence(Access access) {
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
    public void translate() throws IOException {
        access.translate();

        String returnTypeName = access.type.getName();
        if (!returnTypeName.equals(VOID)) {
            codeGenerator.generate(
                    ".CODE",
                    "POP"
            );
        }
    }

    @Override
    public String toString() {
        return "\nCall{" +
                "access=" + access +
                '}';
    }
}
