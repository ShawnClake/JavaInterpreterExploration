package com.shawnclake.part7.tree;

import com.shawnclake.part7.Token;

public class IntegerNode extends BinaryOperatorNode {

    private int value;

    public IntegerNode(Token token) {
        super(null, null, token);
        this.value = Integer.parseInt(token.getValue());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
