package com.shawnclake.part8.tree;

import com.shawnclake.part8.Token;

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

    /*@Override
    public int visit() {
        return this.getValue();
    }*/
}
