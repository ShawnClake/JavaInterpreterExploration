package com.shawnclake.part12.tree;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;

// TODO: Use reflection maybe? Would slow it down a lot, but make it more flexible
abstract public class NodeVisitor {

    abstract protected DynamicPrimitive visitNumNode(NumNode node) throws Exception;
    abstract protected DynamicPrimitive visitBinaryOperatorNode(BinaryOperatorNode node) throws Exception;
    abstract protected DynamicPrimitive visitUnaryOperatorNode(UnaryOperatorNode node) throws Exception;
    abstract protected void visitCompoundNode(CompoundNode node) throws Exception;
    abstract protected void visitAssignNode(AssignNode node) throws Exception;
    abstract protected DynamicPrimitive visitVarNode(VarNode node) throws Exception;
    abstract protected void visitNoOperatorNode(NoOperationNode node) throws Exception;
    abstract protected void visitProgramNode(ProgramNode node) throws Exception;
    abstract protected void visitBlockNode(BlockNode node) throws Exception;
    abstract protected void visitVarDeclarationNode(VarDeclarationNode node) throws Exception;
    abstract protected void visitTypeNode(TypeNode node) throws Exception;
    abstract protected void visitProcedureDeclarationNode(ProcedureDeclarationNode node) throws Exception;

    protected DynamicPrimitive visit(Node node) throws Exception
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
                return new DynamicPrimitive();
            }
            else if(node instanceof VarNode)
            {
                return this.visitVarNode((VarNode) node);
            }
            else if(node instanceof ProgramNode)
            {
                this.visitProgramNode((ProgramNode) node);
                return new DynamicPrimitive();
            }
            else if(node instanceof BlockNode)
            {
                this.visitBlockNode((BlockNode) node);
                return new DynamicPrimitive();
            }
            else if(node instanceof VarDeclarationNode)
            {
                this.visitVarDeclarationNode((VarDeclarationNode) node);
                return new DynamicPrimitive();
            }
            else if(node instanceof ProcedureDeclarationNode)
            {
                this.visitProcedureDeclarationNode((ProcedureDeclarationNode) node);
                return new DynamicPrimitive();
            }
            else if(node instanceof TypeNode)
            {
                this.visitTypeNode((TypeNode) node);
                return new DynamicPrimitive();
            }
            else if(node instanceof BinaryOperatorNode)
            {
                return this.visitBinaryOperatorNode((BinaryOperatorNode) node);
            }
            else if(node instanceof CompoundNode)
            {
                this.visitCompoundNode((CompoundNode) node);
                return new DynamicPrimitive();
            }

            else if(node instanceof NoOperationNode)
            {
                this.visitNoOperatorNode((NoOperationNode) node);
                return new DynamicPrimitive();
            }
        } else {
            throw new Exception("Leaf node shouldn't be null");
        }

        //return 0;
        throw new Exception("visit must not reach this line");
    }

}
