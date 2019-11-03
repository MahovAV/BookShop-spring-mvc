package Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 03.11.2019.
 */
@Entity
public class Flat {
    @Id
    @GeneratedValue
    private int id;

    private String adress;

    public Flat(String adress){
        this.adress=adress;
    }

}
