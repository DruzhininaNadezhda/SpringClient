package com.example.springj310.service.impl;

import com.example.springj310.dto.AddressesDTO;
import com.example.springj310.entity.AddressesEntity;
import com.example.springj310.entity.ClientsEntity;
import com.example.springj310.repository.interfaces.AddressesRepository;
import com.example.springj310.repository.interfaces.ClientRepository;
import com.example.springj310.service.ControlForClientAndAddress;
import com.example.springj310.service.interfaces.AddressesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
@Service
public class AddressesServiceImpl implements AddressesService {
    private final AddressesRepository addressRepository;
    private final ClientRepository clientRepository;
    private final ControlForClientAndAddress controlForClientAndAddress;

    public AddressesServiceImpl(AddressesRepository addressRepository, ClientRepository clientRepository, ControlForClientAndAddress controlForClientAndAddress) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
        this.controlForClientAndAddress = controlForClientAndAddress;
    }


    @Override
    public Optional<AddressesDTO> findAddressesDtoById(long addressId) {
    Optional<AddressesEntity> address = addressRepository.findById(AddressesEntity.class, addressId);
        if (address != null && address.get().getClientid() != null) {
            //ClientsEntity client = entity.getClient();
            return Optional.ofNullable(addressConvertToDto(address.get()));
        }
        return null;
    }
    @Override
    public Stream<AddressesDTO> findAddressesDtoBClientId(long id) {
       return findAll().filter(e -> e.getClientid() == id);
    }

    @Override
    public Stream<AddressesDTO> findAll() {                                 //настроить нормально маппер!!!!!!!!!!!!!
        return addressRepository.findAll(AddressesEntity.class).map(entity -> {
                            ClientsEntity client = entity.getClientid();
                           return AddressesDTO.builder()
                                    .clientid(client != null ? client.getClientid() : 0)
                                    .addressid(entity.getAddressid())
                                    .ip(entity.getIp())
                                    .mac(entity.getMac())
                                   .model(entity.getModel())
                                  .address(entity.getAddress())
                                   .build();
                        }
                );
    }
    @Transactional
    @Override
    public String create(AddressesDTO dto) {
        if(controlForClientAndAddress.controlForClientAndAddress(null,dto.getMac(), dto.getIp(), dto.getAddress(), dto.getModel()).equals("OK")){
        addressRepository.create(addressConvertToEntity(dto));
        return "Адрес создан";}
        return "Адрес не создан" + "\r" + controlForClientAndAddress.controlForClientAndAddress(null,dto.getMac(), dto.getIp(), dto.getAddress(), dto.getModel());
    }
    @Transactional
    @Override
    public String update(AddressesDTO dto) {
        Optional<AddressesEntity> addressesBd = addressRepository.findById(AddressesEntity.class,addressConvertToEntity(dto).getAddressid());
       if (addressesBd.get().getAddressid() != null && controlForClientAndAddress.controlForClientAndAddress(null, dto.getMac(),dto.getIp(), dto.getAddress(), dto.getModel()).equals("OK")) {
            addressRepository.update(addressConvertToEntity(dto));
            return "Адрес обновлён";
        }
        return "Адрес не обновлён" + "\t" + controlForClientAndAddress.controlForClientAndAddress(null, dto.getMac(),dto.getIp(), dto.getAddress(), dto.getModel());
    }
    @Transactional
    @Override
    public void remove(long id) {
        Optional<AddressesEntity> addressBd = addressRepository.findById(AddressesEntity.class,id);
        addressRepository.remove(addressBd.get());
    }
    public AddressesDTO addressConvertToDto(AddressesEntity entity){ // совсем не работает маппер, срочно разобраться с получением лонга из объекта клиента
        return AddressesDTO.builder()
                .clientid(entity.getClientid().getClientid())
                .address(entity.getAddress())
                .ip(entity.getIp())
                .mac(entity.getMac())
                .model(entity.getModel())
                .addressid(entity.getAddressid())
                .build();
    }

    public AddressesEntity addressConvertToEntity(AddressesDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return Objects.isNull(dto)? null: modelMapper.map(dto, AddressesEntity.class);
    }
    @Override
    public Stream<AddressesDTO> filerAddress(String address){
        return addressRepository.filerAddress(address).map(this::addressConvertToDto);
    }
}