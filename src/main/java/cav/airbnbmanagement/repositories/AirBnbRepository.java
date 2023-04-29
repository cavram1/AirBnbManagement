package cav.airbnbmanagement.repositories;

import cav.airbnbmanagement.model.AirBnb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AirBnbRepository extends JpaRepository<AirBnb, Long> {


    ArrayList<AirBnb> findAirBnbByLocation(String loc);
}
