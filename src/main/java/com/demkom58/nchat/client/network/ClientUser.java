package com.demkom58.nchat.client.network;

public class ClientUser {
    private ClientUser() {
    }

    private static int messagesSent = 0;
    private static String name;

    public static void setName(String name) {
        ClientUser.name = name;
    }

    public static String getName() {
        return ClientUser.name;
    }

    public static int getMessagesSent() {
        return ClientUser.messagesSent;
    }

    public static void addSentMessage() {
        ClientUser.messagesSent += 1;
    }

}
