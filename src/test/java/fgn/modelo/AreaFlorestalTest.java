package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe AreaFlorestal
 */
public class AreaFlorestalTest {

    private AreaFlorestal area;

    @BeforeEach
    void setUp() {
        area = new AreaFlorestal(1, "Parque Nacional", "Zona Norte", 25, 1001);
    }

    @Test
    @DisplayName("Deve criar área florestal com dados corretos")
    void testConstrutor() {
        assertEquals(1, area.getIdArea());
        assertEquals("Parque Nacional", area.getNomeArea());
        assertEquals("Zona Norte", area.getLocalizacao());
        assertEquals(25, area.getDistanciaKm());
        assertEquals(1001, area.getIdEstacaoResponsavel());
    }

    @Test
    @DisplayName("Deve alterar ID da área")
    void testSetIdArea() {
        area.setIdArea(5);
        assertEquals(5, area.getIdArea());
    }

    @Test
    @DisplayName("Deve alterar nome da área")
    void testSetNomeArea() {
        area.setNomeArea("Reserva Natural");
        assertEquals("Reserva Natural", area.getNomeArea());
    }

    @Test
    @DisplayName("Deve alterar localização")
    void testSetLocalizacao() {
        area.setLocalizacao("Zona Sul");
        assertEquals("Zona Sul", area.getLocalizacao());
    }

    @Test
    @DisplayName("Deve alterar distância")
    void testSetDistanciaKm() {
        area.setDistanciaKm(30);
        assertEquals(30, area.getDistanciaKm());
    }

    @Test
    @DisplayName("Deve exibir informações formatadas")
    void testExibirInformacoes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        area.exibirInformacoes();

        String output = outputStream.toString();
        assertTrue(output.contains("1. Parque Nacional"));
        assertTrue(output.contains("Localização: Zona Norte"));
        assertTrue(output.contains("Distância: 25 km"));

        System.setOut(System.out);
    }
}