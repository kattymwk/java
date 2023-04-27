package com.example.labs.calculation;

import com.example.labs.models.TimeModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculation {

    public double findMin(List<Double> listOfTimeModel) {
        double min = 0;

        if (!listOfTimeModel.isEmpty()) {
            min = listOfTimeModel.stream().min(Double::compareTo).get();
        }
        return min;
    }

    public double findMax(List<Double> listOfTimeModel) {
        double max = 0;

        if (!listOfTimeModel.isEmpty()) {
            max = listOfTimeModel.stream().max(Double::compareTo).get();
        }
        return max;
    }

    public double findAverage(List<Double> listOfTimeModel) {
        double result = 0;

        if (!listOfTimeModel.isEmpty()) {
            result = listOfTimeModel.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        }
        return result;
    }


}
