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
public class NewOrderController {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private SessionFactory sessionFactory;


    @GetMapping(value = {"/newOrder"})
    public String getNewOrder(@NonNull Model model,
                               @RequestParam int carId) {
        model.addAttribute("carId", carId);
        return "newOrder";
    }

    @PostMapping(value = "/orders/add")
    public String postOrdersAdd(@RequestParam Status_Type status,
                                 @RequestParam Long phone,
                                 @RequestParam int carId,
                                 @RequestParam Boolean test_drive) {
        List<Client> clients = clientDAO.getByFilter(new ClientDAO.Filter(phone, null, null));
        if (clients != null
                && !clients.isEmpty()
                && clients.get(0) != null) {
            Car car = carDAO.getEntityById(carId);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Order order = new Order(null, clients.get(0), timestamp, status, car, test_drive);
            orderDAO.save(order);
            int id = order.getId();
            return  "redirect:../order?id=" + id;
        }
        StringBuilder t = new StringBuilder("redirect:../newClientToOrder?carId=");
        t.append(carId);
        t.append("&phone=");
        t.append(phone);
        t.append("&status=");
        t.append(status);
        t.append("&test=");
        t.append(test_drive);
        return  t.toString();
    }


}
