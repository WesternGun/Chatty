package com.westerngun.Chatty;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Chatty {
    private String line;
    private Set<User> users;
    
    public Chatty() {
        this.users = new HashSet<>();
    }
    
    public User getUserByName(String username) {
        for (User u: users) {
            if (u.getName().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }
    public int readInput(InputStream in) {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.equalsIgnoreCase("exit")) {
                return -1;
            }
            String[] splits = line.split(" ");
            if (splits.length == 1) { //check his/her posts
                reading(splits[0]);
            } else if (splits.length == 2) { //check his/her timeline
                wall(splits[0]);
            } else if (splits.length >= 3) {
                if (splits[1].equals("->")) {
                    posting(splits[0], Util.getContentFromInput(line));
                } else if (splits[1].equals("follows")) {
                    following(splits[0], splits[2]);
                }
            }
        }
        return 0;
    }
    
    private void reading(String username) {
        User current = containsUser(username);
        if (current != null) {
            List<Tweet> tweets = current.getOrderedTweets();
            if (tweets != null) {
                for (Tweet t: tweets) {
                    showTweet(t);
                }
            } else {
                System.out.println(); //no posts, new line
            }
        } else {
            System.out.println(); //no posts, new line
        }
    }
    /* shows your tweets and those who you follow */
    private void wall(String username) {
        User current = containsUser(username);
        if (current != null) {
            List<Tweet> allTweets = new ArrayList<>();
            List<User> following = current.getFollowings();
            
            for (User u: following) {
                allTweets.addAll(u.getTweets());
            }
            
            allTweets.addAll(current.getTweets());
            
            List<Tweet> allTweetsInOrder = allTweets.stream().sorted(Comparator.comparing(Tweet::getDate).reversed()).collect(Collectors.toList());
            for (Tweet t: allTweetsInOrder) {
                showTweet(t);
            }
            
        } else {
            noTweets();
        }
    }
    
    private void posting(String username, String tweet) {
        Tweet newTweet = new Tweet();
        newTweet.setContent(tweet);
        newTweet.setDate(LocalDateTime.now());
        User current = containsUser(username);
        if (current != null) {
            newTweet.setUser(current);
            current.addTweet(newTweet);
        } else {
            User newUser = new User();
            newUser.setName(username);
            users.add(newUser);
            newTweet.setUser(newUser);
            newUser.addTweet(newTweet);
        }
        showTweet(newTweet);
    }
    
    private void following(String user1, String user2) {
        User user1obj = containsUser(user1);
        if (user1obj == null) {
            user1obj = new User();
            user1obj.setName(user1);
            users.add(user1obj);
        }
        User user2obj = containsUser(user2);
        if (user2obj == null) {
            user2obj = new User();
            user2obj.setName(user2);
            users.add(user2obj);
        }
        List<User> following = user1obj.getFollowings();
        if (!following.contains(user2obj)) {
            following.add(user2obj);
        }
    }
    private void showTweet(Tweet tweet) {
        System.out.println(tweet.getContent() + " (" + Util.dateDiff(tweet.getDate(), LocalDateTime.now()) + ")");
    }
    
    private void noTweets() {
        System.out.println();
    }
    
    private User containsUser(String username) {
        if (users == null) {
            return null;
        } else if (users.isEmpty()) {
            return null;
        } else {
            User current = null;
            for (User u: users) {
                if (u.getName().equalsIgnoreCase(username)) {
                    current = u;
                    break;
                }
            }
            return current;
        }
    }
}
