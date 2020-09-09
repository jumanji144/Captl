package com.captl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CaptlUtil {
    public static byte[] toByteArray(ArrayList<Byte> list){
        byte[] result = new byte[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static List<Character> toCharList(String input){
        return input.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    public static boolean contains(char[] array, Object value){
        List<Object> list = Collections.singletonList(array);
        return list.contains(value);
    }
}
