package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.TipoQuilhaDTO;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import br.unitins.topicos1.prancha.service.TipoQuilhaService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TipoQuilhaResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    TipoQuilhaResource tipoQuilhaResource; // injeta o resource real

    @Mock
    TipoQuilhaService tipoQuilhaService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        tipoQuilhaResource.service = tipoQuilhaService;
    }

    // buscar todos os tipos de quilha com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        TipoQuilha tq = new TipoQuilha();
        tq.setId(1L);
        tq.setNome("Single Fin");

        when(tipoQuilhaService.findAll()).thenReturn(List.of(tq)); // quando o service for chamado, vai retornar essa lista de tipos

        given()
        .when()
        .get("/tipos-quilha")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Single Fin"));
    }

    // buscar todos os tipos de quilha com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        TipoQuilha tq = new TipoQuilha();
        tq.setId(2L);
        tq.setNome("Quad Fin");

        when(tipoQuilhaService.findAll()).thenReturn(List.of(tq)); // quando o service for chamado, vai retornar essa lista de tipos

        given()
        .when()
        .get("/tipos-quilha")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Quad Fin"));
    }

    // buscar todos os tipos de quilha pelo nome com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByNomeUser() {
        TipoQuilha tq = new TipoQuilha();
        tq.setId(3L);
        tq.setNome("Single Fin");

        when(tipoQuilhaService.findByNome("Single Fin")).thenReturn(List.of(tq)); // quando o service com esse nome for chamado, vai retornar essa lista de tipos

        given()
        .when()
        .get("/tipos-quilha/nome/Single Fin")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Single Fin"));
    }

    // buscar todos os tipos de quilha pelo nome com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByNomeAdm() {
        TipoQuilha tq = new TipoQuilha();
        tq.setId(4L);
        tq.setNome("Quad Fin");

        when(tipoQuilhaService.findByNome("Quad Fin")).thenReturn(List.of(tq)); // quando o service com esse nome for chamado, vai retornar essa lista de tipos

        given()
        .when()
        .get("/tipos-quilha/nome/Quad Fin")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Quad Fin"));
    }

    // cadastrando um tipo de quilha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        TipoQuilhaDTO dto = new TipoQuilhaDTO("Five Fin");
        TipoQuilha tq = new TipoQuilha();
        tq.setId(5L);
        tq.setNome(dto.nome());

        when(tipoQuilhaService.create(dto)).thenReturn(tq); // quando o service for chamado, vai retornar um tipo de quilha criado

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/tipos-quilha")
        .then()
        .statusCode(201)
        .body("nome", equalTo("Five Fin"));
    }

    // alterando um tipo de quilha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        TipoQuilhaDTO dtoAlterado = new TipoQuilhaDTO("Quad Fin");

        doNothing().when(tipoQuilhaService).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dtoAlterado)
        .when()
        .put("/tipos-quilha/1")
        .then()
        .statusCode(204);
    }

    // apagando um tipo de quilha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(tipoQuilhaService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/tipos-quilha/1")
        .then()
        .statusCode(204);
    }
}