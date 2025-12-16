package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.EnderecoDTO;
import br.unitins.topicos1.prancha.model.Endereco;
import br.unitins.topicos1.prancha.service.EnderecoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class EnderecoResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    EnderecoResource enderecoResource; // injeta o resource real

    @Mock
    EnderecoService enderecoService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        enderecoResource.enderecoService = enderecoService;
    }

    // buscar todos os endereços com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {

        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCidade("Palmas");
        endereco.setEstado("TO");
        endereco.setCep("77000000");

        when(enderecoService.findAll()).thenReturn(List.of(endereco)); // quando o service for chamado, vai retornar essa lista de endereços

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/enderecos")
            .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].cidade", equalTo("Palmas"));
    }

    // buscar todos os endereços com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {

        when(enderecoService.findAll()).thenReturn(List.of()); // quando o service for chamado, vai retornar essa lista de endereços

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/enderecos")
            .then()
            .statusCode(200);
    }

    // buscar todos os endereços pelo cep com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByCepUser() {

        Endereco endereco = new Endereco();
        endereco.setId(2L);
        endereco.setCidade("Araguaína");
        endereco.setEstado("TO");
        endereco.setCep("77800000");

        when(enderecoService.findByCep("77800000")).thenReturn(List.of(endereco)); // quando o service com esse cep for chamado, vai retornar essa lista de endereços

        given()
            .when()
            .get("/enderecos/cep/77800000")
            .then()
            .statusCode(200)
            .body("[0].cep", equalTo("77800000"));
    }

    // buscar todos os endereços pelo cep com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByCepAdm() {

        when(enderecoService.findByCep("77800000")).thenReturn(List.of()); // quando o service com esse cep for chamado, vai retornar essa lista de endereços

        given()
            .when()
            .get("/enderecos/cep/77800000")
            .then()
            .statusCode(200);
    }

    // cadastrando um endereço
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {

        EnderecoDTO dto = new EnderecoDTO("Palmas", "TO", "77000000");

        Endereco endereco = new Endereco();
        endereco.setId(3L);
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());

        when(enderecoService.create(dto)).thenReturn(endereco); // quando o service for chamado, vai retornar um endereço criado

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/enderecos")
            .then()
            .statusCode(201)
            .body("cidade", equalTo("Palmas"))
            .body("estado", equalTo("TO"))
            .body("cep", equalTo("77000000"));
    }

    // alterando um endereço
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {

        EnderecoDTO dto = new EnderecoDTO("Gurupi", "TO", "77400000");

        doNothing().when(enderecoService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .put("/enderecos/1")
            .then()
            .statusCode(204);
    }

    // apagando um endereço
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {

        doNothing().when(enderecoService).delete(1L); // quando o service for chamado, não faz nada

        given()
            .when()
            .delete("/enderecos/1")
            .then()
            .statusCode(204);
    }
}