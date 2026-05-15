package List;

import java.time.LocalDateTime;

public class Consulta {

    private int id;
    private String nomeUsuario;
    private String nomeMedico;
    private String especialidade;

    // quando a consulta vai acontecer
    private LocalDateTime dataConsulta;

    // quando a consulta foi agendada
    private LocalDateTime dataAgendamento;

    private String status;

    private int idMedico;
    private int idUsuario;


    public Consulta(
            int id,
            String nomeUsuario,
            String nomeMedico,
            String especialidade,
            LocalDateTime dataConsulta,
            LocalDateTime dataAgendamento,
            String status,
            int idMedico,
            int idUsuario
    ) {

        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.nomeMedico = nomeMedico;
        this.especialidade = especialidade;
        this.dataConsulta = dataConsulta;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
        this.idMedico = idMedico;
        this.idUsuario = idUsuario;
    }

    public Consulta() {}


    // =========================
    // GETTERS
    // =========================

    public int getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public  int getIdMedico() {
        return idMedico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }


    // =========================
    // SETTERS
    // =========================

    public void setId(int id) {
        this.id = id;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setDataConsulta(
            LocalDateTime dataConsulta
    ) {
        this.dataConsulta = dataConsulta;
    }

    public void setDataAgendamento(
            LocalDateTime dataAgendamento
    ) {
        this.dataAgendamento = dataAgendamento;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    // =========================
    // REGRA DE NEGÓCIO
    // =========================

    public boolean isHorarioValido() {

        if (dataConsulta == null)
            return false;

        LocalDateTime agora =
            LocalDateTime.now();

        // não aceita datas passadas
        if (!dataConsulta.isAfter(agora))
            return false;

        // mínimo 30 minutos de antecedência
        if (dataConsulta.isBefore(
                agora.plusMinutes(30)
        ))
            return false;

        // máximo um ano no futuro
        if (dataConsulta.isAfter(
                agora.plusYears(1)
        ))
            return false;

        return true;
    }


    // =========================
    // DEBUG
    // =========================

    @Override
    public String toString() {

        return "Consulta{" +
                "id=" + id +
                ", usuario='" + nomeUsuario + '\'' +
                ", medico='" + nomeMedico + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", dataConsulta=" + dataConsulta +
                ", dataAgendamento=" + dataAgendamento +
                ", status='" + status + '\'' +
                '}';
    }
}