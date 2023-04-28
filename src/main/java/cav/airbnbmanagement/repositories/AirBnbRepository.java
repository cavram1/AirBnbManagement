package cav.airbnbmanagement.repositories;

import cav.airbnbmanagement.model.AirBnb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirBnbRepository extends JpaRepository<AirBnb, Long> {
}
