package com.shawnclake.part13.symbols;

public class VarSymbol extends Symbol {

    public VarSymbol(String name, Symbol type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "<"+this.getName()+":"+this.getType().getName()+">";
    }
}
