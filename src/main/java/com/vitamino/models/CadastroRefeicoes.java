package com.vitamino.models;

import java.util.Date;
import java.util.List;

public class CadastroRefeicoes {
    private int idRefeicao;
    private String tipoRefeicao; // Ex.: "Café da manhã", "Almoço", etc.
    private Date dataRefeicao;
    private List<Ingrediente> ingredientes;
    private float totalCalorias;

    // Construtor padrão
    public CadastroRefeicoes() {}

    // Construtor com parâmetros
    public CadastroRefeicoes(int idRefeicao, String tipoRefeicao, Date dataRefeicao, List<Ingrediente> ingredientes) {
        this.idRefeicao = idRefeicao;
        this.tipoRefeicao = tipoRefeicao;
        this.dataRefeicao = dataRefeicao;
        this.ingredientes = ingredientes;
        calcularTotalCalorias();
    }

    // Getters e Setters
    public int getIdRefeicao() {
        return idRefeicao;
    }

    public void setIdRefeicao(int idRefeicao) {
        this.idRefeicao = idRefeicao;
    }

    public String getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(String tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public Date getDataRefeicao() {
        return dataRefeicao;
    }

    public void setDataRefeicao(Date dataRefeicao) {
        this.dataRefeicao = dataRefeicao;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
        calcularTotalCalorias(); // Atualiza o total de calorias sempre que os ingredientes mudarem
    }

    public float getTotalCalorias() {
        return totalCalorias;
    }

    // Método para calcular o total de calorias da refeição
    private void calcularTotalCalorias() {
        this.totalCalorias = 0;
        if (ingredientes != null) {
            for (Ingrediente ingrediente : ingredientes) {
                this.totalCalorias += ingrediente.getCaloriasTotais();
            }
        }
    }

    @Override
    public String toString() {
        return "Refeição {" +
                "ID=" + idRefeicao +
                ", Tipo='" + tipoRefeicao + '\'' +
                ", Data=" + dataRefeicao +
                ", Total de Calorias=" + totalCalorias +
                '}';
    }
}
