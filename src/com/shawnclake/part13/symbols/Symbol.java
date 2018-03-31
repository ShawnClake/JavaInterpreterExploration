package com.shawnclake.part13.symbols;

abstract public class Symbol {

    private String name;
    private Symbol type = null;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, Symbol type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Symbol getType() {
        return type;
    }

}
