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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id",unique = true)
    private PersonData data;

    private String password;

    private String name;


    public PersonData getData() {
        return data;
    }

    public void setData(PersonData data) {
        this.data = data;
    }

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
