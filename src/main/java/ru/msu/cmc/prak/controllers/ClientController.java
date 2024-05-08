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
    public String postClientsEdit(@NonNull Model model,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String phoneString,
                                  @RequestParam(required = false) String address,
                                  @RequestParam int id) {
        Long phone = null;
        if (phoneString != null) {
            phone = Long.valueOf(phoneString);
        }
        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (email != null && email.isEmpty()) {
            email = null;
        }
        Client client = clientDAO.getEntityById(id);
        Client client1 = NewClientController.getClient(phone, clientDAO);
        if (client1 != null && !client1.equals(client)) {
            String oldPhone = String.valueOf(client.getPhone());
            String newPhone = phone.toString();
            return ChangeController.getError(model, 2, newPhone, oldPhone, null);
        }
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

