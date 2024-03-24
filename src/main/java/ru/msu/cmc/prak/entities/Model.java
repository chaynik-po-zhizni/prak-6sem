package ru.msu.cmc.prak.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Models")
public class Model implements CommonEntity {
    @Id
    @Column(name = "model_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @JoinColumn(name = "brand_id", nullable = false)
    @ManyToOne()
    private Brand brand;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Model other)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        boolean res;
        res = Objects.equals(this.id, other.id);
        res = res && Objects.equals(this.brand, other.brand);
        res = res && Objects.equals(this.name, other.name);
        return res;
    }
}