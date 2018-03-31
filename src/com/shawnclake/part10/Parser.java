package com.shawnclake.part10;

import com.shawnclake.morgencore.core.component.Collections;
import com.shawnclake.part10.tree.*;

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
        /** program : PROGRAM variable SEMI block DOT */
        eat(Token.TokenType.PROGRAM);

        VarNode varNode = (VarNode)this.variable();
        String programName = varNode.getValue();

        eat(Token.TokenType.SEMI);

        BlockNode blockNode = (BlockNode)this.block();
        ProgramNode node = new ProgramNode(programName, blockNode);

        eat(Token.TokenType.DOT);
        return node;
    }

    private Node block() throws Exception
    {
        /** block : declarations compound_statement */
        ArrayList<Node> declarationNodes = this.declarations();
        CompoundNode compoundNode = (CompoundNode) this.compoundStatement();
        return new BlockNode(declarationNodes, compoundNode);
    }

    private ArrayList<Node> declarations() throws Exception
    {
        /** declarations : VAR (variable_declaration SEMI)+
         | empty */

        ArrayList<Node> declarations = new ArrayList<>();

        if(this.currentToken.getType() == Token.TokenType.VAR)
        {
            this.eat(Token.TokenType.VAR);
            while(this.currentToken.getType() == Token.TokenType.ID)
            {
                declarations.addAll(this.variableDeclaration());
                this.eat(Token.TokenType.SEMI);
            }
        }

        return declarations;
    }

    private ArrayList<Node> variableDeclaration() throws Exception
    {
        /** variable_declaration : ID (COMMA ID)* COLON type_spec */
        ArrayList<VarNode> varNodes = new ArrayList<>(Collections.toList(new VarNode(this.currentToken)));
        eat(Token.TokenType.ID);

        while(this.currentToken.getType() == Token.TokenType.COMMA)
        {
            eat(Token.TokenType.COMMA);
            varNodes.add(new VarNode(this.currentToken));
            eat(Token.TokenType.ID);
        }

        eat(Token.TokenType.COLON);

        TypeNode typeNode = (TypeNode)this.typeSpec();

        ArrayList<Node> varDeclarations = new ArrayList<>();

        for(VarNode varNode : varNodes)
        {
            varDeclarations.add(new VarDeclarationNode(varNode, typeNode));
        }

        return varDeclarations;
    }

    private Node typeSpec() throws Exception
    {
        /** type_spec : INTEGER
         | REAL */

        Token token = this.currentToken;

        if(this.currentToken.getType() == Token.TokenType.INTEGER)
            eat(Token.TokenType.INTEGER);
        else
            eat(Token.TokenType.REAL);

        return new TypeNode(token);
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

        //if(this.currentToken.getType() == Token.TokenType.ID)
        //    throw new Exception("statementList Exception");

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
         | INTEGER_CONST
         | REAL_CONST
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
        else if(token.getType() == Token.TokenType.INTEGER_CONST)
        {
            eat(Token.TokenType.INTEGER_CONST);
            return new NumNode(token);
        }
        else if(token.getType() == Token.TokenType.REAL_CONST)
        {
            eat(Token.TokenType.REAL_CONST);
            return new NumNode(token);
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
        /** term : factor ((MUL | INTEGER_DIV | FLOAT_DIV) factor)* */
        Set<Token.TokenType> allowedTokens = new HashSet<>();

        allowedTokens.add(Token.TokenType.MUL);
        allowedTokens.add(Token.TokenType.INTEGER_DIV);
        allowedTokens.add(Token.TokenType.FLOAT_DIV);

        Node node = this.factor();

        while(allowedTokens.contains(this.currentToken.getType())) {

            Token token = this.currentToken;

            if (token.getType() == Token.TokenType.MUL) {
                eat(Token.TokenType.MUL);
            }
            else if (token.getType() == Token.TokenType.INTEGER_DIV) {
                eat(Token.TokenType.INTEGER_DIV);
            }
            else if (token.getType() == Token.TokenType.FLOAT_DIV) {
                eat(Token.TokenType.FLOAT_DIV);
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
     * program : PROGRAM variable SEMI block DOT
     *         block : declarations compound_statement
     *         declarations : VAR (variable_declaration SEMI)+
     *                      | empty
     *         variable_declaration : ID (COMMA ID)* COLON type_spec
     *         type_spec : INTEGER
     *         compound_statement : BEGIN statement_list END
     *         statement_list : statement
     *                        | statement SEMI statement_list
     *         statement : compound_statement
     *                   | assignment_statement
     *                   | empty
     *         assignment_statement : variable ASSIGN expr
     *         empty :
     *         expr : term ((PLUS | MINUS) term)*
     *         term : factor ((MUL | INTEGER_DIV | FLOAT_DIV) factor)*
     *         factor : PLUS factor
     *                | MINUS factor
     *                | INTEGER_CONST
     *                | REAL_CONST
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
