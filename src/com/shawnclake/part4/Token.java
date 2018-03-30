package com.shawnclake.part4;

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
        PLUS ("PLUS"),
        MINUS ("MINUS"),
        MUL ("MUL"),
        DIV ("DIV"),
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
    private String value;


    /** Constructor */
    public Token(Token.TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }


    /** Returns the TokenType of this token. */
    public Token.TokenType getType() {
        return this.tokenType;
    }


    /** Returns the value of this token as a String. */
    public String getValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return "Token(" + this.tokenType.toString() + ", " + this.value + ")";
    }
}
