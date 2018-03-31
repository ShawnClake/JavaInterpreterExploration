package com.shawnclake.part13.tree;

import com.shawnclake.part13.Token;

public class AssignNode extends BinaryOperatorNode {

    public AssignNode(Node left, Node right, Token operator) {
        super(left, right, operator);
    }
}
