package com.company;

import java.util.Arrays;

public class ExpScanner
{
    public ExpScanner(String str)
    {
        base = str.toCharArray();
    }

    public boolean isNext(TokenTypeEnum... types) throws SyntaxException
    {
        var current = peekNext();
        for(var type : types)
        {
            if (type == current.type) return true;
        }
        return false;
    }

    public Token process(TokenTypeEnum... types) throws SyntaxException
    {
        var current = getNext();
        for(var type : types)
        {
            if (type == current.type) return current;
        }
        throw new SyntaxException(current.type.toString() + " found when expecting " + Arrays.deepToString(types), location);
    }

    public Token peekNext() throws SyntaxException
    {
        var tempLocation = location;
        if (tempLocation >= base.length) return new Token(TokenTypeEnum.EOF);
        try
        {
            if (getTokenType(tempLocation) == TokenTypeEnum.WHITESPACE)
            {
                location++;
                return peekNext();
            }
            else if (getTokenType(tempLocation) != TokenTypeEnum.NUMBER) return new Token(getTokenType(tempLocation), "" + base[tempLocation]);

            String numberStr = "";
            while(tempLocation < base.length && getTokenType(tempLocation) == TokenTypeEnum.NUMBER)
            {
                numberStr += base[tempLocation];
                tempLocation++;
            }
            return new Token(TokenTypeEnum.NUMBER, numberStr);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return new Token(TokenTypeEnum.EOF);
        }
    }

    public Token getNext() throws SyntaxException
    {
        var t = peekNext();
        if(t.value == null) return t;
        location += t.value.length();
        return t;
    }

    private TokenTypeEnum getTokenType(int i) throws SyntaxException, ArrayIndexOutOfBoundsException
    {
        switch(base[i])
        {
            case '1':
                return TokenTypeEnum.NUMBER;
            case '2':
                return TokenTypeEnum.NUMBER;
            case '3':
                return TokenTypeEnum.NUMBER;
            case '4':
                return TokenTypeEnum.NUMBER;
            case '5':
                return TokenTypeEnum.NUMBER;
            case '6':
                return TokenTypeEnum.NUMBER;
            case '7':
                return TokenTypeEnum.NUMBER;
            case '8':
                return TokenTypeEnum.NUMBER;
            case '9':
                return TokenTypeEnum.NUMBER;
            case '0':
                return TokenTypeEnum.NUMBER;
            case '+':
                return TokenTypeEnum.PLUS;
            case '-':
                return TokenTypeEnum.MINUS;
            case '*':
                return TokenTypeEnum.MULTIPLY;
            case '/':
                return TokenTypeEnum.DIVIDE;
            case '(':
                return TokenTypeEnum.OPENPARENTHESES;
            case ')':
                return TokenTypeEnum.CLOSEPARENTHESES;
            case ' ':
                return TokenTypeEnum.WHITESPACE;
            default:
                throw new SyntaxException("Invalid Token Type", i);
        }
    }

    public int getLocation()
    {
        return location;
    }
    private int location = 0;
    public final char[] base;
}
