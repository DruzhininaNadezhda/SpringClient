package com.example.springj310.service.impl;

import com.example.springj310.dto.ClientsDTO;
import com.example.springj310.dto.TypeEnum;
import com.example.springj310.entity.ClientsEntity;
import com.example.springj310.repository.interfaces.ClientRepository;

import com.example.springj310.service.ControlForClientAndAddress;
import com.example.springj310.service.interfaces.ClientsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientsServiceImpl implements ClientsService {
    private final ClientRepository clientRepository;
    private final ControlForClientAndAddress controlForClientAndAddress;


    public ClientsServiceImpl(ClientRepository clientRepository, ControlForClientAndAddress controlForClientAndAddress) {

        this.clientRepository = clientRepository;
        this.controlForClientAndAddress = controlForClientAndAddress;
    }
    @Transactional
    @Override
    public String update(ClientsDTO dto){
       Optional<ClientsEntity> clientsBd = clientRepository.findById(ClientsEntity.class, convertToEntity(dto).getClientid());
      if (clientsBd.get().getClientid() != null && controlForClientAndAddress.controlForClientAndAddress(dto.getClientName(),null, null, null, null).equals("OK")) {
          clientRepository.update(convertToEntity(dto));
          return "Клиент изменён";}
        return "Kлиент не изменён" + "\t" +controlForClientAndAddress.controlForClientAndAddress(dto.getClientName(),null, null, null, null);
    }
    @Override
    public Optional<ClientsDTO> findClientsDtoById(long clientID) {
        Optional<ClientsEntity> clients = clientRepository.findById(ClientsEntity.class,clientID);
        if (clients != null && clients.get().getClientid() != null) {
            return Optional.ofNullable(clientConvertToDto(clients.get()));
        }
        return null;
    }
    @Override
    public ClientsDTO clientConvertToDto(ClientsEntity entity){
        return  ClientsDTO.builder()
                        .clientName(entity.getClientName())
                                .clientid(entity.getClientid())
                                        .datecreatclient(entity.getDatecreatclient())
                                                .typeclient(TypeEnum.getType(entity.getTypeclient()))
                .build();
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT);
//        return modelMapper.map(entity,ClientsDTO.class);
    }
    @Override
    public ClientsEntity convertToEntity(ClientsDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, ClientsEntity.class);
    }
    @Override
    public Stream<ClientsDTO> filerTypeName(String type, String name){
        return clientRepository.filerTypeName(type,name).map(this::clientConvertToDto);
    }

}
