package org.guidgenerator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class GuidHostnameGenerator {

    /**
     * Generates a unique key by combining a UUID and the hostname.
     */
    public static String generateKey() {
        String uuid = UUID.randomUUID().toString();
        String hostname = getHostName();
        return uuid + "-" + hostname;
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown-host";
        }
    }
}
