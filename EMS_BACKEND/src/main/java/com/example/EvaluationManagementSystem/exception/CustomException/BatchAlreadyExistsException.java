package com.example.EvaluationManagementSystem.exception.CustomException;

public class BatchAlreadyExistsException extends RuntimeException{
    public BatchAlreadyExistsException(String message){
        super (message);
    }
}
