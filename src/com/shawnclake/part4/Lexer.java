package com.shawnclake.part4;

/**
 * The Lexer class
 */
public class Lexer {

    // client input string, e.g. "3 + 5"
    private String text;
    // pos is an index for this.text
    private int pos = 0;
    // current char
    private char currentChar;

    public Lexer(String text) {
        this.text = text;

        this.currentChar = this.text.charAt(this.pos);
    }

    /** Advance the this.pos point and the this.currentChar variable. */
    private void advance() {

        this.pos++;

        if (this.pos > this.text.length() - 1) {
            this.currentChar = '\0'; // '\0' denotes the null character
        }
        else {
            this.currentChar = this.text.charAt(this.pos);
        }
    }

    /** skip of whitespace characters */
    private void skipWhitespace() {
        while (this.currentChar != '\0' && this.currentChar == ' ') {
            advance();
        }
    }

    /** go over multiple characters that build an integer; return it as string */
    private String integer() {
        String result = "";
        while (this.currentChar != '\0' && Character.isDigit(this.currentChar)) {
            result += String.valueOf(this.currentChar);
            advance();
        }

        return result;
    }

    /**
     * Lexical analyzer (also known as scanner or tokenizer)
     *
     * This method is repsonsible for breaking a sentence apart into
     * tokens.
     */
    public Token getNextToken() {

        while (this.currentChar != '\0') {

            if (this.currentChar == ' ') {
                skipWhitespace();
                continue;
            }
            if (Character.isDigit(this.currentChar)) {
                return new Token(Token.TokenType.INTEGER, integer());
            }
            if (this.currentChar == '+') {
                advance();
                return new Token(Token.TokenType.PLUS, "+");
            }
            if (this.currentChar == '-') {
                advance();
                return new Token(Token.TokenType.MINUS, "-");
            }
            if (this.currentChar == '*') {
                advance();
                return new Token(Token.TokenType.MUL, "*");
            }
            if (this.currentChar == '/') {
                advance();
                return new Token(Token.TokenType.DIV, "/");
            }
        }

        return new Token(Token.TokenType.EOF, "");
    }

}
