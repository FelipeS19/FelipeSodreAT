import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import Controller.ClientesController;
import model.domain.Clientes;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ClientesControllerTest {
    private ClientesController controller;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        controller = new ClientesController();
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test
    public void testGetAllClientes() {
        Map<Integer, Clientes> clientesMapTest = new HashMap<>();
        Clientes cliente1 = new Clientes(1, "Ana Claudia", "21931-270", "11111-1111");
        Clientes cliente2 = new Clientes(2, "João Silva", "23027-460", "22222-2222");
        clientesMapTest.put(cliente1.getId(), cliente1);
        clientesMapTest.put(cliente2.getId(), cliente2);

        ClientesController.getClientesMap().clear();
        ClientesController.getClientesMap().putAll(clientesMapTest);

        // Execução do método
        Object result = controller.getAllClientes(request, response);


        // Verificação do tipo de resposta
        when(response.type()).thenReturn("text/html"); // Simulando o tipo de resposta

        // Verificação da resposta
        StringBuilder expectedResponse = new StringBuilder("[");
        expectedResponse.append("{\"id\":1,\"Nome\":\"Ana Claudia\",\"cep\":\"21931-270\",\"Telefone\":\"11111-1111\",\"endereco\":\"Rua Henrique Barbosa de Amorim, Jardim Guanabara, RJ\"},");
        expectedResponse.append("{\"id\":2,\"Nome\":\"João Silva\",\"cep\":\"23027-460\",\"Telefone\":\"22222-2222\",\"endereco\":\"Estrada da Pedra, Guaratiba, RJ\"}");
        expectedResponse.append("]");

        assertEquals(expectedResponse.toString(), result); // Verifica se o conteúdo da resposta está correto
    }
    @Test
    public void testAddCliente() {
        when(request.body()).thenReturn("{\"Nome\":\"Teste\", \"cep\":\"12345-678\", \"Telefone\":\"123456789\"}");
        Object result = controller.addCliente(request, response);
        verify(response).status(201);
        assertEquals("Cliente adicionado com sucesso!", result);
    }

    @Test
    public void testDeleteCliente() {
        when(request.params(":id")).thenReturn("3");
        Object result = controller.deleteCliente(request, response);
        verify(response).status(404);
        assertEquals("Cliente não encontrado.", result);
    }
}