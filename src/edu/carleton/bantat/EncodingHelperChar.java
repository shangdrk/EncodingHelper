package edu.carleton.bantat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character. 
 */
public class EncodingHelperChar {
    private int codePoint;

    public EncodingHelperChar(int codePoint) {
        this.codePoint = codePoint;
    }
    
    public EncodingHelperChar(byte[] utf8Bytes) {
        byte infoPuller_1 = 0b00111111;
        byte infoPuller_2 = 0b00011111;
        byte infoPuller_3 = 0b00001111;
        byte infoPuller_4 = 0b00000111;

        if (utf8Bytes.length == 1) {
            codePoint = Byte.toUnsignedInt(utf8Bytes[0]);
        } else if (utf8Bytes.length == 2) {
            codePoint  = (utf8Bytes[0] & infoPuller_1) +
                    (utf8Bytes[1] & infoPuller_2)* (int)Math.pow(2, 6);
        } else if (utf8Bytes.length == 3) {
            codePoint = (utf8Bytes[0] & infoPuller_1) +
                    (utf8Bytes[1] & infoPuller_1)* (int)Math.pow(2, 6) +
                    (utf8Bytes[2] & infoPuller_3)* (int)Math.pow(2, 12);
        } else {
            codePoint = (utf8Bytes[0] & infoPuller_1) +
                    (utf8Bytes[1] & infoPuller_1)* (int)Math.pow(2, 6) +
                    (utf8Bytes[2] & infoPuller_1)* (int)Math.pow(2, 12) +
                    (utf8Bytes[3] & infoPuller_4)* (int)Math.pow(2, 18);
        }

    }
    
    public EncodingHelperChar(char ch) {
        codePoint = (int) ch;
    }
    
    public int getCodePoint() {
        return this.codePoint;
    }
    
    public void setCodePoint(int codePoint) {
        this.codePoint = codePoint;
    }
    
    /**
     * Converts this character into an array of the bytes in its UTF-8
     * representation.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, whose UTF-8 form consists of the two-byte sequence C3 A9, then
     * this method returns a byte[] of length 2 containing C3 and A9.
     * 
     * @return the UTF-8 byte array for this character
     */
    public byte[] toUtf8Bytes() {
        //byte[] Utf8Bytes;
        //if (codePoint <= 0x0024) {
        //    Utf8Bytes = new byte[1];
        //} else if (codePoint > 0x0024 && codePoint <= 0x00a2) {
        //    Utf8Bytes = new byte[2];
        //} else if (codePoint > 0x00a2 && codePoint <= 0x20ac) {
        //    Utf8Bytes = new byte[3];
        //} else {
        //    Utf8Bytes = new byte[4];
        //}

        //byte[] codePointBytes new byte[]

        return null;
    }
    
    /**
     * Generates the conventional 4-digit hexadecimal code point notation for
     * this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string U+00E9 (no quotation marks in
     * the returned String).
     *
     * @return the U+ string for this character
     */
    public String toCodePointString() {
        String result = Integer.toHexString(codePoint);
        String zero = "0";
        while (result.length() < 4) {
            result = zero.concat(result);
        }
        String unicode = "U+";
        String hexString = unicode.concat(result);
        return hexString.toUpperCase();
    }
    
    /**
     * Generates a hexadecimal representation of this character suitable for
     * pasting into a string literal in languages that support hexadecimal byte
     * escape sequences in string literals (e.g. C, C++, and Python).
     *   For example, if this character is a lower-case letter e with an acute
     * accent (U+00E9), then this method returns the string \xC3\xA9. Note that
     * quotation marks should not be included in the returned String.
     *
     * @return the escaped hexadecimal byte string
     */
    public String toUtf8String() {
        // Not yet implemented.
        return "";
    }
    
    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getCharacterName() {
        String name = "Name Not Found";
        try {
            File unicodeData = new File("src/edu/carleton/bantat/" +
                    "UnicodeData.txt");
            Scanner unicodeScan = new Scanner(unicodeData);
            String pointer = this.toCodePointString().substring(2);
            boolean found = false;
            while (!found && unicodeScan.hasNext()) {
                String line = unicodeScan.nextLine();
                String[] split = line.split(";");
                if (split[0].equals(pointer)) {
                    found = true;
                    name = split[1];
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("UnicodeData file not found.");
            System.exit(1);
        }
        return name;
    }

}
