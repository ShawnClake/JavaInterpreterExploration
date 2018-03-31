package com.shawnclake.part12.tree;

import com.shawnclake.part12.Token;

public class AssignNode extends BinaryOperatorNode {

    public AssignNode(Node left, Node right, Token operator) {
        super(left, right, operator);
    }
}
