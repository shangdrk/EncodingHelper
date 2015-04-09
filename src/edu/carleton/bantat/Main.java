package edu.carleton.bantat;

import com.sun.media.jfxmedia.track.Track;

import java.nio.ByteBuffer;
import java.util.Arrays;

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

        //int test1 = 0b00111111;
        //int codePointTest = 0x2f1d;

        //byte[] byteArray = new byte[2];
        //byteArray[0] = (byte) codePointTest;
        //byteArray[1] = (byte) (codePointTest >> 8);

        //System.out.println(byteArray[0]);
        //System.out.println(byteArray[1]);

        //System.out.println(String.codePointAt);

        //EncodingHelperChar testChar = new EncodingHelperChar(0x0024);
        //System.out.println(Arrays.toString(testChar.toUtf8Bytes()));

        EncodingHelperChar test1 = new EncodingHelperChar(0xdddddd);
        System.out.println(test1.toUtf8String());


    }
}
