//package edu.carleton.bantat;

/**
 * Created by torebanta on 4/12/15.
 */
public class EncodingHelper {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("test");
            Frontend.printUsage();
        } else if (args.length == 1) {
            Frontend.stringToSummary(args[0]);
        }

        if (Frontend.argsAnalysis(args).length == 0) {
            System.out.println("Wrong format. See usage information below:");
            Frontend.printUsage();
        }
        int index = -1;
        String codePoints;
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == 'U' && args[i].charAt(1) == '+') {
                index = i;
                break;
            }
        }
        if (index != -1 && (args.length > index + 1)) {
            codePoints = args[index];
            for (int i = index + 1; i < args.length; i++) {
                codePoints = codePoints.concat(" ");
                codePoints = codePoints.concat(args[i]);
            }
            args[index] = codePoints;
//            int length = args.length;
//            for (int i = index + 1; i < length; i++) {
//                args[i] = null;
//            }
        }

        String inputType = Frontend.argsAnalysis(args)[0];
        String outputType = Frontend.argsAnalysis(args)[1];
        if (inputType == null && outputType != null) {
            switch (outputType) {
                case "string":
                    System.out.println(args[args.length-1]);
                    break;
                case "utf8":
                    Frontend.stringToUtf8(args[args.length - 1]);
                    break;
                case "codepoint":
                    Frontend.stringToCodepoint(args[args.length - 1]);
                    break;
                case "summary":
                    Frontend.stringToSummary(args[args.length - 1]);
                    break;
                case "lang":
                    Frontend.stringToLang(args[args.length-1]);
                    break;
                default:
                    System.out.println("Unknown output type. See usage " +
                            "information below:");
                    Frontend.printUsage();
                    break;
            }
        } else if (outputType == null && inputType != null) {
            switch (inputType) {
                case "string":
                    Frontend.stringToSummary(args[args.length - 1]);
                    break;
                case "utf8":
                    Frontend.utf8ToSummary(args[args.length - 1]);
                    break;
                case "codepoint":
                    if (index == -1) {
                        Frontend.codepointToSummary(args[args.length - 1]);
                    }
                    else {
                        Frontend.codepointToSummary(args[index]);
                    }
                    break;
                default:
                    System.out.println("Unknown input type. See usage " +
                            "information below:");
                    Frontend.printUsage();
                    break;
            }
        } else if (outputType != null && inputType != null) {
            switch (inputType + " " + outputType) {
                case "string string":
                    System.out.println(args[args.length-1]);
                    break;
                case "string utf8":
                    Frontend.stringToUtf8(args[args.length-1]);
                    break;
                case "string codepoint":
                    Frontend.stringToCodepoint(args[args.length - 1]);
                    break;
                case "string summary":
                    Frontend.stringToSummary(args[args.length-1]);
                    break;
                case "string lang":
                    Frontend.stringToLang(args[args.length - 1]);
                    break;
                case "utf8 string":
                    Frontend.utf8ToString(args[args.length - 1]);
                    break;
                case "utf8 utf8":
                    System.out.println(args[args.length-1]);
                    break;
                case "utf8 codepoint":
                    Frontend.utf8ToCodepoint(args[args.length - 1]);
                    break;
                case "utf8 summary":
                    Frontend.utf8ToSummary(args[args.length - 1]);
                    break;
                case "utf8 lang":
                    Frontend.utf8ToLang(args[args.length - 1]);
                    break;
                case "codepoint string":
                    if (index == -1) {
                        Frontend.codepointToString(args[args.length - 1]);
                    }
                    else {
                        Frontend.codepointToString(args[index]);
                    }
                    break;
                case "codepoint utf8":
                    if (index == -1) {
                        Frontend.codepointToUtf8(args[args.length - 1]);
                    }
                    else {
                        Frontend.codepointToUtf8(args[index]);
                    }
                    break;
                case "codepoint codepoint":
                    System.out.println(args[args.length-1]);
                    break;
                case "codepoint summary":
                    if (index == -1) {
                        Frontend.codepointToSummary(args[args.length - 1]);
                    }
                    else {
                        Frontend.codepointToSummary(args[index]);
                    }
                    break;
                case "codepoint lang":
                    if (index == -1) {
                        Frontend.codepointToLang(args[args.length - 1]);
                    }
                    else {
                        Frontend.codepointToLang(args[index]);
                    }
                    break;
                default:
                    System.out.println("Unknown input or output type. See usage " +
                            "information below");
                    Frontend.printUsage();
                    break;
            }
        }
    }
}