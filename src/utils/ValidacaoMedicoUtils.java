package utils;

import java.time.DayOfWeek;
import java.util.List;

public class ValidacaoMedicoUtils {

    // Converte DayOfWeek do Java para número (1-7)
    public static int converterDia(DayOfWeek dia) {
        return dia.getValue();
    }

    // Converte String do banco (ENUM) para número
    public static int converterDia(String dia) {
        return switch (dia) {
            case "SEGUNDA" -> 1;
            case "TERCA" -> 2;
            case "QUARTA" -> 3;
            case "QUINTA" -> 4;
            case "SEXTA" -> 5;
            case "SABADO" -> 6;
            case "DOMINGO" -> 7;
            default -> throw new RuntimeException("Dia inválido: " + dia);
        };
    }

    // Regra principal de validação
    public static boolean medicoPodeAtender(List<Integer> diasMedico, DayOfWeek diaConsulta) {

        int dia = converterDia(diaConsulta);

        // bloqueio explícito de fim de semana
        if (dia == 6 || dia == 7) {
            return false;
        }

        return diasMedico.contains(dia);
    }

    // Versão alternativa caso você receba String do banco
    public static boolean medicoPodeAtenderPorString(List<String> diasMedico, DayOfWeek diaConsulta) {

        int dia = converterDia(diaConsulta);

        if (dia == 6 || dia == 7) {
            return false;
        }

        for (String d : diasMedico) {
            if (converterDia(d) == dia) {
                return true;
            }
        }

        return false;
    }
}
