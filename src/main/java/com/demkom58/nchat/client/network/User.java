package com.demkom58.nchat.client.network;

public class User {
    private User() {
    }

    private static int messagesSent = 0;
    private static String name;

    public static void setName(String name) {
        User.name = name;
    }

    public static String getName() {
        return User.name;
    }

    public static int getMessagesSent() {
        return User.messagesSent;
    }

    public static void addSentMessage() {
        User.messagesSent += 1;
    }

}
