package com.shoploc.shoploc.exception;

public class ModificationFailedException extends Exception{
    public ModificationFailedException(String errorMessage){
        super(errorMessage);
    }
}
