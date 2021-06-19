package com.vikas.hotelbookingsystemrestapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {
    @NotNull
    @NotEmpty
    private String name = "";
    @NotNull
    @NotEmpty
    @Email
    private String email = "";
    @NotNull
    @NotEmpty
    private String gender = "";
    @NotNull
    @NotEmpty
    private String residentialCity = "";
    @NotNull
    @NotEmpty
    private String ratingDescription = "";
    @NotNull
    @Max(value = 10)
    private Integer rating = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    public User() {}

    public User(@NotNull @NotEmpty String name, @NotNull @NotEmpty @Email String email,
                @NotNull @NotEmpty String gender, @NotNull @NotEmpty String residentialCity,
                @NotNull @NotEmpty String ratingDescription, @NotNull @NotEmpty Integer rating, Hotel hotel) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.residentialCity = residentialCity;
        this.ratingDescription = ratingDescription;
        this.rating = rating;
        this.hotel = hotel;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResidentialCity() {
        return residentialCity;
    }

    public void setResidentialCity(String residentialCity) {
        this.residentialCity = residentialCity;
    }
}
