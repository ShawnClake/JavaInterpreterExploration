package com.shawnclake.part11.tree;

import com.shawnclake.part11.Token;

public class VarNode extends BinaryOperatorNode {

    private String value;

    public VarNode(Token token) {
        super(null, null, token);
        this.value = token.getValue().getString();
    }

    public String getValue() {
        return value;
    }
}
