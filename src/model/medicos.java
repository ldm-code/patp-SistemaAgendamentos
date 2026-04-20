package model;

import dao.medicosDAO;

public class medicos {

    public static String cadastrarMedico(String nome, String tipo) {

        try {

            if (nome == null || nome.isEmpty()) {
                return "Nome obrigatório";
            }

            if (tipo == null || tipo.isEmpty()) {
                return "Tipo obrigatório";
            }

            medicosDAO.inserir(nome, tipo);

            return "Médico cadastrado com sucesso";

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro no cadastro do médico";
        }
    }
}