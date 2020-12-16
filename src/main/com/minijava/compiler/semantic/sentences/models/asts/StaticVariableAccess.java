package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Attribute;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ClassInStaticAccessNotFoundException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidStaticVariableAccessException;
import com.minijava.compiler.semantic.sentences.models.Context;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class StaticVariableAccess extends Access {
    private Lexeme classLexeme;
    private Lexeme attributeLexeme;

    public StaticVariableAccess(Lexeme classLexeme, Lexeme attributeLexeme) {
        super(classLexeme, attributeLexeme);
        this.classLexeme = classLexeme;
        this.attributeLexeme = attributeLexeme;
    }

    @Override
    public void check(Context context) throws SemanticException {
        String className = classLexeme.getString();
        String attributeName = attributeLexeme.getString();

        Class accessedClass = symbolTable.getClass(className);
        if (accessedClass == null) {
            throw new ClassInStaticAccessNotFoundException(classLexeme);
        }

        Attribute attribute = accessedClass.getAttribute(attributeName);
        if (attribute == null || attribute.getVisibility() == Visibility.PRIVATE || attribute.getForm() == Form.DYNAMIC) {
            throw new InvalidStaticVariableAccessException(attributeLexeme);
        }

        type = attribute.getType();

        if (chainedAccess != null) {
            chainedAccess.check(context, type);
            type = chainedAccess.type;
        }
    }

    @Override
    public boolean isAssignable() {
        return true;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public void translate() throws IOException {
        throw new NotImplementedException(); // Class Records are needed to implement class attributes
    }

    @Override
    public String toString() {
        return "StaticVariableAccess{" +
                "classLexeme=" + classLexeme +
                ", variableLexeme=" + attributeLexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
