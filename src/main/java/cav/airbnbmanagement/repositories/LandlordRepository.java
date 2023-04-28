package cav.airbnbmanagement.repositories;

import cav.airbnbmanagement.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {

    Landlord findFirstByEmailAndPassword(String email, String paswd);
    Landlord findFirstByTokenAndExpTimeIsGreaterThan(String mail, long token);

    Optional<Landlord> findByEmail(String l);
}
