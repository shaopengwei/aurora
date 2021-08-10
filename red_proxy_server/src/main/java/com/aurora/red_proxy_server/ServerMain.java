package com.aurora.red_proxy_server;

import com.aurora.red_proxy_server.server.DiscardServer;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/10 11:43
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
        System.out.println("server:run()");
    }
}
