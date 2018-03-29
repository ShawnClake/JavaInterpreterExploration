package com.shawnclake.part2;

/**
 * The interpreter class.
 */

public class Interpreter {

    // client input string, e.g. "3+5"
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


    private void skipWhitespace() {
        while (this.currentChar != '\0' && this.currentChar == ' ') {
            advance();
        }
    }


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


    /**
     * Compare the current token type with the passed token type.
     * If they match, then "eat" the current token and assign
     * the next token to this.currentToken, otherwise throw exception.
     */
    public void eat(Token.TokenType tokenType) throws Exception {

        if (this.currentToken.getType() == tokenType) {
            this.currentToken = getNextToken();
        }
        else {
            throw new Exception("Token type mismatch");
        }
    }


    /** Interpret the expression (which was set in the constructor). */
    public int expr() throws Exception {
        // set current token to be the first token taken from the input
        this.currentToken = getNextToken();

        // we expect the current token to an integer
        Token left = this.currentToken;
        eat(Token.TokenType.INTEGER); // eat() moves us to the next token

        // we expect the current token to be a '+' or a '-' token
        Token op = this.currentToken;
        if (op.getType() == Token.TokenType.PLUS) {
            eat(Token.TokenType.PLUS);
        }
        else {
            eat(Token.TokenType.MINUS);
        }

        // we expect the current token to be an integer
        Token right = this.currentToken;
        eat(Token.TokenType.INTEGER);

        // after the above call the currentToken is set to EOF token

        // at this point either the INTEGER PLUS INTEGER or
        // the INTEGER MINUS INTEGER sequence of tokens
        // has been successfully found and the method can just
        // return the result of adding or subtracting two integers,
        // thus effectively interpreting client input
        int result;

        if (op.getType() == Token.TokenType.PLUS) {
            result = Integer.parseInt(left.getValue()) + Integer.parseInt(right.getValue());
        }
        else {
            result = Integer.parseInt(left.getValue()) - Integer.parseInt(right.getValue());
        }

        return result;
    }

}
