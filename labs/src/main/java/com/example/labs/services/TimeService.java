package com.example.labs.services;

import com.example.labs.cache.Cache;
import com.example.labs.controllers.TimeController;
import com.example.labs.exceptions.BadArgumentsException;
import com.example.labs.exceptions.DivideException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Component
public class TimeService {

    private static final Logger logger = LogManager.getLogger(TimeController.class);
    private final Cache<List<Double>, Double> cache;

    public TimeService(Cache<List<Double>, Double> cache) {
        this.cache = cache;
    }


    public double calculate(double distance, double speed) throws DivideException {
        double time = distance / speed;
        if (Double.isInfinite(time) || Double.isNaN(time)) {
            throw new DivideException("Division by zero");
        }
        return time;
    }

    public void validate(double distance, double speed) throws BadArgumentsException {
        if (distance <= 0 || speed < 0) {
            throw new BadArgumentsException("Wrong parameters");
        }
        if(speed == 0) throw new DivideException("Divided by zero");
    }


    public double getTime(@RequestParam double distance, @RequestParam double speed) throws DivideException {
        double time;
        List<Double> cacheKey = new ArrayList<>();
        cacheKey.add(distance);
        cacheKey.add(speed);

        if (cache.contain(cacheKey)) {
            logger.info("Get from cache");
            time = cache.get(cacheKey);
        } else {
            logger.info("Calculate");
            time = calculate(distance, speed);
            logger.info("Push to cache");
            cache.push(cacheKey, time);
        }
        return time;
    }

}