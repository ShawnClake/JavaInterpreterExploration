package com.shawnclake.part7;

import com.shawnclake.part7.tree.AbstractSyntaxTree;

public class Interpreter {

    private Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public int interpret()
    {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(this.parser.parse());
        return ast.postOrder();
    }
}
