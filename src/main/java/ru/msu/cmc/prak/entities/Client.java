package ru.msu.cmc.prak.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Clients")
public class Client implements CommonEntity {
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false, unique = true)
    private long phone;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Client other)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        boolean res;
        res = Objects.equals(this.id, other.id);
        res = res && this.phone == other.phone;
        res = res && Objects.equals(this.email, other.email);
        res = res && Objects.equals(this.address, other.address);
        res = res && Objects.equals(this.fullName, other.fullName);
        return res;
    }

    public String getStringPhone() {
        String res = String.valueOf(phone);
        if (res.length() > 10) {
            res = res.substring(res.length() - 10);
        }
        if (res.length() < 10) {
            res = "0".repeat(10 - res.length()) + res;
        }
        return res;
    }
}