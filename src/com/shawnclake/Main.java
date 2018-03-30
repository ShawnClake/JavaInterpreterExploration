package com.shawnclake;

import com.shawnclake.morgencore.core.component.filesystem.FileRead;
import com.shawnclake.morgencore.core.component.terminal.Input;
import com.shawnclake.morgencore.core.component.terminal.Output;
import com.shawnclake.part1.Interpreter;
import com.shawnclake.part4.Lexer;

public class Main {

    public static void main(String args[])
    {
        part9();
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

    }

}
