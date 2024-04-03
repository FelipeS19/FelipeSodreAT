package model.domain;

public class Pedidos {
    private int id;
    private String cliente;
    private String Produtos;
    private double total;

    public Pedidos(int id, String cliente, String Produtos, double total) {
        this.id = id;
        this.cliente = cliente;
        this.Produtos = Produtos;
        this.total = total;
    }
    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }
    public String getCliente() {
        return cliente;
    }

    public String getProdutos() {
        return Produtos;
    }

    public double getTotal() {
        return total;
    }



}
