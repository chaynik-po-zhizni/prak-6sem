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
import ru.msu.cmc.prak.DAO.*;
import ru.msu.cmc.prak.entities.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private ModelDAO modelDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private BrandDAO brandDAO;
    @Autowired
    private SessionFactory sessionFactory;

    private String emptyToNull(String t) {
        if (t != null && t.isEmpty()) {
            return null;
        }
        return t;
    }
    private List<String> stringToList(String t) {
        if (t == null) {
            return null;
        }
        String[] res = t.split(";");
        System.out.println(res);
        return List.of(res);
    }

    @GetMapping(value = "/car/{id}")
    public String getCar1(@NonNull Model model, @PathVariable int id) {
        return getCar(model, id);
    }
    @GetMapping(value = "/car")
    public String getCar2(@NonNull Model model, @RequestParam int id) {
        return getCar(model, id);
    }

    private String getCar(@NonNull Model model, int id) {
        Car car = carDAO.getEntityById(id);
        model.addAttribute("car", car);
        model.addAttribute("maxYear", Year.now().getValue());
        model.addAttribute("isOrdered", orderDAO.carOrdered(car.getId()) != null);
        model.addAttribute("brandList", brandDAO.getAll());
        model.addAttribute("modelList", modelDAO.getAll());
        return "car";
    }

    @GetMapping(value = "/cars/delete")
    public String postCarsDelete(@RequestParam int id) {
        carDAO.delete(id);

        return "redirect:../cars";
    }
    @PostMapping(value = "/car/edit")
    public String postCarsEdit(@RequestParam int id,
                               @RequestParam(required = false) String brandName,//
                               @RequestParam(required = false) String modelName,//
                               @RequestParam(required = false) Boolean left,//
                               @RequestParam(required = false) Integer mileage,//
                               @RequestParam int price,//
                               @RequestParam(required = false) Transmission_Type transmission,//
                               @RequestParam(required = false) Drive_Type drive,//
                               @RequestParam(required = false) Fuel_Type fuel,//
                               @RequestParam(required = false)  Integer power,//
                               @RequestParam(required = false)  Integer capacity,
                               @RequestParam(required = false)  Integer year,
                               @RequestParam(required = false)  Integer numSeats,
                               @RequestParam(required = false)  Integer numDoors,
                               @RequestParam(required = false)  String carBody,
                               @RequestParam(required = false)  String country,
                               @RequestParam(required = false)  String color,
                               @RequestParam(required = false)  Integer volume,
                               @RequestParam(required = false) String inform) {
        Car car = carDAO.getEntityById(id);
        brandName = emptyToNull(brandName);
        modelName = emptyToNull(modelName);
        carBody = emptyToNull(carBody);
        country = emptyToNull(country);
        color = emptyToNull(color);
        inform = emptyToNull(inform);
        ru.msu.cmc.prak.entities.Model mod = modelDAO.getModelByName(brandName, modelName);
        if (mod != null) {
            car.setModel(mod);
        }
        if (left != null) {
            car.setLeft_steering_wheel(left);
        }
        car.setPrice(price);
        car.setMileage(mileage);
        car.setTransmission(transmission);
        car.setDrive(drive);
        car.setFuel(fuel);
        Configuration configur = car.getConfiguration();
        if (configur != null) {
            configur.setCar_body(carBody);
            configur.setColor(color);
            configur.setEngine_capacity(capacity);
            configur.setEngine_power(power);
            configur.setNum_doors(numDoors);
            configur.setNum_seats(numSeats);
            configur.setYear(year);
            configur.setTrunk_volume(volume);
            configur.setAssembly_country(country);
        } else {
            if (carBody != null
                || color != null
                || capacity != null
                || power != null
                || numDoors != null
                || numSeats != null
                || year != null
                || volume != null
                || country != null) {
                configur = new Configuration(power, capacity, year,
                                             numSeats, numDoors, carBody,
                                             country, color, volume);
            }
        }
        car.setConfiguration(configur);
        car.setFeatures(stringToList(inform));
        carDAO.update(car);
        return  "redirect:../car?id=" + id;
    }
}

