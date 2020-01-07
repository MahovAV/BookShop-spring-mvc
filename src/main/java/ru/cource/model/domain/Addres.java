package ru.cource.model.domain;

import javax.persistence.Embeddable;

/**
 * POJO domain object representing an Addres.
 * 
 * @author AlexanderM-O
 *
 */
@Embeddable
public class Addres {
	// will be placed in author table
	private String place;

	public Addres() {
	}

	public Addres(String place) {
		this.setPlace(place);
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
