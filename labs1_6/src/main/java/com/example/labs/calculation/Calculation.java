package com.example.labs.calculation;

import com.example.labs.models.TimeModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculation {

    public double findMin(List<Double> listOfTimes) {
        double min = 0;

        if (!listOfTimes.isEmpty()) {
            min = listOfTimes.stream().min(Double::compareTo).get();
        }
        return min;
    }

    public double findMax(List<Double> listOfTimes) {
        double max = 0;

        if (!listOfTimes.isEmpty()) {
            max = listOfTimes.stream().max(Double::compareTo).get();
        }
        return max;
    }

    public double findAverage(List<Double> listOfTimes) {
        double result = 0;

        if (!listOfTimes.isEmpty()) {
            result = listOfTimes.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        }
        return result;
    }

}
