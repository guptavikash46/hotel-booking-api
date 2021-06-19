package com.vikas.hotelbookingsystemrestapi.repository;

import com.vikas.hotelbookingsystemrestapi.entity.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class HotelDao {

    @PersistenceContext
    private EntityManager em;

    public List<Hotel> getHotels(String city, String date, int numGuests) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
        Root<Hotel> root = cq.from(Hotel.class);
        Predicate cityNameMatch = cb.equal(root.get("city"), city);
        Predicate dateMatch = cb.equal(root.get("date"), date);
        Predicate numberOfGuests = cb.equal(root.get("numberOfGuests"), numGuests);
        cq.select(root).where(cityNameMatch, dateMatch, numberOfGuests);
        TypedQuery<Hotel> query = em.createQuery(cq);
        return query.getResultList();

    }
}
