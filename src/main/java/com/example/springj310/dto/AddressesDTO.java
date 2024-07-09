package com.example.springj310.dto;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AddressesDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long clientid;
    private String ip;
    private Long addressid;
    private String mac;
    private String model;
    private String address;

}
