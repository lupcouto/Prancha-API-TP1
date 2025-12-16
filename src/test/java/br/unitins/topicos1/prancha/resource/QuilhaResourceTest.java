package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.QuilhaDTO;
import br.unitins.topicos1.prancha.model.Quilha;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import br.unitins.topicos1.prancha.service.QuilhaService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class QuilhaResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    QuilhaResource quilhaResource; // injeta uma resource real

    @Mock
    QuilhaService quilhaService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        quilhaResource.service = quilhaService;
    }

    // buscar todas as quilhas com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Quilha quilha = new Quilha();
        quilha.setId(1L);
        quilha.setDescricaoQuilha("Quilha Teste");
        TipoQuilha tipo = new TipoQuilha();
        tipo.setId(1L);
        tipo.setNome("Single Fin");
        quilha.setTipoQuilha(tipo);

        when(quilhaService.findAll()).thenReturn(List.of(quilha)); // quando o service for chamado, vai retornar essa lista de quilhas

        given()
        .when()
        .get("/quilhas")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].descricaoQuilha", equalTo("Quilha Teste"));
    }

    // buscar todas as quilhas com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Quilha quilha = new Quilha();
        quilha.setId(2L);
        quilha.setDescricaoQuilha("Quilha ADM");
        TipoQuilha tipo = new TipoQuilha();
        tipo.setId(1L);
        tipo.setNome("Single Fin");
        quilha.setTipoQuilha(tipo);

        when(quilhaService.findAll()).thenReturn(List.of(quilha)); // quando o service for chamado, vai retornar essa lista de quilhas

        given()
        .when()
        .get("/quilhas")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].descricaoQuilha", equalTo("Quilha ADM"));
    }

    // buscar todas as quilhas pelo tipo de quilha com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByTipoQuilhaUser() {
        TipoQuilha tipo = new TipoQuilha();
        tipo.setId(1L);
        tipo.setNome("Single Fin");

        Quilha quilha = new Quilha();
        quilha.setId(3L);
        quilha.setDescricaoQuilha("Quilha Tipo");
        quilha.setTipoQuilha(tipo);

        when(quilhaService.findByTipoQuilha(any(TipoQuilha.class))).thenReturn(List.of(quilha)); // quando o service com esse tipo de quilha for chamado, vai retornar essa lista de quilhas

        given()
        .when()
        .get("/quilhas/tipoquilha/1")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].descricaoQuilha", equalTo("Quilha Tipo"));
    }

    // buscar todas as quilhas pelo tipo de quilha com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByTipoQuilhaAdm() {
        TipoQuilha tipo = new TipoQuilha();
        tipo.setId(1L);
        tipo.setNome("Single Fin");

        Quilha quilha = new Quilha();
        quilha.setId(4L);
        quilha.setDescricaoQuilha("Quilha Tipo ADM");
        quilha.setTipoQuilha(tipo);

        when(quilhaService.findByTipoQuilha(any(TipoQuilha.class))).thenReturn(List.of(quilha)); // quando o service com esse tipo de quilha for chamado, vai retornar essa lista de quilhas

        given()
        .when()
        .get("/quilhas/tipoquilha/1")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].descricaoQuilha", equalTo("Quilha Tipo ADM"));
    }

    // cadastrando uma quilha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        QuilhaDTO dto = new QuilhaDTO("Nova Quilha", 1L);

        Quilha quilha = new Quilha();
        quilha.setId(5L);
        quilha.setDescricaoQuilha(dto.descricaoQuilha());
        TipoQuilha tipo = new TipoQuilha();
        tipo.setId(dto.idTipoQuilha());
        tipo.setNome("Single Fin");
        quilha.setTipoQuilha(tipo);

        when(quilhaService.create(dto)).thenReturn(quilha); // quando o service for chamado, vai retornar uma quilha criada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/quilhas")
        .then()
        .statusCode(201)
        .body("descricaoQuilha", equalTo("Nova Quilha"))
        .body("tipoQuilha.id", equalTo(1));
    }

    // alterando uma quilha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        QuilhaDTO dto = new QuilhaDTO("Quilha Atualizada", 1L);

        doNothing().when(quilhaService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .put("/quilhas/1")
        .then()
        .statusCode(204);
    }

    // apagando uma quilha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(quilhaService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/quilhas/1")
        .then()
        .statusCode(204);
    }
}