package LojaAPP;

import Controller.ClientesController;
import Controller.PedidosController;
import Controller.ProdutoController;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        Spark.port(8080);

        Spark.staticFileLocation("/");

        Spark.get("/", (req, res) -> {
            return App.class.getResourceAsStream("/index.html");
        });
        ClientesController.caminhos();
        PedidosController.caminhos();
        ProdutoController.caminhos();

        System.out.println("Servidor rodando na porta 8080...");
    }
}