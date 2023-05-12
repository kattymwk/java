package com.example.labs.models;
import java.util.List;

public class Result {

    public List<Double> resultlist;

    public double max;
    public double min;
    public double avg;

    public Result(List<Double> resultlist, double max, double min, double avg){
        this.resultlist = resultlist;
        this.max = max;
        this.min = min;
        this.avg = avg;
    }
}
