package com.okienka;
// select max(idUslugi) from badania where loginPacjenta = 'Pawel99'; - da null
import com.users.JakisUzytkownik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ZlecSzczepienie extends JFrame implements ActionListener {
    private String nazwaUzytkownika;
    private JLabel podajLoginPacjenta, podajLoginPielegniarki, podajDate, podajOpisUslugi; // do ZlecBadanie
    private JTextField patientLoginField, nurseLoginField, dateField, opisField; // do ZlecBadanie
    private JButton acceptButton, cancelButton;
    private String loginPacjentaString, loginPielegniarkiString, dataUslugiString, opisUslugiString; // do ZlecBadanie
    private JPanel panel, buttonPanel;
    public ZlecSzczepienie() {
        // specifying components
        // panel
        panel = new JPanel();
        panel.setSize(500, 500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // buttonPanel & it's buttons
        buttonPanel = new JPanel();
        buttonPanel.setSize(500, 500);
        buttonPanel.setLayout(new GridLayout(2, 1));
        // accept button
        acceptButton = new JButton("akceptuj");
        acceptButton.setFocusable(false);
        acceptButton.setFont(new Font("Courier", Font.PLAIN, 20));
        acceptButton.addActionListener(this);
        buttonPanel.add(acceptButton);
        // cancel button
        cancelButton = new JButton("anuluj");
        cancelButton.setFocusable(false);
        cancelButton.setFont(new Font("Courier", Font.PLAIN, 20));
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        // podajLoginPacjenta label
        podajLoginPacjenta = new JLabel("podaj login pacjenta: ");
        podajLoginPacjenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajLoginPacjenta.setFont(new Font("Courier", Font.PLAIN, 20));
        // patientLoginField
        patientLoginField = new JTextField();
        patientLoginField.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajLoginPielegniarki label
        podajLoginPielegniarki = new JLabel("podaj login pielegniarki: ");
        podajLoginPielegniarki.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajLoginPielegniarki.setFont(new Font("Courier", Font.PLAIN, 20));
        // nurseLoginField
        nurseLoginField = new JTextField();
        nurseLoginField.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajDate label
        podajDate = new JLabel("podaj date wykonania: ");
        podajDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajDate.setFont(new Font("Courier", Font.PLAIN, 20));
        // dateField
        dateField = new JTextField();
        dateField.setFont(new Font("Courier", Font.PLAIN, 20));
        // podajOpisUslugi label
        podajOpisUslugi = new JLabel("podaj opis uslugi: ");
        podajOpisUslugi.setAlignmentX(Component.CENTER_ALIGNMENT);
        podajOpisUslugi.setFont(new Font("Courier", Font.PLAIN, 20));
        // uslugaField
        opisField = new JTextField();
        opisField.setFont(new Font("Courier", Font.PLAIN, 20));
        // end of specifying components
        // adding components
        panel.add(podajLoginPacjenta);
        panel.add(patientLoginField);
        panel.add(podajLoginPielegniarki);
        panel.add(nurseLoginField);
        panel.add(podajDate);
        panel.add(dateField);
        panel.add(podajOpisUslugi);
        panel.add(opisField);
        panel.add(buttonPanel);
        // end of adding components
        setTitle("Dodaj Badanie");
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
            loginPacjentaString = new String(patientLoginField.getText());
            loginPielegniarkiString = new String(nurseLoginField.getText());
            dataUslugiString = new String(dateField.getText());
            opisUslugiString = new String(opisField.getText());
            //ODTAD DZIALAJ
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                        "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select loginName from pacjenci" +
                        " where loginName =  \"" + loginPacjentaString + "\"");
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("select loginName from pielegniarki" +
                        " where loginName =  \"" + loginPielegniarkiString + "\"");
                if (!resultSet.next() || !resultSet2.next()) {
                    JOptionPane.showMessageDialog(null, "Patient or Nurse doesn't exist",
                            "Patient Or Nurse Not Found", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    statement.executeUpdate("call noweSzczepienie(" +
                            "\"" + loginPacjentaString + "\"," + "\"" + loginPielegniarkiString + "\"," +
                            "\"" + opisUslugiString + "\"," +
                            "\"" + dataUslugiString + "\");");
                    JOptionPane.showMessageDialog(null, "Operation successful",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
            catch (Exception excpt) {
                excpt.printStackTrace();
            }
        }
    }
}



