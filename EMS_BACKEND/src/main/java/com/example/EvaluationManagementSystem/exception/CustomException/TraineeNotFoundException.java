package com.example.EvaluationManagementSystem.exception.CustomException;

public class TraineeNotFoundException extends RuntimeException{
    public TraineeNotFoundException(String message){
        super (message);
    }
}
