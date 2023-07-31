package com.example.EvaluationManagementSystem.exception.CustomException;

public class BatchNotFoundException extends RuntimeException{
    public BatchNotFoundException(String message){
        super (message);
    }
}
