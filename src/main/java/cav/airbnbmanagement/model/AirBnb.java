package cav.airbnbmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "airbnb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirBnb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long abID;

    @Column(name = "address")
    private String address;

    @Column(name = "airbnbNum")
    private String airbnbNum;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String desc;

    @Column(name = "price_per_night")
    private String pricePerNight;

    @OneToMany(mappedBy = "airbnb", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Booking> booking;

    @Override
    public String toString() {
        return "AirBnb{" +
                "address='" + address + '\'' +
                ", airbnbNum='" + airbnbNum + '\'' +
                ", location='" + location + '\'' +
                ", desc='" + desc + '\'' +
                ", pricePerNight='" + pricePerNight + '\'' +
                '}';
    }
}
