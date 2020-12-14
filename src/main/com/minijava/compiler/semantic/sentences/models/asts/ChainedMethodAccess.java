package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidChainedMethodAccessException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChainedMethodAccess extends ChainedAccess implements CallableAccess {
    private List<Expression> arguments = new ArrayList<>();

    public ChainedMethodAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public void add(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public void check(Context context) throws SemanticException {
        if (accessedClass == null || !accessedClass.containsMethod(name)) {
            throw new InvalidChainedMethodAccessException(lexeme);
        }

        Method method = accessedClass.getMethod(name);
        type = method.getType();

        CallableAccess.checkArguments(method, lexeme, arguments, context);
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
        return true;
    }

    @Override
    public void translate() throws IOException {
        // TODO pending
        // TODO: pop el this en encadenados estáticos
    }

    @Override
    public String toString() {
        return "ChainedMethodAccess{" +
                "lexeme=" + lexeme +
                ", arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
