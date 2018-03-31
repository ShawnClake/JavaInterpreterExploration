package com.shawnclake.part10;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;

/**
 * Class for the different types of tokens.
 *
 * Compared to the Python implementation we have the problem
 * that the value of a token can differ. For now, we simply
 * store the value as a char and provide getter functions
 * to get the value as char or as int.
 * In the future, we will probably have to find a better solution for that.
 *
 */

public class Token {

    // declare a public enum for the different types of tokens
    public enum TokenType {
        INTEGER ("INTEGER"),
        REAL("REAL"),
        PLUS ("PLUS"),
        MINUS ("MINUS"),
        MUL ("MUL"),
        INTEGER_DIV ("INTEGER_DIV"),
        FLOAT_DIV ("FLOAT_DIV"),
        LPAREN ("("),
        RPAREN (")"),
        ID("ID"),
        ASSIGN("ASSIGN"),
        BEGIN("BEGIN"),
        END("END"),
        SEMI("SEMI"),
        DOT("DOT"),
        PROGRAM("PROGRAM"),
        VAR("VAR"),
        COLON("COLON"),
        COMMA("COMMA"),
        INTEGER_CONST("INTEGER_CONST"),
        REAL_CONST("REAL_CONST"),
        EOF ("EOF");

        // variable to store the description of the enum
        private String tokenTypeDescription;

        // private constructor
        private TokenType(String tokenTypeDescription) {
            this.tokenTypeDescription = tokenTypeDescription;
        }

        @Override
        public String toString() {
            return this.tokenTypeDescription;
        }
    }


    // type of the token
    private Token.TokenType tokenType;
    // the value of the token, e.g. "1", "2", ... , "+", "-"
    private DynamicPrimitive value;


    /** Constructor */
    public Token(Token.TokenType tokenType, DynamicPrimitive value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public Token(Token.TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = new DynamicPrimitive(value);
    }


    /** Returns the TokenType of this token. */
    public Token.TokenType getType() {
        return this.tokenType;
    }


    /** Returns the value of this token as a String. */
    public DynamicPrimitive getValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return "Token(" + this.tokenType.toString() + ", " + this.value.getString() + ")";
    }
}
