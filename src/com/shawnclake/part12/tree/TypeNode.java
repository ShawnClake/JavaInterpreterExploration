package com.shawnclake.part12.tree;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.part12.Token;

public class TypeNode extends BinaryOperatorNode {

    private DynamicPrimitive value;

    public TypeNode(Token token) {
        super(null, null, token);
        this.value = token.getValue();
    }

    public DynamicPrimitive getValue() {
        return value;
    }
}
