package com.elif.bookstoreapi.core.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource Not Found !");
    }

}
