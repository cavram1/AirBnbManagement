package cav.airbnbmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "landlord")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long landlordID;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "expiring_time", nullable = false)
    private long expTime = 0;

    @Override
    public String toString() {
        return "Landlord{" +
                "email='" + email + '\'' +
                ", expTime=" + expTime +
                '}';
    }
}
