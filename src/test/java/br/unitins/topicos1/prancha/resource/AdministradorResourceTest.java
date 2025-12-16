package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.AdministradorDTO;
import br.unitins.topicos1.prancha.model.Administrador;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.service.AdministradorService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class AdministradorResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    AdministradorResource administradorResource; // injeta o resource real

    @Mock
    AdministradorService administradorService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        administradorResource.administradorService = administradorService;
    }

    // buscar todos os administradores com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Administrador a = new Administrador();
        a.setNome("Luisa");
        a.setCargo("Supervisor");
        a.setStatusAdm("Ativo");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        a.setTelefone(t);

        when(administradorService.findAll()).thenReturn(List.of(a)); // quando o service for chamado, vai retornar essa lista de administradores

        given()
        .when()
        .get("/administradores")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Luisa"));
    }

    // buscar todos os administradores com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Administrador a = new Administrador();
        a.setNome("Luisa");
        a.setCargo("Supervisor");
        a.setStatusAdm("Ativo");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        a.setTelefone(t);

        when(administradorService.findAll()).thenReturn(List.of(a)); // quando o service for chamado, vai retornar essa lista de administradores

        given()
        .when()
        .get("/administradores")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Luisa"));
    }

    // buscar todos os administradores pelo nome com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByNomeUser() {
        Administrador a = new Administrador();
        a.setNome("Luisa Pimentel");
        a.setCargo("Supervisor");
        a.setStatusAdm("Ativo");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        a.setTelefone(t);

        when(administradorService.findByNome("Luisa Pimentel")).thenReturn(List.of(a)); // quando o service for chamado com esse nome, vai retornar essa lista de administradores

        given()
        .when()
        .get("/administradores/nome/Luisa Pimentel")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Luisa Pimentel"))
        .body("[0].cargo", equalTo("Supervisor"))
        .body("[0].statusAdm", equalTo("Ativo"))
        .body("[0].telefone.ddd", equalTo("11"))
        .body("[0].telefone.numero", equalTo("999999999"));
    }

    // buscar todos os administradores pelo nome com o perfil user
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByNomeAdm() {
        Administrador a = new Administrador();
        a.setNome("Luisa Pimentel");
        a.setCargo("Supervisor");
        a.setStatusAdm("Ativo");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        a.setTelefone(t);

        when(administradorService.findByNome("Luisa Pimentel")).thenReturn(List.of(a)); // quando o service for chamado com esse nome, vai retornar essa lista de administradores

        given()
        .when()
        .get("/administradores/nome/Luisa Pimentel")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Luisa Pimentel"))
        .body("[0].cargo", equalTo("Supervisor"))
        .body("[0].statusAdm", equalTo("Ativo"))
        .body("[0].telefone.ddd", equalTo("11"))
        .body("[0].telefone.numero", equalTo("999999999"));
    }

    // cadastrando um novo administrador
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        AdministradorDTO dto = new AdministradorDTO("Maria Silva", "21", "988888888", "Gerente", "Ativo");

        Administrador a = new Administrador();
        a.setNome("Maria Silva");
        a.setCargo("Gerente");
        a.setStatusAdm("Ativo");
        Telefone t = new Telefone();
        t.setDdd("21");
        t.setNumero("988888888");
        a.setTelefone(t);

        when(administradorService.create(dto)).thenReturn(a); // quando o service for chamado, vai retornar o administrador criado

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/administradores")
        .then()
        .statusCode(201)
        .body("nome", equalTo("Maria Silva"))
        .body("cargo", equalTo("Gerente"))
        .body("statusAdm", equalTo("Ativo"))
        .body("telefone.ddd", equalTo("21"))
        .body("telefone.numero", equalTo("988888888"));
    }

    // alterando um administrador
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        AdministradorDTO dtoAlterado = new AdministradorDTO("Carlos Eduardo", "31", "977777777", "Coordenador", "Ativo");
        doNothing().when(administradorService).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dtoAlterado)
        .when()
        .put("/administradores/1")
        .then()
        .statusCode(204);
    }

    // apagando um administrador
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(administradorService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/administradores/1")
        .then()
        .statusCode(204);
    }
}