package com.shawnclake.part10.tree;

import java.util.ArrayList;

public class BlockNode extends Node {

    private ArrayList<Node> declarations;
    private CompoundNode compoundStatement;

    public BlockNode(ArrayList<Node> declarations, CompoundNode compoundNode) {
        super(null, null);
        this.declarations = declarations;
        this.compoundStatement = compoundNode;
    }

    public ArrayList<Node> getDeclarations() {
        return declarations;
    }

    public CompoundNode getCompoundStatement() {
        return compoundStatement;
    }
}
