package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.TelefoneDTO;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.service.TelefoneService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class TelefoneResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    TelefoneResource telefoneResource; // injeta o resource real

    @Mock
    TelefoneService telefoneService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        telefoneResource.service = telefoneService;
    }

    // buscar todos os telefones com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Telefone tel = new Telefone();
        tel.setId(1L);
        tel.setDdd("12");
        tel.setNumero("34567890");

        when(telefoneService.findAll()).thenReturn(List.of(tel)); // quando o service for chamado, vai retornar essa lista de telefones

        given()
        .when()
        .get("/telefones")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].ddd", equalTo("12"))
        .body("[0].numero", equalTo("34567890"));
    }

    // buscar todos os telefones com o perfil user
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Telefone tel = new Telefone();
        tel.setId(2L);
        tel.setDdd("99");
        tel.setNumero("98765432");

        when(telefoneService.findAll()).thenReturn(List.of(tel)); // quando o service for chamado, vai retornar essa lista de telefones

        given()
        .when()
        .get("/telefones")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].ddd", equalTo("99"))
        .body("[0].numero", equalTo("98765432"));
    }

    // buscar todos os telefones pelo número com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByNumeroUser() {
        Telefone tel = new Telefone();
        tel.setId(3L);
        tel.setDdd("63");
        tel.setNumero("8924703605");

        when(telefoneService.findByNumero("8924703605")).thenReturn(List.of(tel)); // quando o service com esse número for chamado, vai retornar essa lista de telefones

        given()
        .when()
        .get("/telefones/numero/8924703605")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].ddd", equalTo("63"))
        .body("[0].numero", equalTo("8924703605"));
    }

    // buscar todos os telefones pelo número com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByNumeroAdm() {
        Telefone tel = new Telefone();
        tel.setId(4L);
        tel.setDdd("21");
        tel.setNumero("99887766");

        when(telefoneService.findByNumero("99887766")).thenReturn(List.of(tel)); // quando o service com esse número for chamado, vai retornar essa lista de telefones

        given()
        .when()
        .get("/telefones/numero/99887766")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].ddd", equalTo("21"))
        .body("[0].numero", equalTo("99887766"));
    }

    // cadastrando um telefone
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {
        TelefoneDTO dto = new TelefoneDTO("31", "12345678");
        Telefone tel = new Telefone();
        tel.setId(5L);
        tel.setDdd(dto.ddd());
        tel.setNumero(dto.numero());

        when(telefoneService.create(dto)).thenReturn(tel); // quando o service for chamado, vai retornar um telefone criado

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/telefones")
        .then()
        .statusCode(201)
        .body("ddd", equalTo("31"))
        .body("numero", equalTo("12345678"));
    }

    // alterando um telefone
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {
        TelefoneDTO dtoAlterado = new TelefoneDTO("41", "87654321");

        doNothing().when(telefoneService).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dtoAlterado)
        .when()
        .put("/telefones/1")
        .then()
        .statusCode(204);
    }

    // apagando um telefone
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {
        doNothing().when(telefoneService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/telefones/1")
        .then()
        .statusCode(204);
    }
}