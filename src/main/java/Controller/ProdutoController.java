package Controller;

import com.google.gson.Gson;
import model.domain.Produto;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class ProdutoController {

    public static Map<Integer, Produto> getProdutosMap() {
        return produtosMap;
    }
    private static Map<Integer, Produto> produtosMap = new HashMap<>();
    private static int nextId = 1;

    private static void addProdutosPredefinidos() {
        Produto produto1 = new Produto(nextId++, "Tenis adidas forum low", "adidas",2000,"Tennis");
        Produto produto2 = new Produto(nextId++, "Tenis nike airForce Shadow", "Nike", 5000,"Tennis");

        produtosMap.put(produto1.getId(), produto1);
        produtosMap.put(produto2.getId(), produto2);
    }
    public static void caminhos() {
        addProdutosPredefinidos();

        Spark.get("/produtos", ProdutoController::getAllProdutos);

        Spark.post("/produtos", ProdutoController::addProduto);

        Spark.delete("/produtos/:id", ProdutoController::deleteProduto);
    }

    public static Object getAllProdutos(Request request, Response response) {
        response.type("text/html");
        return new Gson().toJson(produtosMap.values());
    }

    public static Object addProduto(Request request, Response response) {
        Produto produto = new Gson().fromJson(request.body(), Produto.class);
        produto.setId(nextId++);
        produtosMap.put(produto.getId(), produto);
        response.status(201); // Status de criação
        return "Produto adicionado com sucesso!";
    }

    public static Object deleteProduto(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        if (produtosMap.containsKey(id)) {
            produtosMap.remove(id);
            reorganizarIds();
            return "Produto deletado com sucesso!";
        } else {
            response.status(404);
            return "Produto não encontrado.";
        }
    }

    private static void reorganizarIds() {
        Map<Integer, Produto> novoMapa = new HashMap<>();
        int novoId = 1;
        for (Produto produto : produtosMap.values()) {
            produto.setId(novoId++);
            novoMapa.put(produto.getId(), produto);
        }
        produtosMap = novoMapa;
        nextId = novoId;
    }
}
