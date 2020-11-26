package com.minijava.compiler.semantic.declarations.entities.types;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Unit;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;

import java.util.Objects;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.PredefinedEntities.OBJECT;

public class ReferenceType extends Type {
    public static final String NULL = "null";

    private Lexeme lexeme;
    private String genericType;
    private Unit context;
    private Form accessForm;

    public ReferenceType(String name) {
        super(name);
    }

    public ReferenceType(String name, Unit context, Form accessForm) {
        super(name);
        this.context = context;
        this.accessForm = accessForm;
    }

    public ReferenceType(Lexeme lexeme, String genericType, Unit context, Form accessForm) {
        super(lexeme.getString());
        this.lexeme = lexeme;
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

    public Lexeme getLexeme() {
        return lexeme;
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
    public Type instantiate(String newTypeName) {
        if (context.isGeneric()) {
            String contextType = context.getGenericType();

            if (name.equals(contextType)) {
                return new ReferenceType(newTypeName, context, accessForm);
            } else if (genericType != null && genericType.equals(contextType)) {
                return new ReferenceType(lexeme, newTypeName, context, accessForm);
            }
        }

        return this;
    }

    @Override
    protected boolean isSupertype(PrimitiveType other) {
        return false;
    }

    @Override
    protected boolean isSupertype(VoidType other) {
        return false;
    }

    @Override
    protected boolean isSupertype(ReferenceType other) {
        if (other.name.equals(NULL)) {
            return true;
        }

        while (!other.name.equals(OBJECT.getName()) && !other.name.equals(name)) {
            Class otherClass = symbolTable.getClass(other.name);
            other = otherClass.getParentType();
        }

        return other.name.equals(name);
    }

    @Override
    public boolean isSubtype(Type type) {
        return type.isSupertype(this);
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
