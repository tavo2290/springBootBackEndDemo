package common.base.util.repositories.validation;

import com.fasterxml.jackson.databind.JsonNode;
import common.base.exceptions.ApiError;
import common.base.exceptions.ApiSubError;
import common.base.exceptions.EntryDataValidationException;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class FormatAndValidationRepository {

    public Map<String,Object> validateEntryData(List<FieldVal> referenceList, JsonNode entryJson) throws Exception {
        Map<String,Object> castingMap = new LinkedHashMap<>();
        List<ApiSubError> validationErrors = new LinkedList<>();
        referenceList.stream().forEach((FieldVal fieldVal)->{
            boolean touchKey = entryJson.has(fieldVal.getFieldName());
            if(touchKey){
                castEntryData(fieldVal,entryJson.get(fieldVal.getFieldName()),castingMap,validationErrors);
            }else if(!touchKey && fieldVal.isObligatory()){
                validationErrors.add(new ApiSubError(fieldVal.getFieldName(),"Field Obligatory"));
            }
        });
        if(validationErrors.isEmpty()){
            return castingMap;
        }else{
            ApiError apiError = new ApiError(400,"Entry data validation failed","");
            apiError.setSubErrors(validationErrors);
            throw new EntryDataValidationException(apiError);
        }
    }

    void castEntryData(FieldVal fieldVal, JsonNode field, Map<String,Object> castingMap, List<ApiSubError> validationErrors){
        switch (fieldVal.getObjectType()){
            case "String": if(field.isTextual()){
                castingMap.put(fieldVal.getFieldName(),field.asText());
            }else{ validationErrors.add(new ApiSubError(fieldVal.getFieldName(),"Field Type Missmatch required String")); }
                break;
            case "Int": if(field.canConvertToInt()){
                castingMap.put(fieldVal.getFieldName(),field.asInt());
            }else{ validationErrors.add(new ApiSubError(fieldVal.getFieldName(),"Field Type Missmatch required Integer")); }
                break;
            case "Boolean": if(field.isBoolean()) {
                castingMap.put(fieldVal.getFieldName(), field.asBoolean());
            }else{ validationErrors.add(new ApiSubError(fieldVal.getFieldName(),"Field Type Missmatch required Boolean")); }
                break;
            case "Double": if(field.isDouble()) {
                castingMap.put(fieldVal.getFieldName(), field.asDouble());
            }else{ validationErrors.add(new ApiSubError(fieldVal.getFieldName(),"Field Type Missmatch required Double")); }
                break;
            case "Long": if(field.canConvertToLong()){
                castingMap.put(fieldVal.getFieldName(),field.asLong());
            }else{ validationErrors.add(new ApiSubError(fieldVal.getFieldName(),"Field Type Missmatch required Long")); }
                break;
        }
    }

    public void populateObjectFromMap(Map<String,Object> entryMap, Object ob){
        entryMap.forEach((String key, Object value)->{
            try{
                Class objectClass = ob.getClass();
                Field currentField = objectClass.getDeclaredField(key);
                currentField.setAccessible(true);
                currentField.set(ob,value);
            }catch (NoSuchFieldException ex){
                System.out.println("No encontro el campo "+key);
            }catch (IllegalAccessException ex){
                System.out.println("No pudu convertir");
            }
        });
    }
}
