package com.vikas.hotelbookingsystemrestapi.service;

import com.vikas.hotelbookingsystemrestapi.entity.Hotel;
import com.vikas.hotelbookingsystemrestapi.exceptionHandling.CustomError;
import com.vikas.hotelbookingsystemrestapi.repository.HotelDao;
import com.vikas.hotelbookingsystemrestapi.repository.HotelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService{

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelDao hotelDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public int save(Hotel hotel) {
        Optional<Hotel> hotelObj = Optional.of(hotel);
        if (!hotelObj.isEmpty()) {
            hotelRepository.save(hotel);
            return 1;
        }
        return 0;
    }

    public Hotel findOneHotel(Long id) {
        return hotelRepository.findOne(id);
    }

    public List<Hotel> searchHotels(String city, String date, Integer numberOfGuests, String stars,
                                    Boolean hasWifi, Boolean hasRestaurant, Boolean hasAC, Boolean isMealIncluded)
                                    throws CustomError{
        try {
            if (stars == null) stars = "0";
            if (hasWifi == null) hasWifi = false;
            if (hasRestaurant == null) hasRestaurant = false;
            if (hasAC == null) hasAC = false;
            if (isMealIncluded == null) isMealIncluded = false;
            logger.info("stars: "+stars+", wifi: "+hasWifi+", restaurant: "+hasRestaurant+", AC: "+hasAC+"" +
                    " meal: "+isMealIncluded);
            List<Hotel> allHotels = hotelRepository.searchHotels(city, date, numberOfGuests, stars, hasWifi, hasRestaurant,
                    hasAC, isMealIncluded);
            return allHotels;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomError("Either the values are null or invalid.");
        }
    }
    //search only with 3 params
    public List<Hotel> searchHotels(String city, String date, int numberOfGuests)
            throws CustomError{
        List<Hotel> allHotels = hotelDao.getHotels(city, date, numberOfGuests);
        return allHotels;
    }

}
