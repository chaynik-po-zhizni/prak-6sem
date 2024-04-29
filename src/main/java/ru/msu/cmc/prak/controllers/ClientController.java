package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.ClientDAO;
import ru.msu.cmc.prak.entities.Brand;
import ru.msu.cmc.prak.entities.Client;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/client")
    public String getClient(@NonNull Model model, @RequestParam int id) {
        Client client = clientDAO.getEntityById(id);
        model.addAttribute("client", client);
        return "client";
    }

    @PostMapping(value = "/clients/delete")
    public String postClientsDelete(@RequestParam int id) {
        clientDAO.delete(id);

        return "redirect:../clients";
    }

    @PostMapping(value = "/client/edit")
    public String postClientsEdit(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String email,
                                 @RequestParam(required = false) Long phone,
                                 @RequestParam(required = false) String address,
                                 @RequestParam int id) {
        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (email != null && email.isEmpty()) {
            email = null;
        }
        Client client = clientDAO.getEntityById(id);
        if (name != null) {
            client.setFullName(name);
        }
        if (phone != null) {
            client.setPhone(phone);
        }
        client.setAddress(address);
        client.setEmail(email);
        clientDAO.update(client);
        return  "redirect:../client?id=" + id;
    }
}

