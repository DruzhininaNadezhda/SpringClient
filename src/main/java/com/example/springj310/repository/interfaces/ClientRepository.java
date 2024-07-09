package com.example.springj310.repository.interfaces;
import com.example.springj310.entity.ClientsEntity;
import java.util.Optional;
import java.util.stream.Stream;
public interface ClientRepository extends ALlForRepository{
    //public Optional<ClientsEntity> findClientById(Long id);
    //public Stream<ClientsEntity> findAll();
    //public void create(ClientsEntity entity);
    //public void update(ClientsEntity clients);
    //public void remove(ClientsEntity clients);
    public Stream<ClientsEntity> filerTypeName(String type, String name);
}
