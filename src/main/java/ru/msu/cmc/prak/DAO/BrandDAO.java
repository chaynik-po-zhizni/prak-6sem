package ru.msu.cmc.prak.DAO;

import ru.msu.cmc.prak.entities.Brand;

public interface BrandDAO extends CommonDAO <Brand> {
    Brand getBrandByName(String name);
}
