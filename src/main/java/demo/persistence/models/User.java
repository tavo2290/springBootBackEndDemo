package demo.persistence.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user",schema = "admin")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(sequenceName = "admin.user_id_seq", allocationSize = 1, name = "user_seq")
    private Long id;
    private String userName;
    private String password;
    private boolean securityPolicies;
    private boolean delete;
    private String profile;
    private boolean passwordChange;

    @ManyToOne
    @JoinColumn
    private Person person;

    public User(){}

    public User(String userName, String password, String profile, Person person){
        this.userName = userName;
        this.password = password;
        this.securityPolicies = false;
        this.delete = false;
        this.profile = profile;
        this.passwordChange = true;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSecurityPolicies() {
        return securityPolicies;
    }

    public void setSecurityPolicies(boolean securityPolicies) {
        this.securityPolicies = securityPolicies;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isPasswordChange() {
        return passwordChange;
    }

    public void setPasswordChange(boolean passwordChange) {
        this.passwordChange = passwordChange;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
