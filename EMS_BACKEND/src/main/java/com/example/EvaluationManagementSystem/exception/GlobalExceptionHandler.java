package com.example.EvaluationManagementSystem.exception;

import com.example.EvaluationManagementSystem.exception.CustomException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler({
                TraineeAlreadyExistsException.class,
                TrainerAlreadyExistException.class,
                BatchNotFoundException.class,
                BatchAlreadyExistsException.class,
                TraineeNotFoundException.class,
                TrainerNotFoundException.class,
                MarksAlreadyUploadedException.class,
                TaskAlreadySubmittedException.class,
                TraineeAlreadyAssignedException.class,
                InternalServerErrorException.class,
                IllegalArgumentException.class,
                AdminAlreadyExistException.class,
                FileNotFoundException.class,
                UserNotFoundException.class,
                SubmitTaskNotFoundException.class


        })
        public ResponseEntity<Object> handleCustomException(Exception ex) {
            if (ex instanceof TraineeAlreadyExistsException ) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } else if(ex instanceof BatchNotFoundException ){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }else if(ex instanceof TrainerAlreadyExistException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }else if (ex instanceof BatchAlreadyExistsException) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }else if (ex instanceof TrainerNotFoundException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }else if (ex instanceof TraineeNotFoundException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }else if (ex instanceof TaskAlreadySubmittedException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }else if (ex instanceof TraineeAlreadyAssignedException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

            }else if (ex instanceof  MarksAlreadyUploadedException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }else if (ex instanceof  IllegalArgumentException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }else if (ex instanceof  FileNotFoundException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else if (ex instanceof  AdminAlreadyExistException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } else if(ex instanceof  UserNotFoundException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

            } else if (ex instanceof TaskNotFoundException) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

            }else if (ex instanceof SubmitTaskNotFoundException){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

