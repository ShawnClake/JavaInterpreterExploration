package com.shawnclake.part4;

/**
 * The interpreter class.
 */

import java.util.HashSet;
import java.util.Set;

public class Interpreter {

    // lexer instance
    private Lexer lexer;

    // current token instance
    private Token currentToken = null;

    /** Construtor, provide the text that should be interpreted. */
    public Interpreter (Lexer lexer) {
        this.lexer = lexer;
        // initialize the currentChar to be the first character of the input
        this.currentToken = this.lexer.getNextToken();
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
            this.currentToken = this.lexer.getNextToken();
        }
        else {
            throw new Exception("Token type mismatch");
        }
    }


    /** Return an INTEGER token value. */
    private int factor() throws Exception {
        Token t = this.currentToken;
        eat(Token.TokenType.INTEGER);
        return Integer.parseInt(t.getValue());
    }


    /** Interpret the expression (which was set in the constructor).
     *
     * Arithmetic expression parser / interpreter.
     *
     *         expr   : factor ((MUL | DIV) factor)*
     *         factor : INTEGER
     *
     */
    public int expr() throws Exception
    {
        Set<Token.TokenType> allowedTokens = new HashSet<>();

        allowedTokens.add(Token.TokenType.PLUS);
        allowedTokens.add(Token.TokenType.MINUS);
        allowedTokens.add(Token.TokenType.MUL);
        allowedTokens.add(Token.TokenType.DIV);

        int result = this.factor();

        while(allowedTokens.contains(this.currentToken.getType())) {

            Token token = this.currentToken;

            if(token.getType() == Token.TokenType.PLUS) {
                eat(Token.TokenType.PLUS);
                result = result + factor();
            }
            else if (token.getType() == Token.TokenType.MINUS) {
                eat(Token.TokenType.MINUS);
                result = result - factor();
            }
            else if (token.getType() == Token.TokenType.MUL) {
                eat(Token.TokenType.MUL);
                result = result * factor();
            }
            else if (token.getType() == Token.TokenType.DIV) {
                eat(Token.TokenType.DIV);
                result = result / factor();
            }

        }

        return result;
    }
}
