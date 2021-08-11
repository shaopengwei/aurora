package com.aurora.red_netty_client;

import com.aurora.red_netty_client.client.Client;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-10 23:19
 */
public class ClientMain {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: " + Client.class.getSimpleName() + " <host> <port>");
            return;
        }
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        new Client(host, port).run();
    }
}
