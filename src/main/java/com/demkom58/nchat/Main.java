package com.demkom58.nchat;

import com.demkom58.nchat.client.Client;
import com.demkom58.nchat.server.Server;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
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
