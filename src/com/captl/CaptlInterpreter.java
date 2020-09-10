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
    boolean comment;
    byte[] vars = new byte[24];
    List<Character> var_symbols = CaptlUtil.toCharList("!\"§$%&/)=?*+~'#-_.:,;<>|^°");
    int[] back = new int[32768];
    int count;
    int skip;
    int index;
    int indexCache;
    int recursionAmount;
    int recursion_number = 0;
    int depth;
    int maxIndex = 10;

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
                            System.out.print(new String(new byte[]{currentByte}));
                            break;
                        case 'P':
                            System.out.print(Arrays.toString(memory));
                            System.out.print(new String(memory));
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
                        case 'r':
                            recursionAmount = parseInt(input);
                            indexCache = count;
                            continue;
                        case 'R':
                            recursion_number = parseInt(input);
                        case '[':
                            depth++;
                            back[depth] = count;
                            break;
                        case ']':
                            if(memory[index] == recursion_number){
                                depth--;
                            }else {
                                count = back[depth];
                            }
                            break;
                    }
                } else
                    skip--;
            }
            if(recursionAmount > 1) {
                recursionAmount--;
                count = indexCache;
                continue;
            }
            count++;
            if(CaptlEntry.showMem)
            printMemory();
        }
        printMemory();
    }

    private void checkArray() {
        if(index < 0)
            index++;
        if(index >= memory.length)
            index--;
        if(maxIndex < index)
            maxIndex = index;
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

    private void printMemory(){
        System.out.print("\n[");
        for(int i=0;i<maxIndex+1;i++){
            String str = String.valueOf(memory[i]);
            if(i == index)
                str = "\033[4m" + String.valueOf(memory[i]) + "\033[0m";
            if(i < maxIndex)
            System.out.print(str + ",");
            else
                System.out.print(str);
        }
        System.out.print("]");
    }

}
