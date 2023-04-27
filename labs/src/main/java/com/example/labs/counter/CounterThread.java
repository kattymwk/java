package com.example.labs.counter;

public class CounterThread extends Thread{
    @Override
    public void start() {
        Counter.increment();
    }

}
