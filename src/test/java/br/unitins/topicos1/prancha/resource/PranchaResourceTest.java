package br.unitins.topicos1.prancha.resource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.unitins.topicos1.prancha.dto.PranchaDTO;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import br.unitins.topicos1.prancha.model.Habilidade;
import br.unitins.topicos1.prancha.service.PranchaService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class PranchaResourceTest {

    // mock serve para testar apenas os endpoints REST do resource, sem depender do banco ou das regras de negócio

    @Inject
    PranchaResource pranchaResource; // injeta o resource real

    @Mock
    PranchaService pranchaService; // injeta o service real para ser mockado (cria um objeto falso do service)

    // substitui o service real pelo service mockado
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa o mock
        pranchaResource.service = pranchaService;
    }

    // método auxiliar que cria uma prancha
    private Prancha criarPranchaTeste() {
        Prancha prancha = new Prancha();
        prancha.setId(1L);
        prancha.setValor(100.0f);
        prancha.setTamanho(1.8f);
        prancha.setEstoque(10);
        prancha.setTipoPrancha(TipoPrancha.SHORTBOARD);
        prancha.setHabilidade(Habilidade.AVANCADO);
        return prancha;
    }

    // buscar todas as pranchas com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAllUser() {
        when(pranchaService.findAll()).thenReturn(List.of(criarPranchaTeste())); // quando o service for chamado, vai retornar essa lista de pranchas

        given()
        .when()
        .get("/pranchas")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].valor", equalTo(100.0f));
    }

    // buscar todas as pranchas com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetAllAdm() {
        when(pranchaService.findAll()).thenReturn(List.of(criarPranchaTeste())); // quando o service for chamado, vai retornar essa lista de pranchas

        given()
        .when()
        .get("/pranchas")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].valor", equalTo(100.0f));
    }

    // buscar todas as pranchas pelo tipo de prancha com o perfil user
    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetByTipoPranchaUser() {
        when(pranchaService.findByTipoPrancha(TipoPrancha.SHORTBOARD)).thenReturn(List.of(criarPranchaTeste())); // quando o service com esse tipo de prancha for chamado, vai retornar essa lista de pranchas

        given()
        .when()
        .get("/pranchas/tipo/{tipoPrancha}", TipoPrancha.SHORTBOARD)
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].tipoPrancha", equalTo("SHORTBOARD"));
    }

    // buscar todas as pranchas pelo tipo de prancha com o perfil adm
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testGetByTipoPranchaAdm() {
        when(pranchaService.findByTipoPrancha(TipoPrancha.SHORTBOARD)).thenReturn(List.of(criarPranchaTeste())); // quando o service com esse tipo de prancha for chamado, vai retornar essa lista de pranchas

        given()
        .when()
        .get("/pranchas/tipo/{tipoPrancha}", TipoPrancha.SHORTBOARD)
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].tipoPrancha", equalTo("SHORTBOARD"));
    }

    // cadastrando uma prancha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testIncluir() {
        PranchaDTO dto = new PranchaDTO(
            1.8f,
            100.0,
            10,
            TipoPrancha.SHORTBOARD,
            Habilidade.AVANCADO,
            1L,
            1L,
            1L
        );

        when(pranchaService.create(dto)).thenReturn(criarPranchaTeste()); // quando o service for chamado, vai retornar uma prancha criada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .post("/pranchas")
        .then()
        .statusCode(201)
        .body("valor", equalTo(100.0f));
    }

    // alterando uma prancha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testAlterar() {
        PranchaDTO dto = new PranchaDTO(
            1.8f,
            120.0,
            5,
            TipoPrancha.SHORTBOARD,
            Habilidade.AVANCADO,
            1L,
            1L,
            1L
        );

        doNothing().when(pranchaService).update(1L, dto); // quando o service for chamado, não faz nada

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .put("/pranchas/1")
        .then()
        .statusCode(204);
    }

    // apagando uma prancha
    @Test
    @TestSecurity(user = "adm", roles = {"ADM"})
    public void testDelete() {
        doNothing().when(pranchaService).delete(1L); // quando o service for chamado, não faz nada

        given()
        .when()
        .delete("/pranchas/1")
        .then()
        .statusCode(204);
    }
}