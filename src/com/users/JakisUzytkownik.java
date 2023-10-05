package com.users;

public class JakisUzytkownik implements Uzytkownik{
    private String nazwaUzytkownika, rolaUzytkownika;
    public JakisUzytkownik(String nazwaUzytkownika, String rolaUzytkownika){
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.rolaUzytkownika = rolaUzytkownika;
        new UzytkownikDowolny(nazwaUzytkownika, rolaUzytkownika);
    }
}
