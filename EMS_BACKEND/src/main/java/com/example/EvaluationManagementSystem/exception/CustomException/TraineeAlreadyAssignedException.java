package com.example.EvaluationManagementSystem.exception.CustomException;

public class TraineeAlreadyAssignedException extends RuntimeException{
    public TraineeAlreadyAssignedException(String message){
        super (message);
    }
}
