package com.example.springj310.controllers;
import com.example.springj310.dto.AddressesDTO;
import com.example.springj310.service.interfaces.AddressesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/addresses")
public class AddressContr {
    private final AddressesService addressesService;

    public AddressContr(AddressesService addressesService) {
        this.addressesService = addressesService;
    }

    @GetMapping("/remove/{id}")
    public String removeById(@PathVariable("id") Long id, @RequestParam ("clientid1") Long clientid, Model model ) {
        if (id != 0) {
            addressesService.remove(id);
        }else {}
        model.addAttribute("addresses", addressesService.findAddressesDtoBClientId(clientid));
        model.addAttribute("clientId", clientid);
        model.addAttribute("newAddress", new AddressesDTO());
        return "update_address";
    }

    @GetMapping("/findClient/{id}")
    public String findByClientId(Model model, @PathVariable Long id) {
        model.addAttribute("addresses", addressesService.findAddressesDtoBClientId(id));
        model.addAttribute("clientId", id);
        model.addAttribute("newAddress", new AddressesDTO());
        return "update_address";
    }
    @PostMapping("/creat")
    public String creat(@ModelAttribute("newAddress") AddressesDTO addressesDTO, Model model) {
        model.addAttribute("error",addressesService.create(addressesDTO));
        model.addAttribute("addresses", addressesService.findAddressesDtoBClientId(addressesDTO.getClientid()));
        model.addAttribute("clientId", addressesDTO.getClientid());
        model.addAttribute("newAddress", new AddressesDTO());
        return "update_address";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("addressesDTO") AddressesDTO addressesDTO, Model model) {
        model.addAttribute("error",addressesService.update(addressesDTO));
        model.addAttribute("addresses", addressesService.findAddressesDtoBClientId(addressesDTO.getClientid()));
        model.addAttribute("clientId", addressesDTO.getClientid());
        model.addAttribute("newAddress", new AddressesDTO());
        return "update_address";
    }
    }
