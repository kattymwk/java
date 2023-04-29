package com.example.labs;

import com.example.labs.cache.Cache;
import com.example.labs.exceptions.BadArgumentsException;
import com.example.labs.exceptions.DivideException;
import com.example.labs.services.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


class TimeModelControllerTest {

    private  Cache<List<Double>, Double> cache;

    @Test
    void testCalculateTime() throws DivideException {
        cache = new Cache<>();
        TimeService timeService = new TimeService(cache);
        double distance = 100;
        double speed = 50;
        double result = timeService.calculate(distance, speed);
        Assertions.assertEquals(2.0, result,1e-0);
    }

    @Test
    void testCalculateTimeInvalidInput() {
        TimeService timeService = new TimeService(cache);
        assertThrows(BadArgumentsException.class, () -> {
            timeService.validate(100, -1);
        });
    }

    @Test
    void testCalculateTimeDivisionByZero() {
        TimeService timeService = new TimeService(cache);
        assertThrows(DivideException.class, () -> {
            timeService.calculate(100, 0);
        });
    }
}
