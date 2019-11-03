package Domain;

import javax.persistence.*;

/**
 * Created by user on 03.11.2019.
 */
@Entity
public class tenant {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    public tenant(String name){
        this.name=name;
    }

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }
}
