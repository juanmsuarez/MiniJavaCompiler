package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.exceptions.CyclicInheritanceException;
import com.minijava.compiler.semantic.declarations.exceptions.DuplicateParentException;
import com.minijava.compiler.semantic.declarations.exceptions.InvalidParentTypeException;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Interface extends Unit {
    private Set<String> parentNames = new HashSet<>();

    public void add(String interfaceName) {
        if (!parentNames.contains(interfaceName)) {
            parentNames.add(interfaceName);
        } else {
            symbolTable.throwLater(new DuplicateParentException(this, interfaceName));
        }
    }

    @Override
    public boolean validDeclaration() {
        checkInterfacesExist(parentNames);
        if (!validInheritanceGraph()) {
            return false;
        }

        checkChildren();

        return true;
    }

    @Override
    protected SemanticException parentOrInterfaceNotFoundException(String interfaceName) {
        return new InvalidParentTypeException(this, new ReferenceType(interfaceName, this, Form.DYNAMIC));
    }

    private boolean hasCycles(Interface currentInterface, Set<String> visited, Set<String> visiting) {
        String currentName = currentInterface.getName();
        visiting.add(currentName);

        for (String parentName : currentInterface.parentNames) {
            Interface parent = symbolTable.getInterface(parentName);
            if (parent == null) continue;

            if (visiting.contains(parentName)) {
                return true;
            } else if (!visited.contains(parentName) && hasCycles(parent, visited, visiting)) {
                return true;
            }
        }

        visiting.remove(currentName);
        visited.add(currentName);
        return false;
    }

    private boolean validInheritanceGraph() {
        if (hasCycles(this, new HashSet<>(), new HashSet<>())) {
            symbolTable.throwLater(new CyclicInheritanceException(this));
            return false;
        }

        return true;
    }

    private void checkChildren() {
        checkMethods();
    }

    @Override
    public void consolidate() {
        if (consolidated) {
            return;
        }

        for (String parentName : parentNames) {
            Interface parent = symbolTable.getInterface(parentName);
            parent.consolidate();
        }

        consolidateMethods();

        consolidated = true;
    }

    private void consolidateMethods() {
        Collection<Method> inheritedMethods = validInterfacesMethods(parentNames);

        for (Method inheritedMethod : inheritedMethods) {
            consolidateMethod(inheritedMethod, null);
        }
    }

    @Override
    public String toString() {
        return "\nInterface{" +
                "name='" + name + '\'' +
                ", parentNames='" + parentNames + '\'' +
                ", \nmethods=" + methods +
                '}';
    }
}
