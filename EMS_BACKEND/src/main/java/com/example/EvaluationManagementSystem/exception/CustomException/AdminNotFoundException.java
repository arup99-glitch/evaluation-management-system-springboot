package com.example.EvaluationManagementSystem.exception.CustomException;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String message){
        super (message);
    }
}
