package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.BrandDAO;
import ru.msu.cmc.prak.DAO.CarDAO;
import ru.msu.cmc.prak.DAO.ModelDAO;
import ru.msu.cmc.prak.DAO.OrderDAO;
import ru.msu.cmc.prak.entities.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarsController {
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private ModelDAO modelDAO;
    @Autowired
    private BrandDAO brandDAO;
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private String emptyToNull(String t) {
        if (t != null && t.isEmpty()) {
            return null;
        }
        return t;
    }

    private boolean filterOrder(Boolean order, Car car) {
        if (order == null) {
            return true;
        }
        if (order) {
            return orderDAO.carOrdered(car.getId()) != null;
        } else {
            return orderDAO.carOrdered(car.getId()) == null;
        }
    }

    @GetMapping(value = "/cars")
    public String getCars(@NonNull Model model,
                          @RequestParam(required = false) String mod, /////
                          @RequestParam(required = false) String brand, /////
                          @RequestParam(required = false) Drive_Type driveType, ////
                          @RequestParam(required = false) Transmission_Type transmissionType, ////
                          @RequestParam(required = false) Fuel_Type fuelType, ////
                          @RequestParam(required = false) Integer priceFrom,
                          @RequestParam(required = false) Integer priceTo,
                          @RequestParam(required = false) Integer mileageFrom,
                          @RequestParam(required = false) Integer mileageTo,
                          @RequestParam(required = false) Boolean isLeftSteeringWheel,////
                          @RequestParam(required = false) String color, //
                          @RequestParam(required = false) Integer numSeats, //
                          @RequestParam(required = false) String carBody, //
                          @RequestParam(required = false) Integer enginePowerFrom,
                          @RequestParam(required = false) Integer enginePowerTo,
                          @RequestParam(required = false) String country, //
                          @RequestParam(required = false) Integer yearFrom, ///
                          @RequestParam(required = false) Integer yearTo,
                          @RequestParam(required = false) Boolean order) {
        mod = emptyToNull(mod);
        brand = emptyToNull(brand);
        color = emptyToNull(color);
        carBody = emptyToNull(carBody);
        country = emptyToNull(country);
        CarDAO.Filter filter = new CarDAO.Filter(mod, brand, driveType,
                transmissionType, fuelType, priceFrom,
                priceTo, mileageFrom, mileageTo,
                isLeftSteeringWheel, color, numSeats,
                carBody, enginePowerFrom, enginePowerTo,
                country, yearFrom, yearTo);
        List<Car> carList = new ArrayList<>();
        for (Car car: carDAO.getByFilter(filter)) {
            if (filterOrder(order, car)) {
                carList.add(car);
            }
        }
        model.addAttribute("colorList", carDAO.getValuesFiled("color"));
        model.addAttribute("carList", carList);
        model.addAttribute("bodyList", carDAO.getValuesFiled("car_body"));
        model.addAttribute("countryList", carDAO.getValuesFiled("assembly_country"));
        model.addAttribute("seatsList", carDAO.getValuesFiled("num_seats"));
        model.addAttribute("brandList", brandDAO.getAll());
        model.addAttribute("modelList", modelDAO.getAll());
        Integer begin = carDAO.getMinYearValue();
        if (begin != null) {
            int end = Year.now().getValue();
            List<Integer> ret = new ArrayList<>(end - begin + 1);
            for (int i=begin; i<=end; i++) {
                ret.add(i);
            }
            model.addAttribute("yearList", ret);
        }
        return "cars";
    }
}

