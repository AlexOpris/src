package com.company;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {

    public static int number_check(String s) {

        try {
            Integer.parseInt(s);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean string_check(String str){

        char[] charArray = str.toCharArray();

        if(charArray[0] == '"' && charArray[charArray.length - 1] == '"')
            return true;
        else
            return false;
    }

    public static boolean identifier_check(String str)
    {
        if (!((str.charAt(0) >= 'a' && str.charAt(0) <= 'z')
                || (str.charAt(0)>= 'A' && str.charAt(0) <= 'Z')
                || str.charAt(0) == '_'))
            return false;

        for (int i = 1; i < str.length(); i++)
        {
            if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                    || (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                    || str.charAt(i) == '_'))
                return false;
        }
        return true;
    }

    public static boolean token_check(String str){
        return str.equals("INTEGER") || str.equals("CHARACTER") || str.equals("VAR") || str.equals("IF") || str.equals("ELSE") || str.equals("THEN") || str.equals("DO") || str.equals(",") ||
                str.equals("ARR") || str.equals("STRING") || str.equals("WHILE") || str.equals("FOR") ||str.equals("READ") || str.equals("START") || str.equals("VARIABLE") || str.equals("%") ||
                str.equals("AND") || str.equals("end_program") || str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals(":=") || str.equals("<-") ||
                str.equals("<") || str.equals(">") || str.equals("<=") || str.equals(">=") || str.equals("!=") || str.equals("==") || str.equals("(") || str.equals(")") || str.equals(":");
    }

    public static boolean reservedWord_check(String s){
        return Objects.equals(s, "INTEGER");
    }

    public static void main(String[] args) {

        Map<String, Integer> tokenMap = new HashMap<String, Integer>();

        tokenMap.put("identifier", 0);
        tokenMap.put("constant", 1);
        tokenMap.put("INTEGER", 2);
        tokenMap.put("CHARACTER", 3);
        tokenMap.put("VAR", 4);
        tokenMap.put("IF", 5);
        tokenMap.put("ELSE", 6);
        tokenMap.put("THEN", 7);
        tokenMap.put("DO", 8);
        tokenMap.put("ARR", 9);
        tokenMap.put("STRING", 10);
        tokenMap.put("WHILE", 11);
        tokenMap.put("FOR", 12);
        tokenMap.put("READ", 13);
        tokenMap.put("START", 14);
        tokenMap.put("VARIABLE", 15);
        tokenMap.put("AND", 16);
        tokenMap.put("end_program", 17);
        tokenMap.put("+", 18);
        tokenMap.put("-", 19);
        tokenMap.put("*", 20);
        tokenMap.put("/", 21);
        tokenMap.put(":=", 22);
        tokenMap.put("<-", 23);
        tokenMap.put("<", 24);
        tokenMap.put(">", 25);
        tokenMap.put("<=", 26);
        tokenMap.put(">=", 27);
        tokenMap.put("!=", 28);
        tokenMap.put("==", 29);
        tokenMap.put("(", 30);
        tokenMap.put(")", 31);
        tokenMap.put(":", 31);
        tokenMap.put(",", 32);
        tokenMap.put("%", 33);

        Map<String, Integer> SymbolMap = new HashMap<String, Integer>(); //only identifiers and constants
        Map<String, List<Integer>> PIFMap = new HashMap<String, List<Integer>>();
        int symbol_count = 0;

        try {
            File file = new File("C:\\Users\\opris\\OneDrive\\Desktop\\FLCD\\Exercise.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {

                String[] splited = line.split(" ");

                for (String s : splited) {
                    if (number_check(s) == 1) {
                        if (!(tokenMap.containsKey(s))) {
                            if(!(SymbolMap.containsKey(s))) {
                                SymbolMap.put(s, symbol_count);
                                symbol_count++;
                            }
                        }
                    }
                    else if(identifier_check(s)){
                        if(!(tokenMap.containsKey(s))){
                            if(!(SymbolMap.containsKey(s))) {
                                SymbolMap.put(s, symbol_count);
                                symbol_count++;
                            }
                        }
                    }
                    else if(string_check(s)){
                        if(!(tokenMap.containsKey(s))){
                            if(!(SymbolMap.containsKey(s))) {
                                SymbolMap.put(s, symbol_count);
                                symbol_count++;
                            }
                        }
                    }
                    if ((identifier_check(s)) || (number_check(s) == 1) || token_check(s) || string_check(s)) {
                        List<Integer> maps = new ArrayList<>();
                        maps.add(SymbolMap.get(s) == null ? -1 : SymbolMap.get(s));
                        if(tokenMap.containsKey(s)){
                            maps.add(tokenMap.get(s));
                        }
                        else if((identifier_check(s))){
                            maps.add(0);
                        }
                        else if ((number_check(s) == 1 || string_check(s))){
                            maps.add(1);
                        }
                        PIFMap.put(s, maps);
                    }
                }
            }
            fr.close();
            System.out.println("Symbol table content: ");
            for(Map.Entry<String, Integer> entry : SymbolMap.entrySet())
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());

            System.out.println();
            System.out.println("Program Internal Form table: ");

            for(Map.Entry<String, List<Integer>> entry : PIFMap.entrySet())
                System.out.println("Key = " + entry.getKey() + ",   " +
                        " [Symbol table, Token table] = " + entry.getValue());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
