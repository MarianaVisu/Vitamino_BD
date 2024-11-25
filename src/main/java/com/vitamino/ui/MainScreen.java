package com.vitamino.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bem-vindo ao Vitamino!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JLabel label = new JLabel("Bem-vindo! Cadastre-se na plataforma para o acompanhamento!", SwingConstants.CENTER);
        JButton cadastrarButton = new JButton("Cadastrar Paciente");

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroPacienteScreen().setVisible(true);
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(cadastrarButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
