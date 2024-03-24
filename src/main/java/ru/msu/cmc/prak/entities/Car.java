package ru.msu.cmc.prak.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cars")
public class Car implements CommonEntity {
    @Id
    @Column(name = "car_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "model_id")
    @ManyToOne
    private Model model;

    @Column(name = "left_steering_wheel", nullable = false, columnDefinition = "boolean DEFAULT TRUE")
    private boolean left_steering_wheel;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "price", nullable = false)
    private int price;

    @NonNull
    @Column(name = "transmission", nullable = false, columnDefinition = "transmission_type DEFAULT 'MT'")
    @ColumnTransformer(write="?::transmission_type")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Transmission_Type transmission;

    @NonNull
    @Column(name = "drive", nullable = false, columnDefinition = "drive_type DEFAULT 'front'")
    @ColumnTransformer(write="?::drive_type")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Drive_Type drive;

    @NonNull
    @Column(name = "fuel", nullable = false, columnDefinition = "fuel_type DEFAULT 'gasoline'")
    @ColumnTransformer(write="?::fuel_type")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Fuel_Type fuel;

    @Column(name = "configuration")
    @JdbcTypeCode(SqlTypes.JSON)
    private Configuration configuration;

    @Column(name = "features")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> features;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Car other)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        boolean res;
        res = Objects.equals(this.id, other.id);
        res = res && this.left_steering_wheel == other.left_steering_wheel;
        res = res && this.price == other.price;
        res = res && Objects.equals(this.model, other.model);
        res = res && Objects.equals(this.mileage, other.mileage);
        res = res && Objects.equals(this.transmission, other.transmission);
        res = res && Objects.equals(this.drive, other.drive);
        res = res && Objects.equals(this.fuel, other.fuel);
        res = res && Objects.equals(this.features, other.features);
        res = res && Objects.equals(this.configuration, other.configuration);
        return res;
    }
}