package com.example.labs.async;

import com.example.labs.models.TimeModel;
import com.example.labs.services.TimeControllerService;
import com.example.labs.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ResultAsync {
    private final TimeControllerService resultService;
    public final TimeService timeService;

    @Autowired
    public ResultAsync(TimeControllerService resultService, TimeService timeService){
        this.resultService = resultService;
        this.timeService = timeService;
    }


    public int createHalfEmptyModel(TimeModel result){
        TimeModel result1 = new TimeModel();

        result1.setDistance(result.getDistance());
        result1.setSpeed(result.getSpeed());

        resultService.save(result1);

        return result1.getId();
    }


    public CompletableFuture<Integer> computeAsync(int id){
        return CompletableFuture.supplyAsync(() ->{
           try{
               TimeModel result = resultService.findOne(id);
               Thread.sleep(15000);

               result.setTime(timeService.calculate(result.getDistance(),result.getSpeed()));

               resultService.save(result);
               return result.getId();
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
        });
    }


}
