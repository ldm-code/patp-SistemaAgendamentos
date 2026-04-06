package List;


import java.time.LocalDateTime;

public class Consulta {

    private int id;
    private String nomeUsuario;
    private String nomeMedico;
    private String especialidade;
    private LocalDateTime dataConsulta;
    private String status;
    private int idMedico;

  
    public Consulta(int id, String nomeUsuario, String nomeMedico,
                    String especialidade, LocalDateTime dataConsulta, String status, int idMedico) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.nomeMedico = nomeMedico;
        this.especialidade = especialidade;
        this.dataConsulta = dataConsulta;
        this.status = status;
        this.idMedico = idMedico;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
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

    public String getStatus() {
        return status;
    }



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

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // =========================
    // 🔥 REGRA DE NEGÓCIO (VALIDAÇÃO)
    // =========================

    public boolean isHorarioValido() {
        if (dataConsulta == null) return false;

        LocalDateTime agora = LocalDateTime.now();

        // não aceita horários no passado
        if (!dataConsulta.isAfter(agora)) return false;

        // agendamento precisa ter pelo menos 30 minutos de antecedência
        if (dataConsulta.isBefore(agora.plusMinutes(30))) return false;

        // limite máximo (por exemplo, 1 ano no futuro)
        if (dataConsulta.isAfter(agora.plusYears(1))) return false;

        return true;
    }

    // =========================
    // 🔹 toString (debug / console)
    // =========================

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", usuario='" + nomeUsuario + '\'' +
                ", medico='" + nomeMedico + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", data=" + dataConsulta +
                ", status='" + status + '\'' +
                '}';
    }
}
