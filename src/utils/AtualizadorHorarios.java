package utils;

import java.time.LocalDate;
import java.util.List;

import List.MedicosSelect;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.consultas;

public class AtualizadorHorarios {

    public static void atualizar(
            ComboBox<String> comboHora,
            ComboBox<MedicosSelect> selectMedico,
            DatePicker dataConsulta
    ) {

        try {

            MedicosSelect medico =
                    selectMedico.getValue();

            LocalDate data =
                    dataConsulta.getValue();

            // Se ainda não selecionou tudo
            if (medico == null || data == null) {

                comboHora.getSelectionModel().clearSelection();

                comboHora.setValue(null);

                comboHora.getItems().clear();

                comboHora.setPromptText(
                        "Selecione médico e data"
                );

                return;
            }

            // Busca horários disponíveis
            List<String> horarios =
                    consultas.buscarHorariosDisponiveis(
                            medico.getId(),
                            data
                    );


            // Sem horários
            comboHora.getSelectionModel().clearSelection();

            comboHora.setValue(null);

            comboHora.getItems().clear();

            if (horarios.isEmpty()) {

                comboHora.setPromptText(
                        "Sem horários disponíveis"
                );

            } else {

                comboHora.setPromptText(
                        "Selecione um horário"
                );

                comboHora.getItems().addAll(
                        horarios
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
   
        // ===== EDIÇÃO =====
        public static void atualizarEdicao(
                ComboBox<String> comboHora,
                int idMedico,
                LocalDate data,
                String horarioAtual
        ) {

            try {

                comboHora.getItems().clear();
                comboHora.setValue(null);

                List<String> horarios =
                        consultas.buscarHorariosDisponiveis(
                                idMedico,
                                data
                        );

                // mantém horário atual
                if(!horarios.contains(horarioAtual)) {

                    horarios.add(horarioAtual);
                }

                if(horarios.isEmpty()) {

                    comboHora.setDisable(true);

                    comboHora.setPromptText(
                            "Sem horários disponíveis"
                    );

                } else {

                    comboHora.setDisable(false);

                    comboHora.setPromptText(
                            "Selecione um horário"
                    );

                    comboHora.getItems().addAll(horarios);

                    comboHora.setValue(horarioAtual);
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
 }
