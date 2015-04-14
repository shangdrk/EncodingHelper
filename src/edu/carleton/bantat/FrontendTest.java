package edu.carleton.bantat;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dereks on 4/13/15.
 */
public class FrontendTest {

    @Test
    public void testArgsAnalysisReadsInput() throws Exception {
        String[] args = {"-i","string","--output","codepoint","test"};
        String[] expected = {"string","codepoint"};
        String[] actual = Frontend.argsAnalysis(args);
        Assert.assertArrayEquals("Failed: Arrays are not equal",
                expected, actual);
    }

    @Test
    public void testArgsAnalysisReadsReversedInput() throws Exception {
        String[] args = {"-o","utf8","--input","string","test"};
        String[] expected = {"string","utf8"};
        String[] actual = Frontend.argsAnalysis(args);
        Assert.assertArrayEquals("Failed: Arrays are not equal",
                expected, actual);
    }
}