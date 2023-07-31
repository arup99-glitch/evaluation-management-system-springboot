package com.example.EvaluationManagementSystem.exception.CustomException;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
        super (message);
    }
}
