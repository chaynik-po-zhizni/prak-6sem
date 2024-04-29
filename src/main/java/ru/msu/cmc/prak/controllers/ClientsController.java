package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.ClientDAO;
import ru.msu.cmc.prak.entities.Client;

import java.util.List;

@Controller
public class ClientsController {
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/clients")
    public String getClients(@NonNull Model model,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) Long phone) {
        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (email != null && email.isEmpty()) {
            email = null;
        }
        ClientDAO.Filter filter = new ClientDAO.Filter(phone, email, name);
        model.addAttribute("clientList", clientDAO.getByFilter(filter));
        return "clients";
    }
}

