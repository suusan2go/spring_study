package mrs.domain.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="usr")
public class User implements Serializable {
    @Id
    private String userID;

    private String password;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private RoleName rolename;

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public RoleName getRolename() {
        return rolename;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRolename(RoleName rolename) {
        this.rolename = rolename;
    }
}
