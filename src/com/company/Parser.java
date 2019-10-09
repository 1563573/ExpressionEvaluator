package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser
{
    public Parser(String str)
    {
        sc = new ExpScanner(str);
    }

    public int parse() throws SyntaxException
    {
        var result = parseExpression();
        sc.process(TokenTypeEnum.EOF);
        return result;
    }

    public int parseExpression() throws SyntaxException
    {
        if(sc.isNext(TokenTypeEnum.NUMBER, TokenTypeEnum.OPENPARENTHESES))
        {
            var firstPart = parseTerm();
            return parseExpression2(firstPart);
        }
        else if(sc.isNext(TokenTypeEnum.PLUS, TokenTypeEnum.MINUS))
        {
            var addop = sc.process(TokenTypeEnum.PLUS, TokenTypeEnum.MINUS).type;
            var firstPart = parseTerm();
            if(addop == TokenTypeEnum.MINUS) firstPart *= -1;
            return parseExpression2(firstPart);
        }
        else
        {
            throw new SyntaxException(sc.peekNext().type.toString() + " found while expecting " + Arrays.deepToString(new TokenTypeEnum[]{TokenTypeEnum.NUMBER, TokenTypeEnum.OPENPARENTHESES, TokenTypeEnum.PLUS, TokenTypeEnum.MINUS}), sc.getLocation());
        }
    }

    public int parseExpression2(int firstPart) throws SyntaxException
    {
        if(sc.isNext(TokenTypeEnum.PLUS, TokenTypeEnum.MINUS))
        {
            var addop = sc.process(TokenTypeEnum.PLUS, TokenTypeEnum.MINUS).type;
            if (addop == TokenTypeEnum.PLUS) firstPart += parseTerm();
            else firstPart -= parseTerm();
            return parseExpression2(firstPart);
        }
        return firstPart;
    }

    public int parseTerm() throws SyntaxException
    {
        var firstPart = parseFactor();
        return parseTerm2(firstPart);
    }

    public int parseTerm2(int firstPart) throws SyntaxException
    {
        if(sc.isNext(TokenTypeEnum.MULTIPLY, TokenTypeEnum.DIVIDE))
        {
            var mulop = sc.process(TokenTypeEnum.MULTIPLY, TokenTypeEnum.DIVIDE).type;
            if (mulop == TokenTypeEnum.MULTIPLY) firstPart *= parseFactor();
            else firstPart /= parseFactor();
            return parseTerm2(firstPart);
        }
        else
        {
            return firstPart;
        }
    }

    public int parseFactor() throws SyntaxException
    {
        if(sc.isNext(TokenTypeEnum.NUMBER, TokenTypeEnum.PLUS, TokenTypeEnum.MINUS))
        {
            return parseNumber();
        }
        else if (sc.isNext(TokenTypeEnum.OPENPARENTHESES))
        {
            sc.process(TokenTypeEnum.OPENPARENTHESES);
            var result = parseExpression();
            sc.process(TokenTypeEnum.CLOSEPARENTHESES);
            return result;
        }
        else
        {
            throw new SyntaxException(sc.peekNext().type + "found while expecting " + Arrays.deepToString(new TokenTypeEnum[]{TokenTypeEnum.NUMBER, TokenTypeEnum.PLUS, TokenTypeEnum.MINUS, TokenTypeEnum.OPENPARENTHESES}), sc.getLocation());
        }
    }

    public int parseNumber() throws SyntaxException
    {
        int coefficient = 1;
        if(sc.isNext(TokenTypeEnum.PLUS, TokenTypeEnum.MINUS))
        {
            if(sc.isNext(TokenTypeEnum.MINUS)) coefficient *= -1;
            sc.process(TokenTypeEnum.PLUS, TokenTypeEnum.MINUS);
        }
        return coefficient * sc.process(TokenTypeEnum.NUMBER).getIntValue();
    }

    private ExpScanner sc;
}
