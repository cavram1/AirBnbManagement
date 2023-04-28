package cav.airbnbmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "customer")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long custID;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "payment_information")
    private String paymentInfo;

    @Column(name = "phonenumber")
    private long num;

    @Column(name = "vorwahl")
    private String vorwahl;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Booking> bookings;

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", paymentInfo='" + paymentInfo + '\'' +
                ", num=" + num +
                ", vorwahl='" + vorwahl + '\'' +
                '}';
    }
}
