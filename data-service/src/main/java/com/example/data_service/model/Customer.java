package com.example.data_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chain_name")
    private String chainName;

    @Column(name = "address_name")
    private String addressName; // CH3 Ship To Name

    @Column(name = "address_code")
    private String addressCode; // CH3 Ship To Code

}
