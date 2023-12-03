package com.elif.bookstoreapi.core.exceptions;

public class GenericValidationException extends RuntimeException{

    public GenericValidationException(){
        super("Validation Exception");
    }

    public GenericValidationException(String message){
        super(message);
    }

}
