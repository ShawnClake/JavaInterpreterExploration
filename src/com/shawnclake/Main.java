package com.shawnclake;

import com.shawnclake.morgencore.core.component.terminal.Input;
import com.shawnclake.morgencore.core.component.terminal.Output;
import com.shawnclake.part1.Interpreter;

public class Main {

    public static void main(String args[])
    {
        part2();
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

    }

    public static void part4()
    {

    }

    public static void part5()
    {

    }

    public static void part6()
    {

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
