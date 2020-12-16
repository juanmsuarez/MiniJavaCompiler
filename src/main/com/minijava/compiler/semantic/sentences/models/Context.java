package com.minijava.compiler.semantic.sentences.models;

import com.minijava.compiler.semantic.declarations.entities.Callable;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Variable;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.sentences.models.entities.LocalVariable;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Class currentClass;
    private Callable currentCallable;
    private Map<String, LocalVariable> currentLocalVariables = new HashMap<>();

    public Context(Class currentClass, Callable currentCallable) {
        this.currentClass = currentClass;
        this.currentCallable = currentCallable;
    }

    public Context(Context other) {
        this.currentClass = other.currentClass;
        this.currentCallable = other.currentCallable;
        this.currentLocalVariables = new HashMap<>(other.currentLocalVariables);
    }

    public void add(LocalVariable localVariable) {
        currentLocalVariables.put(localVariable.getName(), localVariable);
        currentCallable.add(localVariable);
    }

    public Class getCurrentClass() {
        return currentClass;
    }

    public Callable getCurrentCallable() {
        return currentCallable;
    }

    public boolean isStatic() {
        return currentCallable.getForm() == Form.STATIC;
    }

    private boolean isLocalVariable(String name) {
        return currentLocalVariables.containsKey(name);
    }

    private boolean isParameter(String name) {
        return currentCallable.containsParameter(name);
    }

    private boolean isAttribute(String name) {
        return currentClass.containsAttribute(name);
    }

    public boolean isMethodVariable(String name) {
        return isLocalVariable(name) || isParameter(name);
    }

    public boolean isVariableDefined(String name) {
        return isMethodVariable(name) || isAttribute(name);
    }

    public Variable getVariable(String name) {
        if (isLocalVariable(name)) {
            return currentLocalVariables.get(name);
        } else if (isParameter(name)) {
            return currentCallable.getParameter(name);
        } else {
            return currentClass.getAttribute(name);
        }
    }

    public Form getFormOfVariable(String name) {
        if (isMethodVariable(name)) {
            return null;
        } else {
            return currentClass.getAttribute(name).getForm();
        }
    }
}
