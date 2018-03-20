package com.shawnclake.part1;

public class Token {
    public static final String INTEGER = "INTEGER";
    public static final String PLUS = "PLUS";
    public static final String EOF = "EOF";

    private String type;
    private char value;

    public Token(String type, char value)
    {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(" + this.type + ", " + this.value + ")";
    }

    public String getType() {
        return type;
    }

    public char getValue() {
        return value;
    }
}
