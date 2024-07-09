package com.example.springj310.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class ClientsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clientid",unique=true)
    private Long clientid;
    @Column(name = "client_name", nullable = false)
    private String clientName;
    @Column(name = "typeclient", nullable = false)
    private String typeclient;
    @Column(name = "datecreatclient", nullable = false)
    private Date datecreatclient;
    @OneToMany(mappedBy = "clientid", fetch = FetchType.EAGER)
    private Set<AddressesEntity> addressesEntities = new LinkedHashSet<>();

}