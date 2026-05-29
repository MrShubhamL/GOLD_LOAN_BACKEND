package com.goldback.modals;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Inward {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String jewelleryName;

    private String type;

    private String carat;

    private Double grossWeight;

    private Double netWeight;

    private Double currentGoldRate;

    private Double moneyGiven;

    private Double interest;

    private String startDate;

    private String returnDate;

    private String status;

    private Boolean isOwned;

    @CreationTimestamp
    private LocalDateTime cratedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @OneToOne(mappedBy = "inward", cascade = CascadeType.ALL, orphanRemoval = true)
    private Outward outward;
}
