package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.exceptions.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.PredefinedEntities.OBJECT;

public class Class extends Unit {
    private ReferenceType parentType;
    private Set<String> interfaceNames = new HashSet<>();
    private Constructor constructor;
    private List<Attribute> hiddenAttributes = new ArrayList<>();
    private Map<String, Attribute> attributes = new HashMap<>();

    private Class parent;

    private int nextInstanceAttributeOffset = 1;
    private int nextDynamicMethodOffset = 0;
    private String virtualTableLabel;

    public Class() {
    }

    public Class(String name) {
        super(name);
    }

    public Class(String name, String parentName) {
        super(name);
        this.parentType = new ReferenceType(parentName, this, Form.DYNAMIC);
    }

    public void setParentType(ReferenceType parentType) {
        this.parentType = parentType;
    }

    public ReferenceType getParentType() {
        return parentType;
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
            attribute.setClassUnit(this);
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

    public boolean containsAttribute(String name) {
        return attributes.containsKey(name);
    }

    public boolean containsMethod(String name) {
        return methods.containsKey(name);
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    public int getNumberOfInstanceAttributes() {
        return nextInstanceAttributeOffset - 1;
    }

    public boolean validDeclaration() {
        checkParentExists();
        checkInterfacesExist(interfaceNames);
        if (!validInheritanceChain()) {
            return false;
        }

        checkChildren();
        generateVirtualTableLabel();

        return true;
    }

    private void checkParentExists() {
        if (!name.equals(OBJECT.name) && !parentType.isValidParentClass()) {
            symbolTable.throwLater(new InvalidParentTypeException(this, parentType));
            parentType = new ReferenceType(OBJECT.name, this, Form.DYNAMIC);
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

    private void checkChildren() {
        attributes.values().removeIf(attribute -> !attribute.validDeclaration());

        if (constructor == null || !constructor.validDeclaration()) {
            constructor = new Constructor(name);

            if (!constructor.validDeclaration()) { // side effects are necessary for label generation
                throw new IllegalStateException();
            }
        }

        checkMethods();
    }

    public void consolidate() {
        if (consolidated || name.equals(OBJECT.getName())) {
            return;
        }

        parent = symbolTable.getClass(parentType.getName());
        parent.consolidate();

        consolidateAttributes();
        consolidateMethods();
        checkInterfacesImplemented();

        consolidated = true;
    }

    private void consolidateAttributes() {
        hiddenAttributes.addAll(parent.hiddenAttributes);

        for (Attribute parentAttribute : parent.attributes.values()) {
            String parentAttributeName = parentAttribute.getName();

            if (attributes.containsKey(parentAttributeName) || parentAttribute.getVisibility() == Visibility.PRIVATE) {
                hiddenAttributes.add(parentAttribute);
            } else {
                attributes.put(parentAttributeName, parentAttribute);
            }
        }

        generateAttributeOffsets();
    }

    private void generateAttributeOffsets() {
        nextInstanceAttributeOffset = parent.nextInstanceAttributeOffset;

        for (Attribute attribute : attributes.values()) {
            if (attribute.getClassUnit() == this && attribute.getForm() == Form.DYNAMIC) {
                attribute.setOffset(nextInstanceAttributeOffset++);
            }
        }
    }

    private void consolidateMethods() {
        for (Method method : parent.methods.values()) {
            consolidateMethod(method, parentType.getGenericType());
        }

        generateMethodOffsets();
    }

    private void generateMethodOffsets() {
        nextDynamicMethodOffset = parent.nextDynamicMethodOffset;

        for (Method method : methods.values()) {
            if (method.getUnit() == this && method.getForm() == Form.DYNAMIC) {
                Method parentMethod = parent.methods.get(method.getName());
                int offset = parentMethod != null ? parentMethod.getOffset() : nextDynamicMethodOffset++;
                method.setOffset(offset);
            }
        }
    }

    private void checkInterfacesImplemented() {
        Collection<Method> methodsToImplement = validInterfacesMethods(interfaceNames);

        for (Method methodToImplement : methodsToImplement) { // non-implemented methods aren't fixed
            Method implementedMethod = methods.get(methodToImplement.getName());

            if (implementedMethod == null) {
                symbolTable.throwLater(new NotImplementedException(this, methodToImplement));
            } else if (!implementedMethod.equals(methodToImplement)) {
                symbolTable.throwLater(new InvalidImplementationException(methodToImplement));
            }
        }
    }

    public void checkSentences() {
        constructor.checkSentences(this);

        for (Method method : methods.values()) {
            if (method.getUnit().name.equals(name)) {
                method.checkSentences(this);
            }
        }
    }

    public void translate() throws IOException {
        generateVirtualTable();

        constructor.translate();

        for (Method method : methods.values()) {
            if (method.getUnit().name.equals(name)) {
                method.translate();
            }
        }
    }

    private void generateVirtualTable() throws IOException {
        List<Method> dynamicMethods = this.methods.values().stream()
                .filter(method -> method.form == Form.DYNAMIC)
                .collect(Collectors.toList());

        codeGenerator.generate(".DATA");
        if (dynamicMethods.isEmpty()) {
            codeGenerator.generate(getVirtualTableLabel() + ": NOP");
        } else {
            List<String> sortedMethodLabels = dynamicMethods.stream()
                    .sorted(Comparator.comparing(Method::getOffset))
                    .map(Method::getLabel)
                    .collect(Collectors.toList());

            codeGenerator.generate(getVirtualTableLabel() + ": DW " + String.join(", ", sortedMethodLabels));
        }
    }

    public String getVirtualTableLabel() {
        return virtualTableLabel;
    }

    private void generateVirtualTableLabel() {
        virtualTableLabel = "VT_" + name + '_' + codeGenerator.newLabelId();
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
