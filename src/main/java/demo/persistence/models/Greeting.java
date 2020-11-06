package demo.persistence.models;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class Greeting {

    private long id;
    private String content;

    public Greeting(){}

    public Greeting(long id, String content){
        this.id = id;
        this.content = content;
    }

    public Greeting(Map<String,Object> entryMap){
        entryMap.forEach((String key, Object value)->{
            try{
                System.out.println("LLave:>>"+key);
                Class objectClass = this.getClass();
                System.out.println(objectClass.getField("id"));
                Arrays.stream(objectClass.getFields()).forEach((Field f)->{
                    System.out.println(f.getName());
                });
                Field currentField = objectClass.getField(key);
                System.out.println("Field Opt"+currentField.getName());
                currentField.set(this,value);
            }catch (NoSuchFieldException ex){
                System.out.println("No encontro el campo");
            }catch (IllegalAccessException ex){
                System.out.println("No pudu convertir");
            }
        });
    }

    public long getId(){
        return id;
    }

    public String getContent(){
        return content;
    }

}
