package customException.exceptionMapper;

import customException.NoRecordFoundException;
import model.response.ErrorMessage;
import model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoRecordFoundExceptionMapper implements ExceptionMapper<NoRecordFoundException> {

    public Response toResponse(NoRecordFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.RECORD_ALREADY_EXIST.name(), "http://www.google.com/hello");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
