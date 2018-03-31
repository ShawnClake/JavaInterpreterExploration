package com.shawnclake.part13.tree;

import java.util.ArrayList;

public class CompoundNode extends Node {

    private ArrayList<Node> children = new ArrayList<>();

    public CompoundNode() {
        super(null, null);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }
}
