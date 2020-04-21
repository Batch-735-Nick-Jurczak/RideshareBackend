package com.revature.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.stereotype.Component;

/**
 * Car class that represents a user's car. All cars have an id, color,  total seats, available seats, make, model, year
 * and the corresponding user.
 * 
 * @author Adonis Cabreja
 *
 */

@Component
@Entity
@Table(name="cars")
public class Car implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;
	
	@Column(name = "color")
	private String color;
	
	@Positive
	@Column(name = "total_seats")
	private int totalSeats;
	
	@Positive
	@Column(name = "available_seats")
	private int availableSeats;
	
	@NotBlank
	@Column(name = "car_make")
	private String make;
	
	@NotBlank
	@Column(name = "car_model")
	private String model;
	
	@Positive
	@Column(name="car_year")
	private int year;
	
	@OneToOne
	@JoinColumn(name="user_id", unique=true)
	private User user;

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + availableSeats;
		result = prime * result + carId;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + totalSeats;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (availableSeats != other.availableSeats)
			return false;
		if (carId != other.carId)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (totalSeats != other.totalSeats)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", color=" + color + ", totalSeats=" + totalSeats + ", availableSeats="
				+ availableSeats + ", make=" + make + ", model=" + model + ", year=" + year + ", user=" + user + "]";
	}

	public Car(int carId, String color, @Positive int totalSeats, @NotBlank String make,
			@NotBlank String model, @Positive int year, User user) {
		super();
		this.carId = carId;
		this.color = color;
		this.totalSeats = totalSeats;
		this.availableSeats = totalSeats;
		this.make = make;
		this.model = model;
		this.year = year;
		this.user = user;
	}
	
	public Car(int carId, String color, @Positive int totalSeats, @Positive int availableSeats, @NotBlank String make,
			@NotBlank String model, @Positive int year, User user) {
		super();
		this.carId = carId;
		this.color = color;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		this.make = make;
		this.model = model;
		this.year = year;
		this.user = user;
	}

	public Car() {
		super();
	}
	
}

