package com.vitamino.ui;

import database.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CadastroRefeicaoScreen extends JFrame {

    private JComboBox<String> tipoRefeicaoComboBox;
    private JComboBox<String> ingredientesComboBox;
    private JTextField pesoTextField;
    private JTextArea resumoTextArea;
    private JButton salvarIngredienteButton;
    private JButton salvarRefeicaoButton;

    private ArrayList<String> ingredientesSelecionados;
    private ArrayList<Double> caloriasTotais;
    private String cpfPaciente;
    private String nomePaciente;

    public CadastroRefeicaoScreen(String cpfPaciente, String nomePaciente) {
        this.cpfPaciente = cpfPaciente;
        this.nomePaciente = nomePaciente;

        setTitle("Cadastro de Refeição");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        ingredientesSelecionados = new ArrayList<>();
        caloriasTotais = new ArrayList<>();

        // Painel para seleção do tipo de refeição e ingredientes
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(new JLabel("Tipo de Refeição:"));
        tipoRefeicaoComboBox = new JComboBox<>(new String[]{"Café da manhã", "Almoço", "Café da tarde", "Janta", "Ceia", "Lanche"});
        topPanel.add(tipoRefeicaoComboBox);

        topPanel.add(new JLabel("Ingredientes:"));
        ingredientesComboBox = new JComboBox<>();
        carregarIngredientes();
        topPanel.add(ingredientesComboBox);

        add(topPanel, BorderLayout.NORTH);

        // Painel para adicionar ingredientes e peso
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        middlePanel.add(new JLabel("Peso (em gramas):"));
        pesoTextField = new JTextField(5);
        middlePanel.add(pesoTextField);

        salvarIngredienteButton = new JButton("Salvar");
        salvarIngredienteButton.setPreferredSize(new Dimension(100, 25));
        salvarIngredienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarIngrediente();
            }
        });
        middlePanel.add(salvarIngredienteButton);

        add(middlePanel, BorderLayout.CENTER);

        // Resumo e botão para salvar refeição
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        resumoTextArea = new JTextArea(15, 40);
        resumoTextArea.setEditable(false);
        bottomPanel.add(new JScrollPane(resumoTextArea), BorderLayout.CENTER);

        salvarRefeicaoButton = new JButton("Salvar Refeição");
        salvarRefeicaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarRefeicao();
            }
        });
        bottomPanel.add(salvarRefeicaoButton, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarIngredientes() {
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT Nome FROM Ingredientes")) {

            while (resultSet.next()) {
                ingredientesComboBox.addItem(resultSet.getString("Nome"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar ingredientes: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarIngrediente() {
        String ingredienteSelecionado = (String) ingredientesComboBox.getSelectedItem();
        String pesoTexto = pesoTextField.getText();

        if (ingredienteSelecionado == null || pesoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um ingrediente e insira o peso.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double peso = Double.parseDouble(pesoTexto);
            double calorias = calcularCalorias(ingredienteSelecionado, peso);

            ingredientesSelecionados.add(ingredienteSelecionado + " - " + peso + "g");
            caloriasTotais.add(calorias);

            resumoTextArea.append("Ingrediente: " + ingredienteSelecionado + ", Peso: " + peso + "g, Calorias: " + calorias + " kcal\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso inválido. Digite um valor numérico.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calcularCalorias(String ingrediente, double peso) {
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT CaloriasPorGrama FROM Ingredientes WHERE Nome = ?")) {

            statement.setString(1, ingrediente);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double caloriasPorGrama = resultSet.getDouble("CaloriasPorGrama");
                return caloriasPorGrama * peso;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular calorias: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return 0.0;
    }

    private void salvarRefeicao() {
        if (ingredientesSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um ingrediente antes de salvar a refeição.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipoRefeicao = (String) tipoRefeicaoComboBox.getSelectedItem();
        double totalCalorias = caloriasTotais.stream().mapToDouble(Double::doubleValue).sum();

        resumoTextArea.append("\n--- Resumo Final ---\n");
        resumoTextArea.append("Tipo de Refeição: " + tipoRefeicao + "\n");
        resumoTextArea.append("Total de Calorias: " + totalCalorias + " kcal\n");

        JOptionPane.showMessageDialog(this, "Refeição salva com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new RefeicoesScreen(cpfPaciente, nomePaciente).setVisible(true);
    }
}
