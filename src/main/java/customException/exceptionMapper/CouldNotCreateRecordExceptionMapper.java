package customException.exceptionMapper;

import customException.CouldNotCreateRecordException;
import model.response.ErrorMessage;
import model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouldNotCreateRecordExceptionMapper implements ExceptionMapper<CouldNotCreateRecordException> {

    public Response toResponse(CouldNotCreateRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.NO_RECORD_FOUND_EXCEPTION.name(), "http://www.google.com/hello");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
