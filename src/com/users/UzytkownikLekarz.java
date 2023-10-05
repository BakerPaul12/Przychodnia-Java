package com.users;

public class UzytkownikLekarz implements Uzytkownik{
    private String nazwaUzytkownika, rolaUzytkownika;
    public UzytkownikLekarz(String nazwaUzytkownika, String rolaUzytkownika){
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.rolaUzytkownika = rolaUzytkownika;
        new UzytkownikLekarski(nazwaUzytkownika, rolaUzytkownika);
    }
}
