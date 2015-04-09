package edu.carleton.bantat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Tore Banta
 * @author Derek Shang
 */

/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character. 
 */
public class EncodingHelperChar {
    private int codePoint;

    public EncodingHelperChar(int codePoint) {
        if (codePoint > 0x1fffff || codePoint < 0) {
            throw new IllegalArgumentException("code point out of range");
        }
        this.codePoint = codePoint;
    }
    
    public EncodingHelperChar(byte[] utf8Bytes) {
        if (utf8Bytes.length > 4) {
            throw new IllegalArgumentException("Too many bytes in this array");
        }
        int tempCodePoint = 0;

        final byte infoPuller_1 = 0b00111111;
        final byte infoPuller_2 = 0b00011111;
        final byte infoPuller_3 = 0b00001111;
        final byte infoPuller_4 = 0b00000111;

        if (utf8Bytes.length == 1) {
            tempCodePoint = Byte.toUnsignedInt(utf8Bytes[0]);
        } else if (utf8Bytes.length == 2) {
            tempCodePoint  = ((byte)(utf8Bytes[0] & infoPuller_2)<<6) |
                    (byte)(utf8Bytes[1] & infoPuller_1);
        } else if (utf8Bytes.length == 3) {
            tempCodePoint = ((byte)(utf8Bytes[0] & infoPuller_3)<<12)|
                    ((byte)(utf8Bytes[1] & infoPuller_1)<<6)|
                    (byte)(utf8Bytes[2] & infoPuller_1);
        } else {
            tempCodePoint = ((byte)(utf8Bytes[0] & infoPuller_4)<<18) |
                    ((byte)(utf8Bytes[1] & infoPuller_1)<<12) |
                    ((byte)(utf8Bytes[2] & infoPuller_1)<<6) |
                    (byte)(utf8Bytes[3] & infoPuller_1);
        }

        if (tempCodePoint > 0x1fffff) {
            throw new IllegalArgumentException("Code point specified by the " +
                    "array is undefined");
        }
        codePoint = tempCodePoint;

    }
    
    public EncodingHelperChar(char ch) {
        codePoint = (int) ch;
    }
    
    public int getCodePoint() {
        return this.codePoint;
    }
    
    public void setCodePoint(int codePoint) {
        if (codePoint < 0 || codePoint > 0x1fffff) {
            throw new IllegalArgumentException("Invalid code point");
        }
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
        final byte infoPuller_1 = 0b00111111;
        final byte infoPuller_2 = (byte) 0b11000000;
        final byte infoPuller_3 = (byte) 0b10000000;
        final byte infoPuller_4 = (byte) 0b11100000;
        final byte infoPuller_5 = (byte) 0b11110000;

        byte[] Utf8Bytes;

        if (codePoint <= 0x0024) {
            Utf8Bytes = new byte[]{(byte)codePoint};
        } else if (codePoint > 0x0024 && codePoint <= 0x00a2) {
            Utf8Bytes = new byte[2];
            Utf8Bytes[0] = (byte)(infoPuller_2 | (codePoint >> 6));
            Utf8Bytes[1] = (byte)(infoPuller_3 | (codePoint & infoPuller_1));
        } else if (codePoint > 0x00a2 && codePoint <= 0x20ac) {
            Utf8Bytes = new byte[3];
            Utf8Bytes[0] = (byte)(infoPuller_4 | (codePoint >> 12));
            Utf8Bytes[1] = (byte)(infoPuller_3 | ((codePoint >> 6) &
                    infoPuller_1));
            Utf8Bytes[2] = (byte)(infoPuller_3 | (codePoint & infoPuller_1));
        } else {
            Utf8Bytes = new byte[4];
            Utf8Bytes[0] = (byte)(infoPuller_5 | (codePoint >> 18));
            Utf8Bytes[1] = (byte)(infoPuller_3 | ((codePoint >> 12) &
                    infoPuller_1));
            Utf8Bytes[2] = (byte)(infoPuller_3 | (codePoint >> 6) &
                    infoPuller_1);
            Utf8Bytes[3] = (byte)(infoPuller_3 | (codePoint & infoPuller_1));
        }

        return Utf8Bytes;
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
        String hexString = String.format("U+%04X", codePoint);
        return hexString;
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
        String utf8String = "";
        byte[] utf8Bytes = this.toUtf8Bytes();
        for (byte byteItem : utf8Bytes) {
            if (utf8String == "") {
                utf8String = String.format("\\x%02X", byteItem);
            } else {
                utf8String = utf8String.concat(String.format("\\x%02X",
                        byteItem));
            }
        }

        return utf8String;
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
        String name = "<unknown> ";

        try {
            File unicodeData = new File("src/edu/carleton/bantat/" +
                    "UnicodeData.txt");
            Scanner unicodeScan = new Scanner(unicodeData);
            String pointer = this.toCodePointString().substring(2);
            boolean found = false;
            if (codePoint <= 0x001F) {
                while (!found && unicodeScan.hasNext()) {
                    String line = unicodeScan.nextLine();
                    String[] split = line.split(";");
                    if (split[0].equals(pointer)) {
                        found = true;
                        name = split[1] + " ";
                        name = name.concat(split[10]);
                    }
                }
            } else {
                while (!found && unicodeScan.hasNext()) {
                    String line = unicodeScan.nextLine();
                    String[] split = line.split(";");
                    if (split[0].equals(pointer)) {
                        found = true;
                        name = split[1];
                    }
                }
                if (!found) {
                    name = name.concat(toCodePointString());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("UnicodeData file not found.");
            System.exit(1);
        }
        return name;
    }

}
