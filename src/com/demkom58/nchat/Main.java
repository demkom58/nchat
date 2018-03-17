package com.demkom58.nchat;

import com.demkom58.nchat.client.Client;
import com.demkom58.nchat.server.Server;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static final int PORT = 55555;

    public static final String PROTOCOL_VERSION = "1.2b4";
    public static final String STYLING_VERSION = "1.1";
    public static final String APP_VERSION = "1.1 - SNAPSHOT";

    public static final int MAX_MESSAGE_LENGTH = 1000;

    public static final int CONNECTIONS_PER_IP = 3;
    public static final int MESSAGES_PER_SECOND = 3;
    public static final String DATA_PATH = System.getenv("APPDATA") + "/NChat/";

    public static void main(String[] as) throws Exception {
        List<String> args = Arrays.asList(as);

        if(args.contains("server")) startServer(args);
        else startClient(args);
    }

    private static void startClient(List<String> args) throws Exception {
        Client.start(args);
    }

    private static void startServer(List<String> args) throws Exception {
        Server.start(args);
    }

}
