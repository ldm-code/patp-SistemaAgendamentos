package model;

import java.util.List;

import dao.agendamentosDAO;
import List.Agendamentos;
import List.AgendamentosCancelados;

public class agendamentos {


    public static List<Agendamentos>
    listarConcluidos(){

        try{

            return
              agendamentosDAO
              .listarAgendamentosConcluidos();

        }
        catch(Exception e){

            System.out.println(
                "Erro ao buscar agendamentos"
            );

            e.printStackTrace();

            return List.of();
        }
    }



    public static
    List<AgendamentosCancelados>
    listarEncerradasFiltradas(
        String nome,
        String status
    ){

        try{

            return
                agendamentosDAO
                 .listarEncerradasFiltradas(
                    nome,
                    status
                 );

        }
        catch(Exception e){

            e.printStackTrace();

            return List.of();
        }
    }

}