package com.vikas.hotelbookingsystemrestapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hotel extends AbstractEntity{

    @NotNull
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull
    @NotEmpty(message = "city cannot be empty")
    private String city;
    @NotNull
    @NotEmpty(message = "Date cannot be empty")
    private String date;
    @NotNull
    private Integer numberOfGuests;
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    private Integer totalRooms;
    @NotNull
    private Integer roomsAvailable;
    @NotNull(message = "cost per night cannot be empty")
    private Long costPerNight;
    @NotNull
    @NotEmpty
    @Email(message = "invalid email address")
    private String email;
    @NotNull
    private Long phone;
    private Boolean hasWifi;
    private Boolean hasRestaurant;
    private Boolean hasAC;
    private Boolean hasMeal;
    private String stars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<User> user = new ArrayList<>();

    public Hotel() {}

    public Hotel(@NotNull @NotEmpty(message = "name cannot be empty") String name,
                 @NotNull @NotEmpty(message = "city cannot be empty") String city,
                 @NotNull @NotEmpty(message = "Date cannot be empty") String date,
                 @NotNull Integer numberOfGuests, @NotNull @NotEmpty String address,
                 @NotNull Integer totalRooms, @NotNull Integer roomsAvailable,
                 @NotNull(message = "cost per night cannot be empty") Long costPerNight,
                 @NotNull @NotEmpty @Email(message = "invalid email address") String email,
                 @NotNull Long phone, Boolean hasWifi, Boolean hasRestaurant, Boolean hasAC, Boolean hasMeal, String stars) {
        this.name = name;
        this.city = city;
        this.date = date;
        this.numberOfGuests = numberOfGuests;
        this.address = address;
        this.totalRooms = totalRooms;
        this.roomsAvailable = roomsAvailable;
        this.costPerNight = costPerNight;
        this.email = email;
        this.phone = phone;
        this.hasWifi = hasWifi;
        this.hasRestaurant = hasRestaurant;
        this.hasAC = hasAC;
        this.hasMeal = hasMeal;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public Integer getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(Integer roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }

    public Long getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(Long costPerNight) {
        this.costPerNight = costPerNight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(Boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public Boolean getHasRestaurant() {
        return hasRestaurant;
    }

    public void setHasRestaurant(Boolean hasRestaurant) {
        this.hasRestaurant = hasRestaurant;
    }

    public Boolean getHasAC() {
        return hasAC;
    }

    public void setHasAC(Boolean hasAC) {
        this.hasAC = hasAC;
    }

    public Boolean getHasMeal() {
        return hasMeal;
    }

    public void setHasMeal(Boolean hasMeal) {
        this.hasMeal = hasMeal;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }
}
