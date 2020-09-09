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
        input = input.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").replaceAll("\r", "");
        interpret(input);
    }

    public void interpret(String input) throws IOException {
        byte currentByte = 0;
        while(count < input.length()){
            char c = input.toCharArray()[count];
            if(!comment) {
                currentByte = memory[index];
                if (skip == 0) {
                    switch (c) {
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
                        case 'e':
                            currentByte = 0;
                        case 'E':
                            memory[index] = 0;
                        case ';':
                            comment = !comment;
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

}
