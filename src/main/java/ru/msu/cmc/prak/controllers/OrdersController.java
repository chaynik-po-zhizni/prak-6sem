package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.OrderDAO;
import ru.msu.cmc.prak.entities.Order;
import ru.msu.cmc.prak.entities.Status_Type;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrdersController {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/orders")
    public String getOrders(@NonNull Model model,
                            @RequestParam(required = false) Integer id,
                            @RequestParam(required = false) Status_Type status,
                            @RequestParam(required = false) Boolean testDrive,
                            @RequestParam(required = false) String dateFrom,
                            @RequestParam(required = false) String dateTo,
                            @RequestParam(required = false) Long phone) {
        if (id != null) {
            Order order = orderDAO.getEntityById(id);
            if (order != null) {
                List<Order> orderList  = new ArrayList<>();
                orderList.add(order);
                model.addAttribute("orderList", orderList);
                return "orders";
            }
        }
        Date from = null, to = null;
        if (dateFrom != null && !dateFrom.isEmpty()) {
            from = Timestamp.valueOf(LocalDateTime.parse(dateFrom));
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            to = Timestamp.valueOf(LocalDateTime.parse(dateTo));
        }
        OrderDAO.Filter filter = new OrderDAO.Filter(status, phone, testDrive, from, to);
        model.addAttribute("orderList", orderDAO.getByFilter(filter));
        return "orders";
    }
}
