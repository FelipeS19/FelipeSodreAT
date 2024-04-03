import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import Controller.PedidosController;
import model.domain.Pedidos;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PedidosControllerTest {
    private PedidosController controller;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        controller = new PedidosController();
        request = mock(Request.class);
        response = mock(Response.class);
    }
    @Test
    public void testGetAllPedidos() {
        // Criando alguns pedidos de teste
        Map<Integer, Pedidos> pedidosMapTest = new HashMap<>();
        Pedidos pedido1 = new Pedidos(1, "Maria Silva", "Camisa polo", 50);
        Pedidos pedido2 = new Pedidos(2, "João Santos", "Calça jeans", 70);
        pedidosMapTest.put(pedido1.getId(), pedido1);
        pedidosMapTest.put(pedido2.getId(), pedido2);

        // Definindo os pedidos de teste no controlador de pedidos
        controller.getpedidosMap().clear();
        controller.getpedidosMap().putAll(pedidosMapTest);

        // Executando o método getAllClientes
        Object result = controller.getAllPedidos(request, response);

        // Verificando o tipo de resposta
        when(response.type()).thenReturn("text/html");

        // Verificando a resposta esperada
        StringBuilder expectedResponse = new StringBuilder("[");
        expectedResponse.append("{\"id\":1,\"cliente\":\"Maria Silva\",\"Produtos\":\"Camisa polo\",\"total\":50.0},");
        expectedResponse.append("{\"id\":2,\"cliente\":\"João Santos\",\"Produtos\":\"Calça jeans\",\"total\":70.0}");
        expectedResponse.append("]");

        assertEquals(expectedResponse.toString(), result);
    }


    @Test
    public void testAddPedido() {
        when(request.body()).thenReturn("{\"cliente\":\"Maria Silva\", \"descricao\":\"Camisa polo\", \"valor\":50}");
        Object result = controller.addPedido(request, response);
        verify(response).status(201);
        assertEquals("Pedido adicionado com sucesso!", result);
    }

    @Test
    public void testDeletePedido() {
        when(request.params(":id")).thenReturn("3");
        Object result = controller.deletePedido(request, response);
        verify(response).status(404);
        assertEquals("Pedido não encontrado.", result);
    }
}
