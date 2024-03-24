package ru.msu.cmc.prak.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Brands")
public class Brand implements CommonEntity {
    @Id
    @Column(name = "brand_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Brand other)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        boolean res;
        res = Objects.equals(this.id, other.id);
        res = res && Objects.equals(this.name, other.name);
        return res;
    }
}