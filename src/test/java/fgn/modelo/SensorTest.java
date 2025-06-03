package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Sensor
 */
public class SensorTest {

    private Sensor sensorTermico;
    private Sensor sensorFumaca;
    private Sensor sensorQuimico;

    @BeforeEach
    void setUp() {
        sensorTermico = new Sensor(201, "ThermoDetect", "Térmico");
        sensorFumaca = new Sensor(202, "SmokeAlert", "Fumaça");
        sensorQuimico = new Sensor(203, "ChemSensor", "Químico");
    }

    @Test
    @DisplayName("Deve criar sensor térmico corretamente")
    void testConstrutorTermico() {
        assertEquals(201, sensorTermico.getIdSensor());
        assertEquals("ThermoDetect", sensorTermico.getNomeSensor());
        assertEquals("Térmico", sensorTermico.getTipo());
    }

    @Test
    @DisplayName("Deve alterar dados do sensor")
    void testSetters() {
        sensorTermico.setIdSensor(301);
        sensorTermico.setNomeSensor("NovoSensor");

        assertEquals(301, sensorTermico.getIdSensor());
        assertEquals("NovoSensor", sensorTermico.getNomeSensor());
    }

    @Test
    @DisplayName("Deve executar operação sensor térmico")
    void testOperarTermico() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        sensorTermico.operar();

        String output = outputStream.toString();
        assertTrue(output.contains("ThermoDetect iniciando"));
        assertTrue(output.contains("temperatura"));
        assertTrue(output.contains("pontos de calor"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve executar operação sensor fumaça")
    void testOperarFumaca() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        sensorFumaca.operar();

        String output = outputStream.toString();
        assertTrue(output.contains("densidade de partículas"));
        assertTrue(output.contains("composição química"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve executar operação sensor químico")
    void testOperarQuimico() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        sensorQuimico.operar();

        String output = outputStream.toString();
        assertTrue(output.contains("gases de combustão"));
        assertTrue(output.contains("monóxido de carbono"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve exibir informações do sensor")
    void testExibirInformacoes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        sensorTermico.exibirInformacoes();

        String output = outputStream.toString();
        assertTrue(output.contains("201. ThermoDetect (Térmico)"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve ser instância de Equipamento")
    void testHeranca() {
        assertInstanceOf(Equipamento.class, sensorTermico);
        assertEquals(201, sensorTermico.getId());
        assertEquals("ThermoDetect", sensorTermico.getNome());
    }

    @Test
    @DisplayName("Deve funcionar com diferentes tipos")
    void testTiposDiferentes() {
        assertEquals("Térmico", sensorTermico.getTipo());
        assertEquals("Fumaça", sensorFumaca.getTipo());
        assertEquals("Químico", sensorQuimico.getTipo());
    }
}