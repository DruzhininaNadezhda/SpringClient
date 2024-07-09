package com.example.springj310.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Date;
import java.util.Set;
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ClientsAddressDTO {
    private Long clientid;
    private String clientName;
    private Long addressid;
    //private String typeclient;
    private TypeEnum typeclient;
    private Date datecreatclient;
    private String ip;
    private String mac;
    private String model;
    private String address;
    private Set<AddressesDTO> addresses;
}
