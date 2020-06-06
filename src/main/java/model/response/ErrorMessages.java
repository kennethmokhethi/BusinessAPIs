package model.response;


public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field .Please check documentation for required fields"),
    RECORD_ALREADY_EXIST("Record already exists"),
    NO_RECORD_FOUND_EXCEPTION("No record found for the requested username"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    AUTHENTICATION_FAILED("Incorrect passord or Id"),
    COULD_NOT_UPDATE_RECORD("Failed to update user details"),
    COULD_NOT_DELETE_RECORD("Failed to delete record");

    private String errorMessage;

    ErrorMessages(String errorMessage)
    {
        setErrorMessage(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
