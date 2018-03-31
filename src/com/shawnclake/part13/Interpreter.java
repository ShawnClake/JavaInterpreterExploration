package com.shawnclake.part13;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.part13.tree.*;

import java.util.HashMap;

public class Interpreter extends NodeVisitor {

    private AbstractSyntaxTree abstractSyntaxTree;

    private HashMap<String, DynamicPrimitive> GLOBAL_MEMORY = new HashMap<>();

    public Interpreter(AbstractSyntaxTree abstractSyntaxTree) {
        this.abstractSyntaxTree = abstractSyntaxTree;
    }

    public HashMap<String, DynamicPrimitive> getGLOBAL_MEMORY() {
        return GLOBAL_MEMORY;
    }

    protected DynamicPrimitive visitNumNode(NumNode node)
    {
        return node.getValue();
    }

    protected DynamicPrimitive visitBinaryOperatorNode(BinaryOperatorNode node) throws Exception
    {
        DynamicPrimitive left = this.visit(node.getLeft());
        DynamicPrimitive right = this.visit(node.getRight());

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

    protected DynamicPrimitive visitUnaryOperatorNode(UnaryOperatorNode node) throws Exception
    {
        Token.TokenType operator = node.getToken().getType();
        if(operator == Token.TokenType.PLUS)
            return new DynamicPrimitive(+this.visit(node.getExpr()).getFloat());
        else if (operator ==  Token.TokenType.MINUS)
            return new DynamicPrimitive(-this.visit(node.getExpr()).getFloat());

        throw new Exception("Impossible Unary Operator Node");
    }

    protected void visitCompoundNode(CompoundNode node) throws Exception
    {
        for(Node child : node.getChildren())
        {
            this.visit(child);
        }
    }

    protected void visitAssignNode(AssignNode node) throws Exception
    {
        if(node.getLeft() instanceof VarNode)
        {
            VarNode vNode = (VarNode)node.getLeft();
            this.GLOBAL_MEMORY.put(vNode.getValue(), this.visit(node.getRight()));
        } else {
            throw new Exception("visitAssignNode: Left side of assignment operator is not a variable node.");
        }
    }

    protected DynamicPrimitive visitVarNode(VarNode node) throws Exception
    {
        String varName = node.getValue();

        if(!this.GLOBAL_MEMORY.containsKey(varName))
        {
            throw new Exception("visitVarNode: This variable name does not exist");
        } else {
            return this.GLOBAL_MEMORY.get(varName);
        }
    }

    protected void visitNoOperatorNode(NoOperationNode node) throws Exception
    {

    }

    protected void visitProgramNode(ProgramNode node) throws Exception
    {
        this.visitBlockNode(node.getRootNode());
    }

    protected void visitBlockNode(BlockNode node) throws Exception
    {
        for(Node dNode : node.getDeclarations())
        {
            this.visit(dNode);
        }

        this.visit(node.getCompoundStatement());
    }

    protected void visitVarDeclarationNode(VarDeclarationNode node) throws Exception
    {

    }

    @Override
    protected void visitProcedureDeclarationNode(ProcedureDeclarationNode node) throws Exception {

    }

    protected void visitTypeNode(TypeNode node) throws Exception
    {

    }

    /*private DynamicPrimitive visit(Node node) throws Exception
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
        throw new Exception("visit must not reach this line");
    }*/

    /*private int visit(Node node)
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
                int left = this.visit(node.getLeft());
                int right = this.visit(node.getRight());

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

    /*private DynamicPrimitive postOrder() throws Exception
    {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(this.parser.parse());
        return this.visit(ast.getRoot());
    }*/

    public DynamicPrimitive interpret() throws Exception
    {
        return this.visit(this.abstractSyntaxTree.getRoot());
    }
}
