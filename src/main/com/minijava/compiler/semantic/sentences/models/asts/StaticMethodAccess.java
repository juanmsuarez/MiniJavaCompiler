package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ClassInStaticAccessNotFoundException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidStaticMethodAccessException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.util.ArrayList;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class StaticMethodAccess extends Access implements CallableAccess {
    private Lexeme classLexeme;
    private Lexeme methodLexeme;
    private List<Expression> arguments = new ArrayList<>();

    public StaticMethodAccess(Lexeme classLexeme, Lexeme methodLexeme) {
        super(classLexeme, methodLexeme);
        this.classLexeme = classLexeme;
        this.methodLexeme = methodLexeme;
    }

    @Override
    public void add(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public void check(Context context) throws SemanticException {
        String className = classLexeme.getString();
        String methodName = methodLexeme.getString();

        Class accessedClass = symbolTable.getClass(className);
        if (accessedClass == null) {
            throw new ClassInStaticAccessNotFoundException(classLexeme);
        }

        Method method = accessedClass.getMethod(methodName);
        if (method == null || method.getForm() == Form.DYNAMIC) {
            throw new InvalidStaticMethodAccessException(methodLexeme);
        }

        CallableAccess.checkArguments(method, lexeme, arguments, context);

        type = accessedClass.getMethod(methodName).getType();

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
    public String toString() {
        return "StaticMethodAccess{" +
                "classLexeme=" + classLexeme +
                ", methodLexeme=" + methodLexeme +
                ", arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
