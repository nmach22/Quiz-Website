package main.Manager;

import java.sql.PreparedStatement;

public class ChatMessage {

    private String userFrom;
    private String message;

    public ChatMessage(String userFrom, String message) {
        this.userFrom = userFrom;
        this.message = message;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

