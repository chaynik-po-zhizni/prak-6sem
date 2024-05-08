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
public class NewClientController {
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private SessionFactory sessionFactory;


    @GetMapping(value = {"/newClient"})
    public String getNewClient() {
        return "newClient";
    }

    @PostMapping(value = "/clients/add")
    public String postClientsAdd(@RequestParam String name,
                                 @RequestParam(required = false) String email,
                                 @RequestParam Long phone,
                                 @RequestParam(required = false) String address) {
        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (email != null && email.isEmpty()) {
            email = null;
        }
        Client client = getClient(phone, clientDAO);
        if (client != null) {
            return  "redirect:../client?id=" + client.getId();
        }
        client = new Client(null, email, address, name, phone);
        clientDAO.save(client);
        int id = client.getId();

        return  "redirect:../client?id=" + id;
    }

    static Client getClient(@RequestParam Long phone, ClientDAO clientDAO) {
        List<Client> clients = clientDAO.getByFilter(new ClientDAO.Filter(phone, null, null));
        if (clients != null
                && !clients.isEmpty()) {
            return clients.get(0);
        }
        return null;
    }
}
