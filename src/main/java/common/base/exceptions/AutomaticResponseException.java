package common.base.exceptions;

public class AutomaticResponseException extends RuntimeException {

    private ApiError apiError;

    public AutomaticResponseException(String message){
        super(message);
    }

    public AutomaticResponseException(ApiError apiError){
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
