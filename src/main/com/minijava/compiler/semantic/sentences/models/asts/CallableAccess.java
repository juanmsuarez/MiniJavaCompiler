package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Callable;
import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.entities.Parameter;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ArgumentsNumberMismatchException;
import com.minijava.compiler.semantic.sentences.exceptions.NonConformingArgumentException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.semantic.declarations.entities.types.VoidType.VOID;

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

    static void reserveMemoryForReturnTypeIfNeeded(Type returnType, Form form) throws IOException {
        String returnTypeName = returnType.getName();
        if (!returnTypeName.equals(VOID)) {
            codeGenerator.generate(
                    ".CODE",
                    "RMEM 1"
            );

            if (form == Form.DYNAMIC) {
                codeGenerator.generate("SWAP");
            }
        }
    }

    static void translateArguments(List<Expression> arguments, Form form) throws IOException {
        for (Expression argument : arguments) {
            argument.translate();

            if (form == Form.DYNAMIC) {
                codeGenerator.generate(
                        ".CODE",
                        "SWAP"
                );
            }
        }
    }

    static void call(Method method) throws IOException {
        if (method.getForm() == Form.DYNAMIC) {
            callDynamic(method.getOffset());
        } else {
            callStatic(method.getLabel());
        }
    }

    static void callDynamic(int offset) throws IOException {
        codeGenerator.generate(
                ".CODE",
                "DUP",
                "LOADREF 0",
                "LOADREF " + offset,
                "CALL"
        );
    }

    static void callStatic(String label) throws IOException {
        codeGenerator.generate(
                ".CODE",
                "PUSH " + label,
                "CALL"
        );
    }
}
