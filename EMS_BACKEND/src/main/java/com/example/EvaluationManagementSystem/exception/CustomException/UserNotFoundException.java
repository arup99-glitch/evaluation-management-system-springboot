package com.example.EvaluationManagementSystem.exception.CustomException;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super (message);
    }
}
