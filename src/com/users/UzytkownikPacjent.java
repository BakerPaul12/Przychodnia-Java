package com.users;

public class UzytkownikPacjent implements Uzytkownik{
    private String nazwaUzytkownika, rolaUzytkownika;
    public UzytkownikPacjent(String nazwaUzytkownika, String rolaUzytkownika){
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.rolaUzytkownika = rolaUzytkownika;
        new UzytkownikPacjentowy(nazwaUzytkownika, rolaUzytkownika);
    }
}