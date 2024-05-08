package ru.msu.cmc.prak.entities;


public enum Transmission_Type {
    MT ("Механика"),
    AT ("Автомат"),
    CVT ("Вариатор"),
    Robot ("Робот");
    private String readable;
    Transmission_Type(String code){
        this.readable = code;
    }
    public String getReadName() {
        return this.readable;
    }
}