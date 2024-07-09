package com.example.springj310.service.interfaces;

import com.example.springj310.dto.AddressesDTO;

import java.util.Optional;
import java.util.stream.Stream;

public interface AddressesService {
    public Optional<AddressesDTO> findAddressesDtoById(long addressId);
    public Stream<AddressesDTO> findAll();
    public String create(AddressesDTO dto);
    public String update(AddressesDTO dto);
    public void remove(long id);
    public Stream<AddressesDTO> findAddressesDtoBClientId(long id);
    public Stream<AddressesDTO> filerAddress(String address);
}
