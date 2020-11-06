package common.base.services.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.base.exceptions.ApiError;
import common.base.exceptions.AutomaticResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class ResponseManagerService {

    public Function<Map, Map> simpleErrorFunction = (Map responseCall) -> {
        if((Boolean) responseCall.get("status")){
            return responseCall;
        }else {
            throw new AutomaticResponseException(new ApiError((Integer) responseCall.get("code"), responseCall.get("data").toString(), ""));
        }
    };

    public Consumer<Map> simpleFunctionVerify = (Map responseCall) -> {
        if(!(Boolean) responseCall.get("status")){
            throw new AutomaticResponseException(new ApiError((Integer) responseCall.get("code"), responseCall.get("data").toString(), ""));
        }
    };

    public ResponseEntity<Object> listResponse(List<Map> lista){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.status(200).body(objectMapper.writeValueAsString(lista));
        }catch (JsonProcessingException e){
            throw new AutomaticResponseException(new ApiError(400, "Error while parsing the response", ""));
        }
    }
}
