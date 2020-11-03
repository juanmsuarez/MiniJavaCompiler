package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.semantic.entities.types.ReferenceType;
import com.minijava.compiler.semantic.exceptions.*;

import java.util.*;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.entities.PredefinedEntities.OBJECT;

public class Class extends Unit {
    private ReferenceType parentType;
    private Set<String> interfaceNames = new HashSet<>();
    private Constructor constructor;
    private List<Attribute> hiddenAttributes = new ArrayList<>();
    private Map<String, Attribute> attributes = new HashMap<>();

    public Class() {
    }

    public Class(String name) {
        super(name);
    }

    public Class(String name, String parentName) {
        super(name);
        this.parentType = new ReferenceType(parentName, this);
    }

    public void setParentType(ReferenceType parentType) {
        this.parentType = parentType;
    }

    public void add(String interfaceName) {
        if (!interfaceNames.contains(interfaceName)) {
            interfaceNames.add(interfaceName);
        } else {
            symbolTable.throwLater(new DuplicateImplementationException(this, interfaceName));
        }
    }

    public void add(Attribute attribute) {
        String name = attribute.getName();

        if (!attributes.containsKey(name)) {
            attributes.put(name, attribute);
        } else {
            symbolTable.throwLater(new DuplicateAttributeException(attribute));
        }
    }

    public void add(Constructor constructor) {
        if (constructor.getName().equals(name)) {
            if (this.constructor == null) {
                this.constructor = constructor;
            } else {
                symbolTable.throwLater(new DuplicateConstructorException(constructor));
            }
        } else {
            symbolTable.throwLater(new InvalidConstructorException(constructor));
        }

        currentCallable = constructor;
    }

    public boolean validDeclaration() {
        // TODO: check generic type not defined?
        checkParentExists();
        checkInterfacesExist(interfaceNames);
        if (!validInheritanceChain()) {
            return false;
        }

        checkChildren();

        return true;
    }

    class B<T> {
    }

    class A extends B<B> {
    }

    private void checkParentExists() {
        if (!name.equals(OBJECT.name) && !parentType.isGloballyValid()) { // TODO: check parent's generic type
            symbolTable.throwLater(new InvalidParentTypeException(this, parentType));
            parentType = new ReferenceType(OBJECT.name, this);
        }
    }

    protected SemanticException parentOrInterfaceNotFoundException(String interfaceName) {
        return new InterfaceNotFoundException(this, interfaceName);
    }

    private boolean validInheritanceChain() {
        Set<String> ancestors = new HashSet<>();
        Class currentClass = this;

        while (currentClass != null && !currentClass.name.equals(OBJECT.name) && !ancestors.contains(currentClass.name)) { // go up in chain
            ancestors.add(currentClass.name);

            currentClass = symbolTable.getClass(currentClass.parentType.getName());
        }

        if (currentClass != null && !currentClass.name.equals(OBJECT.name)) { // cycle found
            symbolTable.throwLater(new CyclicInheritanceException(this));
            return false;
        }

        return true;
    }

    private void checkChildren() { // TODO: children's usage of generic type
        attributes.values().removeIf(attribute -> !attribute.validDeclaration());

        if (constructor == null || !constructor.validDeclaration()) {
            constructor = new Constructor(name);
        }

        checkMethods();
    }

    public void consolidate() {
        if (consolidated || name.equals(OBJECT.getName())) {
            return;
        }

        Class parent = symbolTable.getClass(parentType.getName());
        parent.consolidate(); // TODO: instantiate parent methods and attributes (if generic)

        consolidateAttributes();
        consolidateMethods();
        checkInterfacesImplemented();

        consolidated = true;
    }

    private void consolidateAttributes() {
        Class parent = symbolTable.getClass(parentType.getName());

        hiddenAttributes.addAll(parent.hiddenAttributes);

        for (Attribute parentAttribute : parent.attributes.values()) {
            String parentAttributeName = parentAttribute.getName();

            if (attributes.containsKey(parentAttributeName)) {
                hiddenAttributes.add(parentAttribute);
            } else {
                attributes.put(parentAttributeName, parentAttribute);
            }
        }
    }

    private void consolidateMethods() {
        Class parent = symbolTable.getClass(parentType.getName());

        for (Method method : parent.methods.values()) {
            consolidateMethod(method);
        }
    }

    private void checkInterfacesImplemented() {
        Collection<Method> methodsToImplement = validInterfacesMethods(interfaceNames);

        for (Method methodToImplement : methodsToImplement) {
            Method implementedMethod = methods.get(methodToImplement.getName());

            if (implementedMethod == null) {
                symbolTable.throwLater(new NotImplementedException(this, methodToImplement));
            } else if (!implementedMethod.equals(methodToImplement)) {
                symbolTable.throwLater(new InvalidImplementationException(this, methodToImplement));
            }
        }
    }

    @Override
    public String toString() {
        return "\nClass{" +
                "name='" + name + '\'' +
                ", genericType='" + genericType + '\'' +
                ", parent='" + parentType + '\'' +
                ", interfaceNames='" + interfaceNames + '\'' +
                ", \nconstructor=" + constructor +
                ", \nhiddenAttributes=" + hiddenAttributes +
                ", \nattributes=" + attributes +
                ", \nmethods=" + methods +
                '}';
    }
}
