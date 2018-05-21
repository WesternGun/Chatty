package com.westerngun.Chatty;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class TestChatty {
    @Parameter(0)
    public String input;
    public User alice;
    public User bob;
    
    private Chatty app;
    
    public TestChatty() {
        this.app = new Chatty();
    }
    
    @Parameters
    public static Collection follow() {
        return Arrays.asList(new Object[][] {
            {"Alice follows Bob"},
            {"Alice -> I am Alice"}
        });
    }
    
    @Test
    public void testUserFollowed() {
        //Assume: ignore some test input
        Assume.assumeTrue("Input: " + input + "; not of type \"follow\", ignore!", input.split(" ").length == 3 && input.contains("follows"));
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        app.readInput(in);
        alice = app.getUserByName("Alice");
        bob = app.getUserByName("Bob");
        assertTrue(alice.getFollowings().contains(bob));
    }
    
    @Test
    public void testPublishedSomeTweets() {
        //Assume: ignore some test input
        Assume.assumeTrue("Input: " + input + "; Not of type \"publish\", ignore!", input.contains("->"));
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        app.readInput(in);
        alice = app.getUserByName("Alice");
        boolean contains = false;
        for (Tweet t: alice.getTweets()) {
            if (t.getContent().equals(Util.getContentFromInput(input))) {
                 contains = true;
            }
        }
        assertTrue(contains);
    }
}
