package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.ClienteDTO;
import br.unitins.topicos1.prancha.model.Cliente;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class ClienteResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    ClienteResource clienteResource; // injeta o resource real

    @Mock
    ClienteService clienteService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        clienteResource.clienteService = clienteService;
    }

    // buscar todos os clientes com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Cliente c = new Cliente();
        c.setNome("Luisa");
        c.setCpf("12345678900");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        c.setTelefone(t);

        when(clienteService.findAll()).thenReturn(List.of(c)); // quando o service for chamado, vai retornar essa lista de clientes

        given()
        .when()
        .get("/clientes")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Luisa"));
    }

    // buscar todos os clientes com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Cliente c = new Cliente();
        c.setNome("Luisa");
        c.setCpf("12345678900");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        c.setTelefone(t);

        when(clienteService.findAll()).thenReturn(List.of(c)); // quando o service for chamado, vai retornar essa lista de clientes

        given()
        .when()
        .get("/clientes")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Luisa"));
    }

    // buscar todos os clientes pelo cpf com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByCpfUser() {
        Cliente c = new Cliente();
        c.setNome("Luisa Pimentel");
        c.setCpf("12345678900");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        c.setTelefone(t);

        when(clienteService.findByCpf("12345678900")).thenReturn(List.of(c)); // quando o service com esse cpf for chamado, vai retornar essa lista de clientes

        given()
        .when()
        .get("/clientes/cpf/12345678900")
        .then()
        .statusCode(200)
        .body("[0].nome", equalTo("Luisa Pimentel"))
        .body("[0].cpf", equalTo("12345678900"))
        .body("[0].telefone.ddd", equalTo("11"))
        .body("[0].telefone.numero", equalTo("999999999"));
    }

    // buscar todos os clientes pelo cpf com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByCpfAdm() {
        Cliente c = new Cliente();
        c.setNome("Luisa Pimentel");
        c.setCpf("12345678900");
        Telefone t = new Telefone();
        t.setDdd("11");
        t.setNumero("999999999");
        c.setTelefone(t);

        when(clienteService.findByCpf("12345678900")).thenReturn(List.of(c)); // quando o service com esse cpf for chamado, vai retornar essa lista de clientes

        given()
        .when()
        .get("/clientes/cpf/12345678900")
        .then()
        .statusCode(200)
        .body("[0].nome", equalTo("Luisa Pimentel"))
        .body("[0].cpf", equalTo("12345678900"))
        .body("[0].telefone.ddd", equalTo("11"))
        .body("[0].telefone.numero", equalTo("999999999"));
    }

    // cadastrando um cliente
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {
        ClienteDTO dto = new ClienteDTO("Maria Silva", "21", "988888888", "98765432100");
        Cliente c = new Cliente();
        c.setNome("Maria Silva");
        c.setCpf("98765432100");
        Telefone t = new Telefone();
        t.setDdd("21");
        t.setNumero("988888888");
        c.setTelefone(t);

        when(clienteService.create(dto)).thenReturn(c); // quando o service for chamado, vai retornar um cliente criado

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/clientes")
        .then()
        .statusCode(201)
        .body("nome", equalTo("Maria Silva"))
        .body("cpf", equalTo("98765432100"))
        .body("telefone.ddd", equalTo("21"))
        .body("telefone.numero", equalTo("988888888"));
    }

    // alterando um cliente
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {
        ClienteDTO dtoAlterado = new ClienteDTO("Carlos Eduardo", "31", "977777777", "11122233344");
        doNothing().when(clienteService).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dtoAlterado)
        .when()
        .put("/clientes/1")
        .then()
        .statusCode(204);
    }

    // apagando um cliente
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {
        doNothing().when(clienteService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/clientes/1")
        .then()
        .statusCode(204);
    }
}