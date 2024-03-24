package ru.msu.cmc.prak.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.msu.cmc.prak.entities.Car;
import ru.msu.cmc.prak.entities.Drive_Type;
import ru.msu.cmc.prak.entities.Fuel_Type;
import ru.msu.cmc.prak.entities.Transmission_Type;

import java.util.List;

public interface CarDAO extends CommonDAO <Car>{

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Filter {
        private String model;
        private String brand;
        private Drive_Type driveType;
        private Transmission_Type transmissionType;
        private Fuel_Type fuelType;
        private Integer priceFrom;
        private Integer priceTo;
        private Integer mileageFrom;
        private Integer mileageTo;
        private Boolean isLeftSteeringWheel;

        private String color;
        private Integer numSeats;
        private String carBody;
        private Integer enginePowerFrom;
        private Integer enginePowerTo;
        private String country;
        private Integer yearFrom;
        private Integer yearTo;
    }

    List <Car> getByFilter(Filter filter);

    List <String> getValuesFiled(String field);

    Integer getMinYearValue();
}
