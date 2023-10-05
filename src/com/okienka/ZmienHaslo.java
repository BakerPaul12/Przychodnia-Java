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

public class ZmienHaslo extends JFrame implements ActionListener {
    private String nazwaUzytkownika, rola, nazwaBD;
    private JLabel podajStareHaslo, podajNoweHaslo, podajNoweZnowu;
    private JPasswordField stareHasloBox, noweHasloBox, againNoweBox;
    private JButton acceptButton, cancelButton;
    private String stareString, starePrawdziweString, noweString, confirmString;
    private JPanel panel, buttonPanel;

    public ZmienHaslo(String nazwaUzytkownika, String rola) {
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.rola = rola;
        if (rola == "Lekarz")
            nazwaBD = "lekarze";
        else if (rola == "Pielęgniarka")
            nazwaBD = "pielegniarki";
        else if (rola == "Pacjent")
            nazwaBD = "pacjenci";
        // specifying components
        // panel
        panel = new JPanel();
        panel.setSize(500, 500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // buttonPanel & it's buttons
        buttonPanel = new JPanel();
        buttonPanel.setSize(500, 500);
        buttonPanel.setLayout(new GridLayout(1, 3));
        acceptButton = new JButton("Akceptuj");
        acceptButton.setFocusable(false);
        acceptButton.setFont(new Font("Courier", Font.PLAIN, 20));
        //ZA CHWILE
        acceptButton.addActionListener(this);
        // ZACHWILE
        cancelButton = new JButton("Zakoncz");
        cancelButton.setFocusable(false);
        cancelButton.setFont(new Font("Courier", Font.PLAIN, 20));
        //ZA CHWILE
        cancelButton.addActionListener(this);
        //ZA CHWILE
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        // podajStareHaslo label
        podajStareHaslo = new JLabel("podaj stare hasło: ");
        podajStareHaslo.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajStareHaslo.setFont(new Font("Courier", Font.PLAIN, 20));
        // stareHasloBox passwordField
        stareHasloBox = new JPasswordField();
        stareHasloBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajNoweHaslo label
        podajNoweHaslo = new JLabel("podaj nowe hasło: ");
        podajNoweHaslo.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajNoweHaslo.setFont(new Font("Courier", Font.PLAIN, 20));
        // noweHasloBox passwordField
        noweHasloBox = new JPasswordField();
        noweHasloBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajNoweZnowu
        podajNoweZnowu = new JLabel("powtórz nowe hasło: ");
        podajNoweZnowu.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajNoweZnowu.setFont(new Font("Courier", Font.PLAIN, 20));
        // againNoweBox passwordField
        againNoweBox = new JPasswordField();
        againNoweBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // end of specifying components
        // adding components
        panel.add(podajStareHaslo);
        panel.add(stareHasloBox);
        panel.add(podajNoweHaslo);
        panel.add(noweHasloBox);
        panel.add(podajNoweZnowu);
        panel.add(againNoweBox);
        panel.add(buttonPanel);
        // end of adding components
        setTitle("ZMIANA HASŁA");
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
        if (e.getSource() == cancelButton) {
            // jesli tylko zamknac okno, a aplikacja nadal bedzie dzialac
            dispose();
            // jesli chcesz kompletnie wyjsc
            //System.exit(420);
        }
        if (e.getSource() == acceptButton) {
            System.out.println("Poczekaj, sprawdzmy czy wypelniles dobrze pola..");
            stareString = new String(stareHasloBox.getPassword());
            noweString = new String(noweHasloBox.getPassword());
            confirmString = new String(againNoweBox.getPassword());
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                        "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select password from " + nazwaBD +
                        " where loginName =  \"" + nazwaUzytkownika + "\"");
                // tu error
                if (!resultSet.next() || !resultSet.getString("password").equals(stareString)) {
                    JOptionPane.showMessageDialog(null, "Incompatibile passwords or wrong old password",
                            "Password Change Warning", JOptionPane.WARNING_MESSAGE);

                } else {
                    starePrawdziweString = resultSet.getString("password");
                    if (noweString.equals(confirmString)) {
                        try {
                            Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                                    "root", "root");
                            Statement statement2 = connection.createStatement();
                            statement.executeUpdate("update " + nazwaBD + " set password = " +
                                    "\"" + noweString + "\"" + " where loginName =  \"" + nazwaUzytkownika + "\"");
                            JOptionPane.showMessageDialog(null, "Password has been changed",
                                    "Password Change Info", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception excpt) {
                            excpt.printStackTrace();
                        }
                        dispose();
                    }
                    // otwieranie nowego okienka, gdy login i haslo sa te same (prosty warunek na pocz)
                    else {
                        JOptionPane.showMessageDialog(null, "Incompatibile passwords or wrong old password",
                                "Password Change Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception excpt) {
                excpt.printStackTrace();
            }
        }
    }
}
