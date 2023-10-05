package com.login;

import com.okienka.ZalozKonto;
import com.users.JakisUzytkownik;
import com.users.UzytkownikLekarz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginScreen extends JFrame implements ActionListener{
    private boolean succeeded = false;
    private JLabel loginLabel, passwordLabel, userTypeLabel;
    private JButton proceedButton, cancelButton, registerButton;
    private JTextField loginNameBox;
    private JPasswordField passwordBox;
    private String loginName;
    private String password;
    private String nazwaBD;
    private String rolaComboBox;
    private int errorCounter = 0;
    private static final int maxErrorAttempts = 3;
    private JPanel panel, buttonPanel;
    private JComboBox role;
    private String[] listaRol = {"Lekarz", "Pielęgniarka", "Pacjent"};
    public LoginScreen() {

        // specifying components
        // panel
        panel = new JPanel();
        panel.setSize(500,500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // buttonPanel & it's buttons
        buttonPanel = new JPanel();
        buttonPanel.setSize(500,500);
        buttonPanel.setLayout(new GridLayout(1,3));
        proceedButton = new JButton("Dalej");
        proceedButton.setFocusable(false);
        proceedButton.setFont(new Font("Courier", Font.PLAIN, 20));
        proceedButton.addActionListener(this);
        cancelButton = new JButton("Zakoncz");
        cancelButton.setFocusable(false);
        cancelButton.setFont(new Font("Courier", Font.PLAIN, 20));
        cancelButton.addActionListener(this);
        registerButton = new JButton("Zaloz konto");
        registerButton.setFocusable(false);
        registerButton.setFont(new Font("Courier", Font.PLAIN, 20));
        registerButton.addActionListener(this);
        buttonPanel.add(proceedButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(registerButton);
        // loginLabel
        loginLabel = new JLabel("podaj nazwę użytkownika: ");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        // loginNameBox
        loginNameBox = new JTextField();
        loginNameBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // passwordLabel
        passwordLabel = new JLabel("podaj hasło: ");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        // passwordBox
        passwordBox = new JPasswordField();
        passwordBox.setFont(new Font("Courier", Font.PLAIN, 20));
        // userTypeLabel
        userTypeLabel = new JLabel("zaloguj jako: ");
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTypeLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        // role
        role = new JComboBox(listaRol);
        role.setFont(new Font("Courier", Font.PLAIN, 20));
        role.setFocusable(false);
        // end of specifying components
        // adding components
        panel.add(loginLabel);
        panel.add(loginNameBox);
        panel.add(passwordLabel);
        panel.add(passwordBox);
        panel.add(userTypeLabel);
        panel.add(role);
        panel.add(buttonPanel);
        // end of adding components
        setTitle("PORADNIA");
        ImageIcon logo = new ImageIcon("SymbolPrzychodni.png");
        setIconImage(logo.getImage());
        setContentPane(panel);
        pack();
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton) {
            // jesli tylko zamknac okno, a aplikacja nadal bedzie dzialac
            //this.dispose();
            // jesli chcesz kompletnie wyjsc
            System.exit(4);
        }
        if(e.getSource() == proceedButton) {
            System.out.println("Brawo, kliknales logowanie, teraz sprawdzam..");
            loginName = loginNameBox.getText();
            password = new String(passwordBox.getPassword());
            rolaComboBox = role.getSelectedItem().toString();
            if(rolaComboBox == "Lekarz")
                nazwaBD = "lekarze";
            else if(rolaComboBox == "Pielęgniarka")
                nazwaBD = "pielegniarki";
            else if(rolaComboBox == "Pacjent")
                nazwaBD = "pacjenci";
            System.out.println("oto login:" + loginName + " a to pass:" + password + " a oto rola: " + rolaComboBox);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                        "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select password from " + nazwaBD +
                        " WHERE loginName =  \"" + loginName + "\"");
                if(!resultSet.next() || !resultSet.getString("password").equals(password)) {
                    JOptionPane.showMessageDialog(null, "Wrong name or password",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                    if (++errorCounter >= maxErrorAttempts)
                        System.exit(123);
                }
                else {
                    // otwieranie nowego okienka, gdy login i haslo sa prawidlowe
                    this.dispose();
                    new JakisUzytkownik(loginName, rolaComboBox);
                }
            }
            catch (Exception excpt) {
                excpt.printStackTrace();
            }
        }
        if(e.getSource() == registerButton) {
            new ZalozKonto();
        }
    }
}
/* do wyswietlania danych
 while (resultSet.next()) {
                    System.out.println(resultSet.getString("loginName"));
                }
 */