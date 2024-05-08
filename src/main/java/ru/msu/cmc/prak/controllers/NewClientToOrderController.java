package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.CarDAO;
import ru.msu.cmc.prak.DAO.ClientDAO;
import ru.msu.cmc.prak.DAO.OrderDAO;
import ru.msu.cmc.prak.entities.Car;
import ru.msu.cmc.prak.entities.Client;
import ru.msu.cmc.prak.entities.Order;
import ru.msu.cmc.prak.entities.Status_Type;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class NewClientToOrderController {
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private OrderDAO orderDAO;

    private String emptyToNull(String t) {
        if (t != null && t.isEmpty()) {
            return null;
        }
        return t;
    }


    @GetMapping(value = {"/newClientToOrder"})
    public String getNewClient(@NonNull Model model,
                               @RequestParam int carId,
                               @RequestParam Long phone,
                               @RequestParam Status_Type status,
                               @RequestParam Boolean test) {
        model.addAttribute("phone", phone);
        model.addAttribute("carId", carId);
        model.addAttribute("status", status);
        model.addAttribute("test_drive", test);
        return "newClientToOrder";
    }

    @PostMapping(value = "/newClientToOrder/add")
    public String postClientsAdd(@RequestParam String name,
                                 @RequestParam int carId,
                                 @RequestParam(required = false) String email,
                                 @RequestParam Long phone,
                                 @RequestParam(required = false) String address,
                                 @RequestParam Status_Type status,
                                 @RequestParam Boolean test_drive) {
        name = emptyToNull(name);
        email = emptyToNull(email);
        Client client = new Client(null, email, address, name, phone);
        clientDAO.save(client);
        Car car = carDAO.getEntityById(carId);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Order order = new Order(null, client, timestamp, status, car, test_drive);
        orderDAO.save(order);
        int id = order.getId();
        return  "redirect:../order?id=" + id;
    }
}
