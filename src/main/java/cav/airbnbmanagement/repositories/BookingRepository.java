package cav.airbnbmanagement.repositories;


import cav.airbnbmanagement.model.AirBnb;
import cav.airbnbmanagement.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT DISTINCT b.airbnb.airbnbNum FROM Booking b " +
            "LEFT OUTER JOIN AirBnb a ON b.airbnb.abID = a.abID" +
            " WHERE (?1 < b.bookFrom AND (?2 < b.bookEnd AND ?2 < b.bookFrom)) " +
            "OR (?1 > b.bookEnd AND ?2 > b.bookEnd)" +
            "OR b.bookFrom IS NULL")
    List<AirBnb> getAllFreeAirbnbs(LocalDate from, LocalDate to);
}
