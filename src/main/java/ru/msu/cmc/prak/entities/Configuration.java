package ru.msu.cmc.prak.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Configuration implements Serializable {
    private Integer engine_power;
    private Integer engine_capacity;
    private Integer year;
    private Integer num_seats;
    private Integer num_doors;
    private String car_body;
    private String assembly_country;
    private String color;
    private Integer trunk_volume;

    public List<String> getCurrentFields() {
        List<String> res = new ArrayList<>();
        if (engine_power != null) {
            res.add("engine_power");
        }
        if (engine_capacity != null) {
            res.add("engine_capacity");
        }
        if (year != null) {
            res.add("year");
        }
        if (num_seats != null) {
            res.add("num_seats");
        }
        if (num_doors != null) {
            res.add("num_doors");
        }
        if (car_body != null) {
            res.add("car_body");
        }
        if (assembly_country != null) {
            res.add("assembly_country");
        }
        if (color != null) {
            res.add("color");
        }
        if (trunk_volume != null) {
            res.add("trunk_volume");
        }
        return res;
    }
    public static List<String> getFields() {
        List<String> res = new ArrayList<>();
        res.add("engine_power");
        res.add("engine_capacity");
        res.add("year");
        res.add("num_seats");
        res.add("num_doors");
        res.add("car_body");
        res.add("assembly_country");
        res.add("color");
        res.add("trunk_volume");
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Configuration other)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        boolean res;
        res = Objects.equals(this.engine_power, other.engine_power);
        res = res && Objects.equals(this.engine_capacity, other.engine_capacity);
        res = res && Objects.equals(this.year, other.year);
        res = res && Objects.equals(this.num_seats, other.num_seats);
        res = res && Objects.equals(this.num_doors, other.num_doors);
        res = res && Objects.equals(this.car_body, other.car_body);
        res = res && Objects.equals(this.assembly_country, other.assembly_country);
        res = res && Objects.equals(this.color, other.color);
        res = res && Objects.equals(this.trunk_volume, other.trunk_volume);
        return res;
    }

}
