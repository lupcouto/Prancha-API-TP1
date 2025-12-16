package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.FornecedorDTO;
import br.unitins.topicos1.prancha.model.Fornecedor;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.service.FornecedorService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FornecedorResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    FornecedorResource fornecedorResource; // injeta resource real

    @Mock
    FornecedorService service; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        fornecedorResource.service = service;
    }

    // buscar todos os fornecedores com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setCnpj("9832403710");
        Telefone telefone = new Telefone();
        telefone.setDdd("63");
        telefone.setNumero("999999999");
        fornecedor.setTelefone(telefone);

        when(service.findAll()).thenReturn(List.of(fornecedor)); // quando o service for chamado, vai retornar essa lista de fornecedores

        given()
        .when()
        .get("/fornecedores")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].nome", equalTo("Fornecedor Teste"));
    }

    // buscar todos os fornecedores com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setCnpj("9832403710");
        Telefone telefone = new Telefone();
        telefone.setDdd("63");
        telefone.setNumero("999999999");
        fornecedor.setTelefone(telefone);

        when(service.findAll()).thenReturn(List.of(fornecedor)); // quando o service for chamado, vai retornar essa lista de fornecedores

        given()
        .when()
        .get("/fornecedores")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
    }

    // buscar todos os fornecedores com esse cnpj com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByCnpjUser() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(2L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setCnpj("9832403710");

        when(service.findByCnpj("9832403710")).thenReturn(List.of(fornecedor)); // quando o service com esse cnpj for chamado, vai retornar essa lista de fornecedores

        given()
        .when()
        .get("/fornecedores/cnpj/9832403710")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].cnpj", equalTo("9832403710"));
    }

    // buscar todos os fornecedores com esse cnpj com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByCnpjAdm() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(2L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setCnpj("9832403710");

        when(service.findByCnpj("9832403710")).thenReturn(List.of(fornecedor)); // quando o service com esse cnpj for chamado, vai retornar essa lista de fornecedores

        given()
        .when()
        .get("/fornecedores/cnpj/9832403710")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
    }

    // cadastrando um fornecedor
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        FornecedorDTO dto = new FornecedorDTO("Fornecedor Novo", "63", "988888888", "123456789");

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(3L);
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        fornecedor.setTelefone(telefone);

        when(service.create(dto)).thenReturn(fornecedor); // quando o service for chamado, vai retornar um fornecedor criado

        given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
        .post("/fornecedores")
        .then()
        .statusCode(201)
        .body("cnpj", equalTo("123456789"))
        .body("nome", equalTo("Fornecedor Novo"));
    }

    // alterando um fornecedor
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        FornecedorDTO dtoAlterado = new FornecedorDTO("Fornecedor Alterado", "63", "966666666", "3516584946");

        doNothing().when(service).update(1L, dtoAlterado); // quando o service for chamado, não faz nada

        given()
        .contentType(ContentType.JSON)
        .body(dtoAlterado)
        .when()
        .put("/fornecedores/1")
        .then()
        .statusCode(204);
    }

    // apagando um fornecedor
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(service).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/fornecedores/1")
        .then()
        .statusCode(204);
    }
}