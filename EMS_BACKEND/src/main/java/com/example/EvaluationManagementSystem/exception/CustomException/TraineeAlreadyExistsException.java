package com.example.EvaluationManagementSystem.exception.CustomException;

public class TraineeAlreadyExistsException extends RuntimeException{
    public TraineeAlreadyExistsException(String message){
        super (message);
    }
}
