package com.westerngun.Chatty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class TestChattyUnit {
    @Parameter(0)
    public String input;
    public User alice;
    public User bob;
    
    private Chatty app;
    
    public TestChattyUnit() {
        this.app = new Chatty();
    }
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }
    
    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
    @Parameters
    public static Collection follow() {
        return Arrays.asList(new Object[][] {
            {"Alice follows Bob"},
            {"Alice -> I am Alice"},
            {"Alice"},
            {"Bob"}
        });
    }
    
    @Test
    public void testUserFollowed() {
        //Assume: ignore some test input if is not type "follow"(like "Alice follows Bob")
        Assume.assumeTrue("Input: " + input + "; not of type \"follow\", ignore!", input.split(" ").length == 3 && input.contains("follows"));
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        app.readInput(in);
        alice = app.getUserByName("Alice");
        bob = app.getUserByName("Bob");
        assertTrue(alice.getFollowings().contains(bob));
    }
    
    @Test
    public void testPublishedSomeTweets() {
        //Assume: ignore some test input if is not type "publish"(like "Alice -> Today is a nice day"); CAUTION: the space can be more than 2!
        Assume.assumeTrue("Input: " + input + "; Not of type \"publish\", ignore!", input.contains("->"));
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        app.readInput(in);
        alice = app.getUserByName("Alice");
        boolean contains = false;
        for (Tweet t: alice.getTweets()) {
            if (t.getContent().equals(Util.getContentFromInput(input))) {
                 contains = true;
                 break;
            }
        }
        assertTrue(contains);
    }
    
    @Test
    public void testReadingTweets() {
        //Assume: ignore some test input if is not type "read"(like "Alice")
        Assume.assumeTrue("Input: " + input + "; Not of type \"read\", ignore!", input.split(" ").length == 1);
        
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        app.readInput(in);
        alice = app.getUserByName(input);
        if (alice == null) {
            assertNull("The user " + input + " hasn't been created. ", alice);
            return;
        }
        boolean read = true;
        if (alice.getTweets().isEmpty()) {
            assertTrue("The user " + input + " hasn't published any tweets. ", alice.getTweets().isEmpty());
            return;
        }
        for (Tweet t: alice.getTweets()) {
            if (!out.toString().contains(t.getContent())) {
                read = false;
                break;
            }
        }
        assertTrue(read);
    }
}
