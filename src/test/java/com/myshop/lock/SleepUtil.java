package com.myshop.lock;

public class SleepUtil {
    public static void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }
}
