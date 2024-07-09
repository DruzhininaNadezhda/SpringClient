package com.example.springj310.service.interfaces;

import com.example.springj310.dto.ClientsDTO;
import com.example.springj310.entity.ClientsEntity;

import java.util.Optional;
import java.util.stream.Stream;

public interface ClientsService {
    public String update(ClientsDTO dto);
    public Optional<ClientsDTO> findClientsDtoById(long clientID);
    public ClientsDTO clientConvertToDto(ClientsEntity entity);
    public ClientsEntity convertToEntity(ClientsDTO dto);
    public Stream<ClientsDTO> filerTypeName(String type, String name);
}
