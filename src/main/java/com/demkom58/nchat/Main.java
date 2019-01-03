package com.demkom58.nchat;

import com.demkom58.nchat.client.Client;
import com.demkom58.nchat.server.Server;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.Arrays;

public class Main {
    public static final String HOST = "localhost";
    public static final int PORT = 55555;

    public static final String PROTOCOL_VERSION = "1.2b4";
    public static final String STYLING_VERSION = "1.1";
    public static final String APP_VERSION = "1.1 - SNAPSHOT";

    public static final int MAX_MESSAGE_LENGTH = 1000;
    public static final String STANDARD_IP = "localhost";

    public static final int CONNECTIONS_PER_IP = 3;
    public static final int MESSAGES_PER_SECOND = 3;
    public static final String DATA_PATH = System.getenv("APPDATA") + "/NChat/";
    public static final String STYLES_PATH = DATA_PATH + "styles/";

    public static void main(String[] args) throws Exception {
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);

        final OptionParser optionParser = new OptionParser();

        optionParser.acceptsAll(Arrays.asList("s", "server"));
        optionParser.acceptsAll(Arrays.asList("h", "host")).availableIf("server").withRequiredArg();
        optionParser.acceptsAll(Arrays.asList("p", "port")).availableIf("server").withRequiredArg();
        optionParser.accepts("help").forHelp();

        final OptionSet optionSet = optionParser.parse(args);

        if(optionSet.has("server"))
            startServer(optionSet);
        else
            startClient(optionSet);

    }

    private static void startClient(OptionSet optionSet) {
        Client.start(optionSet);
    }

    private static void startServer(OptionSet optionSet) throws Exception {
        Server.start(optionSet);
    }

}
