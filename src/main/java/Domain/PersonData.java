package Domain;

import Domain.PersonEntity;

import javax.persistence.*;

/**
 * Created by user on 02.11.2019.
 */
@Entity
public class PersonData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne(mappedBy = "data")
    private PersonEntity entity;


    private String adress;

    private String habits;

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public PersonEntity getEntity() {
        return entity;
    }

    public void setEntity(PersonEntity entity) {
        this.entity = entity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
