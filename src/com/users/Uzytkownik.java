package com.users;

import com.login.LoginScreen;
import com.okienka.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface Uzytkownik {
    class UzytkownikDowolny extends JFrame {
        public PanelKtorys panelDol;
        public PanelSpecyficznyGora panelGora;
        public PanelSpecyficznySrodek panelSrodek;
        private String nazwaUzytkownika, rolaUzytkownika;
        private int rows, cols;
        public UzytkownikDowolny(String nazwaUzytkownika, String rolaUzytkownika) {
            this.nazwaUzytkownika = nazwaUzytkownika;
            this.rolaUzytkownika = rolaUzytkownika;
            /*
            >>>>>>>>>>>>>>>>>>>>>>>>>>>
            ROLE I OD NICH ZALEZA ROWS COLS
            >>>>>>>>>>>>>>>>>>>>>>>>>>>
             */
            setTitle("UzytkownikDowolny");
            if(rolaUzytkownika == "Lekarz"){
                rows = 2;
                cols = 3;
                setTitle("UzytkownikLek");
            }
            if(rolaUzytkownika == "Pielęgniarka"){
                rows = 2;
                cols = 2;
                setTitle("UzytkownikPiel");
            }
            if(rolaUzytkownika == "Pacjent"){
                rows = 1;
                cols = 3;
                setTitle("UzytkownikPac");
            }
            ImageIcon logo = new ImageIcon("SymbolPrzychodni.png");
            setIconImage(logo.getImage());
            setBounds(0, 0, 720, 720);
            panelGora = new PanelSpecyficznyGora(150,30,140,this);
            panelSrodek = new PanelSpecyficznySrodek(3,3,50,this);
            panelDol = new PanelKtorys(1,1,50,150,200);
            setLayout(new BorderLayout());
            this.add(panelGora, BorderLayout.NORTH);
            this.add(panelSrodek, BorderLayout.CENTER);
            this.add(panelDol, BorderLayout.SOUTH);
            setResizable(false);
            setLocationByPlatform(true);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        class PanelKtorys extends JPanel{
            private int liczbaKolumn;
            private int liczbaWierszy;
            private Kafelek tablicaKafelkow[][];
            public PanelKtorys(int liczbaKolumn, int liczbaWierszy, int kolorR, int kolorG, int kolorB){
                this.liczbaKolumn = liczbaKolumn;
                this.liczbaWierszy = liczbaWierszy;
                setLayout(new GridLayout(liczbaWierszy,liczbaKolumn,0,0));
                setBackground(new Color(kolorR, kolorG, kolorB));
                setVisible(true);
                tablicaKafelkow = new Kafelek[liczbaWierszy][liczbaKolumn];
                for(int i = 0; i < liczbaWierszy; i++){
                    for(int j = 0; j < liczbaKolumn; j++){
                        tablicaKafelkow[i][j] = new Kafelek(100,100,100,
                                70,70, "hi", new KafelekListenerExp(this.tablicaKafelkow[i][j]));
                        tablicaKafelkow[i][j].addActionListener(new KafelekListenerExp(tablicaKafelkow[i][j]));
                        this.add(tablicaKafelkow[i][j]);
                    }
                }
            }
            public int getLiczbaKolumn(){
                return liczbaKolumn;
            }
            public int getLiczbaWierszy(){
                return  liczbaWierszy;
            }
            public Kafelek[][] getTablicaKafelkow() {
                return tablicaKafelkow;
            }
            public void setTablicaKafelkow(Kafelek tablicaKafelkow[][]){
                this.tablicaKafelkow = tablicaKafelkow;
            }
        }
        // PANEL SPECYFICZNY - UMOZLIWIA POLACZENIE TAKIE JAK CHCESZ KAFELKOW Z ICH ACTION LISTENERAMI
        class PanelSpecyficznyGora extends PanelKtorys{
            private Kafelek tablicaKafelkow[][];
            private UzytkownikDowolny mojUzytkownik;
            private JLabel zalogowanyJakoLabel, zalogowanaRola;
            public PanelSpecyficznyGora(int kolorR, int kolorG, int kolorB,
                                        UzytkownikDowolny mojUzytkownik){
                super(2, 2, kolorR, kolorG, kolorB);
                setTablicaKafelkow(new Kafelek[2][2]);
                this.mojUzytkownik = mojUzytkownik;
                // usuwa guziki z superklasy
                super.remove(super.getTablicaKafelkow()[0][0]);
                super.remove(super.getTablicaKafelkow()[0][1]);
                super.remove(super.getTablicaKafelkow()[1][0]);
                super.remove(super.getTablicaKafelkow()[1][1]);
                //dodaje label zalogowanyJakoLabel
                //zalogowanyJakoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                zalogowanyJakoLabel = new JLabel(" zalogowany jako: " + mojUzytkownik.nazwaUzytkownika);
                zalogowanyJakoLabel.setFont(new Font("Courier", Font.BOLD, 15));
                zalogowanyJakoLabel.setBackground(new Color(125,50,185));
                zalogowanyJakoLabel.setOpaque(true);
                super.add(zalogowanyJakoLabel);
                //dodaje label zalogowanaRola
                zalogowanaRola = new JLabel(" rola: " + mojUzytkownik.rolaUzytkownika);
                zalogowanaRola.setFont(new Font("Courier", Font.BOLD, 15));
                zalogowanyJakoLabel.setBackground(new Color(15,250,185));
                zalogowanyJakoLabel.setOpaque(true);
                super.add(zalogowanaRola);
                //upewniam sie o dobre wymiary
                assert getTablicaKafelkow()[0].length == 2;
                assert getTablicaKafelkow()[1].length == 2;
                //laduje odpowiednie action listeners do odp kafelkow
                getTablicaKafelkow()[0][0] = new Kafelek(150,130,200,
                        70,70, "Log Out",
                        new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                // nie dodawaj, podwojnie sie zrobi
                //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                super.add(getTablicaKafelkow()[0][0]);
                getTablicaKafelkow()[0][1] = new Kafelek(50,180,200,
                        70,70, "Change Password",
                        new KafelekListenerChangePasswd(getTablicaKafelkow()[0][1]));
                super.add(getTablicaKafelkow()[0][1]);
            }
            @Override
            public Kafelek[][] getTablicaKafelkow() {
                return tablicaKafelkow;
            }
            @Override
            public void setTablicaKafelkow(Kafelek tablicaKafelkow[][]){
                this.tablicaKafelkow = tablicaKafelkow;
            }
        }
        class PanelSpecyficznySrodek extends PanelKtorys{
            private Kafelek tablicaKafelkow[][];
            private UzytkownikDowolny mojUzytkownik;
            public PanelSpecyficznySrodek(int kolorR, int kolorG, int kolorB,
                                        UzytkownikDowolny mojUzytkownik){
                super(cols, rows, kolorR, kolorG, kolorB);
                setTablicaKafelkow(new Kafelek[rows][cols]);
                this.mojUzytkownik = mojUzytkownik;
                // usuwa guziki z superklasy
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < cols; j++){
                        super.remove(super.getTablicaKafelkow()[i][j]);
                    }
                }
                //upewniam sie o dobre wymiary
                assert getTablicaKafelkow()[0].length == rows;
                assert getTablicaKafelkow()[1].length == cols;
                if(rolaUzytkownika.equals("Lekarz")) {
                    //laduje odpowiednie action listeners do odp kafelkow
                    getTablicaKafelkow()[0][0] = new Kafelek(220, 40, 200,
                            70, 70, "Sprawdz Badanie",
                            new KafelekListenerSprawdzBadanie(getTablicaKafelkow()[0][0]));
                    // nie dodawaj, podwojnie sie zrobi
                    //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                    super.add(getTablicaKafelkow()[0][0]);
                    getTablicaKafelkow()[0][1] = new Kafelek(120, 220, 20,
                            70, 70, "Sprawdz Wizyte",
                            new KafelekListenerSprawdzWizyte(getTablicaKafelkow()[0][1]));
                    super.add(getTablicaKafelkow()[0][1]);
                    getTablicaKafelkow()[0][2] = new Kafelek(240, 180, 0,
                            70, 70, "Sprawdz Szczepienie",
                            new KafelekListenerSprawdzSzczepienie(getTablicaKafelkow()[0][2]));
                    super.add(getTablicaKafelkow()[0][2]);
                    getTablicaKafelkow()[1][0] = new Kafelek(20, 20, 230,
                            70, 70, "Zlec Badanie",
                            new KafelekListenerZlecBadanie(getTablicaKafelkow()[1][0]));
                    super.add(getTablicaKafelkow()[1][0]);
                    getTablicaKafelkow()[1][1] = new Kafelek(200, 120, 200,
                            70, 70, "Zlec Szczepienie",
                            new KafelekListenerZlecSzczepienie(getTablicaKafelkow()[1][1]));
                    super.add(getTablicaKafelkow()[1][1]);
                    getTablicaKafelkow()[1][2] = new Kafelek(0, 230, 120,
                            70, 70, "Zlec Wizyte",
                            new KafelekListenerZlecWizyte(getTablicaKafelkow()[1][2]));
                    super.add(getTablicaKafelkow()[1][2]);
                }
                if(rolaUzytkownika.equals("Pielęgniarka")) {
                    //laduje odpowiednie action listeners do odp kafelkow
                    getTablicaKafelkow()[0][0] = new Kafelek(220, 40, 200,
                            70, 70, "Sprawdz Badanie",
                            new KafelekListenerSprawdzBadanie(getTablicaKafelkow()[0][0]));
                    // nie dodawaj, podwojnie sie zrobi
                    //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                    super.add(getTablicaKafelkow()[0][0]);
                    getTablicaKafelkow()[0][1] = new Kafelek(120, 220, 20,
                            70, 70, "Sprawdz Wizyte",
                            new KafelekListenerSprawdzWizyte(getTablicaKafelkow()[0][1]));
                    super.add(getTablicaKafelkow()[0][1]);
                    getTablicaKafelkow()[1][0] = new Kafelek(240, 180, 0,
                            70, 70, "Sprawdz Szczepienie",
                            new KafelekListenerSprawdzSzczepienie(getTablicaKafelkow()[1][0]));
                    super.add(getTablicaKafelkow()[1][0]);
                    getTablicaKafelkow()[1][1] = new Kafelek(200, 120, 200,
                            70, 70, "Zlec Szczepienie",
                            new KafelekListenerZlecSzczepienie(getTablicaKafelkow()[1][1]));
                    super.add(getTablicaKafelkow()[1][1]);
                }
                if(rolaUzytkownika.equals("Pacjent")) {
                    //laduje odpowiednie action listeners do odp kafelkow
                    getTablicaKafelkow()[0][0] = new Kafelek(220, 40, 200,
                            70, 70, "Sprawdz Badanie",
                            new KafelekListenerSprawdzBadanie(getTablicaKafelkow()[0][0]));
                    // nie dodawaj, podwojnie sie zrobi
                    //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                    super.add(getTablicaKafelkow()[0][0]);
                    getTablicaKafelkow()[0][1] = new Kafelek(120, 220, 20,
                            70, 70, "Sprawdz Wizyte",
                            new KafelekListenerSprawdzWizyte(getTablicaKafelkow()[0][1]));
                    super.add(getTablicaKafelkow()[0][1]);
                    getTablicaKafelkow()[0][2] = new Kafelek(240, 180, 0,
                            70, 70, "Sprawdz Szczepienie",
                            new KafelekListenerSprawdzSzczepienie(getTablicaKafelkow()[0][2]));
                    super.add(getTablicaKafelkow()[0][2]);
                }
            }
            @Override
            public Kafelek[][] getTablicaKafelkow() {
                return tablicaKafelkow;
            }
            @Override
            public void setTablicaKafelkow(Kafelek tablicaKafelkow[][]){
                this.tablicaKafelkow = tablicaKafelkow;
            }
        }
        class Kafelek extends JButton{
            public Kafelek(int kolorR, int kolorG, int kolorB, int szerokoscPola, int wysokoscPola,
                           String tekstKafelka, ActionListener zachowanieKafelka){
                setBounds(0,0,szerokoscPola,wysokoscPola);
                setFocusable(false);
                setBackground(new Color(kolorR, kolorG, kolorB));
                setFont(new Font("Courier", Font.BOLD, 15));
                setText(tekstKafelka);
                addActionListener(zachowanieKafelka);
            }
        }
        // tu bylby zbior klas KafelekListener, ktore robia rozne fajne rzeczy, ALBO dac taki zbior do konkretnego uzytkownika
        class KafelekListenerExp implements ActionListener{
            private Kafelek mojKafelek;
            public KafelekListenerExp(Kafelek mojKafelek){
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == mojKafelek)
                    System.out.println("oto ja,funkcja kafelka");
            }
            public void setMojKafelek(Kafelek tenKafelekChce){
                mojKafelek = tenKafelekChce;
            }
        }
        class KafelekListenerLogout extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerLogout(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginScreen();
            }
        }
        class KafelekListenerChangePasswd extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerChangePasswd(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new ZmienHaslo(nazwaUzytkownika, rolaUzytkownika);
            }
        }
        class KafelekListenerSprawdzBadanie extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerSprawdzBadanie(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new SprawdzBadanie(nazwaUzytkownika, rolaUzytkownika);
            }
        }
        class KafelekListenerSprawdzSzczepienie extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerSprawdzSzczepienie(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new SprawdzSzczepienie(nazwaUzytkownika, rolaUzytkownika);
            }
        }
        class KafelekListenerSprawdzWizyte extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerSprawdzWizyte(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new SprawdzWizyte(nazwaUzytkownika, rolaUzytkownika);
            }
        }
        // TEN KAFELEK DODAJ TYLKO LEKARZOM
        class KafelekListenerZlecBadanie extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerZlecBadanie(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new ZlecBadanie();
            }
        }
        // TEN KAFELEK DODAJ TYLKO LEKARZOM
        class KafelekListenerZlecWizyte extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerZlecWizyte(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new ZlecWizyte();
            }
        }
        // TEN KAFELEK DODAJ TYLKO LEKARZOM I PIELEGNIARKOM
        class KafelekListenerZlecSzczepienie extends KafelekListenerExp{
            private Kafelek mojKafelek;
            public KafelekListenerZlecSzczepienie(Kafelek mojKafelek){
                super(mojKafelek);
                this.mojKafelek = mojKafelek;
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                new ZlecSzczepienie();
            }
        }
    }
    class UzytkownikLekarski extends UzytkownikDowolny{
        private String nazwaUzytkownika, rolaUzytkownika;
        private PanelSpecyficznySrodekLekarz lekarski;
        public UzytkownikLekarski(String nazwaUzytkownika, String rolaUzytkownika){
            super(nazwaUzytkownika, rolaUzytkownika);
            this.nazwaUzytkownika = nazwaUzytkownika;
            this.rolaUzytkownika = rolaUzytkownika;

        }
        class PanelSpecyficznySrodekLekarz extends PanelSpecyficznySrodek{
            private Kafelek tablicaKafelkow[][];
            private UzytkownikDowolny mojUzytkownik;
            public PanelSpecyficznySrodekLekarz(int kolorR, int kolorG, int kolorB,
                                          UzytkownikDowolny mojUzytkownik) {
                super(kolorR, kolorG, kolorB,mojUzytkownik);
                setTablicaKafelkow(new Kafelek[2][3]);
                this.mojUzytkownik = mojUzytkownik;
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        super.remove(super.getTablicaKafelkow()[i][j]);
                    }
                }
                // rozebrana tablica super
                //upewniam sie o dobre wymiary
                assert getTablicaKafelkow()[0].length == 2;
                assert getTablicaKafelkow()[1].length == 3;
                //laduje odpowiednie action listeners do odp kafelkow
                getTablicaKafelkow()[0][0] = new Kafelek(220,40,200,
                        70,70, "Sprawdz Badanie",
                        new KafelekListenerSprawdzBadanie(getTablicaKafelkow()[0][0]));
                // nie dodawaj, podwojnie sie zrobi
                //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                super.add(getTablicaKafelkow()[0][0]);
                getTablicaKafelkow()[0][1] = new Kafelek(120,220,20,
                        70,70, "Sprawdz Wizyte",
                        new KafelekListenerSprawdzWizyte(getTablicaKafelkow()[0][1]));
                super.add(getTablicaKafelkow()[0][1]);
                getTablicaKafelkow()[0][2] = new Kafelek(240,180,0,
                        70,70, "Sprawdz Szczepienie",
                        new KafelekListenerSprawdzSzczepienie(getTablicaKafelkow()[0][2]));
                super.add(getTablicaKafelkow()[0][2]);
                getTablicaKafelkow()[1][0] = new Kafelek(20,20,230,
                        70,70, "Zlec Badanie",
                        new KafelekListenerZlecBadanie(getTablicaKafelkow()[1][0]));
                super.add(getTablicaKafelkow()[1][0]);
                getTablicaKafelkow()[1][1] = new Kafelek(200,120,200,
                        70,70, "Zlec Szczepienie",
                        new KafelekListenerZlecSzczepienie(getTablicaKafelkow()[1][1]));
                super.add(getTablicaKafelkow()[1][1]);
                getTablicaKafelkow()[1][2] = new Kafelek(0,230,120,
                        70,70, "Zlec Wizyte",
                        new KafelekListenerZlecWizyte(getTablicaKafelkow()[1][2]));
                super.add(getTablicaKafelkow()[1][2]);
            }
            @Override
            public Kafelek[][] getTablicaKafelkow() {
                return tablicaKafelkow;
            }
            @Override
            public void setTablicaKafelkow(Kafelek tablicaKafelkow[][]){
                this.tablicaKafelkow = tablicaKafelkow;
            }
        }
    }
    class UzytkownikPielegniarski extends UzytkownikDowolny{
        private String nazwaUzytkownika, rolaUzytkownika;
        public UzytkownikPielegniarski(String nazwaUzytkownika, String rolaUzytkownika){
            super(nazwaUzytkownika, rolaUzytkownika);
            this.nazwaUzytkownika = nazwaUzytkownika;
            this.rolaUzytkownika = rolaUzytkownika;
            super.panelSrodek = new PanelSpecyficznySrodekPielegniarka(3,3,50,this);
            super.panelSrodek.revalidate();
        }
        class PanelSpecyficznySrodekPielegniarka extends PanelSpecyficznySrodek{
            private Kafelek tablicaKafelkow[][];
            private UzytkownikDowolny mojUzytkownik;
            public PanelSpecyficznySrodekPielegniarka(int kolorR, int kolorG, int kolorB,
                                                UzytkownikDowolny mojUzytkownik) {
                super(kolorR, kolorG, kolorB,mojUzytkownik);
                setTablicaKafelkow(new Kafelek[2][2]);
                this.mojUzytkownik = mojUzytkownik;
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        super.remove(super.getTablicaKafelkow()[i][j]);
                    }
                }
                // rozebrana tablica super
                //upewniam sie o dobre wymiary
                assert getTablicaKafelkow()[0].length == 2;
                assert getTablicaKafelkow()[1].length == 2;
                //laduje odpowiednie action listeners do odp kafelkow
                getTablicaKafelkow()[0][0] = new Kafelek(220,40,200,
                        70,70, "Sprawdz Badanie",
                        new KafelekListenerSprawdzBadanie(getTablicaKafelkow()[0][0]));
                // nie dodawaj, podwojnie sie zrobi
                //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                super.add(getTablicaKafelkow()[0][0]);
                getTablicaKafelkow()[0][1] = new Kafelek(120,220,20,
                        70,70, "Sprawdz Wizyte",
                        new KafelekListenerSprawdzWizyte(getTablicaKafelkow()[0][1]));
                super.add(getTablicaKafelkow()[0][1]);
                getTablicaKafelkow()[1][0] = new Kafelek(240,180,0,
                        70,70, "Sprawdz Szczepienie",
                        new KafelekListenerSprawdzSzczepienie(getTablicaKafelkow()[1][0]));
                super.add(getTablicaKafelkow()[1][0]);
                getTablicaKafelkow()[1][1] = new Kafelek(200,120,200,
                        70,70, "Zlec Szczepienie",
                        new KafelekListenerZlecSzczepienie(getTablicaKafelkow()[1][1]));
                super.add(getTablicaKafelkow()[1][1]);
            }
            @Override
            public Kafelek[][] getTablicaKafelkow() {
                return tablicaKafelkow;
            }
            @Override
            public void setTablicaKafelkow(Kafelek tablicaKafelkow[][]){
                this.tablicaKafelkow = tablicaKafelkow;
            }
        }
    }
    class UzytkownikPacjentowy extends UzytkownikDowolny{
        private String nazwaUzytkownika, rolaUzytkownika;
        public UzytkownikPacjentowy(String nazwaUzytkownika, String rolaUzytkownika){
            super(nazwaUzytkownika, rolaUzytkownika);
            this.nazwaUzytkownika = nazwaUzytkownika;
            this.rolaUzytkownika = rolaUzytkownika;
            super.panelSrodek = new PanelSpecyficznySrodekPacjent(3,3,50,this);
            super.panelSrodek.revalidate();
        }
        class PanelSpecyficznySrodekPacjent extends PanelSpecyficznySrodek{
            private Kafelek tablicaKafelkow[][];
            private UzytkownikDowolny mojUzytkownik;
            public PanelSpecyficznySrodekPacjent(int kolorR, int kolorG, int kolorB,
                                                      UzytkownikDowolny mojUzytkownik) {
                super(kolorR, kolorG, kolorB,mojUzytkownik);
                setTablicaKafelkow(new Kafelek[1][3]);
                this.mojUzytkownik = mojUzytkownik;
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        super.remove(super.getTablicaKafelkow()[i][j]);
                    }
                }
                // rozebrana tablica super
                //upewniam sie o dobre wymiary
                assert getTablicaKafelkow()[0].length == 1;
                assert getTablicaKafelkow()[1].length == 3;
                //laduje odpowiednie action listeners do odp kafelkow
                getTablicaKafelkow()[0][0] = new Kafelek(220,40,200,
                        70,70, "Sprawdz Badanie",
                        new KafelekListenerSprawdzBadanie(getTablicaKafelkow()[0][0]));
                // nie dodawaj, podwojnie sie zrobi
                //getTablicaKafelkow()[0][0].addActionListener(new KafelekListenerLogout(getTablicaKafelkow()[0][0]));
                super.add(getTablicaKafelkow()[0][0]);
                getTablicaKafelkow()[0][1] = new Kafelek(120,220,20,
                        70,70, "Sprawdz Wizyte",
                        new KafelekListenerSprawdzWizyte(getTablicaKafelkow()[0][1]));
                super.add(getTablicaKafelkow()[0][1]);
                getTablicaKafelkow()[0][2] = new Kafelek(240,180,0,
                        70,70, "Sprawdz Szczepienie",
                        new KafelekListenerSprawdzSzczepienie(getTablicaKafelkow()[0][2]));
                super.add(getTablicaKafelkow()[0][2]);
            }
            @Override
            public Kafelek[][] getTablicaKafelkow() {
                return tablicaKafelkow;
            }
            @Override
            public void setTablicaKafelkow(Kafelek tablicaKafelkow[][]){
                this.tablicaKafelkow = tablicaKafelkow;
            }
        }
    }
}
