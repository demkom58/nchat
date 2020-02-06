package com.demkom58.nchat.common.network.util;

import io.netty.util.CharsetUtil;

import java.util.Objects;

public class StringUtil {
    /**
     * Calculates the byte-size of a string in UTF-8 encoding.
     * Thanks to
     * <a href="http://stackoverflow.com/questions/8511490/calculating-length-in-utf-8-of-java-string-without-actually-encoding-it">McDowell</a>
     *
     * @param sequence CharSequence to calculate the size of.
     * @return Byte-size of sting in UTF-8
     */
    public static int getUtf8Size(CharSequence sequence) {
        int count = 0;
        for (int i = 0, len = sequence.length(); i < len; i++) {
            char ch = sequence.charAt(i);
            if (ch <= 0x7F) count++;
            else if (ch <= 0x7FF) count += 2;
            else if (Character.isHighSurrogate(ch)) {
                count += 4;
                ++i;
            } else count += 3;
        }
        return count;
    }

    /**
     * Gets the bytes of a String encoded in UTF-8. If the String is null,
     * a byte[] with the length of zero is returned.
     *
     * @param string String to encode
     * @return Bytes of the String encoded in UTF-8; zero length if null
     */
    public static byte[] getUtf8Bytes(String string) {
        if (string == null) return new byte[]{};
        else return string.getBytes(CharsetUtil.UTF_8);
    }

    /**
     * Opposite of {@link #getUtf8Bytes(String)}
     * Returns null if length is zero
     *
     * @param bytes string in bytes
     * @return null if length is zero or if input is null
     */
    public static String getUtf8String(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;

        return new String(bytes, CharsetUtil.UTF_8);
    }

    public static boolean compare(String str1, String str2) {
        return (Objects.equals(str1, str2));
    }

    private StringUtil() {
    }
}
