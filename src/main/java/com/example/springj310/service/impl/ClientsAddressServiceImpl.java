package com.example.springj310.service.impl;
import com.example.springj310.dto.AddressesDTO;
import com.example.springj310.dto.ClientsAddressDTO;
import com.example.springj310.dto.ClientsDTO;
import com.example.springj310.entity.AddressesEntity;
import com.example.springj310.entity.ClientsEntity;
import com.example.springj310.repository.interfaces.AddressesRepository;
import com.example.springj310.repository.interfaces.ClientRepository;
import com.example.springj310.service.ControlForClientAndAddress;
import com.example.springj310.service.interfaces.AddressesService;
import com.example.springj310.service.interfaces.ClientsAddressService;
import com.example.springj310.service.interfaces.ClientsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class ClientsAddressServiceImpl implements ClientsAddressService {
    private final ClientRepository clientRepository;
    private final ClientsService clientsService;
    private final AddressesRepository addressRepository;
    private final AddressesService addressesService;

    private final ControlForClientAndAddress controlForClientAndAddress;

    public ClientsAddressServiceImpl(ClientRepository clientRepository, ClientsService clientsService, AddressesRepository addressRepository, AddressesService addressesService, ControlForClientAndAddress controlForClientAndAddress) {
        this.clientRepository = clientRepository;
        this.clientsService = clientsService;
        this.addressRepository = addressRepository;
        this.addressesService = addressesService;
        this.controlForClientAndAddress = controlForClientAndAddress;
    }
    @Override
    public Optional<ClientsDTO> findClientsDtoById(long clientID) {
       Optional<ClientsEntity> clients = clientRepository.findById(ClientsEntity.class,clientID);
        if (clients != null && clients.get().getClientid() != null) {
            return Optional.ofNullable(clientsService.clientConvertToDto(clients.get()));
        }
        return null;
    }
    @Override
    public Stream<ClientsAddressDTO> findAll() {
        List<ClientsAddressDTO> clientsAddress = new LinkedList<>();
        clientRepository.findAll(ClientsEntity.class).forEach(entity -> {
                    Set<AddressesEntity> addresses = entity.getAddressesEntities();
                    if (addresses == null|| addresses.isEmpty()) {
                        clientsAddress.add(addressClientConvertToDto(entity,null));
                    }
                    else {addresses.forEach(address ->clientsAddress.add(addressClientConvertToDto(entity,address)));
                        }
                }
        );
        return clientsAddress.stream().sorted(Comparator.comparingLong(ClientsAddressDTO::getClientid));
    }
    @Override
    public Stream<ClientsAddressDTO> findFilter(String type, String name) {
        Stream<ClientsAddressDTO> clientsAddress;
        clientsAddress =filerTypeName (type,name);
        Stream<ClientsAddressDTO> clientsAddress2;
        clientsAddress2 = filerAddress(name).filter(e -> (type!=null && !type.isEmpty())? e.getTypeclient().getType().equals(type):e.getTypeclient().getType().equals(e.getTypeclient().getType()));
        return Stream.concat(clientsAddress2,clientsAddress).distinct().sorted(Comparator.comparing(ClientsAddressDTO::getClientid));
    }
    @Transactional
     @Override
   public String create(ClientsAddressDTO dto){
        if(controlForClientAndAddress.controlForClientAndAddress(dto.getClientName(),dto.getMac(), dto.getIp(), dto.getAddress(), dto.getModel()).equals("OK")){
            clientRepository.create(addressClientConvertToClientEntity(dto));//создание клиента
         ClientsEntity clientsEntity = clientsService.convertToEntity(findClientsDtoById( //получение клиента обратно для ID
                 clientUniControl(dto.getClientName(), dto.getTypeclient().getType(), findAll())).get()); // поиск ID
         AddressesEntity addressesEntity = addressClientConvertToAddressEntity(dto);
         addressesEntity.setClientid(clientsEntity); //присваение ID клиента адресу
       addressRepository.create(addressesEntity);
        return "Клиент создан";}
        return "Kлиент не создан" +"\n"+ controlForClientAndAddress.controlForClientAndAddress(dto.getClientName(),dto.getMac(), dto.getIp(), dto.getAddress(), dto.getModel());
    }
    @Transactional
    @Override
    public void remove(long id) {
        Optional<ClientsEntity> clientsBd = clientRepository.findById(ClientsEntity.class,id);
        if (clientsBd.get().getAddressesEntities() != null || !clientsBd.get().getAddressesEntities().isEmpty()) {
            for (AddressesEntity addresses : clientsBd.get().getAddressesEntities()) {
                addressRepository.remove(addresses);
            }
        }
        clientRepository.remove(clientsBd.get());
    }
    @Override
    public ClientsAddressDTO addressClientConvertToDto(ClientsEntity entity2, AddressesEntity entity) {
        if (entity == null)
        {
            ModelMapper modelMapper = new ModelMapper();
            ClientsAddressDTO dto= modelMapper.map(entity2, ClientsAddressDTO.class);
            return dto;}
        ModelMapper modelMapper = new ModelMapper();
        ClientsAddressDTO dto= modelMapper.map(entity2, ClientsAddressDTO.class);
        modelMapper.map(entity,dto);
        return dto;
    }

    public Stream<ClientsAddressDTO> addressClientConvertToDtoFromClientsDTO(ClientsDTO clientsDTO) {
        List lll = new LinkedList();
        if (addressesService.findAddressesDtoBClientId(clientsDTO.getClientid())== null|| addressesService.findAddressesDtoBClientId(clientsDTO.getClientid()).collect(Collectors.toList()).isEmpty()) {
            ModelMapper modelMapper = new ModelMapper();
            ClientsAddressDTO dto = modelMapper.map(clientsDTO, ClientsAddressDTO.class);
            System.out.println(1);
            lll.add(dto);
        } else {
            for (AddressesDTO addressesDTO : addressesService.findAddressesDtoBClientId(clientsDTO.getClientid()).collect(Collectors.toList())) {
        ModelMapper modelMapper = new ModelMapper();
        ClientsAddressDTO dto = modelMapper.map(clientsDTO, ClientsAddressDTO.class);
            modelMapper.map(addressesDTO, dto);
                lll.add(dto);
        }
        }return lll.stream();
    }
    public ClientsAddressDTO addressClientConvertToDtoFromAddressDTO(AddressesDTO addressesDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ClientsAddressDTO dto = modelMapper.map(clientsService.findClientsDtoById(addressesDTO.getClientid()), ClientsAddressDTO.class);
        modelMapper.map(addressesDTO, dto);
        return dto;
    }
    @Override
    public ClientsEntity addressClientConvertToClientEntity(ClientsAddressDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, ClientsEntity.class);
    }
    @Override
    public AddressesEntity addressClientConvertToAddressEntity(ClientsAddressDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, AddressesEntity.class);
    }

    public long clientUniControl(String name, String type, Stream <ClientsAddressDTO> clients) {
        List <ClientsAddressDTO> clients1 = clients.collect(Collectors.toList());
        for (ClientsAddressDTO client : clients1) {
            if (client.getClientName() != null || client.getTypeclient()!= null) {
                if (client.getClientName().equals(name)&& client.getTypeclient().equals(type)) {
                    return client.getClientid();
                }
            }
        } return 0L;
    }
    public Stream<ClientsAddressDTO> filerTypeName(String type,String name) {
       return clientsService.filerTypeName(type, name).flatMap(this::addressClientConvertToDtoFromClientsDTO);
    }
    @Override
    public Stream<ClientsAddressDTO> filerAddress(String address){
        return addressesService.filerAddress(address).map(this::addressClientConvertToDtoFromAddressDTO);
    }
}

