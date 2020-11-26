package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Callable;
import com.minijava.compiler.semantic.declarations.entities.Parameter;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ArgumentsNumberMismatchException;
import com.minijava.compiler.semantic.sentences.exceptions.NonConformingArgumentException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.util.List;

public interface CallableAccess {
    void add(Expression argument);

    static void checkArguments(Callable callable, Lexeme callLexeme, List<Expression> arguments, Context context) throws SemanticException {
        if (callable.parametersSize() != arguments.size()) {
            throw new ArgumentsNumberMismatchException(callLexeme, callable.parametersSize());
        }

        for (int i = 0; i < arguments.size(); i++) {
            Expression argument = arguments.get(i);
            argument.check(context);

            Parameter parameter = callable.getParameter(i);
            if (!argument.type.isSubtype(parameter.getType())) {
                throw new NonConformingArgumentException(argument.leftLexeme, i, parameter.getType());
            }
        }
    }

    // TODO: VERIFICAR -> que los errores en argumentos se muestren correctamente
}
