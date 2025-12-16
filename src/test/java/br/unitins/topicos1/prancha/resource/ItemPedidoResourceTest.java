package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.ItemPedidoDTO;
import br.unitins.topicos1.prancha.model.ItemPedido;
import br.unitins.topicos1.prancha.model.Pedido;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.service.ItemPedidoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class ItemPedidoResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    ItemPedidoResource itemPedidoResource; // injeta o resource real

    @Mock
    ItemPedidoService itemPedidoService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        itemPedidoResource.itemPedidoService = itemPedidoService;
    }

    // buscar todos os itens desse pedido com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByPedidoUser() {
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setQuantidade(2);
        item.setPrecoUnit(1000.0);
        item.setPrancha(new Prancha());
        item.setPedido(new Pedido());

        when(itemPedidoService.findByPedido(1L)).thenReturn(List.of(item)); // quando o service com esse pedido for chamado, vai retornar essa lista de itens

        given()
        .when()
        .get("/pedidos/1/itens")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].quantidade", equalTo(2));
    }

    // buscar todos os itens desse pedido com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByPedidoAdm() {
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setQuantidade(2);
        item.setPrecoUnit(1000.0);
        item.setPrancha(new Prancha());
        item.setPedido(new Pedido());

        when(itemPedidoService.findByPedido(1L)).thenReturn(List.of(item)); // quando o service com esse pedido for chamado, vai retornar essa lista de itens

        given()
        .when()
        .get("/pedidos/1/itens")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
    }

    // cadastrando um item
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {
        ItemPedidoDTO dto = new ItemPedidoDTO(1L, 3, 1500.0);

        ItemPedido item = new ItemPedido();
        item.setId(2L);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnit(dto.precoUnit());
        item.setPrancha(new Prancha());
        item.setPedido(new Pedido());

        when(itemPedidoService.create(1L, dto)).thenReturn(item); // quando o service for chamado, vai retornar um item criado

        given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post("/pedidos/1/itens")
        .then()
        .statusCode(201)
        .body("quantidade", equalTo(3))
        .body("precoUnit", equalTo(1500.0F));
    }

    // alterando um item
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {
        ItemPedidoDTO dtoAlterado = new ItemPedidoDTO(1L, 5, 800.0);

        doNothing().when(itemPedidoService).update(1L, 1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(ContentType.JSON)
        .body(dtoAlterado)
        .when()
        .put("/pedidos/1/itens/1")
        .then()
        .statusCode(204);
    }

    // apagando um item
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {
        doNothing().when(itemPedidoService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/pedidos/1/itens/1")
        .then()
        .statusCode(204);
    }
}