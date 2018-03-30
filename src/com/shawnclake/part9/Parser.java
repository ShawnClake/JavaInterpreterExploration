package com.shawnclake.part9;

import com.shawnclake.morgencore.core.component.Collections;
import com.shawnclake.part9.tree.*;

import java.util.ArrayList;
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

    private Node program() throws Exception
    {
        /** program : compound_statement DOT */
        Node node = this.compoundStatement();
        eat(Token.TokenType.DOT);
        return node;
    }

    private Node compoundStatement() throws Exception
    {
        /** compound_statement: BEGIN statement_list END */
        eat(Token.TokenType.BEGIN);
        ArrayList<Node> nodes = this.statementList();
        eat(Token.TokenType.END);

        CompoundNode root = new CompoundNode();
        for(Node node : nodes)
        {
            root.getChildren().add(node);
        }

        return root;
    }

    private ArrayList<Node> statementList() throws Exception
    {
        /** statement_list : statement
         | statement SEMI statement_list */

        Node node = this.statement();

        ArrayList<Node> results = new ArrayList<>(Collections.toList(node));

        while(this.currentToken.getType() == Token.TokenType.SEMI)
        {
            this.eat(Token.TokenType.SEMI);
            results.add(this.statement());
        }

        if(this.currentToken.getType() == Token.TokenType.ID)
            throw new Exception("statementList Exception");

        return results;
    }

    private Node statement() throws Exception
    {
        /** statement : compound_statement
         | assignment_statement
         | empty */

        Node node;

        if(this.currentToken.getType() == Token.TokenType.BEGIN)
            node = this.compoundStatement();
        else if(this.currentToken.getType() == Token.TokenType.ID)
            node = this.assignmentStatement();
        else
            node = this.empty();

        return node;
    }

    private Node assignmentStatement() throws Exception
    {
        /** assignment_statement : variable ASSIGN expr */

        Node left = this.variable();
        Token token = this.currentToken;
        this.eat(Token.TokenType.ASSIGN);
        Node right = this.expr();

        return new AssignNode(left, right, token);
    }

    private Node variable() throws Exception
    {
        /** variable : ID */

        VarNode node = new VarNode(this.currentToken);
        this.eat(Token.TokenType.ID);
        return node;
    }

    private Node empty() throws Exception
    {
        /** An empty production */

        return new NoOperationNode();
    }


    /** Return an INTEGER token value. */
    private Node factor() throws Exception {
        /** factor : PLUS factor
         | MINUS factor
         | INTEGER
         | LPAREN expr RPAREN
         | variable */

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
            return this.variable();
            //throw new Exception("Parsing error in factor");
        }

    }

    private Node term() throws Exception
    {
        /** term : factor ((MUL | DIV) factor)* */
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
        /** expr : term ((PLUS | MINUS) term)* */

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

    /**
     *
     * program : compound_statement DOT
     *         compound_statement : BEGIN statement_list END
     *         statement_list : statement
     *                        | statement SEMI statement_list
     *         statement : compound_statement
     *                   | assignment_statement
     *                   | empty
     *         assignment_statement : variable ASSIGN expr
     *         empty :
     *         expr: term ((PLUS | MINUS) term)*
     *         term: factor ((MUL | DIV) factor)*
     *         factor : PLUS factor
     *                | MINUS factor
     *                | INTEGER
     *                | LPAREN expr RPAREN
     *                | variable
     *         variable: ID
     *
     */
    public Node parse() throws Exception
    {

        Node node = this.program();
        if(this.currentToken.getType() != Token.TokenType.EOF)
            throw new Exception("parse: End of File was NOT reached.");

        return node;
    }
}
