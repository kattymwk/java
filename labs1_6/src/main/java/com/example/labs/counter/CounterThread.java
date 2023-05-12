package com.example.labs.counter;

public class CounterThread extends Thread{
    @Override
    public void run() {
        Counter.increment();
    }

}
