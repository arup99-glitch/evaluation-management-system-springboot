package com.example.EvaluationManagementSystem.exception.CustomException;



public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String message){
        super (message);
    }
}
