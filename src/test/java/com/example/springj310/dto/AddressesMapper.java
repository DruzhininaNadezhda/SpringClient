package com.example.springj310.dto;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressesMapper {
    AddressesMapper INSTANCE = Mappers.getMapper(AddressesMapper.class);
    AddressesDTO toDto(AddressesDTO Addresses);
}
