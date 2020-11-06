package common.base.exceptions;

import java.util.*;
import java.util.stream.Collectors;

public class ApiError {

    private int status;
    private Date errorDate;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    public ApiError(int status, String message, String debugMessage) {
        this.errorDate = new Date();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
        subErrors = new LinkedList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    public Map getResponseMap(){
        Map map = new LinkedHashMap<>();
        map.put("errorDate",this.errorDate.toString());
        map.put("message",this.message);
        map.put("debugMessage",this.debugMessage);
        if(!this.subErrors.isEmpty()){
            map.put("detailError",this.subErrors.stream().map(error->{
                Map subMap = new LinkedHashMap<>();
                subMap.put("field",error.getField());
                subMap.put("message",error.getMessage());
                return subMap;
            }).collect(Collectors.toList()));
        }
        return map;
    }
}
