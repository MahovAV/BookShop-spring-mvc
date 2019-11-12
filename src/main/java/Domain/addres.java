package Domain;

import javax.persistence.Embeddable;

/**
 * Created by user on 05.11.2019.
 */
@Embeddable
public class addres {
    //will be placed in author table
    private String place;

    public addres(){}

    public addres(String place) {
        this.place = place;
    }
}
