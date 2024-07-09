package com.example.springj310.service.interfaces;
import com.example.springj310.dto.ClientsAddressDTO;
import com.example.springj310.dto.ClientsDTO;
import com.example.springj310.entity.AddressesEntity;
import com.example.springj310.entity.ClientsEntity;

import java.util.Optional;
import java.util.stream.Stream;
public interface ClientsAddressService {
    public Optional<ClientsDTO> findClientsDtoById(long clientID);
    public Stream<ClientsAddressDTO> findAll();
    public void remove(long id);
    public String create(ClientsAddressDTO dto);
    public Stream<ClientsAddressDTO> findFilter(String type, String name);
    public ClientsAddressDTO addressClientConvertToDto(ClientsEntity entity2, AddressesEntity entity);
    public AddressesEntity addressClientConvertToAddressEntity(ClientsAddressDTO dto);
    public ClientsEntity addressClientConvertToClientEntity(ClientsAddressDTO dto);
    public long clientUniControl(String name, String type, Stream <ClientsAddressDTO> clients);
    public Stream<ClientsAddressDTO> filerTypeName(String type,String name);
    public Stream<ClientsAddressDTO> filerAddress(String address);
}
