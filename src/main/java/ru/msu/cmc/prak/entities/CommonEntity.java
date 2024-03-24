package ru.msu.cmc.prak.entities;

import jakarta.persistence.criteria.CriteriaBuilder;

public interface CommonEntity {
    Integer getId() ;
    void setId(Integer id);
}