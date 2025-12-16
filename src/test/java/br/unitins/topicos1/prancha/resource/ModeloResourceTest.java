package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.ModeloDTO;
import br.unitins.topicos1.prancha.model.Marca;
import br.unitins.topicos1.prancha.model.Modelo;
import br.unitins.topicos1.prancha.service.ModeloService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class ModeloResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    ModeloResource modeloResource; // injeta resource real

    @Mock
    ModeloService service; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        modeloResource.service = service;
    }

    // buscar todos os modelos com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("DNA");
        modelo.setMarca(new Marca());

        when(service.findAll()).thenReturn(List.of(modelo)); // quando o service for chamado, vai retornar essa lista de modelos

        given()
        .when()
        .get("/modelos")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("DNA"));
    }

    // buscar todos os modelos com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("DNA");
        modelo.setMarca(new Marca());

        when(service.findAll()).thenReturn(List.of(modelo)); // quando o service for chamado, vai retornar essa lista de modelos

        given()
        .when()
        .get("/modelos")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("DNA"));
    }

    // buscar todos os modelos pelo nome com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByNomeUser() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("DNA");
        modelo.setMarca(new Marca());

        when(service.findByNome("DNA")).thenReturn(List.of(modelo)); // quando o service com esse nome for chamado, vai retornar essa lista de modelos

        given()
        .when()
        .get("/modelos/nome/DNA")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("DNA"));
    }

    // buscar todos os modelos pelo nome com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByNomeAdm() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("DNA");
        modelo.setMarca(new Marca());

        when(service.findByNome("DNA")).thenReturn(List.of(modelo)); // quando o service com esse nome for chamado, vai retornar essa lista de modelos

        given()
        .when()
        .get("/modelos/nome/DNA")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("DNA"));
    }

    // cadastrando um modelo
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        ModeloDTO dto = new ModeloDTO("Seaside", 1L);

        Modelo modelo = new Modelo();
        modelo.setId(2L);
        modelo.setNome(dto.nome());
        Marca marca = new Marca();
        marca.setId(dto.idMarca());
        modelo.setMarca(marca);

        when(service.create(dto)).thenReturn(modelo); // quando o service for chamado, vai retornar um modelo criado

        given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post("/modelos")
        .then()
        .statusCode(201)
        .body("nome", equalTo("Seaside"))
        .body("marca.id", equalTo(1));
    }

    // alterando um modelo
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        ModeloDTO dtoAlterado = new ModeloDTO("Fusion Fish", 1L);

        doNothing().when(service).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(ContentType.JSON)
        .body(dtoAlterado)
        .when()
        .put("/modelos/1")
        .then()
        .statusCode(204);
    }

    // apagando um modelo
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(service).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/modelos/1")
        .then()
        .statusCode(204);
    }
}