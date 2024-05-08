package ru.msu.cmc.prak.entities;


public enum Fuel_Type {
    gas ("Газ"),
    diesel ("Дизель"),
    electricity ("Электричество"),
    gasoline ("Бензин");
    private String readable;
    Fuel_Type(String code){
        this.readable = code;
    }
    public String getReadName() {
        return this.readable;
    }
}