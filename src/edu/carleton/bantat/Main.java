package edu.carleton.bantat;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, graders!");
        System.out.println("Are you thoroughly entertained by this code?");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("No? Really? How about a cool dog to look at...");
        System.out.println("          __");
        System.out.println(" \\ ______/ V`-,");
        System.out.println("  }        /~~");
        System.out.println(" /_)----,r'");
        System.out.println(" b       b");
        System.out.println();
        System.out.println("Still not entertained? Alright, I give up");

        byte[] arr = new byte[]{(byte)0xF8, (byte)0x3F};
        System.out.println(String.format("%x", 10));
        //int test1 = 0b00111111;
        //int codePointTest = 0x2f1d;

        //byte[] byteArray = new byte[2];
        //byteArray[0] = (byte) codePointTest;
        //byteArray[1] = (byte) (codePointTest >> 8);

        //System.out.println(byteArray[0]);
        //System.out.println(byteArray[1]);

        //System.out.println(String.codePointAt);
        byte[] input = new byte[] {(byte)
                0x88, (byte)0x8d,(byte)0x90, (byte)0xf0};
        EncodingHelperChar test2 = new EncodingHelperChar(input);
        System.out.println(test2.getCodePoint() + ", actual: " + 0x10348);

        byte [] bytes = ByteBuffer.allocate(4).putInt(17291729).array();
        System.out.println(Arrays.toString(bytes));
    }
}
