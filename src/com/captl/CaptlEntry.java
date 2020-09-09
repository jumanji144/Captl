package com.captl;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CaptlEntry {

    public static void main(String[] args) throws IOException {
        List<String> arrayList = Arrays.asList(args);
        if(arrayList.size() == 1){
            displayHelp(args);
        }
        if(arrayList.size() > 1){
            if(arrayList.contains("-i")){
                new CaptlConsole();
            }
            else if(arrayList.contains("-f")) {
                if(Paths.get(args[1]).toFile().exists()) {
                    File file;
                    Scanner reader = new Scanner(Paths.get(args[1]).toFile());
                    String lines = "";
                    while (reader.hasNext()) {
                        lines += reader.nextLine();
                    }
                    new CaptlInterpreter(lines);
                }else {
                    System.out.println("File does not exist");
                }
            }else {
                new CaptlInterpreter(Arrays.toString(args));
            }
        }
    }

    public static void displayHelp(String[] args) {
        System.out.println("Usage: " + args[0] + "[-if] <filename>\n -i enter interpreter console\n -f open from file");
    }

}
