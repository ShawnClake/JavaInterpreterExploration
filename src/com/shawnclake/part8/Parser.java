package com.shawnclake.part8;

import com.shawnclake.part8.tree.BinaryOperatorNode;
import com.shawnclake.part8.tree.IntegerNode;
import com.shawnclake.part8.tree.Node;
import com.shawnclake.part8.tree.UnaryOperatorNode;

import java.util.HashSet;
import java.util.Set;

public class Parser {

    // lexer instance
    private Lexer lexer;

    // current token instance
    private Token currentToken = null;

    /** Construtor, provide the text that should be interpreted. */
    public Parser (Lexer lexer) {
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
    private Node factor() throws Exception {
        /* factor : INTEGER | LPAREN expr RPAREN */

        Token token = this.currentToken;
        //Output.pln(token.toString());
        if(token.getType() == Token.TokenType.PLUS)
        {
            eat(Token.TokenType.PLUS);
            return new UnaryOperatorNode(token, this.factor());
        }
        else if(token.getType() == Token.TokenType.MINUS)
        {
            eat(Token.TokenType.MINUS);
            return new UnaryOperatorNode(token, this.factor());
        }
        else if(token.getType() == Token.TokenType.INTEGER)
        {
            eat(Token.TokenType.INTEGER);
            return new IntegerNode(token);
        } else if(token.getType() == Token.TokenType.LPAREN)
        {
            eat(Token.TokenType.LPAREN);
            Node node = this.expr();
            eat(Token.TokenType.RPAREN);
            return node;
        } else
        {
            throw new Exception("Parsing error in factor");
        }

    }

    private Node term() throws Exception
    {
        /* term : factor ((MUL | DIV) factor)* */
        Set<Token.TokenType> allowedTokens = new HashSet<>();

        allowedTokens.add(Token.TokenType.MUL);
        allowedTokens.add(Token.TokenType.DIV);

        Node node = this.factor();

        while(allowedTokens.contains(this.currentToken.getType())) {

            Token token = this.currentToken;

            if (token.getType() == Token.TokenType.MUL) {
                eat(Token.TokenType.MUL);
            }
            else if (token.getType() == Token.TokenType.DIV) {
                eat(Token.TokenType.DIV);
            }

            node = new BinaryOperatorNode(node, this.factor(), token);

        }

        return node;
    }


    /** Interpret the expression (which was set in the constructor).
     *
     * Arithmetic expression parser / interpreter.
     *
     *         expr   : term ((PLUS | MINUS) term)*
     *         term   : factor ((MUL | DIV) factor)*
     *         factor : INTEGER | LPAREN expr RPAREN
     *
     */
    public Node expr() throws Exception
    {
        Set<Token.TokenType> allowedTokens = new HashSet<>();

        allowedTokens.add(Token.TokenType.PLUS);
        allowedTokens.add(Token.TokenType.MINUS);

        Node node = this.term();

        while(allowedTokens.contains(this.currentToken.getType())) {

            Token token = this.currentToken;

            if(token.getType() == Token.TokenType.PLUS) {
                eat(Token.TokenType.PLUS);
            }
            else if (token.getType() == Token.TokenType.MINUS) {
                eat(Token.TokenType.MINUS);
            }

            node = new BinaryOperatorNode(node, this.term(), token);

        }

        return node;
    }

    public Node parse()
    {
        try {
            return this.expr();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
