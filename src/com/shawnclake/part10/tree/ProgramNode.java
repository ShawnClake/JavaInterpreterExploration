package com.shawnclake.part10.tree;

public class ProgramNode extends Node {

    private String programName;
    private BlockNode rootNode;

    public ProgramNode(String name, BlockNode rootNode) {
        super(null, null);
        this.programName = name;
        this.rootNode = rootNode;
    }

    public String getProgramName() {
        return programName;
    }

    public BlockNode getRootNode() {
        return rootNode;
    }
}
