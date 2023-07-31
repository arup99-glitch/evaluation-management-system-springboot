package com.example.EvaluationManagementSystem.exception.CustomException;

public class TaskAlreadySubmittedException extends RuntimeException{
    public TaskAlreadySubmittedException(String message){
        super (message);
    }
}
