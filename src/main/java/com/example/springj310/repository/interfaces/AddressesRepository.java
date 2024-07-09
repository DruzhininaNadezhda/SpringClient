package com.example.springj310.repository.interfaces;

import com.example.springj310.entity.AddressesEntity;

import java.util.Optional;
import java.util.stream.Stream;

public interface AddressesRepository extends ALlForRepository {
    //public Optional<AddressesEntity> findAddressById(Long id);
    //public Stream<AddressesEntity> findAll();
    //public void create(AddressesEntity entity);
   // public void update(AddressesEntity addresses);
    //public void remove(AddressesEntity addresses);
    public Stream<AddressesEntity> filerAddress(String address);
}
