package com.example.springj310.controllers;
import com.example.springj310.dto.ClientsDTO;
import com.example.springj310.service.interfaces.ClientsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientsUpdateContr {
    private final ClientsService clientsService;

    public ClientsUpdateContr(ClientsService clientsService) {
        this.clientsService = clientsService;
    }
    @GetMapping("update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("clientsDTO", clientsService.findClientsDtoById(id));
        return "update_page";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("clientsDTO") ClientsDTO clientsDTO, Model model) {
        model.addAttribute("error", clientsService.update(clientsDTO));
        model.addAttribute("clientsDTO", clientsService.findClientsDtoById(clientsDTO.getClientid()));
        return "update_page";
    }
}
