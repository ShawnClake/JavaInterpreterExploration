package com.shawnclake.part8;

import com.shawnclake.part8.tree.*;

public class Interpreter {

    private Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    private int visitIntegerNode(IntegerNode node)
    {
        return node.getValue();
    }

    private int visitBinaryOperatorNode(BinaryOperatorNode node) throws Exception
    {
        int left = this.postOrderHelper(node.getLeft());
        int right = this.postOrderHelper(node.getRight());

        if(node.getToken().getType() == Token.TokenType.PLUS)
            return left + right;
        if(node.getToken().getType() == Token.TokenType.MINUS)
            return left - right;
        if(node.getToken().getType() == Token.TokenType.MUL)
            return left * right;
        if(node.getToken().getType() == Token.TokenType.DIV)
            return left / right;

        throw new Exception("Impossible Binary Operator Node");
    }

    private int visitUnaryOperatorNode(UnaryOperatorNode node) throws Exception
    {
        Token.TokenType operator = node.getToken().getType();
        if(operator == Token.TokenType.PLUS)
            return +this.postOrderHelper(node.getExpr());
        else if (operator ==  Token.TokenType.MINUS)
            return -this.postOrderHelper(node.getExpr());

        throw new Exception("Impossible Unary Operator Node");
    }

    private int postOrderHelper(Node node) throws Exception
    {
        if(node != null)
        {
            if(node instanceof IntegerNode)
            {
                return this.visitIntegerNode((IntegerNode)node);
            }
            else if(node instanceof UnaryOperatorNode)
            {
                return this.visitUnaryOperatorNode((UnaryOperatorNode)node);
            }
            else if(node instanceof BinaryOperatorNode)
            {
                return this.visitBinaryOperatorNode((BinaryOperatorNode) node);
            }
        } else {
            throw new Exception("Leaf node shouldn't be null");
        }

        //return 0;
        throw new Exception("postOrderHelper must not reach this line");
    }

    /*private int postOrderHelper(Node node)
    {
        if(node != null)
        {
            if(node instanceof IntegerNode)
            {
                IntegerNode nNode = (IntegerNode)node;
                return nNode.getValue();
            }
            else if(node instanceof BinaryOperatorNode)
            {
                int left = this.postOrderHelper(node.getLeft());
                int right = this.postOrderHelper(node.getRight());

                BinaryOperatorNode bNode = (BinaryOperatorNode)node;

                if(bNode.getToken().getType() == Token.TokenType.PLUS)
                    return left + right;
                if(bNode.getToken().getType() == Token.TokenType.MINUS)
                    return left - right;
                if(bNode.getToken().getType() == Token.TokenType.MUL)
                    return left * right;
                if(bNode.getToken().getType() == Token.TokenType.DIV)
                    return left / right;


            }
        }

        return 0;
    }*/

    private int postOrder() throws Exception
    {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(this.parser.parse());
        return this.postOrderHelper(ast.getRoot());
    }

    public int interpret() throws Exception
    {
        return this.postOrder();
    }
}
