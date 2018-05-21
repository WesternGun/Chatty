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
public class TestChattyIntegrated {
    @Parameter(0)
    public String[] inputs;
    
    public User alice;
    public User bob;
    
    private Chatty app;
    
    public TestChattyIntegrated() {
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
            {new String[] {"Alice follows Bob","Alice -> I am Alice", "Alice"}},
        });
    }
    
    @Test
    public void testUserFollowed() {
        for (String input: inputs) {
            if (input.split(" ").length == 3 && input.contains("follows")) {
                ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
                app.readInput(in);
                alice = app.getUserByName("Alice");
                bob = app.getUserByName("Bob");
                assertTrue(alice.getFollowings().contains(bob));
            } else if (input.contains("->")) {
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
            } else if (input.split(" ").length == 1) {
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
    }
    
}
