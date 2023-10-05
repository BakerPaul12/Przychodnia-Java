package com.okienka;

import com.users.JakisUzytkownik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ZalozKonto extends JFrame implements ActionListener, ItemListener {
    private String rola, nazwaBD, potencjalnyUzytkownik, hasloString,
            confirmHasloString, noweImie, noweNazwisko, nowyWiek, nowaSpecjalizacja, SQL;
    private JLabel podajRole, podajLogin, podajHaslo, podajHasloAgain, podajImie, podajNazwisko, podajSpecjalizacje, podajWiek;
    private JPasswordField hasloBox, againHasloBox;
    private JTextField loginBox, imieBox, nazwiskoBox, specjalizacjaBox, wiekBox;
    private JButton acceptButton, cancelButton;
    private JPanel panel, buttonPanel;
    private JComboBox role;
    private String[] listaRol = {"Lekarz", "Pielęgniarka", "Pacjent"};

    public ZalozKonto() {
        // specifying components
        // panel
        panel = new JPanel();
        panel.setSize(500, 500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // buttonPanel & it's buttons
        buttonPanel = new JPanel();
        buttonPanel.setSize(500, 500);
        buttonPanel.setLayout(new GridLayout(1, 2));
        acceptButton = new JButton("Akceptuj");
        acceptButton.setFocusable(false);
        acceptButton.setFont(new Font("Courier", Font.PLAIN, 20));
        //ZA CHWILE
        acceptButton.addActionListener(this);
        // ZACHWILE
        cancelButton = new JButton("Anuluj");
        cancelButton.setFocusable(false);
        cancelButton.setFont(new Font("Courier", Font.PLAIN, 20));
        //ZA CHWILE
        cancelButton.addActionListener(this);
        //ZA CHWILE
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        // podajRole label
        podajRole = new JLabel("Wybierz swoją rolę: ");
        podajRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajRole.setFont(new Font("Courier", Font.PLAIN, 20));
        // role ComboBox
        role = new JComboBox(listaRol);
        role.setFont(new Font("Courier", Font.PLAIN, 20));
        role.setFocusable(false);
        // podajLogin label
        podajLogin = new JLabel("Podaj login: ");
        podajLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajLogin.setFont(new Font("Courier", Font.PLAIN, 20));
        // loginBox TextField
        loginBox = new JTextField();
        loginBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajImie label
        podajImie = new JLabel("Podaj imię: ");
        podajImie.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajImie.setFont(new Font("Courier", Font.PLAIN, 20));
        // imieBox TextField
        imieBox = new JTextField();
        imieBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajNazwisko label
        podajNazwisko = new JLabel("Podaj nazwisko: ");
        podajNazwisko.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajNazwisko.setFont(new Font("Courier", Font.PLAIN, 20));
        // nazwiskoBox TextField
        nazwiskoBox = new JTextField();
        nazwiskoBox.setFont(new Font("Courier", Font.PLAIN, 20));

        //te sa wyjatkowe - raz sie pokazuja, raz nie!
        // podajSpecjalizacje label
        podajSpecjalizacje = new JLabel("Podaj specjalizacje: ");
        podajSpecjalizacje.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajSpecjalizacje.setFont(new Font("Courier", Font.PLAIN, 20));
        // nazwiskoBox TextField
        specjalizacjaBox = new JTextField();
        specjalizacjaBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajWiek label
        podajWiek = new JLabel("Podaj wiek: ");
        podajWiek.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajWiek.setFont(new Font("Courier", Font.PLAIN, 20));
        // wiekBox TextField
        wiekBox = new JTextField();
        wiekBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // koniec wyjatkowych!!!

        // podajHaslo label
        podajHaslo = new JLabel("Podaj hasło: ");
        podajHaslo.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajHaslo.setFont(new Font("Courier", Font.PLAIN, 20));
        // hasloBox PasswordField
        hasloBox = new JPasswordField();
        hasloBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajHasloAgain label
        podajHasloAgain = new JLabel("Powtórz hasło: ");
        podajHasloAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajHasloAgain.setFont(new Font("Courier", Font.PLAIN, 20));
        // againHasloBox PasswordField
        againHasloBox = new JPasswordField();
        againHasloBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // end of specifying components
        // adding components
        panel.add(podajRole);
        panel.add(role);
        panel.add(podajLogin);
        panel.add(loginBox);
        panel.add(podajImie);
        panel.add(imieBox);
        panel.add(podajNazwisko);
        panel.add(nazwiskoBox);
        // raz widac
        panel.add(podajSpecjalizacje);
        panel.add(specjalizacjaBox);
        panel.add(podajWiek);
        panel.add(wiekBox);
        // a raz nie
        panel.add(podajHaslo);
        panel.add(hasloBox);
        panel.add(podajHasloAgain);
        panel.add(againHasloBox);
        panel.add(buttonPanel);
        // end of adding components
        role.addItemListener(this);
        setTitle("ZAŁÓZ KONTO");
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
    public void itemStateChanged(ItemEvent x){
        if(role.getSelectedItem() == "Lekarz"){
            podajSpecjalizacje.setVisible(true);
            specjalizacjaBox.setVisible(true);
            podajWiek.setVisible(false);
            wiekBox.setVisible(false);
        }
        if(role.getSelectedItem() == "Pielęgniarka"){
            podajSpecjalizacje.setVisible(false);
            specjalizacjaBox.setVisible(false);
            podajWiek.setVisible(false);
            wiekBox.setVisible(false);
        }
        if(role.getSelectedItem() == "Pacjent"){
            podajSpecjalizacje.setVisible(false);
            specjalizacjaBox.setVisible(false);
            podajWiek.setVisible(true);
            wiekBox.setVisible(true);
        }
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
            hasloString = new String(hasloBox.getPassword());
            confirmHasloString = new String(againHasloBox.getPassword());
            potencjalnyUzytkownik = new String(loginBox.getText());
            noweImie = new String(imieBox.getText());
            noweNazwisko = new String(nazwiskoBox.getText());
            nowyWiek = new String(wiekBox.getText());
            nowaSpecjalizacja = new String(specjalizacjaBox.getText());
            // nazwa bazy zalezna od roli
            if (role.getSelectedItem().toString() == "Lekarz") {
                nazwaBD = "lekarze";
                SQL = "insert into " + nazwaBD + " values( " +
                        "\"" + potencjalnyUzytkownik + "\"," + "\"" + confirmHasloString + "\"," +
                        "\"" + noweImie + "\"," + "\"" + noweNazwisko + "\"," + "\"" + nowaSpecjalizacja + "\")";
            }
            if (role.getSelectedItem().toString() == "Pielęgniarka"){
                nazwaBD = "pielegniarki";
                SQL = "insert into " + nazwaBD + " values( " +
                        "\"" + potencjalnyUzytkownik + "\"," + "\"" + confirmHasloString + "\"," +
                        "\"" + noweImie + "\"," + "\"" + noweNazwisko + "\")";
            }
            if (role.getSelectedItem().toString() == "Pacjent"){
                nazwaBD = "pacjenci";
                SQL = "insert into " + nazwaBD + " values( " +
                        "\"" + potencjalnyUzytkownik + "\"," + "\"" + confirmHasloString + "\"," +
                        "\"" + noweImie + "\"," + "\"" + noweNazwisko + "\"," + "\"" + nowyWiek + "\")";
                System.out.println(SQL);
            }
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                        "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select loginName from " + nazwaBD +
                        " where password =  \"" + potencjalnyUzytkownik + "\"");
                // tu error
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "That user already exists!",
                            "Already Exiting User Error", JOptionPane.WARNING_MESSAGE);

                } else {
                    if (hasloString.equals(confirmHasloString)) {
                        try {
                            Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                                    "root", "root");
                            Statement statement2 = connection.createStatement();
                            statement.executeUpdate(SQL);
                            JOptionPane.showMessageDialog(null, "Account has been created",
                                    "Password Change Info", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception excpt) {
                            excpt.printStackTrace();
                        }
                        dispose();
                    }
                    // otwieranie nowego okienka, gdy login i haslo sa te same (prosty warunek na pocz)
                    else {
                        JOptionPane.showMessageDialog(null, "Incompatibile passwords!",
                                "Password Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception excpt) {
                excpt.printStackTrace();
            }
        }
    }
}
