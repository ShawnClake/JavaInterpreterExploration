package com.shawnclake.part9.tree;

import com.shawnclake.part9.Token;

public class VarNode extends BinaryOperatorNode {

    private String value;

    public VarNode(Token token) {
        super(null, null, token);
        this.value = token.getValue();
    }

    public String getValue() {
        return value;
    }
}
