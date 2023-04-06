package com.p.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {

        String host = "localhost";
//      if (args.length > 0) {
//         host = args[0];
//      }
//      for (int i = 0; i < 1024; i++) {
//         checkPort(host,i);
//      }
        int port = 8989;
        boolean available = checkPortRunning(host, port);
        System.out.println("port : " + port + (available ? " is running." : " is not running"));
        if (available)
            taskkill(port);
    }

    private static boolean checkPortRunning(String host, int i) {
        Socket Skt;
        boolean running = false;
        try {
            System.out.println("Looking for " + i);
            Skt = new Socket(host, i);
            System.out.println("There is a server on port " + i + " of " + host);
            running = true;
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException occured" + e);
        } catch (IOException e) {
            System.out.println("IOException occured" + e);
        }
        return running;
    }

    private static void taskkill(int port) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("cmd /c netstat -ano | findstr " + port);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));
            String s = null;
            if ((s = stdInput.readLine()) != null) {
                int index = s.lastIndexOf(" ");
                String sc = s.substring(index, s.length());

                rt.exec("cmd /c Taskkill /PID" + sc + " /T /F");

            }
            System.out.println("Server Stopped");
        } catch (Exception e) {
            System.out.println("Something Went wrong with server");
        }
    }
}