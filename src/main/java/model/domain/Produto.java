package model.domain;


public class Produto {
    private int id;
    private String Nome;
    private String Marca;
    private double preço;
    private String desc;

    public Produto(int id, String Nome, String Marca, double preço, String desc) {
        this.id = id;
        this.Nome = Nome;
        this.Marca = Marca;
        this.preço = preço;
        this.desc = desc;
    }



    public String getNome() {
        return Nome;
    }

    public String getMarca() {
        return Marca;
    }

    public double getPreço() {
        return preço;
    }

    public String getDesc() {
        return desc;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
