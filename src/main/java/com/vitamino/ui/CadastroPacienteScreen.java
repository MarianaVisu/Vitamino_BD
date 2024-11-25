package com.vitamino.ui;

import com.vitamino.database.DatabaseHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroPacienteScreen extends JFrame {
    public CadastroPacienteScreen() {
        setTitle("Cadastro de Paciente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        JTextField dataNascimentoField = new JTextField();
        JLabel idadeLabel = new JLabel("Idade:");
        JTextField idadeField = new JTextField();
        JLabel telefoneLabel = new JLabel("Telefone:");
        JTextField telefoneField = new JTextField();

        JButton salvarButton = new JButton("Salvar");

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                String nome = nomeField.getText();
                String email = emailField.getText();
                String dataNascimento = dataNascimentoField.getText();
                String idade = idadeField.getText();
                String telefone = telefoneField.getText();

                if (cpf.isEmpty() || nome.isEmpty() || email.isEmpty() || dataNascimento.isEmpty() || idade.isEmpty() || telefone.isEmpty()) {
                    JOptionPane.showMessageDialog(CadastroPacienteScreen.this,
                            "Preencha todos os campos!", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (Connection connection = DatabaseHelper.getConnection()) {
                    String sql = "INSERT INTO CadastroPaciente (CPF, Nome, Email, DataNascimento, Idade, Telefone) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, cpf);
                    statement.setString(2, nome);
                    statement.setString(3, email);
                    statement.setString(4, dataNascimento);
                    statement.setInt(5, Integer.parseInt(idade));
                    statement.setString(6, telefone);
                    statement.executeUpdate();

                    // Mensagem de sucesso
                    JOptionPane.showMessageDialog(CadastroPacienteScreen.this,
                            "Paciente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    // Fecha a tela atual e abre a tela de refeições
                    dispose(); // Fecha a tela de cadastro do paciente
                    new RefeicoesScreen(cpf, nome).setVisible(true); // Abre a tela de refeições
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(CadastroPacienteScreen.this,
                            "Erro ao cadastrar paciente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CadastroPacienteScreen.this,
                            "Idade deve ser um número válido!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(cpfLabel);
        add(cpfField);
        add(nomeLabel);
        add(nomeField);
        add(emailLabel);
        add(emailField);
        add(dataNascimentoLabel);
        add(dataNascimentoField);
        add(idadeLabel);
        add(idadeField);
        add(telefoneLabel);
        add(telefoneField);
        add(salvarButton);
    }
}
