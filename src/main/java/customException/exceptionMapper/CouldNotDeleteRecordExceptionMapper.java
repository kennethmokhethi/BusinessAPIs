package customException.exceptionMapper;

import customException.CouldNotDeleteRecordException;
import model.response.ErrorMessage;
import model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouldNotDeleteRecordExceptionMapper implements ExceptionMapper<CouldNotDeleteRecordException> {

    public Response toResponse(CouldNotDeleteRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.COULD_NOT_DELETE_RECORD.name(), "http://www.google.com/hello");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}
