package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.*;
import br.unitins.topicos1.prancha.model.Pix;
import br.unitins.topicos1.prancha.service.PedidoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class PedidoResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    PedidoResource pedidoResource; // injeta o resource real

    @Mock
    PedidoService pedidoService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        pedidoResource.pedidoService = pedidoService;
    }

    // buscar todos os pedidos com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Pix pix = new Pix();
        pix.setChave("chave-teste");

        PedidoResponseDTO response = new PedidoResponseDTO(
            1L,
            LocalDateTime.now(),
            200.0,
            new ClienteDTO("Cliente Teste", "63", "999999999", "12345678910"),
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixResponseDTO(pix),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, 100.0))
        );

        when(pedidoService.findAll()).thenReturn(List.of(response)); // quando o service for chamado, vai retornar essa lista de pedidos

        given()
        .when()
        .get("/pedidos")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].id", equalTo(1));
    }

    // buscar todos os pedidos com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Pix pix = new Pix();
        pix.setChave("chave-teste");

        PedidoResponseDTO response = new PedidoResponseDTO(
            1L,
            LocalDateTime.now(),
            200.0,
            new ClienteDTO("Cliente Teste", "63", "999999999", "12345678910"),
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixResponseDTO(pix),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, 100.0))
        );

        when(pedidoService.findAll()).thenReturn(List.of(response)); // quando o service for chamado, vai retornar essa lista de pedidos

        given()
        .when()
        .get("/pedidos")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].id", equalTo(1));
    }

    // buscar todos os pedidos pelo cliente com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByClienteUser() {
        Long idCliente = 1L;
        Pix pix = new Pix();
        pix.setChave("chave-teste");

        PedidoResponseDTO response = new PedidoResponseDTO(
            1L,
            LocalDateTime.now(),
            200.0,
            new ClienteDTO("Cliente Teste", "63", "999999999", "12345678910"),
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixResponseDTO(pix),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, 100.0))
        );

        when(pedidoService.findByCliente(idCliente)).thenReturn(List.of(response)); // quando o service com esse cliente for chamado, vai retornar essa lista de pedidos

        given()
        .when()
        .get("/pedidos/cliente/" + idCliente)
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].cliente.nome", equalTo("Cliente Teste"));
    }

    // buscar todos os pedidos pelo cliente com o perfil user
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByClienteAdm() {
        Long idCliente = 1L;
        Pix pix = new Pix();
        pix.setChave("chave-teste");

        PedidoResponseDTO response = new PedidoResponseDTO(
            1L,
            LocalDateTime.now(),
            200.0,
            new ClienteDTO("Cliente Teste", "63", "999999999", "12345678910"),
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixResponseDTO(pix),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, 100.0))
        );

        when(pedidoService.findByCliente(idCliente)).thenReturn(List.of(response)); // quando o service com esse cliente for chamado, vai retornar essa lista de pedidos

        given()
        .when()
        .get("/pedidos/cliente/" + idCliente)
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].cliente.nome", equalTo("Cliente Teste"));
    }

    // cadastrando um pedido
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {
        Pix pix = new Pix();
        pix.setChave("chave-teste");

        PedidoDTO dto = new PedidoDTO(
            1L,
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixDTO("chave-teste"),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, null))
        );

        PedidoResponseDTO response = new PedidoResponseDTO(
            1L,
            LocalDateTime.now(),
            200.0,
            new ClienteDTO("Cliente Teste", "63", "999999999", "12345678910"),
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixResponseDTO(pix),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, 100.0))
        );

        when(pedidoService.create(dto)).thenReturn(response); // quando o service for chamado, vai retornar um pedido criado

        given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post("/pedidos")
        .then()
        .statusCode(201)
        .body("cliente.nome", equalTo("Cliente Teste"))
        .body("formaPagamento", equalTo("PIX"));
    }

    // alterando um pedido
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {
        PedidoDTO dto = new PedidoDTO(
            1L,
            new EnderecoDTO("Cidade", "UF", "00000-000"),
            "PIX",
            new PixDTO("chave-teste"),
            null,
            null,
            List.of(new ItemPedidoDTO(1L, 2, null))
        );

        doNothing().when(pedidoService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .put("/pedidos/1")
        .then()
        .statusCode(204);
    }

    // pagando um pedido
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testPagar() {
        doNothing().when(pedidoService).pagar(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .put("/pedidos/1/pagar")
        .then()
        .statusCode(204);
    }

    // finalizando um pedido
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testFinalizar() {
        doNothing().when(pedidoService).finalizar(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .put("/pedidos/1/finalizar")
        .then()
        .statusCode(200)
        .body(equalTo("Pedido finalizado com sucesso."));
    }

    // apagando um pedido
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {
        doNothing().when(pedidoService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/pedidos/1")
        .then()
        .statusCode(204);
    }
}