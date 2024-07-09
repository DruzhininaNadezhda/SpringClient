package com.example.springj310.controllers;
import com.example.springj310.dto.ClientsAddressDTO;
import com.example.springj310.service.impl.ClientsAddressServiceImpl;
import com.example.springj310.service.interfaces.AddressesService;
import com.example.springj310.service.interfaces.ClientsAddressService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/")
public class ViewListControllers {
    private final ClientsAddressService clientsAddressService;
    private final AddressesService addressesService;

    public ViewListControllers(ClientsAddressService clientsService, AddressesService addressesService) {
        this.clientsAddressService = clientsService;
        this.addressesService = addressesService;
    }

    @GetMapping()
    public String findAll(Model model, @Param("keyword") String type,String name) {
        List<ClientsAddressDTO> clientsAddress = clientsAddressService.findFilter(type, name).toList();
        model.addAttribute("clientsAddress", clientsAddress);
        return "clients_addresses";
    }

    @GetMapping("/remove/{id}")
    public String removeById(@PathVariable("id") Integer id) {
        clientsAddressService.remove(id);
        return "redirect:/";
    }
    @GetMapping("/removeAddress/{id}")
    public String removeById(@PathVariable("id") Long id) {
        if (id != 0) {
            addressesService.remove(id);
        }
        return "redirect:/";
    }
}
