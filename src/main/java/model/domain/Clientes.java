package model.domain;

public class Clientes {
    private int id;
    private final String Nome;
    private final String Telefone;
    private final String cep;
    private String Endereco;

    public Clientes(int id, String Nome, String cep, String Telefone) {
        this.id = id;
        this.Nome = Nome;
        this.cep = cep;
        this.Telefone = Telefone;
    }
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {return Nome;}

    public String getTelefone() {return Telefone;}

    public String getcep() {return cep;}

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereço) {
        Endereco = endereço;
    }
}
