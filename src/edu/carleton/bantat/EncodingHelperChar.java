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
    // CodePoint is the the key information we need to perform all sorts of
    // conversions
    private int codePoint;

    // Constructor version 1: directly takes an integer as codePoint
    public EncodingHelperChar(int codePoint) {
        // Handles exception
        if (codePoint > 0x1fffff || codePoint < 0) {
            throw new IllegalArgumentException("code point out of range");
        }
        this.codePoint = codePoint;
    }

    // Constructor version 2: takes a utf-8 byte array as input and convert
    // it to codePoint
    public EncodingHelperChar(byte[] utf8Bytes) {
        // handles exception
        if (utf8Bytes.length > 4) {
            throw new IllegalArgumentException("Too many bytes in this array");
        }
        // creates a temporary value to store a candidate code point integer.
        // If this value is later found to be illegal, will be discarded.
        int tempCodePoint;

        // defines several bit masks to perform the utf8 - unicode conversion.
        final byte infoPuller_1 = 0b00111111;
        final byte infoPuller_2 = 0b00011111;
        final byte infoPuller_3 = 0b00001111;
        final byte infoPuller_4 = 0b00000111;

        // the utf8 - unicode conversion algorithm
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

        // handles another exception
        if (tempCodePoint > 0x1fffff) {
            throw new IllegalArgumentException("Code point specified by the " +
                    "array is undefined");
        }
        codePoint = tempCodePoint;

    }
    
    // Constructor version 3: takes a character and stores its value as
    // codePoint.
    public EncodingHelperChar(char ch) {
        codePoint = (int) ch;
    }
    
    public int getCodePoint() {
        return this.codePoint;
    }
    
    public void setCodePoint(int codePoint) {
        // handles exception
        if (codePoint < 0 || codePoint > 0x1fffff) {
            throw new IllegalArgumentException("Invalid code point");
        }
        this.codePoint = codePoint;
    }

    /**
     * Convert the integer code point to its character representation.
     * @return the character representation of the code point
     */
    public char toCharacter() {
        return Character.toChars(codePoint)[0];
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
        // defines several bit masks to perform the unicode - utf8 conversion.
        final byte infoPuller_1 = 0b00111111;
        final byte infoPuller_2 = (byte) 0b11000000;
        final byte infoPuller_3 = (byte) 0b10000000;
        final byte infoPuller_4 = (byte) 0b11100000;
        final byte infoPuller_5 = (byte) 0b11110000;

        // declares the return value
        byte[] Utf8Bytes;

        // the unicode - utf8 conversion algorithm
        // the codePoint ranges that determine the length of Utf8Bytes come
        // from Wikipedia page "UTF-8": http://en.wikipedia.org/wiki/UTF-8
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
        // Uses format() method of String to produce U+ representation of int
        // codepoint with length >= 4, padded with zeroes
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
        // initializes the return value
        String utf8String = "";
        // converts the codePoint to utf8 byte array, which is later
        // converted to a string of the given format.
        byte[] utf8Bytes = this.toUtf8Bytes();
        for (byte byteItem : utf8Bytes) {
            // handles the first byte in case
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
        //vars 'name' starts as unknown, in case codepoint not assigned char
        String name = "<unknown> ";

        try {
            // Load 'UnicodeData.txt' from relative path, construct File and
            // Scanner objects
            File unicodeData = new File("src/edu/carleton/bantat/" +
                    "UnicodeData.txt");
            Scanner unicodeScan = new Scanner(unicodeData);
            // Create pointer vars from codepoint string representation for use
            // in searching for name, boolean to record if search was successful
            String pointer = this.toCodePointString().substring(2);
            boolean found = false;
            // Duplicate search algorithm in if() statement for special
            // '<control>' characters
            if (codePoint <= 0x001F) {
                while (!found && unicodeScan.hasNext()) {
                    String line = unicodeScan.nextLine();
                    String[] split = line.split(";");
                    // When found, concatenates name ('<control>') to actual
                    // name in index [10]
                    if (split[0].equals(pointer)) {
                        found = true;
                        name = split[1] + " ";
                        name = name.concat(split[10]);
                    }
                }
            }
            else {
                // Scans txt file while name has not been found yet
                while (!found && unicodeScan.hasNext()) {
                    //Splits each line by semi-colon, if index [0] matches
                    // vars pointer, reassigns name to appropriate string, and
                    // sets found to true
                    String line = unicodeScan.nextLine();
                    String[] split = line.split(";");
                    if (split[0].equals(pointer)) {
                        found = true;
                        name = split[1];
                    }
                }
                // (Since all codepoint values are used for <control>
                // characters, this if statement for unassigned codepoints is
                // only in second while loop) If name wasn't found,
                // concatenates unicode string representation to '<unknown> '
                // string
                if (!found) {
                    name = name.concat(toCodePointString());
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("UnicodeData file not found.");
            System.exit(1);
        }
        // Returns string name if found, and '<unknown> + UnicodeString' if not
        return name;
    }

    /**
     * Generates the official Unicode grouping for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "Latin-1 Supplement"
     * (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getGroupName() {
        // vars 'name' starts as unknown, in case codepoint not assigned char
        String name = "Group not found for ";

        try {
            // Load 'UnicodeData.txt' from relative path, construct File and
            // Scanner objects
            File unicodeData = new File("src/edu/carleton/bantat/" +
                    "lang.txt");
            Scanner unicodeScan = new Scanner(unicodeData);
            // Create pointer vars from codepoint string representation for use
            // in searching for group, boolean to record if search was
            // successful
            String pointer = this.toCodePointString().substring(2);
            boolean found = false;
            // Scans txt file while group has not been found yet
            while (!found && unicodeScan.hasNext()) {
                // Splits each line by semi-colon, if index [0] less than
                // vars pointer and index [1] greater than pointer,
                // reassigns name to appropriate string, and
                // sets found to true
                String line = unicodeScan.nextLine();
                String[] split = line.split(";");
                String rangeStartStr = split[0];
                String rangeEndStr = split[1];
                int rangeStart = Integer.valueOf(rangeStartStr, 16)
                        .intValue();
                int rangeEnd = Integer.valueOf(rangeEndStr, 16).intValue();
                if ((codePoint >= rangeStart) && (codePoint <= rangeEnd)) {
                    found = true;
                    name = split[2];
                }
            }
            if (!found) {
                name = name.concat(toCodePointString());
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("UnicodeData file not found.");
            System.exit(1);
        }
        // Returns string name if found, and '<unknown> + UnicodeString' if not
        return name;
    }

}
