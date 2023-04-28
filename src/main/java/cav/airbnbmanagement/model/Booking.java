package cav.airbnbmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long bookingID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AirBnb airbnb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Customer customer;

    @Column(name = "book_from")
    private LocalDate bookFrom;

    @Column(name = "book_end")
    private LocalDate bookEnd;

    @Override
    public String toString() {
        return "Booking{" +
                "airbnb=" + airbnb +
                ", customer=" + customer +
                ", bookFrom=" + bookFrom +
                ", bookEnd=" + bookEnd +
                '}';
    }
}
