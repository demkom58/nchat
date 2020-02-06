package com.demkom58.nchat.server;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import joptsimple.OptionParser;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@UtilityClass
public class Main {
    public static void main(@NotNull final String... args) throws Exception {
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);

        final OptionParser optionParser = new OptionParser();

        optionParser.acceptsAll(Arrays.asList("h", "host")).withRequiredArg();
        optionParser.acceptsAll(Arrays.asList("p", "port")).withRequiredArg();
        optionParser.accepts("help").forHelp();

        Server.start(optionParser.parse(args));
    }
}
