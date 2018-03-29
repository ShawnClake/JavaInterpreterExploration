package com.shawnclake.part3;

/**
 * The interpreter class.
 */

import java.util.HashSet;
import java.util.Set;

public class Interpreter {

    // client input string, e.g. "3 + 5"
    private String text;
    // pos is an index for this.text
    int pos = 0;
    // current token instance
    Token currentToken = null;
    // current char
    char currentChar;


    /** Construtor, provide the text that should be interpreted. */
    public Interpreter (String inputText) {
        this.text = inputText;
        // initialize the currentChar to be the first character of the input
        this.currentChar = this.text.charAt(this.pos);
    }


    /*************************************************************/
    /** Lexer code                                              **/
    /*************************************************************/

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
    public Token getNextToken() throws Exception {

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
        }

        return new Token(Token.TokenType.EOF, "");
    }


    /*************************************************************/
    /** Parser / Interpreter code                               **/
    /*************************************************************/

    /**
     * Compare the current token type with an allowed token type.
     * If there's a match, then "eat" the current token and assign
     * the next token to this.currentToken, otherwise throw exception.
     */
    public void eat(Token.TokenType allowedTokenType) throws Exception {

        if (this.currentToken.getType() == allowedTokenType) {
            this.currentToken = getNextToken();
        }
        else {
            throw new Exception("Token type mismatch");
        }
    }


    /** Return an INTEGER token value. */
    private int term() throws Exception {
        Token t = this.currentToken;
        eat(Token.TokenType.INTEGER);
        return Integer.parseInt(t.getValue());
    }


    /** Interpret the expression (which was set in the constructor). */
    public int expr() throws Exception {

        // set current token to be the first token taken from the input
        this.currentToken = getNextToken();


        Set<Token.TokenType> allowedTokens = new HashSet<Token.TokenType>();

        allowedTokens.add(Token.TokenType.PLUS);
        allowedTokens.add(Token.TokenType.MINUS);

        int result = this.term();

        while(allowedTokens.contains(this.currentToken.getType())) {

            Token token = this.currentToken;

            if(token.getType() == Token.TokenType.PLUS) {
                eat(Token.TokenType.PLUS);
                result = result + term();
            }
            else if (token.getType() == Token.TokenType.MINUS) {
                eat(Token.TokenType.MINUS);
                result = result - term();
            }

        }

        return result;
    }
}
