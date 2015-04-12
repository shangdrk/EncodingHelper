package edu.carleton.bantat;

/**
 * Created by torebanta on 4/12/15.
 */
public class Frontend {
    private Frontend() { /* Creates an empty constructor to avoid
     instantiating this class */ }

    public static void printUsage() {}

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
        for (int i=0; i<aString.length(); i++) {
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

    }

    public static void stringToCodepoint(String input) {

    }

    public static void stringToLang(String input) {

    }

    public static void utf8ToSummary(String input) {

    }

    public static void utf8ToString(String input) {

    }

    public static void utf8ToCodepoint(String input) {

    }

    public static void utf8ToLang(String input) {

    }

    public static void codepointToSummary(String input) {

    }

    public static void codepointToString(String input) {

    }

    public static void codepointToUtf8(String input) {

    }

    public static void codepointToLang(String input) {

    }







    public static String[] argsAnalysis(String[] args) {
        String[] result = new String[]{null, null};
        for (int i=0; i<args.length; i++) {
            if (args[i] == "-i" || args[i] == "--input") {
                if (i+1<args.length) { result[0] = args[i+1]; }
                else {
                    System.out.println("Wrong format. See usage information " +
                            "below:");
                    printUsage();
                }
            } else if (args[i] == "-o" || args[i] == "--output") {
                if (i+1<args.length) { result[1] = args[i+1]; }
                else {
                    System.out.println("Wrong format. See usage information " +
                            "below:");
                    printUsage();
                }
            }
        }
        return result;
    }
}
