package com.shawnclake.part7.tree;

import com.shawnclake.part7.Token;

/**
 * A binary operator is an operator which operates on exactly two operands
 * e.g. +, -, *, /
 */
public class BinaryOperatorNode extends Node {

    private Token token;

    public BinaryOperatorNode(Node left, Node right, Token operator) {
        super(left, right);
        this.token = operator;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
