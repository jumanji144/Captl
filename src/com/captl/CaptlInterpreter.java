package com.captl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CaptlInterpreter {

    byte[] memory = new byte[32768];
    int currentVar = 0;
    boolean comment;
    byte[] vars = new byte[24];
    List<Character> var_symbols = CaptlUtil.toCharList("!\"§$%&/)=?*+~'#-_.:,;<>|^°");
    int count;
    int skip;
    int index;

    public CaptlInterpreter(String input) throws IOException {
        input = input.replaceAll("\t", "").replaceAll("\r", "");
        interpret(input);
    }

    public void interpret(String input) throws IOException {
        byte currentByte;
        while(count < input.length()){
            char c = input.toCharArray()[count];
            if(c == ';')
                comment = !comment;
            if(!comment) {
                currentByte = memory[index];
                if (skip == 0) {
                    switch (c) {
                        case 'A':
                            memory[index] = (byte)parseInt(input);
                            continue;
                        case 'd':
                            for (byte value : parseString(input).getBytes()) {
                                memory[index] = value;
                                index++;
                                checkArray();
                            }
                            break;
                        case 'D':
                            memory[index] = (byte)(memory[index] - (byte)parseInt(input));
                            continue;
                        case 'h':
                            currentByte--;
                            memory[index] = currentByte;
                            break;
                        case 'H':
                            currentByte++;
                            memory[index] = currentByte;
                            break;
                        case 'N':
                            index++;
                            checkArray();
                            break;
                        case 'n':
                            index--;
                            checkArray();
                            break;
                        case 'p':
                            System.out.println(new String(new byte[]{currentByte}));
                            break;
                        case 'P':
                            System.out.println(Arrays.toString(memory));
                            System.out.println(new String(memory));
                            break;
                        case 'i':
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            memory[index] = (byte) reader.read();
                            break;
                        case 'I':
                            reader = new BufferedReader(new InputStreamReader(System.in));
                            for (byte value : reader.readLine().getBytes()) {
                                memory[index] = value;
                                index++;
                                checkArray();
                            }
                            break;
                        case 'V':
                            char symbol = input.toCharArray()[count + 1];
                            if (var_symbols.contains(symbol)) {
                                vars[getSymbolIndex(symbol)] = currentByte;
                                count++;
                            }
                            break;
                        case 'e':
                            currentByte = 0;
                            break;
                        case 'E':
                            memory[index] = 0;
                            break;
                    }
                } else
                    skip--;
            }
            count++;
        }
    }

    private void checkArray() {
        if(index < 0)
            index++;
        if(index >= memory.length)
            index--;
    }

    private int getSymbolIndex(char symbol){
        int index = 0;
        for(char c:var_symbols){
            if(c == symbol)
                return index;
            index++;
        }
        return -1;
    }

    private int parseInt(String input){
        String cache = "";
        char[] charArray = input.toCharArray();
        count++;
        if(getSymbolIndex(charArray[count]) != -1){
            return vars[getSymbolIndex(charArray[count])];
        }
        while(is_digit(charArray[count])){
            cache += charArray[count];
            count++;
        }
        return Integer.parseInt(cache);
    }

    private String parseString(String input){
        String cache = "";
        char[] charArray = input.toCharArray();
        count++;
        if(charArray[count] == '"') {
            count++;
            while (charArray[count] != '"') {
                if(charArray[count] == '\\'){
                    count++;
                }
                cache += charArray[count];
                count++;
            }
        }
        return cache;
    }

    private boolean is_digit(char c){
        switch (c){
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

}
