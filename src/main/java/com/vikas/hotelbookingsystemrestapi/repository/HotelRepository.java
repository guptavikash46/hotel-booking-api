package com.vikas.hotelbookingsystemrestapi.repository;

import com.vikas.hotelbookingsystemrestapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE h.id=:id")
    Hotel findOne(@Param("id") Long id);

    @Query("SELECT h FROM Hotel h WHERE (h.city=:city) " +
            "AND (h.date=:date) " +
            "AND (h.numberOfGuests=:numberOfGuests) " +
            "AND (h.stars=:stars) " +
            "AND (h.hasWifi=:hasWifi) " +
            "AND (h.hasRestaurant=:hasRestaurant) " +
            "AND (h.hasAC=:hasAC) " +
            "AND (hasMeal=:hasMeal)")
    List<Hotel> searchHotels(@Param("city") String city, @Param("date") String date,
                             @Param("numberOfGuests") int numberOfGuests,
                             @Param("stars") String stars, @Param("hasWifi") boolean hasWifi,
                             @Param("hasRestaurant") boolean hasRestaurant,
                             @Param("hasAC") boolean hasAC, @Param("hasMeal") boolean hasMeal);


    @Query("SELECT h FROM Hotel h WHERE (h.city=:city) " +
            "AND (h.date=:date) " +
            "AND (h.numberOfGuests=:numberOfGuests)")
    List<Hotel> searchHotels(@Param("city") String city, @Param("date") String date,
                             @Param("numberOfGuests") int numberOfGuests);
}

