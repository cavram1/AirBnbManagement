package cav.airbnbmanagement.repositories;

import cav.airbnbmanagement.model.AirBnb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface AirBnbRepository extends JpaRepository<AirBnb, Long> {


    ArrayList<AirBnb> findAirBnbByLocation(String loc);


}
