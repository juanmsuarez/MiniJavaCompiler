package com.minijava.compiler.semantic.entities.types;

import com.minijava.compiler.semantic.entities.Unit;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class ReferenceType extends Type {
    private String genericType;
    private Unit context;

    public ReferenceType(String name, Unit context) {
        super(name);
        this.context = context;
    }

    public ReferenceType(String name, String genericType, Unit context) {
        super(name);
        this.genericType = genericType;
        this.context = context;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }

    public String getGenericType() {
        return genericType;
    }

    public boolean isValid() {
        if (name.equals(context.getGenericType())) { // type parameter
            return genericType == null;
        } else { // global type
            return isGloballyValid();
        }
    }

    public boolean isGloballyValid() { // for types defined globally
        return symbolTable.contains(name) && isTypeParameterValid();
    }

    private boolean isTypeParameterValid() {
        Unit typeUnitReference = symbolTable.get(name);

        if (typeUnitReference.isGeneric()) {
            return genericType != null && isParameterTypeDefined();
        } else {
            return genericType == null;
        }
    }

    private boolean isParameterTypeDefined() {
        return genericType.equals(context.getGenericType()) || (symbolTable.contains(genericType) && !symbolTable.get(genericType).isGeneric());
    }

    // TODO: redefine equals

    @Override
    public String toString() {
        return "ReferenceType{" +
                "name='" + name + '\'' +
                ", genericType='" + genericType + '\'' +
                ", context='" + context.getName() + '\'' +
                '}';
    }
}
