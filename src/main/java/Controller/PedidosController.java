package Controller;

import com.google.gson.Gson;
import model.domain.Pedidos;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class PedidosController {

    public static Map<Integer, Pedidos> getpedidosMap() {
        return pedidosMap;
    }
    private static Map<Integer, Pedidos> pedidosMap = new HashMap<>();
    private static int nextId = 1;

    private static void addPedidosPredefinidos() {
        Pedidos pedido1 = new Pedidos(nextId++, "Ana Claudia", "Tenis adidas forum low", 2000);
        Pedidos pedido2 = new Pedidos(nextId++, "João Silva", "Tenis nike airForce Shadow", 5000);

        pedidosMap.put(pedido1.getId(), pedido1);
        pedidosMap.put(pedido2.getId(), pedido2);
    }

    public static void caminhos() {
        addPedidosPredefinidos();

        Spark.get("/pedidos", PedidosController::getAllPedidos);

        Spark.post("/pedidos", PedidosController::addPedido);

        Spark.delete("/pedidos/:id", PedidosController::deletePedido);
    }

    public static Object getAllPedidos(Request request, Response response) {
        response.type("text/html");
        return new Gson().toJson(pedidosMap.values());
    }

    public static Object addPedido(Request request, Response response) {
        Pedidos pedido = new Gson().fromJson(request.body(), Pedidos.class);
        pedido.setId(nextId++);
        pedidosMap.put(pedido.getId(), pedido);
        response.status(201);
        return "Pedido adicionado com sucesso!";
    }

    public static Object deletePedido(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        if (pedidosMap.containsKey(id)) {
            pedidosMap.remove(id);
            reorganizarIds();
            return "Pedido deletado com sucesso!";
        } else {
            response.status(404);
            return "Pedido não encontrado.";
        }
    }

    private static void reorganizarIds() {
        int novoId = 1;
        for (Pedidos pedido : pedidosMap.values()) {
            pedido.setId(novoId++);
        }
        nextId = novoId; // Atualizar o próximo ID
    }

}
