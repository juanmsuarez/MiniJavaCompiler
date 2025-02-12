package com.minijava.compiler.semantic;

import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.*;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.exceptions.DuplicateUnitException;
import com.minijava.compiler.semantic.declarations.exceptions.MainMethodNotFoundException;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minijava.compiler.semantic.declarations.entities.PredefinedEntities.MAIN;

public class SymbolTable {
    private Map<String, Class> classes = new HashMap<>();
    private Map<String, Interface> interfaces = new HashMap<>();

    private Unit currentUnit;
    private Form currentAccessForm;

    private List<SemanticException> exceptions = new ArrayList<>();

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

    public List<SemanticException> getExceptions() {
        return exceptions;
    }

    public void throwLater(SemanticException exception) {
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
        if (getMainMethod() == null) {
            throwLater(new MainMethodNotFoundException());
        }
    }

    public Method getMainMethod() {
        Method main = null;

        for (Class aClass : classes.values()) {
            Method method = aClass.getMethod(MAIN.getName());
            if (MAIN.equals(method)) {
                if (main == null || method.getLexeme().compareTo(main.getLexeme()) < 0) {
                    main = method;
                }
            }
        }

        return main;
    }

    public void consolidate() {
        consolidate(interfaces);
        consolidate(classes);
    }

    private <T extends Unit> void consolidate(Map<String, T> unitsMap) {
        unitsMap.values().forEach(T::consolidate);
    }

    public void checkSentences() {
        for (Class aClass : classes.values()) {
            aClass.checkSentences();
        }
    }

    public void translate() throws IOException {
        for (Class aClass : classes.values()) {
            aClass.translate();
        }
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "classes=" + classes +
                "interfaces=" + interfaces +
                '}';
    }
}
