package com.businessapi.customException;

public class MissingRequiredFieldException extends RuntimeException{

     private static final long serialVersionUID = 577667565564657565L;

    public MissingRequiredFieldException(String message)
    {
        super(message);
    }
}
