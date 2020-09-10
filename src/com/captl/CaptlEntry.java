package com.captl;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CaptlEntry {

    public static boolean showMem = false;

    public static void main(String[] args) throws IOException {
        List<String> arrayList = Arrays.asList(args);
        if(arrayList.size() == 1){
            displayHelp(args);
        }
        if(arrayList.size() > 1){
            if(arrayList.contains("-i")){
                new CaptlConsole();
            }
            if(arrayList.contains("-s")){
                showMem = true;
            }
            if(arrayList.contains("-b")) {
                if(Paths.get(args[arrayList.indexOf("-b")+1]).toFile().exists()) {
                    CaptlConverter.saveToFile(Paths.get(args[arrayList.indexOf("-b")+1]).toFile());
                }else {
                    System.out.println("File does not exist");
                }
            }
            if(arrayList.contains("-f")) {
                if(Paths.get(args[arrayList.indexOf("-f")+1]).toFile().exists()) {
                    Scanner reader = new Scanner(Paths.get(args[arrayList.indexOf("-f")+1]).toFile());
                    String lines = "";
                    while (reader.hasNext()) {
                        lines += reader.nextLine();
                    }
                    new CaptlInterpreter(lines);
                }else {
                    System.out.println("File does not exist");
                }
            }
        }
    }

    public static void displayHelp(String[] args) {
        System.out.println("Usage: " + args[0] + "[-ifbs] <filename>\n -i enter interpreter console\n -f open from file\n -b convert from brainfuck to Captl\n -s shows the memory during execution");
    }

}
