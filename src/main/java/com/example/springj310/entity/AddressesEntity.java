package com.example.springj310.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class AddressesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "addressid",unique=true)
    private Long addressid;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "mac", nullable = false)
    private String mac;
    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "address", nullable = false)
    private String address;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private ClientsEntity clientid;

}

