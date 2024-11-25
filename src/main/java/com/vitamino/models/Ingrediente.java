package com.vitamino.models;

public class Ingrediente {
    private int idIngrediente;       // ID único do ingrediente
    private String nome;            // Nome do ingrediente
    private float caloriasPorGrama; // Calorias por grama do ingrediente
    private float peso;             // Peso do ingrediente usado na refeição em gramas

    // Construtor padrão
    public Ingrediente() {}

    // Construtor com parâmetros
    public Ingrediente(int idIngrediente, String nome, float caloriasPorGrama, float peso) {
        this.idIngrediente = idIngrediente;
        this.nome = nome;
        this.caloriasPorGrama = caloriasPorGrama;
        this.peso = peso;
    }

    // Getters e Setters
    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCaloriasPorGrama() {
        return caloriasPorGrama;
    }

    public void setCaloriasPorGrama(float caloriasPorGrama) {
        this.caloriasPorGrama = caloriasPorGrama;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    // Método para calcular as calorias totais com base no peso
    public float getCaloriasTotais() {
        return this.peso * this.caloriasPorGrama;
    }

    @Override
    public String toString() {
        return "Ingrediente {" +
                "ID=" + idIngrediente +
                ", Nome='" + nome + '\'' +
                ", Calorias por Grama=" + caloriasPorGrama +
                ", Peso=" + peso +
                ", Calorias Totais=" + getCaloriasTotais() +
                '}';
    }
}
