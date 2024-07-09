package com.example.springj310.controllers;

import com.example.springj310.dto.ClientsAddressDTO;
import com.example.springj310.service.interfaces.ClientsAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/add")
public class ClientAddressAddContr {
    private final ClientsAddressService clientsAddressService;

    public ClientAddressAddContr(ClientsAddressService clientsAddressService) {
        this.clientsAddressService = clientsAddressService;
    }

    @GetMapping("/creat")
    public String creat(Model model) {
        model.addAttribute("clientsAddressDTO", new ClientsAddressDTO());
        return "creat_page";
    }

    @PostMapping("/creat")
    public String creat(@ModelAttribute("clientsAddressDTO") ClientsAddressDTO clientsAddressDTO, Model model) {
            if (clientsAddressService.clientUniControl(clientsAddressDTO.getClientName(), clientsAddressDTO.getTypeclient().getType(), clientsAddressService.findAll())==0l) {
        model.addAttribute("error", clientsAddressService.create(clientsAddressDTO));
            } else {
            model.addAttribute("error", "Клиент уже существует");
            }
        return "creat_page";
    }
}
