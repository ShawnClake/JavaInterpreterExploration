package com.shawnclake;

import com.shawnclake.morgencore.core.component.terminal.Input;
import com.shawnclake.morgencore.core.component.terminal.Output;
import com.shawnclake.part1.Interpreter;
import com.shawnclake.part4.Lexer;

public class Main {

    public static void main(String args[])
    {
        part6();
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

    }

    public static void part8()
    {

    }

    public static void part9()
    {

    }

    public static void part10()
    {

    }

}
