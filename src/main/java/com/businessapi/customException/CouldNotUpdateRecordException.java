package com.businessapi.customException;

public class CouldNotUpdateRecordException extends RuntimeException{

    private static final long serialVersionUID = 111502565564657565L;

    public CouldNotUpdateRecordException(String message)
    {
        super(message);
    }
}
