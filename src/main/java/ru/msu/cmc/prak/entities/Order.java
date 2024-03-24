package ru.msu.cmc.prak.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements CommonEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne()
    private Client client;

    @NonNull
    @Column(name = "date", nullable = false)
    private Timestamp date;

    @NonNull
    @Column(name = "status", nullable = false, columnDefinition = "status_type DEFAULT 'issued'")
    @ColumnTransformer(write="?::status_type")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Status_Type status;

    @NonNull
    @JoinColumn(name = "car_id", nullable = false)
    @OneToOne()
    private Car car;

    @Column(name = "test_drive", nullable = false, columnDefinition = "boolean DEFAULT FALSE")
    private boolean test_drive;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Order other)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        boolean res;
        res = Objects.equals(this.id, other.id);
        res = res && this.test_drive == other.test_drive;
        res = res && Objects.equals(this.client, other.client);
        res = res && Objects.equals(this.date, other.date);
        res = res && Objects.equals(this.status, other.status);
        res = res && Objects.equals(this.car, other.car);
        return res;
    }
}