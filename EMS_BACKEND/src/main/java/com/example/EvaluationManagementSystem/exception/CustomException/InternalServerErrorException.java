package com.example.EvaluationManagementSystem.exception.CustomException;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message){
        super (message);
    }
}
