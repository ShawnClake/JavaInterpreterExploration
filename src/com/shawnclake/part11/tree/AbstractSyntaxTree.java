package com.shawnclake.part11.tree;

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

}
