package com.okienka;

import com.users.JakisUzytkownik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SprawdzWizyte extends JFrame implements ActionListener {
    private String nazwaUzytkownika, rola;
    private JLabel podajIdBadania, podajLoginPacjenta;
    private JTextField idField, patientLoginField;
    private JButton okButton, searchButton;
    private String idBadaniaString, loginPacjentaString;
    private JPanel panel, buttonPanel;
    private JTextArea dataBadania, lekarzWykonujacy, opisBadania;

    public SprawdzWizyte(String nazwaUzytkownika, String rola) {
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.rola = rola;
        // specifying components
        // panel
        panel = new JPanel();
        panel.setSize(500, 500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // buttonPanel & it's buttons
        buttonPanel = new JPanel();
        buttonPanel.setSize(500, 500);
        buttonPanel.setLayout(new GridLayout(2, 1));
        // SEARCH button
        searchButton = new JButton("wyszukaj");
        searchButton.setFocusable(false);
        searchButton.setFont(new Font("Courier", Font.PLAIN, 20));
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);
        // OK button
        okButton = new JButton("OK");
        okButton.setFocusable(false);
        okButton.setFont(new Font("Courier", Font.PLAIN, 20));
        okButton.addActionListener(this);
        buttonPanel.add(okButton);
        // podajLoginPacjenta label
        podajLoginPacjenta = new JLabel("podaj login pacjenta: ");
        podajLoginPacjenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajLoginPacjenta.setFont(new Font("Courier", Font.PLAIN, 20));
        // patientLoginField
        patientLoginField = new JTextField();
        patientLoginField.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajID label
        podajIdBadania = new JLabel("podaj ID wizyty: ");
        podajIdBadania.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajIdBadania.setFont(new Font("Courier", Font.PLAIN, 20));
        // idField
        idField = new JTextField();
        idField.setFont(new Font("Courier", Font.PLAIN, 20));
        // date TextArea
        dataBadania = new JTextArea(1, 10);
        dataBadania.setFont(new Font("Courier", Font.PLAIN, 15));
        dataBadania.setEditable(false);
        // pielegniarkaWykonujaca TextArea
        lekarzWykonujacy = new JTextArea(1, 10);
        lekarzWykonujacy.setFont(new Font("Courier", Font.PLAIN, 15));
        lekarzWykonujacy.setEditable(false);
        // opisBadania TextArea
        opisBadania = new JTextArea(5, 45);
        opisBadania.setFont(new Font("Courier", Font.PLAIN, 15));
        opisBadania.setEditable(false);
        // end of specifying components
        // adding components
        panel.add(podajLoginPacjenta);
        if (rola == "Lekarz" || rola == "Pielęgniarka") {
            //wariant dla pracownikow
            panel.add(patientLoginField);
        }
        else if (rola == "Pacjent")
            loginPacjentaString = nazwaUzytkownika;
        panel.add(podajIdBadania);
        panel.add(idField);
        panel.add(dataBadania);
        panel.add(lekarzWykonujacy);
        panel.add(opisBadania);
        panel.add(buttonPanel);
        // end of adding components
        setTitle("Sprawdz Szczepienie");
        ImageIcon logo = new ImageIcon("SymbolPrzychodni.png");
        setIconImage(logo.getImage());
        setContentPane(panel);
        pack();
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            // jesli tylko zamknac okno, a aplikacja nadal bedzie dzialac
            dispose();
            // jesli chcesz kompletnie wyjsc
            //System.exit(420);
        }
        if (e.getSource() == searchButton) {
            if (rola == "Lekarz" || rola == "Pielęgniarka")
                loginPacjentaString = new String(patientLoginField.getText());
            idBadaniaString = new String(idField.getText());
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                        "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select wizyty.dataWykonania, lekarze.imie," +
                        "lekarze.nazwisko, opis from wizyty join lekarze on loginLekarza = loginName " +
                        " where loginPacjenta =  \"" + loginPacjentaString + "\" and idUslugi = " +
                        idBadaniaString);
                if (!resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Nothing found, try with different input",
                            "No Results Found", JOptionPane.WARNING_MESSAGE);
                }
                else{

                    dataBadania.setText("DATA WYKONANIA: " + String.valueOf(resultSet.getDate("dataWykonania")));
                    String tempString;
                    tempString = (resultSet.getString("lekarze.imie") + " "
                            + resultSet.getString("lekarze.nazwisko"));
                    lekarzWykonujacy.setText("LEKARZ WYKONUJACY: " + tempString);
                    opisBadania.setText("OPIS: " + resultSet.getString("opis"));
                }
            }
            catch (Exception excpt) {
                excpt.printStackTrace();
            }
        }
    }
}

