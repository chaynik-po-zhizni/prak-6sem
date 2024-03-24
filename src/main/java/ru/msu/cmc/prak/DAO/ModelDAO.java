package ru.msu.cmc.prak.DAO;

import ru.msu.cmc.prak.entities.Model;

import java.util.List;

public interface ModelDAO extends CommonDAO <Model> {
    List <Model> getModelsByBrand(String brandName);
    Model getModelByName(String brandName, String name);
}
