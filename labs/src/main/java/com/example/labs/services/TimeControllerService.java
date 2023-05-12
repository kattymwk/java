package com.example.labs.services;

import com.example.labs.models.TimeModel;
import com.example.labs.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeControllerService {

    private final ResultRepository resultRepository;

    @Autowired
    public TimeControllerService(ResultRepository resultRepository){this.resultRepository = resultRepository;}


    public void save(TimeModel result){
        resultRepository.save(result);
    }

    public TimeModel findOne(int id){
        Optional<TimeModel> foundResult = resultRepository.findById(id);
        return foundResult.orElse(null);
    }

    public TimeModel findByDistanceAndSpeed(double distance,double speed){
        Optional<TimeModel> foundResult = Optional.ofNullable(resultRepository.findByDistanceAndSpeed(distance,speed));
        return foundResult.orElse(null);
    }
}
