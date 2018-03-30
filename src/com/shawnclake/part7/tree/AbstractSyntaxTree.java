package com.shawnclake.part7.tree;

import com.shawnclake.part7.Token;

public class AbstractSyntaxTree {

    private Node root;

    public AbstractSyntaxTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }


    private int postOrderHelper(Node node)
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
    }

    public int postOrder()
    {
        return this.postOrderHelper(this.root);
    }

}
