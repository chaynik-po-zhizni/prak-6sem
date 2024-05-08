package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.OrderDAO;
import ru.msu.cmc.prak.entities.Client;
import ru.msu.cmc.prak.entities.Order;
import ru.msu.cmc.prak.entities.Status_Type;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/order")
    public String getOrder(@NonNull Model model, @RequestParam int id) {
        Order order = orderDAO.getEntityById(id);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping(value = "/orderCar")
    public String getCarOrder(@NonNull Model model, @RequestParam int id) {
        List<Order> orders = orderDAO.getAll();
        for (Order order: orders) {
            if (order.getCar().getId() == id) {
                model.addAttribute("order", order);
                break;
            }
        }
        return "order";
    }

    @PostMapping(value = "/orders/delete")
    public String postOrdersDelete(@RequestParam int id) {
        orderDAO.delete(id);

        return "redirect:../orders";
    }

    @PostMapping(value = "/order/edit")
    public String postOrdersEdit(@RequestParam Status_Type status,
                                  @RequestParam Boolean test_drive,
                                  @RequestParam int id) {
        System.out.println(status.getReadName());
        System.out.println(test_drive);
        System.out.println(id);
        Order order = orderDAO.getEntityById(id);
        order.setStatus(status);
        order.setTest_drive(test_drive);
        orderDAO.update(order);
        System.out.println(order.getStatus().getReadName());
        System.out.println(order.isTest_drive());
        System.out.println(order.getId());
        return  "redirect:../order?id=" + id;
    }
}
