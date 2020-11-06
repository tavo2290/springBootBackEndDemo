package demo.persistence.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "person",schema = "admin")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(sequenceName = "admin.person_id_seq", allocationSize = 1, name = "person_seq")
    private Long id;
    private String firstName;
    private String lastName;
    private String identification;
    private boolean delete;
    private String email;

    public Person(){}

    public Person(String firstName, String lastName, String identification, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.identification = identification;
        this.email = email;
        this.delete = false;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getIdentification(){
        return identification;
    }

    public String getEmail(){
        return email;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Map<String,Object> mapDescriptor(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("id",this.id);
        map.put("firstName",this.firstName);
        map.put("lastName",this.lastName);
        return map;
    }

    /*@OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    Set<User> users;

    public Set<User> getUsers(){
        return users;
    }*/
}
