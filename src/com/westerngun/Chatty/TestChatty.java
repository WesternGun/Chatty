package com.westerngun.Chatty;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
    @Parameter(1)
    public static User alice;
    @Parameter(2)
    public static User bob;
    
    private static Chatty app;
    
    public TestChatty() {
        this.app = new Chatty();
    }
    
    @Parameters(name="{index}: user {0} should follow user {1}")
    public static Collection follow() {
        alice = app.getUserByName("Alice");
        bob = app.getUserByName("Bob");
        return Arrays.asList(new Object[][] {
            {"Alice follows Bob", alice, bob}
        });
    }
    
    @Test
    public void testUserFollowed() {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        app.readInput(in);
        assertTrue(alice.getFollowings().contains(bob));
    }
}
