package br.unitins.topicos1.prancha.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.CartaoDTO;
import br.unitins.topicos1.prancha.dto.CartaoResponseDTO;
import br.unitins.topicos1.prancha.model.Cartao;
import br.unitins.topicos1.prancha.service.CartaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class CartaoResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    CartaoResource cartaoResource; // injeta o resource real

    @Mock
    CartaoService cartaoService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        cartaoResource.cartaoService = cartaoService;
    }

    // buscar todos os cartões com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {

        Cartao cartao = new Cartao();
        cartao.setId(1L);
        cartao.setNumeroCartao("2222333344445555");
        cartao.setNomeTitular("Titular Teste");
        cartao.setDataVencimento(LocalDate.of(2040, 1, 1));

        CartaoResponseDTO response = new CartaoResponseDTO(cartao);

        when(cartaoService.findAll()).thenReturn(List.of(response)); // quando o service for chamado, vai retornar essa lista de cartões

        given()
            .when()
            .get("/cartoes")
            .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].numeroCartao", equalTo("2222333344445555"));
    }

    // buscar todos os cartões com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {

        when(cartaoService.findAll()).thenReturn(List.of()); // quando o service for chamado, vai retornar essa lista de cartões

        given()
            .when()
            .get("/cartoes")
            .then()
            .statusCode(200);
    }

    // buscar todos os cartões pelo número com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByNumeroCartaoUser() {

        Cartao cartao = new Cartao();
        cartao.setId(2L);
        cartao.setNumeroCartao("1111222233334444");
        cartao.setNomeTitular("Usuário");
        cartao.setDataVencimento(LocalDate.of(2040, 1, 1));

        CartaoResponseDTO response = new CartaoResponseDTO(cartao);

        when(cartaoService.findByNumeroCartao("1111222233334444")).thenReturn(List.of(response)); // quando o service com esse número for chamado, vai retornar essa lista de cartões

        given()
            .when()
            .get("/cartoes/numeroCartao/1111222233334444")
            .then()
            .statusCode(200)
            .body("[0].numeroCartao", equalTo("1111222233334444"));
    }

    // buscar todos os cartões pelo número com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByNumeroCartaoAdm() {

        when(cartaoService.findByNumeroCartao("9999888877776666")).thenReturn(List.of()); // quando o service com esse número for chamado, vai retornar essa lista de cartões

        given()
            .when()
            .get("/cartoes/numeroCartao/9999888877776666")
            .then()
            .statusCode(200);
    }

    // cadastrando um novo cartão
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {

        CartaoDTO dto = new CartaoDTO(
            "5555666677778888",
            "Titular Inclusão",
            LocalDate.of(2041, 1, 1)
        );

        Cartao cartao = new Cartao();
        cartao.setId(3L);
        cartao.setNumeroCartao(dto.numeroCartao());
        cartao.setNomeTitular(dto.nomeTitular());
        cartao.setDataVencimento(dto.dataVencimento());

        CartaoResponseDTO response = new CartaoResponseDTO(cartao);

        when(cartaoService.create(dto)).thenReturn(response); // quando o service for chamado, vai retornar um cartão criado

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .post("/cartoes")
            .then()
            .statusCode(201)
            .body("numeroCartao", equalTo("5555666677778888"))
            .body("nomeTitular", equalTo("Titular Inclusão"));
    }

    // alterando um cartão
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {

        CartaoDTO dto = new CartaoDTO(
            "9999000011112222",
            "Titular Alterado",
            LocalDate.of(2042, 1, 1)
        );

        doNothing().when(cartaoService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .put("/cartoes/1")
            .then()
            .statusCode(204);
    }

    // apagando um cartão
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {

        doNothing().when(cartaoService).delete(1L); // quando o service for chamado, não faz nada

        given()
            .when()
            .delete("/cartoes/1")
            .then()
            .statusCode(204);
    }
}