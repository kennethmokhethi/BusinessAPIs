package com.businessapi.customException.exceptionMapper;

import com.businessapi.customException.MissingRequiredFieldException;
import com.businessapi.model.response.ErrorMessage;
import com.businessapi.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {

    public Response toResponse(MissingRequiredFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.MISSING_REQUIRED_FIELD.name(), "http://www.google.com/hello");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
