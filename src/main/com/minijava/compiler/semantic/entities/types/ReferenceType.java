package com.minijava.compiler.semantic.entities.types;

import com.minijava.compiler.semantic.entities.Unit;
import com.minijava.compiler.semantic.entities.modifiers.Form;

import java.util.Objects;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class ReferenceType extends Type {
    private String genericType;
    private Unit context;
    private Form accessForm;

    public ReferenceType(String name, Unit context, Form accessForm) {
        super(name);
        this.context = context;
        this.accessForm = accessForm;
    }

    public ReferenceType(String name, String genericType, Unit context, Form accessForm) {
        super(name);
        this.genericType = genericType;
        this.context = context;
        this.accessForm = accessForm;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }

    public String getGenericType() {
        return genericType;
    }

    public boolean isValidParentClass() {
        return symbolTable.containsClass(name) && isTypeArgumentValid();
    }

    public boolean isValid() {
        if (name.equals(context.getGenericType())) { // type parameter
            return genericType == null && accessForm.equals(Form.DYNAMIC);
        } else { // global type
            return isGloballyValid();
        }
    }

    private boolean isGloballyValid() { // for types defined globally
        return symbolTable.contains(name) && isTypeArgumentValid();
    }

    private boolean isTypeArgumentValid() {
        Unit typeUnitReference = symbolTable.get(name);

        if (typeUnitReference.isGeneric()) {
            return genericType != null && isParameterTypeDefined();
        } else {
            return genericType == null;
        }
    }

    private boolean isParameterTypeDefined() {
        return (genericType.equals(context.getGenericType()) && accessForm.equals(Form.DYNAMIC))
                || (symbolTable.contains(genericType) && !symbolTable.get(genericType).isGeneric());
    }

    @Override
    public Type instantiate(String newType) {
        if (context.isGeneric()) {
            String contextType = context.getGenericType();

            if (name.equals(contextType)) {
                return new ReferenceType(newType, context, accessForm);
            } else if (genericType != null && genericType.equals(contextType)) {
                return new ReferenceType(name, newType, context, accessForm);
            }
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReferenceType that = (ReferenceType) o;
        return Objects.equals(genericType, that.genericType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genericType);
    }

    @Override
    public String toString() {
        return "ReferenceType{" +
                "name='" + name + '\'' +
                ", genericType='" + genericType + '\'' +
                ", context='" + context.getName() + '\'' +
                '}';
    }
}
