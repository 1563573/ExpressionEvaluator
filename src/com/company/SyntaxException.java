package com.company;

public class SyntaxException extends Exception
{
    public SyntaxException(String str, int i)
    {
        message = str;
        index = i;
    }

    @Override
    public String toString()
    {
        return "Syntax error " +  message + " at " + index;
    }

    public final int index;
    public final String message;
}
