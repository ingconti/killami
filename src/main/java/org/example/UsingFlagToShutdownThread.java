package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UsingFlagToShutdownThread extends Thread {
    private volatile boolean running = true;
    boolean done = false;
    public void run() {
        int i = 0;
        BufferedReader br = null;

        br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        boolean ready = false;

        while (running && !done) {
            try {
                ready = br.ready();
                 if (ready){
                     int n = br.read();
                     if (n!=10 && n!=13) {
                         char asciiToChar = (char) n;
                         System.out.println(asciiToChar);
                         s += asciiToChar;
                     }else{
                         done = true;
                     }
                 }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //System.out.println("."  + i + " " +  s);
            i++;
            System.out.flush();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {}
        } // while


        if (done){
            System.out.println( s + "\n");
        }else {
            System.out.println("Shutting down thread\n");
        };
    }
    public void shutdown() {
        running = false;
    }
    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("main started\n");
        UsingFlagToShutdownThread t = new UsingFlagToShutdownThread();
        t.start();
        Thread.sleep(5000);
        t.shutdown();
        System.out.println("main done\n");

    }
}