package common.base.util.repositories.validation;

public class FieldVal {

    private String fieldName;
    private boolean obligatory;
    private String objectType;

    public FieldVal(String fieldName, boolean obligatory, String objectType) {
        this.fieldName = fieldName;
        this.obligatory = obligatory;
        this.objectType = objectType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
