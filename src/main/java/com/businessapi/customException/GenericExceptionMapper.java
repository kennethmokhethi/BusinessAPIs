package com.businessapi.customException;

import com.businessapi.model.response.ErrorMessage;
import com.businessapi.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        ErrorMessage errorMessage = new ErrorMessage(throwable.getMessage(),
                ErrorMessages.INTERNAL_SERVER_ERROR.name(), "http://www.google.com/hello");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}
