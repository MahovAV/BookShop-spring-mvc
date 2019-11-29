package ru.cource.model.domain;

import javax.persistence.Embeddable;

/**
 * Created by user on 05.11.2019.
 */
@Embeddable
public class Addres {
    //will be placed in author table
    private String place;

    public Addres(){}

    public Addres(String place) {
        this.place = place;
    }
}
