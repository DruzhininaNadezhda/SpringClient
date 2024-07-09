package com.example.springj310.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;


@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ClientsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long clientid;
    private String clientName;
    //private String typeclient;
    private TypeEnum typeclient;
    private Date datecreatclient;
    private Set<AddressesDTO> addresses;

}