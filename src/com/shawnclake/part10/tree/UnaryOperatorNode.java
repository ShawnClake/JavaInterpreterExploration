package com.shawnclake.part10.tree;

import com.shawnclake.part10.Token;

public class UnaryOperatorNode extends BinaryOperatorNode {

    private Node expr;

    public UnaryOperatorNode(Token operator, Node expr) {
        super(null, null, operator);
        this.expr = expr;
    }

    public Node getExpr() {
        return expr;
    }

    public void setExpr(Node expr) {
        this.expr = expr;
    }
}
