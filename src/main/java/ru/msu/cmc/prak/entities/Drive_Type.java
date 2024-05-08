package ru.msu.cmc.prak.entities;


public enum Drive_Type {
    front ("Передний"),
    rear ("Задний"),
    four ("Полный");
    private String readable;
    Drive_Type(String code){
        this.readable = code;
    }
    public String getReadName() {
        return this.readable;
    }
}