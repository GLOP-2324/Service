package com.shoploc.shoploc.exception;

public class InsertionFailedException extends Exception{
    public InsertionFailedException(String errorMessage){
        super(errorMessage);
    }
}
