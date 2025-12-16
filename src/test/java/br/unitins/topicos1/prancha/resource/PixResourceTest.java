package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.PixDTO;
import br.unitins.topicos1.prancha.dto.PixResponseDTO;
import br.unitins.topicos1.prancha.model.Pix;
import br.unitins.topicos1.prancha.service.PixService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class PixResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    PixResource pixResource; // injeta o resource real

    @Mock
    PixService pixService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        pixResource.pixService = pixService;
    }

    // método auxiliar que cria um pix
    private PixResponseDTO criarPixTeste() {
        Pix pix = new Pix();
        pix.setId(1L);
        pix.setChave("chave-teste-123");
        return new PixResponseDTO(pix); 
    }

    // buscar todos os pix com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        PixResponseDTO dto = criarPixTeste();

        when(pixService.findAll()).thenReturn(List.of(dto)); // quando o service for chamado, vai retornar essa lista de pix

        given()
        .when()
        .get("/pix")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].chave", equalTo("chave-teste-123"));
    }

    // buscar todos os pix com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        PixResponseDTO dto = criarPixTeste();

        when(pixService.findAll()).thenReturn(List.of(dto)); // quando o service for chamado, vai retornar essa lista de pix

        given()
        .when()
        .get("/pix")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].chave", equalTo("chave-teste-123"));
    }

    // buscar todos os pix pela chave com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByChaveUser() {
        PixResponseDTO dto = criarPixTeste();

        when(pixService.findByChave("chave-teste-123")).thenReturn(List.of(dto)); // quando o service com essa chave for chamado, vai retornar essa lista de pix

        given()
        .when()
        .get("/pix/chave/{chave}", "chave-teste-123")
        .then()
        .statusCode(200)
        .body("$[0].chave", equalTo("chave-teste-123"));
    }

    // buscar todos os pix pela chave com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByChaveAdm() {
        PixResponseDTO dto = criarPixTeste();

        when(pixService.findByChave("chave-teste-123")).thenReturn(List.of(dto)); // quando o service com essa chave for chamado, vai retornar essa lista de pix

        given()
        .when()
        .get("/pix/chave/{chave}", "chave-teste-123")
        .then()
        .statusCode(200)
        .body("$[0].chave", equalTo("chave-teste-123"));
    }

    // cadastrando um pix
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {
        PixDTO dto = new PixDTO("nova-chave-555");

        Pix pix = new Pix();
        pix.setId(2L);
        pix.setChave("nova-chave-555");
        PixResponseDTO response = new PixResponseDTO(pix);

        when(pixService.create(dto)).thenReturn(response); // quando o service for chamado, vai retornar um pix criado

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/pix")
        .then()
        .statusCode(201)
        .body("chave", equalTo("nova-chave-555"));
    }

    // alterando um pix
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {
        PixDTO dto = new PixDTO("chave-alterada-999");

        doNothing().when(pixService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .put("/pix/1")
        .then()
        .statusCode(204);
    }

    // apagando um pix
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {
        doNothing().when(pixService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/pix/1")
        .then()
        .statusCode(204);
    }
}