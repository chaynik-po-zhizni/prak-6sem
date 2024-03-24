package ru.msu.cmc.prak.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.msu.cmc.prak.entities.*;

import java.util.List;

public interface ClientDAO extends CommonDAO <Client> {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Filter {
        private Long phone;
        private String email;
        private String name;
    }

    List<Client> getByFilter(Filter filter);
}
