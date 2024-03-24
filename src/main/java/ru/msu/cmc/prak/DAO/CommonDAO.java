package ru.msu.cmc.prak.DAO;

import ru.msu.cmc.prak.entities.CommonEntity;

import java.util.List;

public interface CommonDAO <E extends CommonEntity> {
    public  List<E> getAll();
    public  E getEntityById(int id);
    public  E update(E entity);
    public  void delete(int id);
    public  void delete(E entity);
    public  void save(E entity);
}
