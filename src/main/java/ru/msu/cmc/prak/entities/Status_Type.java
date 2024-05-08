package ru.msu.cmc.prak.entities;


import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;


public enum Status_Type implements Serializable {
    issued ("Создан"),
    in_progress ("В процессе"),
    done ("Готов"),
    canceled ("Отменен");
    private String readable;
    Status_Type(String code){
        this.readable = code;
    }
    public String getReadName() {
        return this.readable;
    }
}