package com.users;

public class UzytkownikPielegniarka implements Uzytkownik{
    private String nazwaUzytkownika, rolaUzytkownika;
    public UzytkownikPielegniarka(String nazwaUzytkownika, String rolaUzytkownika){
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.rolaUzytkownika = rolaUzytkownika;
        new UzytkownikPielegniarski(nazwaUzytkownika, rolaUzytkownika);
    }
}