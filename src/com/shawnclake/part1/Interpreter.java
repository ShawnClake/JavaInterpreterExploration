package com.shawnclake.part1;

public class Interpreter {

    private String text;
    private int pos = 0;
    private Token token;

    public Interpreter(String text) {
        this.text = text;
    }

    public void error() throws Exception
    {
        throw new Exception("Error Parsing Input");
    }

    public Token getNextToken()
    {
        String temp = this.text;

        if(this.pos > temp.length() - 1)
            return new Token(Token.EOF, ' ');

        char current = temp.charAt(this.pos);

        if(Character.isDigit(current))
        {
            Token token = new Token(Token.INTEGER, current);
            this.pos++;
            return token;
        }

        if(current == '+')
        {
            Token token = new Token(Token.PLUS, current);
            this.pos++;
            return token;
        }

        try {
            this.error();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void eat(String tokenType)
    {
        if(this.token.getType().equals(tokenType))
            this.token = this.getNextToken();
        else
            try {
                this.error();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public String expr()
    {
        this.token = this.getNextToken();

        Token left = this.token;
        this.eat(Token.INTEGER);

        Token operator = this.token;
        this.eat(Token.PLUS);

        Token right = this.token;
        this.eat(Token.INTEGER);

        int r = Character.getNumericValue(left.getValue()) + Character.getNumericValue(right.getValue());

        return "" + r;
    }
}
