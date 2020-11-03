package com.minijava.compiler.semantic;

import com.minijava.compiler.semantic.entities.Class;
import com.minijava.compiler.semantic.entities.*;
import com.minijava.compiler.semantic.entities.modifiers.Form;
import com.minijava.compiler.semantic.exceptions.DuplicateUnitException;
import com.minijava.compiler.semantic.exceptions.MainMethodNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minijava.compiler.semantic.entities.PredefinedEntities.MAIN;

public class SymbolTable {
    private Map<String, Class> classes = new HashMap<>();
    private Map<String, Interface> interfaces = new HashMap<>();

    private Unit currentUnit;
    private Form currentAccessForm;

    private List<Exception> exceptions = new ArrayList<>();

    public SymbolTable() {
        initialize();
    }

    private void initialize() {
        add(PredefinedEntities.OBJECT);
        add(PredefinedEntities.SYSTEM);
    }

    public Unit get(String name) {
        if (classes.containsKey(name)) {
            return classes.get(name);
        } else {
            return interfaces.getOrDefault(name, null);
        }
    }

    public Class getClass(String className) {
        return classes.get(className);
    }

    public Interface getInterface(String interfaceName) {
        return interfaces.get(interfaceName);
    }

    public boolean contains(String unitName) {
        return classes.containsKey(unitName) || interfaces.containsKey(unitName);
    }

    public boolean containsClass(String className) {
        return classes.containsKey(className);
    }

    public boolean containsInterface(String interfaceName) {
        return interfaces.containsKey(interfaceName);
    }

    public void add(Class newClass) {
        add(classes, newClass);
    }

    public void add(Interface newInterface) {
        add(interfaces, newInterface);
    }

    private <T extends Unit> void add(Map<String, T> unitsMap, T unit) {
        String name = unit.getName();

        if (!interfaces.containsKey(name) && !classes.containsKey(name)) {
            unitsMap.put(name, unit);
        } else {
            exceptions.add(new DuplicateUnitException(unit));
        }
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public Form getCurrentAccessForm() {
        return currentAccessForm;
    }

    public void setCurrentAccessForm(Form currentAccessForm) {
        this.currentAccessForm = currentAccessForm;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void throwLater(Exception exception) {
        exceptions.add(exception);
    }

    public void checkDeclarations() {
        checkUnits(interfaces);
        checkUnits(classes);

        checkMainExists();
    }

    private <T extends Unit> void checkUnits(Map<String, T> unitsMap) {
        List<T> invalidUnits = unitsMap.values()
                .stream()
                .filter(unit -> !unit.validDeclaration())
                .collect(Collectors.toList());

        invalidUnits.forEach(unit -> unitsMap.remove(unit.getName()));
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
        consolidate(interfaces);
        consolidate(classes);
    }

    private <T extends Unit> void consolidate(Map<String, T> unitsMap) {
        unitsMap.values().forEach(T::consolidate);
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "classes=" + classes +
                "interfaces=" + interfaces +
                '}';
    }
}
