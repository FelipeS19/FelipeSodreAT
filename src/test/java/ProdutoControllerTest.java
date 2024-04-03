import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import Controller.ProdutoController;
import model.domain.Produto;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ProdutoControllerTest {
    private ProdutoController controller;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        controller = new ProdutoController();
        request = mock(Request.class);
        response = mock(Response.class);
    }


    @Test
    public void testGetAllProdutos() {
        // Criando alguns produtos de teste
        Map<Integer, Produto> produtosMapTest = new HashMap<>();
        Produto produto1 = new Produto(1, "Camisa polo", "Ralph Lauren", 100,"Camisa");
        Produto produto2 = new Produto(2, "Calça jeans", "Levis", 80,"Calça");
        produtosMapTest.put(produto1.getId(), produto1);
        produtosMapTest.put(produto2.getId(), produto2);

        // Definindo os produtos de teste no controlador de produtos
        controller.getProdutosMap().clear();
        controller.getProdutosMap().putAll(produtosMapTest);

        // Executando o método getAllProdutos
        Object result = controller.getAllProdutos(request, response);

        // Verificando o tipo de resposta
        when(response.type()).thenReturn("text/html");

        // Verificando a resposta esperada
        StringBuilder expectedResponse = new StringBuilder("[");
        expectedResponse.append("{\"id\":1,\"Nome\":\"Camisa polo\",\"Marca\":\"Ralph Lauren\",\"preço\":100.0,\"desc\":\"Camisa\"},");
        expectedResponse.append("{\"id\":2,\"Nome\":\"Calça jeans\",\"Marca\":\"Levis\",\"preço\":80.0,\"desc\":\"Calça\"}");
        expectedResponse.append("]");

        assertEquals(expectedResponse.toString(), result);
    }

    @Test
    public void testAddProduto() {
        when(request.body()).thenReturn("{\"nome\":\"Camisa polo\", \"marca\":\"Ralph Lauren\", \"preco\":100}");
        Object result = controller.addProduto(request, response);
        verify(response).status(201);
        assertEquals("Produto adicionado com sucesso!", result);
    }

    @Test
    public void testDeleteProduto() {
        when(request.params(":id")).thenReturn("3");
        Object result = controller.deleteProduto(request, response);
        verify(response).status(404);
        assertEquals("Produto não encontrado.", result);
    }
}