package Domain;


import javax.persistence.*;

/**
 * Created by user on 02.11.2019.
 */
@Entity
//@Access(value= AccessType.PROPERTY)//telling that we will access via set and get methods
public class PersonEntity {

    @Id
    @GeneratedValue
    private int id;

    private String password;

    private String name;




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
