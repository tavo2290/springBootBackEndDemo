package common.base.exceptions;

public class EntryDataValidationException extends Exception {

    private ApiError apiError;

    public EntryDataValidationException(String message){
        super(message);
    }

    public EntryDataValidationException(ApiError apiError){
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
