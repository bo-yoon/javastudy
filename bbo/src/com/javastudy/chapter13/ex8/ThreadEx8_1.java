package com.javastudy.chapter13.ex8;

public class ThreadEx8_1 extends Thread {
    public void run() {
        for(int i =0; i < 300; i++) {
            System.out.print("-");
            for(int x =0; x < 10000000; x++);
        }
    }
}
