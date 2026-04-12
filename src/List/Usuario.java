package List;

public class Usuario {

    private int id;
    private String matricula;
    private String nome;
    private String email;
    private String cpf;
    private String tipo;

    // getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getTipo() {
    	return tipo;
    }
    public void setTipo(String tipo) {
    	this.tipo= tipo;
    	
    }
}
