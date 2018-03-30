package com.shawnclake.part9;

import com.shawnclake.part9.tree.*;

import java.util.HashMap;

public class Interpreter {

    private Parser parser;

    private HashMap<String, Integer> GLOBAL_SCOPE = new HashMap<>();

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public HashMap<String, Integer> getGLOBAL_SCOPE() {
        return GLOBAL_SCOPE;
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

    private void visitCompoundNode(CompoundNode node) throws Exception
    {
        for(Node child : node.getChildren())
        {
            this.postOrderHelper(child);
        }
    }

    private void visitAssignNode(AssignNode node) throws Exception
    {
        if(node.getLeft() instanceof VarNode)
        {
            VarNode vNode = (VarNode)node.getLeft();
            this.GLOBAL_SCOPE.put(vNode.getValue(), this.postOrderHelper(node.getRight()));
        } else {
            throw new Exception("visitAssignNode: Left side of assignment operator is not a variable node.");
        }
    }

    private int visitVarNode(VarNode node) throws Exception
    {
        String varName = node.getValue();

        if(!this.GLOBAL_SCOPE.containsKey(varName))
        {
            throw new Exception("visitVarNode: This variable name does not exist");
        } else {
            return this.GLOBAL_SCOPE.get(varName);
        }
    }

    private void visitNoOperatorNode(NoOperationNode node) throws Exception
    {

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
            else if(node instanceof AssignNode)
            {
                this.visitAssignNode((AssignNode) node);
                return 0;
            }
            else if(node instanceof VarNode)
            {
                return this.visitVarNode((VarNode) node);
            }
            else if(node instanceof BinaryOperatorNode)
            {
                return this.visitBinaryOperatorNode((BinaryOperatorNode) node);
            }
            else if(node instanceof CompoundNode)
            {
                this.visitCompoundNode((CompoundNode) node);
                return 0;
            }

            else if(node instanceof NoOperationNode)
            {
                this.visitNoOperatorNode((NoOperationNode) node);
                return 0;
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
