package edu.carleton.bantat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by torebanta on 4/4/15.
 */
public class EncodingHelperCharTest {

    private int MAXIMUM = 0x10FFFF;
    private static int testCodePoint = 0x0480;
    private byte[] testByteCodePoint = {(byte) 130, (byte) 27};
    private char testChar = 'a';

    @Test
    public void EncodingHelperCharShouldTakeValidCodePoint() throws Exception {
        EncodingHelperChar testChar1 = new EncodingHelperChar(testCodePoint);
        assertTrue("Codepoint greater than valid hex range", testChar1.getCodePoint() <= MAXIMUM);
        assertTrue("Codepoint negative", testChar1.getCodePoint() >= 0);
    }

    @Test
    public void EncodingHelperCharShouldConvertFromByteToValidCodePoint() throws Exception {
        EncodingHelperChar testChar2 = new EncodingHelperChar(testByteCodePoint);
        assertTrue("Codepoint greater than valid hex range", testChar2.getCodePoint() <= MAXIMUM);
        assertTrue("Codepoint negative", testChar2.getCodePoint() >= 0);
    }

    @Test
    public void EncodingHelperCharShouldConvertFromCharToValidCodePoint() throws Exception {
        EncodingHelperChar testChar3 = new EncodingHelperChar(testChar);
        assertTrue("Codepoint greater than valid hex range", testChar3.getCodePoint() <= MAXIMUM);
        assertTrue("Codepoint negative", testChar3.getCodePoint() >= 0);
    }

    @Test
    public void testGetCodePointReturnsValidCodePoint() throws Exception {
        EncodingHelperChar testChar4 = new EncodingHelperChar(testChar);
        assertTrue("Codepoint greater than valid hex range", testChar4.getCodePoint() <= MAXIMUM);
        assertTrue("Codepoint negative", testChar4.getCodePoint() >= 0);
    }

    @Test
    public void testSetCodePointConversion() throws Exception {
        EncodingHelperChar testChar5 = new EncodingHelperChar(testCodePoint);
        testChar5.setCodePoint(0x0A07);
        assertEquals("Codepoint different than setCodePoint argument", 0x0A07, testChar5.getCodePoint());
    }

    @Test
    public void testToUtf8BytesReturnsNonNull() throws Exception {
        EncodingHelperChar testChar6 = new EncodingHelperChar(testChar);
        byte[] testByteArray = testChar6.toUtf8Bytes();
        assertFalse("Codepoint byte array is null", testByteArray == null);
    }

    @Test
    public void toUtf8BytesShouldHaveLength1to4() throws Exception {
        EncodingHelperChar testChar7 = new EncodingHelperChar(0x00F3);
        assertTrue("Invalid UTF8 character, too many bytes", (testChar7.toUtf8Bytes().length <= 4));
    }

    @Test
    public void toCodePointStringShouldHaveRightLength() throws Exception {
        EncodingHelperChar testChar8 = new EncodingHelperChar(testChar);
        assertTrue("Invalid code point, too short", (testChar8.toCodePointString().length() >= 6));
        assertTrue("Invalid code point, too long", (testChar8.toCodePointString().length() <= 8));
    }

    @Test
    public void testToCodePointStringReturnsValidHexadecimalString() throws Exception {
        EncodingHelperChar testChar9 = new EncodingHelperChar(testChar);
        String testString = testChar9.toCodePointString();
        boolean status = true;
        for (int i = 2; i < testString.length(); i++) {
            assertTrue("Character in String codepoint invalid",
                    (testString.charAt(i) >= 'A' && testString.charAt(i) <= 'F')
                            || (testString.charAt(i) >= '0'
                            && testString.charAt(i) <= '9'));
        }
    }

    @Test
    public void toUtf8StringShouldHaveRightLength() throws Exception {
        EncodingHelperChar testChar10 = new EncodingHelperChar(0x00F3);
        assertTrue("Invalid UTF8 string", (testChar10.toUtf8String().length() % 4 == 0));
        assertTrue("Invalid UTF8 string, too short", (testChar10.toUtf8String().length() >= 4));
        assertTrue("Invalid UTF8 string, too long", (testChar10.toUtf8String().length() <= 16));
    }

    @Test
    public void testGetCharacterNameReturnsNonNull() throws Exception {
        EncodingHelperChar testChar11 = new EncodingHelperChar(testChar);
        assertFalse("Character name is null", testChar11.getCharacterName() == null);
    }
}