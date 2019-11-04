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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flat_id")
    private FlatWhichCouldWorkWithTenant flat;

    tenant(){}

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

    public FlatWhichCouldWorkWithTenant getFlat() {
        return flat;
    }

    public void setFlat(FlatWhichCouldWorkWithTenant flat) {
        this.flat = flat;
    }


}
