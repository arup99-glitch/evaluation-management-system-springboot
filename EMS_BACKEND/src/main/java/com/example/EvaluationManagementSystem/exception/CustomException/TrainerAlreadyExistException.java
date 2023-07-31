package com.example.EvaluationManagementSystem.exception.CustomException;

public class TrainerAlreadyExistException extends RuntimeException{
    public TrainerAlreadyExistException(String message){
        super (message);
    }
}
