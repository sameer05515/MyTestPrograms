package com.p.socket;

import java.io.IOException;
import java.net.*;

public class CheckSocket {
    public static void main(String[] args) {
        int port=8989;
//        boolean available=available(port);
        boolean available=isTcpPortAvailable(port);

        System.out.println("port : "+port+(available?" is in use.":"is available"));
    }

//    private static boolean available(int port) {
//        try (Socket ignored = new Socket("localhost", port)) {
//            return false;
//        } catch (IOException ignored) {
//            return true;
//        }
//    }

    public static boolean isTcpPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            // setReuseAddress(false) is required only on OSX,
            // otherwise the code will not work correctly on that platform
            serverSocket.setReuseAddress(false);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port), 1);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
