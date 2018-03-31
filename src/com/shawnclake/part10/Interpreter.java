package com.shawnclake.part10;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.part10.tree.*;

import java.util.HashMap;

public class Interpreter {

    private Parser parser;

    private HashMap<String, DynamicPrimitive> GLOBAL_SCOPE = new HashMap<>();

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public HashMap<String, DynamicPrimitive> getGLOBAL_SCOPE() {
        return GLOBAL_SCOPE;
    }

    private DynamicPrimitive visitNumNode(NumNode node)
    {
        return node.getValue();
    }

    private DynamicPrimitive visitBinaryOperatorNode(BinaryOperatorNode node) throws Exception
    {
        DynamicPrimitive left = this.postOrderHelper(node.getLeft());
        DynamicPrimitive right = this.postOrderHelper(node.getRight());

        if(node.getToken().getType() == Token.TokenType.PLUS)
            return new DynamicPrimitive(left.getFloat() + right.getFloat());
        if(node.getToken().getType() == Token.TokenType.MINUS)
            return new DynamicPrimitive(left.getFloat() - right.getFloat());
        if(node.getToken().getType() == Token.TokenType.MUL)
            return new DynamicPrimitive(left.getFloat() * right.getFloat());
        if(node.getToken().getType() == Token.TokenType.INTEGER_DIV)
            return new DynamicPrimitive((int)(left.getFloat() / right.getFloat()));
        if(node.getToken().getType() == Token.TokenType.FLOAT_DIV)
            return new DynamicPrimitive(left.getFloat() / right.getFloat());

        throw new Exception("Impossible Binary Operator Node");
    }

    private DynamicPrimitive visitUnaryOperatorNode(UnaryOperatorNode node) throws Exception
    {
        Token.TokenType operator = node.getToken().getType();
        if(operator == Token.TokenType.PLUS)
            return new DynamicPrimitive(+this.postOrderHelper(node.getExpr()).getFloat());
        else if (operator ==  Token.TokenType.MINUS)
            return new DynamicPrimitive(-this.postOrderHelper(node.getExpr()).getFloat());

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

    private DynamicPrimitive visitVarNode(VarNode node) throws Exception
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

    private void visitProgramNode(ProgramNode node) throws Exception
    {
        this.visitBlockNode(node.getRootNode());
    }

    private void visitBlockNode(BlockNode node) throws Exception
    {
        for(Node dNode : node.getDeclarations())
        {
            this.visitVarDeclarationNode((VarDeclarationNode)dNode);
        }

        this.visitCompoundNode(node.getCompoundStatement());
    }

    private void visitVarDeclarationNode(VarDeclarationNode node) throws Exception
    {

    }

    private void visitTypeNode(TypeNode node) throws Exception
    {

    }

    private DynamicPrimitive postOrderHelper(Node node) throws Exception
    {
        if(node != null)
        {
            if(node instanceof NumNode)
            {
                return this.visitNumNode((NumNode) node);
            }
            else if(node instanceof UnaryOperatorNode)
            {
                return this.visitUnaryOperatorNode((UnaryOperatorNode)node);
            }
            else if(node instanceof AssignNode)
            {
                this.visitAssignNode((AssignNode) node);
                return new DynamicPrimitive(0);
            }
            else if(node instanceof VarNode)
            {
                return this.visitVarNode((VarNode) node);
            }
            else if(node instanceof ProgramNode)
            {
                this.visitProgramNode((ProgramNode) node);
                return new DynamicPrimitive(0);
            }
            else if(node instanceof BlockNode)
            {
                this.visitBlockNode((BlockNode) node);
                return new DynamicPrimitive(0);
            }
            else if(node instanceof VarDeclarationNode)
            {
                this.visitVarDeclarationNode((VarDeclarationNode) node);
                return new DynamicPrimitive(0);
            }
            else if(node instanceof TypeNode)
            {
                this.visitTypeNode((TypeNode) node);
                return new DynamicPrimitive(0);
            }
            else if(node instanceof BinaryOperatorNode)
            {
                return this.visitBinaryOperatorNode((BinaryOperatorNode) node);
            }
            else if(node instanceof CompoundNode)
            {
                this.visitCompoundNode((CompoundNode) node);
                return new DynamicPrimitive(0);
            }

            else if(node instanceof NoOperationNode)
            {
                this.visitNoOperatorNode((NoOperationNode) node);
                return new DynamicPrimitive(0);
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

    private DynamicPrimitive postOrder() throws Exception
    {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(this.parser.parse());
        return this.postOrderHelper(ast.getRoot());
    }

    public DynamicPrimitive interpret() throws Exception
    {
        return this.postOrder();
    }
}
