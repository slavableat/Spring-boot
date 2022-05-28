package com.example.springboot.Service.accessCounter;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccessCounterService {
    private int counter = 0;
    private final Lock lock;

    public AccessCounterService() {
        this.lock = new ReentrantLock();
    }

    public int incrementAccessCounterAndGetResult(){
        this.lock.lock();
        this.counter++;
        int result = this.counter;
        this.lock.unlock();
        return result;
    }
}
