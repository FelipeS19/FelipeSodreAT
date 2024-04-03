package Controller;

import com.google.gson.Gson;
import model.domain.Clientes;
import model.domain.endereço;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClientesController {

    public static Map<Integer, Clientes> getClientesMap() {
        return clientesMap;
    }
    private static Map<Integer, Clientes> clientesMap = new HashMap<>();
    private static int nextId = 1;

    private static void addClientesPredefinidos() {
        Clientes cliente1 = new Clientes(nextId++, "Ana Claudia", "21931-270", "11111-1111");
        Clientes cliente2 = new Clientes(nextId++, "João Silva", "23027-460", "22222-2222");

        clientesMap.put(cliente1.getId(), cliente1);
        clientesMap.put(cliente2.getId(), cliente2);
    }

    public static void caminhos() {
        addClientesPredefinidos();

        Spark.get("/clientes", ClientesController::getAllClientes);

        Spark.post("/clientes", ClientesController::addCliente);

        Spark.delete("/clientes/:id", ClientesController::deleteCliente);
    }

    public static Object getAllClientes(Request request, Response response) {
        response.type("text/html");
        StringBuilder clientesJson = new StringBuilder("[");
        for (Clientes cliente : clientesMap.values()) {
            String endereco = getEnderecoByCep(cliente.getcep());
            cliente.setEndereco(endereco);
            clientesJson.append("{\"id\":").append(cliente.getId())
                    .append(",\"Nome\":\"").append(cliente.getNome())
                    .append("\",\"cep\":\"").append(cliente.getcep())
                    .append("\",\"Telefone\":\"").append(cliente.getTelefone())
                    .append("\",\"endereco\":\"").append(cliente.getEndereco())
                    .append("\"},");
        }
        if (!clientesMap.isEmpty()) {
            clientesJson.setLength(clientesJson.length() - 1); // Remover a última vírgula
        }
        clientesJson.append("]");
        return clientesJson.toString();
    }

    public static Object addCliente(Request request, Response response) {
        Clientes cliente = new Gson().fromJson(request.body(), Clientes.class);
        cliente.setId(nextId++);
        clientesMap.put(cliente.getId(), cliente);
        response.status(201);
        return "Cliente adicionado com sucesso!";
    }

    public static Object deleteCliente(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        if (clientesMap.containsKey(id)) {
            clientesMap.remove(id);
            reorganizarIds();
            return "Cliente deletado com sucesso!";
        } else {
            response.status(404);
            return "Cliente não encontrado.";
        }
    }
    private static void reorganizarIds() {
        Map<Integer, Clientes> novoMapa = new HashMap<>();
        int novoId = 1;
        for (Clientes cliente : clientesMap.values()) {
            cliente.setId(novoId++);
            novoMapa.put(cliente.getId(), cliente);
        }
        clientesMap = novoMapa;
        nextId = novoId;
    }
    private static String getEnderecoByCep(String cep) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            StringBuilder endereco = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                endereco.append(output);
            }
            conn.disconnect();
            Gson gson = new Gson();
            endereço enderecoResponse = gson.fromJson(endereco.toString(), endereço.class);
            return enderecoResponse.getLogradouro() + ", " + enderecoResponse.getBairro() + ", " + enderecoResponse.getUf();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
