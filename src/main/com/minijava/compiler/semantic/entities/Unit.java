package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.DuplicateMethodException;
import com.minijava.compiler.semantic.exceptions.InterfaceCollisionException;
import com.minijava.compiler.semantic.exceptions.InvalidRedefinitionException;
import com.minijava.compiler.semantic.exceptions.SemanticException;

import java.util.*;
import java.util.stream.Collectors;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public abstract class Unit {
    protected Lexeme lexeme;
    protected String name;
    protected String genericType;
    protected Map<String, Method> methods = new HashMap<>();

    protected Callable currentCallable;
    protected boolean consolidated = false;

    public Unit() {
    }

    public Unit(String name) {
        this.name = name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public String getName() {
        return name;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }

    public String getGenericType() {
        return genericType;
    }

    public boolean isGeneric() {
        return genericType != null;
    }

    public abstract void add(String interfaceName);

    public Method getMethod(String name) {
        return methods.get(name);
    }

    public void add(Method method) {
        String name = method.getName();

        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            symbolTable.throwLater(new DuplicateMethodException(method));
        }

        currentCallable = method;
    }

    public Callable getCurrentCallable() {
        return currentCallable;
    }

    public abstract boolean validDeclaration();

    protected void checkInterfacesExist(Set<String> interfaces) {
        List<String> nonexistentParents = interfaces
                .stream()
                .filter(interfaceName -> !symbolTable.containsInterface(interfaceName))
                .collect(Collectors.toList());

        nonexistentParents.forEach((nonexistentInterface) -> {
            symbolTable.throwLater(parentOrInterfaceNotFoundException(nonexistentInterface));
            interfaces.remove(nonexistentInterface);
        });
    }

    protected abstract SemanticException parentOrInterfaceNotFoundException(String interfaceName);

    protected void checkMethods() {
        methods.values().removeIf(method -> !method.validDeclaration());
    }

    public abstract void consolidate();

    protected Collection<Method> validInterfacesMethods(Set<String> interfaces) {
        Map<String, Method> interfacesMethods = new HashMap<>();

        for (String interfaceName : interfaces) {
            Interface anInterface = symbolTable.getInterface(interfaceName);

            for (Method interfaceMethod : anInterface.methods.values()) { // for each method to implement
                String methodName = interfaceMethod.getName();

                if (interfacesMethods.containsKey(interfaceMethod.getName())) { // if already seen
                    Method otherInterfaceMethod = interfacesMethods.get(methodName);

                    if (!interfaceMethod.equals(otherInterfaceMethod)) { // check for collision
                        symbolTable.throwLater(new InterfaceCollisionException(this, interfaceMethod));
                    }
                } else {
                    interfacesMethods.put(methodName, interfaceMethod);
                }
            }
        }

        return interfacesMethods.values();
    }

    protected void consolidateMethod(Method parentMethod, String parentGenericType) {
        String parentMethodName = parentMethod.getName();
        Method instantiatedParentMethod = parentMethod.instantiate(parentGenericType);

        Method childMethod = methods.get(parentMethodName);
        if (childMethod != null && !childMethod.equals(instantiatedParentMethod)) { // remove if it's invalidly redefined
            symbolTable.throwLater(new InvalidRedefinitionException(childMethod));
            methods.remove(childMethod.getName());
        }

        if (!methods.containsKey(parentMethodName)) { // add if not redefined
            methods.put(parentMethodName, parentMethod);
        }
    }
}
