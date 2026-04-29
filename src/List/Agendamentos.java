package List;

import java.time.LocalDateTime;
public class Agendamentos {



	    private int idConsulta;
	    private String nomeUsuario;
	    private String nomeMedico;
	    private String tipoMedico;
	    private LocalDateTime dataConsulta;
	    private String status;
	    private int idAgendamento;


	    public int getIdAgendamento() {
	        return idAgendamento;
	    }

	    public void setIdAgendamento(int idAgendamento) {
	        this.idAgendamento = idAgendamento;
	    }

	    // GETTERS E SETTERS

	    public int getIdConsulta() {
	        return idConsulta;
	    }

	    public void setIdConsulta(int idConsulta) {
	        this.idConsulta = idConsulta;
	    }

	    public String getNomeUsuario() {
	        return nomeUsuario;
	    }

	    public void setNomeUsuario(String nomeUsuario) {
	        this.nomeUsuario = nomeUsuario;
	    }

	    public String getNomeMedico() {
	        return nomeMedico;
	    }

	    public void setNomeMedico(String nomeMedico) {
	        this.nomeMedico = nomeMedico;
	    }

	    public String getTipoMedico() {
	        return tipoMedico;
	    }

	    public void setTipoMedico(String tipoMedico) {
	        this.tipoMedico = tipoMedico;
	    }

	    public LocalDateTime getDataConsulta() {
	        return dataConsulta;
	    }

	    public void setDataConsulta(LocalDateTime dataConsulta) {
	        this.dataConsulta = dataConsulta;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    
	}

}
