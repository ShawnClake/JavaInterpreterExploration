package com.shawnclake.part9.tree;

import com.shawnclake.part9.Token;

public class AssignNode extends BinaryOperatorNode {

    public AssignNode(Node left, Node right, Token operator) {
        super(left, right, operator);
    }
}
