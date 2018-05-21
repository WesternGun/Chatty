package com.westerngun.Chatty;

import java.time.LocalDateTime;
import java.util.Date;

public class Tweet {
    private String content;
    private LocalDateTime date;
    private User user;
    protected LocalDateTime getDate() {
        return date;
    }
    protected void setDate(LocalDateTime date) {
        this.date = date;
    }
    protected User getUser() {
        return user;
    }
    protected void setUser(User user) {
        this.user = user;
    }
    
    protected String getContent() {
        return content;
    }
    protected void setContent(String content) {
        this.content = content;
    }
    public Tweet() {
        
    }
    
}
