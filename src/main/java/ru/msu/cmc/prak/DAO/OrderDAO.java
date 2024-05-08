package ru.msu.cmc.prak.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.msu.cmc.prak.entities.*;

import java.util.Date;
import java.util.List;

public interface OrderDAO extends CommonDAO <Order>{
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Filter {
        private Status_Type status;
        private Long phone;
        private Boolean isNeedTestDrive;
        private Date dateFrom;
        private Date dateTo;
    }

    List<Order> getByFilter(Filter filter);
    public Order carOrdered(int id);
}
