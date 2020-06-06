package com.businessapi.customException.exceptionMapper;

import com.businessapi.customException.AuthenticationException;
import com.businessapi.model.response.ErrorMessage;
import com.businessapi.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    public Response toResponse(AuthenticationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.AUTHENTICATION_FAILED.name(), "http://www.google.com/hello");
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
    }
}
