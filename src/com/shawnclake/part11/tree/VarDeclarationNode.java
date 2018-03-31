package com.shawnclake.part11.tree;

public class VarDeclarationNode extends Node {

    public VarDeclarationNode(VarNode varNode, TypeNode typeNode) {
        super(varNode, typeNode);
    }

    public TypeNode getType()
    {
        return (TypeNode)this.getRight();
    }

    public VarNode getVar()
    {
        return (VarNode)this.getLeft();
    }
}
