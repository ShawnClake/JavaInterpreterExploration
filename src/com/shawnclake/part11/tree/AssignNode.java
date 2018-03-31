package com.shawnclake.part11.tree;

import com.shawnclake.part11.Token;

public class AssignNode extends BinaryOperatorNode {

    public AssignNode(Node left, Node right, Token operator) {
        super(left, right, operator);
    }
}
