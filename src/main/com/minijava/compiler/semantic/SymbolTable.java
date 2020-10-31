package com.minijava.compiler.semantic;

import com.minijava.compiler.semantic.entities.Class;
import com.minijava.compiler.semantic.entities.Method;
import com.minijava.compiler.semantic.entities.PredefinedEntities;
import com.minijava.compiler.semantic.exceptions.DuplicateClassException;
import com.minijava.compiler.semantic.exceptions.MainMethodNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minijava.compiler.semantic.entities.PredefinedEntities.MAIN;

public class SymbolTable {

    private Map<String, Class> classes = new HashMap<>();

    private Class currentClass;

    private List<Exception> exceptions = new ArrayList<>();

    public SymbolTable() {
        initialize();
    }

    private void initialize() {
        add(PredefinedEntities.OBJECT);
        add(PredefinedEntities.SYSTEM);
    }

    public Class get(String className) {
        return classes.get(className);
    }

    public boolean contains(String className) {
        return classes.containsKey(className);
    }

    public void add(Class newClass) { // TODO: interfaces deberían estar acá también
        String name = newClass.getName();

        if (!classes.containsKey(name)) {
            classes.put(name, newClass);
        } else {
            exceptions.add(new DuplicateClassException(newClass));
        }
    }

    public Class getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void throwLater(Exception exception) {
        exceptions.add(exception);
    }

    public void checkDeclarations() {
        checkClasses();

        checkMainExists();
    }

    private void checkClasses() {
        List<Class> invalidClasses = classes.values()
                .stream()
                .filter(aClass -> !aClass.validDeclaration())
                .collect(Collectors.toList());

        invalidClasses.forEach(aClass -> classes.remove(aClass.getName()));
    }

    private void checkMainExists() {
        boolean mainExists = false;
        for (Class aClass : classes.values()) {
            Method method = aClass.getMethod(MAIN.getName());
            if (MAIN.equals(method)) {
                mainExists = true;
            }
        }

        if (!mainExists) {
            throwLater(new MainMethodNotFoundException());
        }
    }

    public void consolidate() {

    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "classes=" + classes +
                '}';
    }
}
