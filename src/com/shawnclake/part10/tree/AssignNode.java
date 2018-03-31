package com.shawnclake.part10.tree;

import com.shawnclake.part10.Token;

public class AssignNode extends BinaryOperatorNode {

    public AssignNode(Node left, Node right, Token operator) {
        super(left, right, operator);
    }
}
