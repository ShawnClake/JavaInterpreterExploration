package com.shawnclake.part13.symbols;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private HashMap<String, Symbol> _symbols = new HashMap<>();

    public SymbolTable() {
        this.define(new BuiltInSymbolType("INTEGER"));
        this.define(new BuiltInSymbolType("REAL"));
    }

    public HashMap<String, Symbol> getRaw() {
        return _symbols;
    }

    @Override
    public String toString() {
        String output = "Symbols: \n";

        for(Map.Entry<String, Symbol> entry : this._symbols.entrySet())
        {
            output += entry.getKey() + ":" + entry.getValue().toString() + "\n";
        }

        return output;
    }

    public void define(Symbol symbol)
    {
        //Output.pln("Defining: " + symbol.getName());
        this._symbols.put(symbol.getName(), symbol);
    }

    public Symbol lookup(String name)
    {
        //Output.pln("Lookup: " + name);
        return this._symbols.getOrDefault(name, null);
    }
}
