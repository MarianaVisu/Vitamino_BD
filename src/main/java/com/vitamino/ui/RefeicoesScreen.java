package com.vitamino.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefeicoesScreen extends JFrame {
    private JTable refeicoesTable;
    private DefaultTableModel tableModel;

    private String cpfPaciente;
    private String nomePaciente;

    public RefeicoesScreen(String cpfPaciente, String nomePaciente) {
        this.cpfPaciente = cpfPaciente;
        this.nomePaciente = nomePaciente;

        setTitle("Refeições - " + nomePaciente);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Bem-vindo(a), " + nomePaciente + "! Aqui estão suas refeições.", SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Data", "Tipo de Refeição", "Total de Calorias"}, 0);
        refeicoesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(refeicoesTable);

        JButton novaRefeicaoButton = new JButton("Adicionar Nova Refeição");
        novaRefeicaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroRefeicaoScreen(cpfPaciente, nomePaciente).setVisible(true);
                dispose();
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(novaRefeicaoButton, BorderLayout.SOUTH);

        carregarRefeicoes();
    }

    private void carregarRefeicoes() {
        // Código opcional para carregar as refeições, se necessário
    }
}
