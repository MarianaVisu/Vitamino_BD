package com.vitamino;

import database.DatabaseHelper;

public class Vitamino {
    public static void main(String[] args) {
        // Inicializa o banco de dados
        database.DatabaseHelper.initializeDatabase();
       
       // Verifica se as tabelas existem
        database.DatabaseHelper.checkTables();

        // Abre a tela principal
        com.vitamino.ui.MainScreen.main(args);
    }
}
