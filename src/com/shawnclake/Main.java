package com.shawnclake;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.morgencore.core.component.filesystem.FileRead;
import com.shawnclake.morgencore.core.component.terminal.Input;
import com.shawnclake.morgencore.core.component.terminal.Output;
import com.shawnclake.part1.Interpreter;
import com.shawnclake.part11.symbols.SymbolTableBuilder;
import com.shawnclake.part4.Lexer;

import java.util.Map;

public class Main {

    public static void main(String args[])
    {
        try {
            part13();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part1()
    {
        String input = Input.readLine();

        Interpreter interpreter = new Interpreter(input);
        Output.pln(interpreter.expr());
    }

    public static void part2()
    {
        String input = Input.readLine();

        com.shawnclake.part2.Interpreter interpreter = new com.shawnclake.part2.Interpreter(input);
        try {
            Output.pln(""+interpreter.expr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part3()
    {
        String input = Input.readLine();

        com.shawnclake.part3.Interpreter interpreter = new com.shawnclake.part3.Interpreter(input);
        try {
            Output.pln(""+interpreter.expr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part4()
    {
        String input = Input.readLine();
        Lexer lexer = new Lexer(input);
        com.shawnclake.part4.Interpreter interpreter = new com.shawnclake.part4.Interpreter(lexer);
        try {
            Output.pln(""+interpreter.expr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part5()
    {
        String input = Input.readLine();
        com.shawnclake.part5.Lexer lexer = new com.shawnclake.part5.Lexer(input);
        com.shawnclake.part5.Interpreter interpreter = new com.shawnclake.part5.Interpreter(lexer);
        try {
            Output.pln(""+interpreter.expr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part6()
    {
        String input = Input.readLine();
        com.shawnclake.part6.Lexer lexer = new com.shawnclake.part6.Lexer(input);
        com.shawnclake.part6.Interpreter interpreter = new com.shawnclake.part6.Interpreter(lexer);
        try {
            Output.pln(""+interpreter.expr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part7()
    {
        String input = Input.readLine();
        com.shawnclake.part7.Lexer lexer = new com.shawnclake.part7.Lexer(input);
        com.shawnclake.part7.Parser parser = new com.shawnclake.part7.Parser(lexer);
        com.shawnclake.part7.Interpreter interpreter = new com.shawnclake.part7.Interpreter(parser);
        try {
            Output.pln(""+interpreter.interpret());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part8()
    {
        String input = Input.readLine();
        com.shawnclake.part8.Lexer lexer = new com.shawnclake.part8.Lexer(input);
        com.shawnclake.part8.Parser parser = new com.shawnclake.part8.Parser(lexer);
        com.shawnclake.part8.Interpreter interpreter = new com.shawnclake.part8.Interpreter(parser);
        try {
            Output.pln(""+interpreter.interpret());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part9()
    {
        String input = Input.readLine();

        FileRead fileRead = new FileRead("D:\\Programming\\Projects\\JavaInterpreterExploration\\src\\com\\shawnclake\\part9/assignment.txt");

        String fInput = "";
        for(String line : fileRead.getEntireFile())
        {
            fInput += line;
        }

        Output.pln(fInput);

        com.shawnclake.part9.Lexer lexer = new com.shawnclake.part9.Lexer(fInput);
        com.shawnclake.part9.Parser parser = new com.shawnclake.part9.Parser(lexer);
        com.shawnclake.part9.Interpreter interpreter = new com.shawnclake.part9.Interpreter(parser);
        try {
            interpreter.interpret();
            Output.pln(interpreter.getGLOBAL_SCOPE().toString());
            //Output.pln(""+interpreter.interpret());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part10()
    {
        String input = Input.readLine();

        FileRead fileRead = new FileRead("D:\\Programming\\Projects\\JavaInterpreterExploration\\src\\com\\shawnclake\\part10/assignment.txt");

        String fInput = "";
        for(String line : fileRead.getEntireFile())
        {
            fInput += line;
        }

        Output.pln(fInput);

        com.shawnclake.part10.Lexer lexer = new com.shawnclake.part10.Lexer(fInput);
        com.shawnclake.part10.Parser parser = new com.shawnclake.part10.Parser(lexer);
        com.shawnclake.part10.Interpreter interpreter = new com.shawnclake.part10.Interpreter(parser);
        try {
            interpreter.interpret();
            for(Map.Entry<String, DynamicPrimitive> entry : interpreter.getGLOBAL_SCOPE().entrySet())
            {
                Output.pln(entry.getKey().toString() + ": " + entry.getValue().getString());
            }
            Output.pln(interpreter.getGLOBAL_SCOPE().toString());
            //Output.pln(""+interpreter.interpret());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void part11() throws Exception
    {
        String input = Input.readLine();

        FileRead fileRead = new FileRead("D:\\Programming\\Projects\\JavaInterpreterExploration\\src\\com\\shawnclake\\part11/assignment.txt");

        String fInput = "";
        for(String line : fileRead.getEntireFile())
        {
            fInput += line;
        }

        Output.pln(fInput);

        com.shawnclake.part11.Lexer lexer = new com.shawnclake.part11.Lexer(fInput);
        com.shawnclake.part11.Parser parser = new com.shawnclake.part11.Parser(lexer);

        com.shawnclake.part11.tree.AbstractSyntaxTree abstractSyntaxTree = parser.parse();

        com.shawnclake.part11.symbols.SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.build(abstractSyntaxTree);

        Output.pln("");
        Output.pln("Symbol Table:");
        Output.pln(symbolTableBuilder.getSymbolTable().toString());


        com.shawnclake.part11.Interpreter interpreter = new com.shawnclake.part11.Interpreter(abstractSyntaxTree);

        interpreter.interpret();
        for(Map.Entry<String, DynamicPrimitive> entry : interpreter.getGLOBAL_MEMORY().entrySet())
        {
            Output.pln(entry.getKey().toString() + ": " + entry.getValue().getString());
        }
        Output.pln(interpreter.getGLOBAL_MEMORY().toString());
        //Output.pln(""+interpreter.interpret());

    }

    public static void part12() throws Exception
    {
        String input = Input.readLine();

        FileRead fileRead = new FileRead("D:\\Programming\\Projects\\JavaInterpreterExploration\\src\\com\\shawnclake\\part12/assignment.txt");

        String fInput = "";
        for(String line : fileRead.getEntireFile())
        {
            fInput += line;
        }

        Output.pln(fInput);

        com.shawnclake.part12.Lexer lexer = new com.shawnclake.part12.Lexer(fInput);
        com.shawnclake.part12.Parser parser = new com.shawnclake.part12.Parser(lexer);

        com.shawnclake.part12.tree.AbstractSyntaxTree abstractSyntaxTree = parser.parse();

        com.shawnclake.part12.symbols.SymbolTableBuilder symbolTableBuilder = new com.shawnclake.part12.symbols.SymbolTableBuilder();
        symbolTableBuilder.build(abstractSyntaxTree);

        Output.pln("");
        Output.pln("Symbol Table:");
        Output.pln(symbolTableBuilder.getSymbolTable().toString());


        com.shawnclake.part12.Interpreter interpreter = new com.shawnclake.part12.Interpreter(abstractSyntaxTree);

        interpreter.interpret();
        for(Map.Entry<String, DynamicPrimitive> entry : interpreter.getGLOBAL_MEMORY().entrySet())
        {
            Output.pln(entry.getKey().toString() + ": " + entry.getValue().getString());
        }
        Output.pln(interpreter.getGLOBAL_MEMORY().toString());
        //Output.pln(""+interpreter.interpret());

    }

    public static void part13() throws Exception
    {
        String input = Input.readLine();

        FileRead fileRead = new FileRead("D:\\Programming\\Projects\\JavaInterpreterExploration\\src\\com\\shawnclake\\part13/assignment.txt");

        String fInput = "";
        for(String line : fileRead.getEntireFile())
        {
            fInput += line;
        }

        Output.pln(fInput);

        com.shawnclake.part13.Lexer lexer = new com.shawnclake.part13.Lexer(fInput);
        com.shawnclake.part13.Parser parser = new com.shawnclake.part13.Parser(lexer);

        com.shawnclake.part13.tree.AbstractSyntaxTree abstractSyntaxTree = parser.parse();

        com.shawnclake.part13.symbols.SymbolTableBuilder symbolTableBuilder = new com.shawnclake.part13.symbols.SymbolTableBuilder();
        symbolTableBuilder.build(abstractSyntaxTree);

        Output.pln("");
        Output.pln("Symbol Table:");
        Output.pln(symbolTableBuilder.getSymbolTable().toString());


        com.shawnclake.part13.Interpreter interpreter = new com.shawnclake.part13.Interpreter(abstractSyntaxTree);

        interpreter.interpret();
        for(Map.Entry<String, DynamicPrimitive> entry : interpreter.getGLOBAL_MEMORY().entrySet())
        {
            Output.pln(entry.getKey().toString() + ": " + entry.getValue().getString());
        }
        Output.pln(interpreter.getGLOBAL_MEMORY().toString());
        //Output.pln(""+interpreter.interpret());
    }

    public static void part14() throws Exception
    {

    }

    public static void part15() throws Exception
    {

    }

}
