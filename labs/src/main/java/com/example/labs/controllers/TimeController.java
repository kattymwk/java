package com.example.labs.controllers;

import com.example.labs.async.ResultAsync;
import com.example.labs.calculation.Calculation;
import com.example.labs.counter.Counter;
import com.example.labs.counter.CounterThread;
import com.example.labs.exceptions.BadArgumentsException;
import com.example.labs.exceptions.DivideException;
import com.example.labs.exceptions.ErrorResponse;
import com.example.labs.models.Result;
import com.example.labs.models.TimeModel;
import com.example.labs.services.TimeControllerService;
import com.example.labs.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TimeController {

    private static final Logger logger = LogManager.getLogger(TimeController.class);
    private final TimeService timeService;
    private final TimeModel timeModel;
    private final Calculation calculation;

    private final TimeControllerService timeControllerService;

    private final ResultAsync resultAsync;

    @Autowired
    public TimeController(TimeService timeService,
                          TimeModel timeModel, Calculation calculation, TimeControllerService timeControllerService, ResultAsync resultAsync) {
        this.timeService = timeService;
        this.timeModel = timeModel;
        this.calculation = calculation;
        this.timeControllerService = timeControllerService;
        this.resultAsync = resultAsync;
    }

    @ExceptionHandler(BadArgumentsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadArgumentsException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DivideException.class})
    public ResponseEntity<ErrorResponse> handleException(DivideException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/time")
    public ResponseEntity<?> calculateTime(@RequestParam double distance, @RequestParam double speed) throws BadArgumentsException, DivideException {

        CounterThread threadCounter = new CounterThread();
        threadCounter.start();

        timeService.validate(distance, speed);

        logger.info("Check parametes");

        double time;

        time = timeService.getTime(distance, speed);

        logger.info(String.format("Time for distance %f and speed %f is %f", distance, speed, time));

        timeModel.setTime(time);
        timeModel.setDistance(distance);
        timeModel.setSpeed(speed);

        return ResponseEntity.ok("Time is: " + timeModel.getTime() + "\nCounter: " + Counter.getCounter());
    }

    @PostMapping("/times")
    public ResponseEntity<?> bulk(@RequestBody List<TimeModel> listOfTimeModel) {

        List<Double> resultList = listOfTimeModel.stream().map(x -> {

                return timeService.calculate(x.getDistance(),x.getSpeed());

        }).collect(Collectors.toList());


        double max = calculation.findMax(resultList);
        double min = calculation.findMin(resultList);
        double avg = calculation.findAverage(resultList) ;

        Result calculations = new Result(resultList,max, min, avg);

        return ResponseEntity.ok(calculations);
    }



    @PostMapping("/write")
    public ResponseEntity<Integer> methodDecode(@RequestBody TimeModel result) throws BadArgumentsException {

        timeService.validate(result.getDistance(),result.getSpeed());

        TimeModel result1 = timeControllerService.findByDistanceAndSpeed(result.getDistance(),result.getSpeed());

        if(result1 == null){
            int id = resultAsync.createHalfEmptyModel(result);
            resultAsync.computeAsync(id);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
            // return id;
        }else{
            return ResponseEntity.ok(result1.getId());
          // return result1.getId();
        }

    }

    @GetMapping("/find/{id}")
    public TimeModel result(@PathVariable("id") int id){
        return timeControllerService.findOne(id);
    }

}