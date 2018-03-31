package com.shawnclake.part12.symbols;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.part12.tree.*;

public class SymbolTableBuilder extends NodeVisitor {

    private SymbolTable symbolTable = new SymbolTable();

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public DynamicPrimitive build(AbstractSyntaxTree abstractSyntaxTree) throws Exception
    {
        return this.visit(abstractSyntaxTree.getRoot());
    }

    @Override
    protected DynamicPrimitive visitNumNode(NumNode node) throws Exception {
        return null;
    }

    @Override
    protected DynamicPrimitive visitBinaryOperatorNode(BinaryOperatorNode node) throws Exception {
        this.visit(node.getLeft());
        this.visit(node.getRight());
        return null;
    }

    @Override
    protected DynamicPrimitive visitUnaryOperatorNode(UnaryOperatorNode node) throws Exception {
        this.visit(node.getExpr());
        return null;
    }

    @Override
    protected void visitCompoundNode(CompoundNode node) throws Exception {
        for(Node child : node.getChildren())
        {
            this.visit(child);
        }
    }

    @Override
    protected void visitAssignNode(AssignNode node) throws Exception {
        String varName = ((VarNode)node.getLeft()).getValue();
        Symbol varSymbol = this.symbolTable.lookup(varName);
        if(varSymbol == null)
            throw new Exception("Variable is never declared: " + varName);
        this.visit(node.getRight());
    }

    @Override
    protected DynamicPrimitive visitVarNode(VarNode node) throws Exception {
        String varName = node.getValue();
        Symbol varSymbol = this.symbolTable.lookup(varName);
        if(varSymbol == null)
            throw new Exception("Variable is never declared: " + varName);
        return null;
    }

    @Override
    protected void visitNoOperatorNode(NoOperationNode node) throws Exception {

    }

    @Override
    protected void visitProgramNode(ProgramNode node) throws Exception {
        this.visit(node.getRootNode());
    }

    @Override
    protected void visitBlockNode(BlockNode node) throws Exception {
        for(Node dNode : node.getDeclarations())
        {
            this.visit(dNode);
        }

        this.visit(node.getCompoundStatement());
    }

    @Override
    protected void visitVarDeclarationNode(VarDeclarationNode node) throws Exception {
        String typeName = node.getType().getValue().getString();
        Symbol typeSymbol = this.symbolTable.lookup(typeName);
        String varName = node.getVar().getValue();
        VarSymbol varSymbol = new VarSymbol(varName, typeSymbol);
        this.symbolTable.define(varSymbol);
        //Output.pln(this.getSymbolTable().getRaw().toString());
    }

    @Override
    protected void visitProcedureDeclarationNode(ProcedureDeclarationNode node) throws Exception {

    }

    @Override
    protected void visitTypeNode(TypeNode node) throws Exception {

    }
}
