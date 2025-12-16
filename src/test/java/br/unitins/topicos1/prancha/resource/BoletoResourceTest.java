package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import br.unitins.topicos1.prancha.dto.BoletoDTO;
import br.unitins.topicos1.prancha.dto.BoletoResponseDTO;
import br.unitins.topicos1.prancha.model.Boleto;
import br.unitins.topicos1.prancha.service.BoletoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class BoletoResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @InjectMock
    BoletoService boletoService; // injeta o service real para ser mockado (cria um objeto falso do service) e já substitui o service real pelo mockado

    // buscar todos os boletos com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {

        Boleto boleto = new Boleto();
        boleto.setId(1L);
        boleto.setCodigoBarras("123");
        boleto.setDataVencimento(LocalDate.of(2040, 1, 1));
        boleto.setDataPagamento(null);
        boleto.setStatusPagamento(null);

        BoletoResponseDTO response = new BoletoResponseDTO(boleto);

        when(boletoService.findAll()).thenReturn(java.util.List.of(response)); // quando o service for chamado, vai retornar essa lista de boletos

        given()
            .when()
            .get("/boletos")
            .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].codigoBarras", equalTo("123"));
    }

    // buscar todos os boletos pelo código de barras com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByCodigoBarrasAdm() {

        Boleto boleto = new Boleto();
        boleto.setId(2L);
        boleto.setCodigoBarras("999");
        boleto.setDataVencimento(LocalDate.of(2040, 1, 1));

        BoletoResponseDTO response = new BoletoResponseDTO(boleto);

        when(boletoService.findByCodigoBarras("999")).thenReturn(java.util.List.of(response)); // quando o service com esse código de barras for chamado, vai retornar essa lista de boletos

        given()
            .when()
            .get("/boletos/codigoBarras/999")
            .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].codigoBarras", equalTo("999"));
    }

    // cadastrando um novo boleto
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testIncluir() {

        BoletoDTO dto = new BoletoDTO(
            "111222333",
            LocalDate.of(2045, 5, 10)
        );

        Boleto boleto = new Boleto();
        boleto.setId(3L);
        boleto.setCodigoBarras(dto.codigoBarras());
        boleto.setDataVencimento(dto.dataVencimento());

        BoletoResponseDTO response = new BoletoResponseDTO(boleto);

        when(boletoService.create(dto)).thenReturn(response); // quando o service for chamado, vai retornar o boleto criado

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .post("/boletos")
            .then()
            .statusCode(201)
            .body("codigoBarras", equalTo("111222333"));
    }

    // alterando um boleto
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testAlterar() {

        BoletoDTO dto = new BoletoDTO(
            "888777666",
            LocalDate.of(2042, 2, 2)
        );

        doNothing().when(boletoService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .put("/boletos/1")
            .then()
            .statusCode(204);
    }

    // apagando um boleto
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testDelete() {

        doNothing().when(boletoService).delete(1L); // quando o service for chamado, não faz nada

        given()
            .when()
            .delete("/boletos/1")
            .then()
            .statusCode(204);
    }
}