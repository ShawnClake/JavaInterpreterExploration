package com.shawnclake.part12.tree;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.part12.Token;

public class NumNode extends BinaryOperatorNode {

    private DynamicPrimitive value;

    public NumNode(Token token) {
        super(null, null, token);
        this.value = token.getValue();
    }

    public DynamicPrimitive getValue() {
        return value;
    }

    public void setValue(DynamicPrimitive value) {
        this.value = value;
    }

    /*@Override
    public int visit() {
        return this.getValue();
    }*/
}
