package com.example.EvaluationManagementSystem.exception.CustomException;

public class SubmitTaskNotFoundException extends RuntimeException{
    public SubmitTaskNotFoundException(String message){
        super (message);
    }
}
