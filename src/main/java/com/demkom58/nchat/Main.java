package com.demkom58.nchat;

import joptsimple.OptionParser;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        final OptionParser optionParser = new OptionParser();
        optionParser.acceptsAll(Arrays.asList("s", "server"));

        if (optionParser.parse(args).has("server"))
            com.demkom58.nchat.server.Main.main(args);
        else
            com.demkom58.nchat.client.Main.main(args);
    }

}
