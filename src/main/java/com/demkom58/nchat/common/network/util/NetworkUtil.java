package com.demkom58.nchat.common.network.util;

import io.netty.channel.Channel;

public class NetworkUtil {
    public static String getAddress(Channel ch) {
        StringBuilder address = new StringBuilder(ch.remoteAddress().toString());
        address.deleteCharAt(0);
        return address.toString();
    }
}
