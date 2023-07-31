package com.example.EvaluationManagementSystem.exception.CustomException;

public class MarksAlreadyUploadedException extends RuntimeException{
    public MarksAlreadyUploadedException(String message){
        super (message);
    }
}
