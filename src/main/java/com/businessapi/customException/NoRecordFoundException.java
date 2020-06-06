package com.businessapi.customException;

public class NoRecordFoundException extends RuntimeException{

    private static final long serialVersionUID = 577667565564688865L;

    public NoRecordFoundException(String message)
    {
        super(message);
    }


}