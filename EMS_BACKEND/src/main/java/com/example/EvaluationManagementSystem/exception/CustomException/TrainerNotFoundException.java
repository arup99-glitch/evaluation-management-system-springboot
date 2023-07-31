package com.example.EvaluationManagementSystem.exception.CustomException;

public class TrainerNotFoundException extends RuntimeException{
    public TrainerNotFoundException(String message){
        super (message);
    }
}
