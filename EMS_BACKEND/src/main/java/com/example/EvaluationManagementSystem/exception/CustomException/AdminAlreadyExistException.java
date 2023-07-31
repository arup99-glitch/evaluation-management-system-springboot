package com.example.EvaluationManagementSystem.exception.CustomException;

public class AdminAlreadyExistException extends RuntimeException{
    public AdminAlreadyExistException(String message){
        super (message);
    }
}
