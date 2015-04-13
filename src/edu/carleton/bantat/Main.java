package edu.carleton.bantat;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String input = "U+00EA U+0074 U+0072 U+0065";
        ArrayList<EncodingHelperChar> chars = new ArrayList();
        String[] split = input.split(" ");
        for (int i = 0; i < split.length; i++) {
            int codePt = Integer.valueOf(split[i].substring(2),16).intValue();
            EncodingHelperChar newChar = new EncodingHelperChar(codePt);
            chars.add(newChar);
        }
        for (int i = 0; i < chars.size(); i++) {
            System.out.println("Char " + (i + 1) + ": "
                    + chars.get(i).toCharacter()
                    + ", Group: " + chars.get(i).getGroupName());
        }

    }
}
