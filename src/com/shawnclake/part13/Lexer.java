package com.shawnclake.part13;

import java.util.HashMap;

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

    private HashMap<String, Token> RESERVED_KEYWORDS = new HashMap<>();

    public Lexer(String text) {
        this.text = text;

        this.currentChar = this.text.charAt(this.pos);

        this.RESERVED_KEYWORDS.put("PROGRAM", new Token(Token.TokenType.PROGRAM, "PROGRAM"));
        this.RESERVED_KEYWORDS.put("VAR", new Token(Token.TokenType.VAR, "VAR"));
        this.RESERVED_KEYWORDS.put("DIV", new Token(Token.TokenType.INTEGER_DIV, "DIV"));
        this.RESERVED_KEYWORDS.put("INTEGER", new Token(Token.TokenType.INTEGER, "INTEGER"));
        this.RESERVED_KEYWORDS.put("REAL", new Token(Token.TokenType.REAL, "REAL"));
        this.RESERVED_KEYWORDS.put("BEGIN", new Token(Token.TokenType.BEGIN, "BEGIN"));
        this.RESERVED_KEYWORDS.put("END", new Token(Token.TokenType.END, "END"));
        this.RESERVED_KEYWORDS.put("PROCEDURE", new Token(Token.TokenType.PROCEDURE, "PROCEDURE"));
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

    public char peek()
    {
        int peek_pos = this.pos + 1;
        if(peek_pos > this.text.length() - 1)
            return '\0'; // '\0' denotes the null character
        else
            return this.text.charAt(peek_pos);
    }

    /** skip of whitespace characters */
    private void skipWhitespace() {
        while (this.currentChar != '\0' && this.currentChar == ' ') {
            advance();
        }
    }

    /** skip comments */
    private void skipComment()
    {
        while(this.currentChar != '}')
            this.advance();
        this.advance(); // The closing curly brace
    }

    /** Build integer & real constants */
    private Token number() {
        String result = "";
        Token token;

        while (this.currentChar != '\0' && Character.isDigit(this.currentChar)) {
            result += String.valueOf(this.currentChar);
            advance();
        }

        if(this.currentChar == '.')
        {
            result += String.valueOf(this.currentChar);
            this.advance();

            while (this.currentChar != '\0' && Character.isDigit(this.currentChar)) {
                result += String.valueOf(this.currentChar);
                advance();
            }

            token = new Token(Token.TokenType.REAL_CONST, result);
        } else {
            token = new Token(Token.TokenType.INTEGER_CONST, result);
        }

        return token;
    }

    /**
     * Handle identifiers and reserved keywords
     */
    private Token _id()
    {
        String result = "";

        while (this.currentChar != '\0' && Character.isLetterOrDigit(this.currentChar)) {
            result += String.valueOf(this.currentChar);
            advance();
        }

        return RESERVED_KEYWORDS.getOrDefault(result, new Token(Token.TokenType.ID, result));
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
            if(this.currentChar == '{')
            {
                advance();
                skipComment();
                continue;
            }
            if (Character.isLetter(this.currentChar)) {
                return this._id();
            }
            if (Character.isDigit(this.currentChar)) {
                return this.number();
            }
            if (this.currentChar == ':' && this.peek() == '=') {
                advance();
                advance();
                return new Token(Token.TokenType.ASSIGN, ":=");
            }
            if (this.currentChar == ';') {
                advance();
                return new Token(Token.TokenType.SEMI, ";");
            }
            if(this.currentChar == ':')
            {
                advance();
                return new Token(Token.TokenType.COLON, ":");
            }
            if(this.currentChar == ',')
            {
                advance();
                return new Token(Token.TokenType.COMMA, ",");
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
                return new Token(Token.TokenType.FLOAT_DIV, "/");
            }
            if (this.currentChar == '(') {
                advance();
                return new Token(Token.TokenType.LPAREN, "(");
            }
            if (this.currentChar == ')') {
                advance();
                return new Token(Token.TokenType.RPAREN, ")");
            }
            if (this.currentChar == '.')
            {
                advance();
                return new Token(Token.TokenType.DOT, ".");
            }
        }

        return new Token(Token.TokenType.EOF, "");
    }

}
