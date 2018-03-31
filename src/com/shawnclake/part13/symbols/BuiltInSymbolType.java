package com.shawnclake.part13.symbols;

public class BuiltInSymbolType extends Symbol {

    public BuiltInSymbolType(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
