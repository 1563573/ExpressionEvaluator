package com.company;

public class Main
{
    public static void main(String[] args) throws SyntaxException
    {
        var p = new Parser(new java.util.Scanner(System.in).nextLine());
        System.out.println(p.parse());
    }
}
