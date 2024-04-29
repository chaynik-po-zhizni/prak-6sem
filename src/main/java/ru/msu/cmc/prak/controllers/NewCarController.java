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
import ru.msu.cmc.prak.DAO.ModelDAO;
import ru.msu.cmc.prak.entities.*;

import java.util.List;

@Controller
public class NewCarController {
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private ModelDAO modelDAO;
    @Autowired
    private SessionFactory sessionFactory;

    private String emptyToNull(String t) {
        if (t != null && t.isEmpty()) {
            return null;
        }
        return t;
    }
    private List<String> stringToList(String t) {
        String[] res = t.split(";");
        System.out.println(res);
        return List.of(res);
    }

    @GetMapping(value = {"/newCar"})
    public String getNewCar() {
        return "newCar";
    }

    @PostMapping(value = "/cars/add")
    public String postCarsAdd(@RequestParam(required = false) String brandName,
                              @RequestParam(required = false) String modelName,
                              @RequestParam(required = false) Boolean left,
                              @RequestParam(required = false) Integer mileage,
                              @RequestParam int price,
                              @RequestParam(required = false) Transmission_Type transmission,
                              @RequestParam(required = false) Drive_Type drive,
                              @RequestParam(required = false) Fuel_Type fuel,
                              @RequestParam(required = false)  Integer power,
                              @RequestParam(required = false)  Integer capacity,
                              @RequestParam(required = false)  Integer year,
                              @RequestParam(required = false)  Integer numSeats,
                              @RequestParam(required = false)  Integer numDoors,
                              @RequestParam(required = false)  String carBody,
                              @RequestParam(required = false)  String country,
                              @RequestParam(required = false)  String color,
                              @RequestParam(required = false)  Integer volume,
                              @RequestParam(required = false) String inform) {
        brandName = emptyToNull(brandName);
        modelName = emptyToNull(modelName);
        carBody = emptyToNull(carBody);
        country = emptyToNull(country);
        color = emptyToNull(color);
        inform = emptyToNull(inform);
        boolean isLeft = true;
        if (left != null) {
            isLeft = left;
        }
        Configuration configur = new Configuration(power, capacity, year,
                numSeats, numDoors, carBody,
                country, color, volume);

        Car car = new Car(null, modelDAO.getModelByName(brandName, modelName),
                isLeft, mileage, price, transmission, drive, fuel,
                configur, stringToList(inform));
        carDAO.save(car);
        int id = car.getId();

        return  "redirect:../car?id=" + id;
    }
}

