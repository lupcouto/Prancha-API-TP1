package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.MarcaDTO;
import br.unitins.topicos1.prancha.model.Marca;
import br.unitins.topicos1.prancha.service.MarcaService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class MarcaResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    MarcaResource marcaResource; // injeta o resource real

    @Mock
    MarcaService service; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        marcaResource.service = service;
    }

    // buscar todas as marcas com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Mormaii");
        marca.setPaisOrigem("Brasil");

        when(service.findAll()).thenReturn(List.of(marca)); // quando o service for chamado, vai retornar essa lista de marcas

        given()
        .when()
        .get("/marcas")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Mormaii"));
    }

    // buscar todas as marcas com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Mormaii");
        marca.setPaisOrigem("Brasil");

        when(service.findAll()).thenReturn(List.of(marca)); // quando o service for chamado, vai retornar essa lista de marcas

        given()
        .when()
        .get("/marcas")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
    }

    // buscar todas as marcas pelo nome com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByNomeUser() {
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Mormaii");
        marca.setPaisOrigem("Brasil");

        when(service.findByNome("Mormaii")).thenReturn(List.of(marca)); // quando o service com esse nome for chamado, vai retornar essa lista de marcas

        given()
        .when()
        .get("/marcas/nome/Mormaii")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Mormaii"))
        .body("[0].paisOrigem", equalTo("Brasil"));
    }

    // buscar todas as marcas pelo nome com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByNomeAdm() {
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNome("Mormaii");
        marca.setPaisOrigem("Brasil");

        when(service.findByNome("Mormaii")).thenReturn(List.of(marca)); // quando o service com esse nome for chamado, vai retornar essa lista de marcas

        given()
        .when()
        .get("/marcas/nome/Mormaii")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Mormaii"))
        .body("[0].paisOrigem", equalTo("Brasil"));
    }

    // cadastrando uma marca
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        MarcaDTO dto = new MarcaDTO("Rusty", "Estados Unidos");

        Marca marca = new Marca();
        marca.setId(2L);
        marca.setNome(dto.nome());
        marca.setPaisOrigem(dto.paisOrigem());

        when(service.create(dto)).thenReturn(marca); // quando o service for chamado, vai retornar uma marca criada

        given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post("/marcas")
        .then()
        .statusCode(201)
        .body("nome", equalTo("Rusty"))
        .body("paisOrigem", equalTo("Estados Unidos"));
    }

    // alterando uma marca
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        MarcaDTO dtoAlterado = new MarcaDTO("Firewire", "Estados Unidos");

        doNothing().when(service).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(ContentType.JSON)
        .body(dtoAlterado)
        .when()
        .put("/marcas/1")
        .then()
        .statusCode(204);
    }

    // apagando uma marca
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(service).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/marcas/1")
        .then()
        .statusCode(204);
    }
}