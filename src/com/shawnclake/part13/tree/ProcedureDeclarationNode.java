package com.shawnclake.part13.tree;

public class ProcedureDeclarationNode extends Node {

    private BlockNode blockNode;
    private String procedureName;

    public ProcedureDeclarationNode(String procedureName, BlockNode blockNode) {
        super(null, null);
        this.blockNode = blockNode;
        this.procedureName = procedureName;
    }

    public BlockNode getBlockNode() {
        return blockNode;
    }

    public String getProcedureName() {
        return procedureName;
    }
}
