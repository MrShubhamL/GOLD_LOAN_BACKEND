package com.goldback.modals;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Outward {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String jewelleryName;

    private String weight;

    private String carat;

    private Double marketValuation;

    private Double moneyTaken;

    private Double interest;

    private String returnDate;

    private String status;

    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToOne
    @JoinColumn(name = "inward_id", nullable = true)
    private Inward inward;
}
