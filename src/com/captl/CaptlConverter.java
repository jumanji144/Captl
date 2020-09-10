package com.captl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CaptlConverter {

    public static String toCaptl(String brainfuck){
        return brainfuck
                .replaceAll(Pattern.quote("+"), "H")
                .replaceAll(Pattern.quote("-"), "h")
                .replaceAll(Pattern.quote("<"), "n")
                .replaceAll(Pattern.quote(">"), "N")
                .replaceAll(Pattern.quote("."), "p")
                .replaceAll(Pattern.quote(","), "i");
    }

    public static void saveToFile(File file){
        try {
            Scanner reader = new Scanner(file);
            String lines = "";
            while (reader.hasNext()) {
                lines += reader.nextLine();
            }
            lines = toCaptl(lines);
            File out = new File(file.getAbsolutePath() + ".captl");
            if(out.exists())
                out.delete();
            out.createNewFile();
            Writer writer = new FileWriter(out);
            writer.write(lines);
            writer.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
