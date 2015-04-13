//package edu.carleton.bantat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by torebanta on 4/12/15.
 */
public class Frontend {
    private Frontend() { /* Creates an empty constructor to avoid
     instantiating this class */ }

    public static void printUsage() {
        System.out.println("Usage:");
        System.out.println("java EncodingHelper [-i type] [-o type] <data>");
        System.out.println("You may also use --input and --output");
        System.out.println("Possible types: string, codepoint, utf8, summary");
        System.exit(1);
    }

    public static void stringToSummary(String input) {
        if (input.length() == 1) {
            charStandardOutput(input.charAt(0));
        } else {
            stringStandardOutput(input);
        }
    }

    private static void charStandardOutput(char aChar) {
        EncodingHelperChar ehChar = new EncodingHelperChar(aChar);
        System.out.println("Character: " + ehChar.toCharacter());
        System.out.println("Code point: " + ehChar.toCodePointString());
        System.out.println("Name: " + ehChar.getCharacterName());
        System.out.println("UTF-8: " + ehChar.toUtf8String());
    }

    private static void stringStandardOutput(String aString) {
        EncodingHelperChar[] input = new EncodingHelperChar[aString.length()];
        for (int i = 0; i < aString.length(); i++) {
            input[i] = new EncodingHelperChar(aString.charAt(i));
        }
        String outCodePoints = "", outUTF8 = "";
        for (EncodingHelperChar item : input) {
            outCodePoints = outCodePoints.concat(item.toCodePointString()+" ");
            outUTF8 = outUTF8.concat(item.toUtf8String());
        }
        System.out.println("String: " + aString);
        System.out.println("Code points: " + outCodePoints);
        System.out.println("UTF-8: " + outUTF8);
    }

    public static void stringToUtf8(String input) {
        // Creates ArrayList to contain input chars
        ArrayList<EncodingHelperChar> chars
                = new ArrayList<EncodingHelperChar>();
        // For loop parses string and adds new EncodingHelperChar objects to
        // the ArrayList
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            EncodingHelperChar newChar = new EncodingHelperChar(current);
            chars.add(newChar);
        }
        // Prints out utf8 string representations
        for (int i = 0; i < chars.size(); i++) {
            System.out.print(chars.get(i).toUtf8String());
        }
        System.out.println();
    }

    public static void stringToCodepoint(String input) {
        // Creates ArrayList to contain input chars
        ArrayList<EncodingHelperChar> chars
                = new ArrayList<EncodingHelperChar>();
        // For loop parses string and adds new EncodingHelperChar objects to
        // the ArrayList
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            EncodingHelperChar newChar = new EncodingHelperChar(current);
            chars.add(newChar);
        }
        for (int i = 0; i < chars.size() - 1; i++) {
            System.out.print(chars.get(i).toCodePointString() + " ");
        }
        // Prints out hex string representations
        System.out.println(chars.get(chars.size() - 1).toCodePointString());
    }

    public static void stringToLang(String input) {
        // Creates ArrayList to contain input chars
        ArrayList<EncodingHelperChar> chars
                = new ArrayList<EncodingHelperChar>();
        // For loop parses string and adds new EncodingHelperChar objects to
        // the ArrayList
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            EncodingHelperChar newChar = new EncodingHelperChar(current);
            chars.add(newChar);
        }
        // Prints out each char, and Unicode group it belongs to
        for (int i = 0; i < chars.size(); i++) {
            System.out.println("Char " + (i + 1) + ": " + input.charAt(i) +
                    ", Group: " + chars.get(i).getGroupName());
        }
        System.out.println();
    }

    public static void utf8ToSummary(String input) {
        List<String> rawStringData = new LinkedList<>(Arrays.asList(input
                .split("\\\\x")));
        rawStringData.remove(0);
        List<Byte> rawByteData = new ArrayList<>();
        for (String rawString : rawStringData) {
            rawByteData.add((byte)(Integer.valueOf(rawString, 16).intValue()));
        }

        String outString = "", outCodePoints = "";
        List<EncodingHelperChar> data = new ArrayList<>();

        while (!rawByteData.isEmpty()) {
            int num = determineUtfBytes(rawByteData.get(0));
            if (rawByteData.size() >= num) {
                byte[] dataByte = new byte[num];
                List<Byte> temp = rawByteData.subList(0, num);
                for (int i=0;i<num;i++) { dataByte[i] = temp.get(i); }
                data.add(new EncodingHelperChar(dataByte));
                for (int i=0;i<num;i++) { rawByteData.remove(0); }
            } else {
                System.out.println("Invalid UTF-8 sequence");
            }
        }

        for (EncodingHelperChar item : data) {
            outCodePoints = outCodePoints.concat(item.toCodePointString()+" ");
            outString += item.toCharacter();
        }
        System.out.println("String: " + outString);
        System.out.println("Code points: " + outCodePoints);
        System.out.println("UTF-8: " + input);

    }

    private static int determineUtfBytes(byte aUtf) {
        String aUtfInString = Integer.toBinaryString(Byte.toUnsignedInt(aUtf));
        int count = 0;
        for (int i=0;i<aUtfInString.length();i++) {
            if (aUtfInString.charAt(i) == '1') { count++; }
            else break;
        }

        count = (count > 1) ? count : 1;
        return count;
    }

    public static void utf8ToString(String input) {
        List<String> rawStringData = new LinkedList<>(Arrays.asList(input
                .split("\\\\x")));
        rawStringData.remove(0);
        List<Byte> rawByteData = new ArrayList<>();
        for (String rawString : rawStringData) {
            rawByteData.add((byte)(Integer.valueOf(rawString, 16).intValue()));
        }

        String outString = "";
        List<EncodingHelperChar> data = new ArrayList<>();

        while (!rawByteData.isEmpty()) {
            int num = determineUtfBytes(rawByteData.get(0));
            if (rawByteData.size() >= num) {
                byte[] dataByte = new byte[num];
                List<Byte> temp = rawByteData.subList(0, num);
                for (int i=0;i<num;i++) { dataByte[i] = temp.get(i); }
                data.add(new EncodingHelperChar(dataByte));
                for (int i=0;i<num;i++) { rawByteData.remove(0); }
            } else {
                System.out.println("Invalid UTF-8 sequence");
            }
        }

        for (EncodingHelperChar item : data) {
            outString += item.toCharacter();
        }
        System.out.println(outString);
    }

    public static void utf8ToCodepoint(String input) {
        List<String> rawStringData = new LinkedList<>(Arrays.asList(input
                .split("\\\\x")));
        rawStringData.remove(0);
        List<Byte> rawByteData = new ArrayList<>();
        for (String rawString : rawStringData) {
            rawByteData.add((byte)(Integer.valueOf(rawString, 16).intValue()));
        }

        String outCodePoints = "";
        List<EncodingHelperChar> data = new ArrayList<>();

        while (!rawByteData.isEmpty()) {
            int num = determineUtfBytes(rawByteData.get(0));
            if (rawByteData.size() >= num) {
                byte[] dataByte = new byte[num];
                List<Byte> temp = rawByteData.subList(0, num);
                for (int i=0;i<num;i++) { dataByte[i] = temp.get(i); }
                data.add(new EncodingHelperChar(dataByte));
                for (int i=0;i<num;i++) { rawByteData.remove(0); }
            } else {
                System.out.println("Invalid UTF-8 sequence");
            }
        }

        for (EncodingHelperChar item : data) {
            outCodePoints =
                    outCodePoints.concat(item.toCodePointString() + " ");
        }
        System.out.println(outCodePoints);
    }

    public static void utf8ToLang(String input) {
        List<String> rawStringData =new LinkedList<>(Arrays.asList(input
                .split("\\\\x")));
        rawStringData.remove(0);
        List<Byte> rawByteData = new ArrayList<>();
        for (String rawString : rawStringData) {
            rawByteData.add((byte)(Integer.valueOf(rawString, 16).intValue()));
        }

        String outString = "";
        List<EncodingHelperChar> data = new ArrayList<>();

        while (!rawByteData.isEmpty()) {
            int num = determineUtfBytes(rawByteData.get(0));
            if (rawByteData.size() >= num) {
                byte[] dataByte = new byte[num];
                List<Byte> temp = rawByteData.subList(0, num);
                for (int i=0;i<num;i++) { dataByte[i] = temp.get(i); }
                data.add(new EncodingHelperChar(dataByte));
                for (int i=0;i<num;i++) { rawByteData.remove(0); }
            } else {
                System.out.println("Invalid UTF-8 sequence");
            }
        }

        for (EncodingHelperChar item : data) {
            outString += item.toCharacter();
        }
        System.out.println("String: " + outString);
        for (int i = 0; i < data.size(); i++) {
            System.out.println("Char " + (i + 1) + ": "
                    + data.get(i).toCharacter() +
                    ", Group: " + data.get(i).getGroupName());
        }
    }

    public static void codepointToSummary(String input) {
        // Separate behaviour for multiple codepoints
        if (input.contains(" ")) {
            // Creates ArrayList of char objects
            ArrayList<EncodingHelperChar> chars
                    = new ArrayList<EncodingHelperChar>();
            // Splits input string by space character
            String[] split = input.split(" ");
            for (int i = 0; i < split.length; i++) {
                // Parse int codepoint from string input
                int codePt
                        = Integer.valueOf(split[i].substring(2), 16).intValue();
                // and create EncodingHelperChar object, and add to ArrayList
                EncodingHelperChar newChar = new EncodingHelperChar(codePt);
                chars.add(newChar);
            }
            // Prints additional formatted text for summary
            System.out.print("String: ");
            for (int i = 0; i < chars.size() - 1; i++) {
                System.out.print(chars.get(i).toCharacter());
            }
            System.out.println(chars.get(chars.size() - 1).toCharacter());
            System.out.print("Code points: ");
            for (int i = 0; i < chars.size() - 1; i++) {
                System.out.print(chars.get(i).toCodePointString() + " ");
            }
            System.out.println(chars.get(chars.size() - 1).toCodePointString());
            System.out.print("UTF-8: ");
            for (int i = 0; i < chars.size(); i++) {
                System.out.print(chars.get(i).toUtf8String());
            }
            System.out.println();
        }
        // Single codepoint behaviour
        else {
            ArrayList<EncodingHelperChar> chars
                    = new ArrayList<EncodingHelperChar>();
            String[] split = input.split(" ");
            for (int i = 0; i < split.length; i++) {
                int codePt
                        = Integer.valueOf(split[i].substring(2), 16).intValue();
                EncodingHelperChar newChar = new EncodingHelperChar(codePt);
                chars.add(newChar);
            }
            System.out.print("Character: ");
            for (int i = 0; i < chars.size() - 1; i++) {
                System.out.print(chars.get(i).toCharacter());
            }
            System.out.println(chars.get(chars.size() - 1).toCharacter());
            System.out.print("Code point: ");
            for (int i = 0; i < chars.size() - 1; i++) {
                System.out.print(chars.get(i).toCodePointString() + " ");
            }
            System.out.println(chars.get(chars.size() - 1).toCodePointString());
            System.out.print("UTF-8: ");
            for (int i = 0; i < chars.size(); i++) {
                System.out.print(chars.get(i).toUtf8String());
            }
            System.out.println();
        }
    }

    public static void codepointToString(String input) {
        // Creates ArrayList of char objects
        ArrayList<EncodingHelperChar> chars
                = new ArrayList<EncodingHelperChar>();
        // Splits input string by space character
        String[] split = input.split(" ");

        for (int i = 0; i < split.length; i++) {
            // Parse int codepoint from string input
            int codePt = Integer.valueOf(split[i].substring(2),16).intValue();
            // and create EncodingHelperChar object, and add to ArrayList
            EncodingHelperChar newChar = new EncodingHelperChar(codePt);
            chars.add(newChar);
        }
        // Prints char representation for each in input string
        for (int i = 0; i < chars.size(); i++) {
            System.out.print(chars.get(i).toCharacter());
        }
        System.out.println();
    }

    public static void codepointToUtf8(String input) {
        // Creates ArrayList of char objects
        ArrayList<EncodingHelperChar> chars
                = new ArrayList<EncodingHelperChar>();
        // Splits input string by space character
        String[] split = input.split(" ");
        for (int i = 0; i < split.length; i++) {
            // Parse int codepoint from string input
            int codePt = Integer.valueOf(split[i].substring(2),16).intValue();
            // and create EncodingHelperChar object, and add to ArrayList
            EncodingHelperChar newChar = new EncodingHelperChar(codePt);
            chars.add(newChar);
        }
        // Prints utf8 string representations for each char
        for (int i = 0; i < chars.size(); i++) {
            System.out.print(chars.get(i).toUtf8String());
        }
        System.out.println();
    }

    public static void codepointToLang(String input) {
        // Creates ArrayList of char objects
        ArrayList<EncodingHelperChar> chars
                = new ArrayList<EncodingHelperChar>();
        // Splits input string by space character
        String[] split = input.split(" ");
        for (int i = 0; i < split.length; i++) {
            // Parse int codepoint from string input
            int codePt = Integer.valueOf(split[i].substring(2),16).intValue();
            // and create EncodingHelperChar object, and add to ArrayList
            EncodingHelperChar newChar = new EncodingHelperChar(codePt);
            chars.add(newChar);
        }
        // Prints chars and group names for each char
        for (int i = 0; i < chars.size(); i++) {
            System.out.println("Char " + (i + 1) + ": "
                    + chars.get(i).toCharacter()
                    + ", Group: " + chars.get(i).getGroupName());
        }
        System.out.println();
    }







    public static String[] argsAnalysis(String[] args) {
        String[] result = new String[]{null, null};
        for (int i=0; i < args.length; i++) {
            if (args[i].equals("-i") || args[i].equals("--input")) {
                if (i+1<args.length) { result[0] = args[i+1]; }
                else {
                    System.out.println("Wrong format. See usage information " +
                            "below:");
                    printUsage();
                }
            } else if (args[i].equals("-o") || args[i].equals("--output")) {
                if (i+1<args.length) { result[1] = args[i+1]; }
                else {
                    System.out.println("Wrong format. See usage information " +
                            "below:");
                    printUsage();
                }
            }
        }
        if (result[0] == null && result[1] == null) {
            System.out.println("Wrong format. See usage information "
                    + "below:");
            printUsage();
        }
        return result;
    }
}