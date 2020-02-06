package com.demkom58.nchat.client;

import joptsimple.OptionParser;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class Main {
    public static void main(@NotNull final String... args) {
        Client.start(new OptionParser().parse(args));
    }
}
