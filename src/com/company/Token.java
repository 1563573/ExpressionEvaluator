package com.company;

public class Token
{
    public Token(TokenTypeEnum t, String val)
    {
        type = t;
        value = val;
    }

    public Token(TokenTypeEnum t)
    {
        type = t;
        value = null;
    }

    @Override
    public String toString()
    {
        return value;
    }
    public int getIntValue()
    {
        return Integer.parseInt(value);
    }

    public final TokenTypeEnum type;
    public final String value;

}
