package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Ocorrencia
 */
public class OcorrenciaTest {

    private AreaFlorestal area;
    private Sensor sensor;
    private Drone drone;
    private Usuario usuario;
    private EstacaoBombeiros estacao;

    @BeforeEach
    void setUp() {
        area = new AreaFlorestal(1, "Parque Nacional", "Zona Norte", 25, 1001);
        sensor = new Sensor(201, "ThermoDetect", "Térmico");
        drone = new Drone(101, "DJI Phantom", 1001);
        usuario = new Usuario("João Silva", 12345678901L, "15/03/1990");
        estacao = new EstacaoBombeiros(1001, "1ª Companhia", "Rua A, 123",
                "São Paulo", "SP", "Capitão Silva", 10001);
    }

    @Test
    @DisplayName("Deve criar ocorrência com sensor")
    void testConstrutorComSensor() {
        Ocorrencia ocorrencia = new Ocorrencia(1, area, 50, sensor, drone, 15);

        assertEquals(1, ocorrencia.getIdOcorrencia());
        assertEquals(area, ocorrencia.getAreaAfetada());
        assertEquals(50, ocorrencia.getHectaresAfetados());
        assertEquals(sensor, ocorrencia.getSensorDetector());
        assertEquals(drone, ocorrencia.getDroneVarredura());
        assertEquals(15, ocorrencia.getTempoChegadaMinutos());
        assertEquals("Ativo", ocorrencia.getStatusOcorrencia());
        assertEquals("Alerta Ativo", ocorrencia.getNivelRisco());
        assertNull(ocorrencia.getUsuarioDenunciante());
    }

    @Test
    @DisplayName("Deve criar área segura")
    void testConstrutorAreaSegura() {
        Ocorrencia ocorrencia = new Ocorrencia(2, area, drone);

        assertEquals(2, ocorrencia.getIdOcorrencia());
        assertEquals(0, ocorrencia.getHectaresAfetados());
        assertEquals("Concluído", ocorrencia.getStatusOcorrencia());
        assertEquals("Seguro", ocorrencia.getNivelRisco());
        assertNull(ocorrencia.getSensorDetector());
    }

    @Test
    @DisplayName("Deve criar ocorrência com usuário")
    void testConstrutorComUsuario() {
        Ocorrencia ocorrencia = new Ocorrencia(3, area, 30, drone, usuario, 20);

        assertEquals(usuario, ocorrencia.getUsuarioDenunciante());
        assertEquals("Ativo", ocorrencia.getStatusOcorrencia());
        assertEquals("Investigação", ocorrencia.getNivelRisco());
    }

    @Test
    @DisplayName("Deve calcular nível de risco corretamente")
    void testCalculoNivelRisco() {
        Ocorrencia investigacao = new Ocorrencia(1, area, 25, sensor, drone, 15);
        Ocorrencia alerta = new Ocorrencia(2, area, 75, sensor, drone, 15);
        Ocorrencia emergencia = new Ocorrencia(3, area, 150, sensor, drone, 15);

        assertEquals("Investigação", investigacao.getNivelRisco());
        assertEquals("Alerta Ativo", alerta.getNivelRisco());
        assertEquals("Emergência", emergencia.getNivelRisco());
    }

    @Test
    @DisplayName("Deve calcular tempo de chegada")
    void testCalcularTempoChegada() {
        assertEquals(20, Ocorrencia.calcularTempoChegada(25, 75)); // 25km a 75km/h = 20 min
        assertEquals(8, Ocorrencia.calcularTempoChegada(10, 75));  // 10km a 75km/h = 8 min
        assertEquals(40, Ocorrencia.calcularTempoChegada(50, 75)); // 50km a 75km/h = 40 min
    }

    @Test
    @DisplayName("Deve marcar como segura")
    void testMarcarComoSegura() {
        Ocorrencia ocorrencia = new Ocorrencia(1, area, 50, sensor, drone, 15);

        ocorrencia.marcarComoSegura();

        assertEquals("Concluído", ocorrencia.getStatusOcorrencia());
        assertEquals("Seguro", ocorrencia.getNivelRisco());
        assertEquals(0, ocorrencia.getHectaresAfetados());
    }

    @Test
    @DisplayName("Deve alterar status")
    void testSetStatusOcorrencia() {
        Ocorrencia ocorrencia = new Ocorrencia(1, area, 50, sensor, drone, 15);

        ocorrencia.setStatusOcorrencia("Concluído");
        assertEquals("Concluído", ocorrencia.getStatusOcorrencia());
    }

    @Test
    @DisplayName("Deve ter data de detecção definida")
    void testDataHoraDeteccao() {
        Ocorrencia ocorrencia = new Ocorrencia(1, area, 50, sensor, drone, 15);

        assertNotNull(ocorrencia.getDataHoraDeteccao());
    }

    @Test
    @DisplayName("Deve exibir relatório")
    void testExibirRelatorio() {
        Ocorrencia ocorrencia = new Ocorrencia(1, area, 50, sensor, drone, 15);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ocorrencia.exibirRelatorio(estacao);

        String output = outputStream.toString();
        assertTrue(output.contains("OCORRÊNCIA REGISTRADA #1"));
        assertTrue(output.contains("Parque Nacional"));
        assertTrue(output.contains("50 hectares"));

        System.setOut(System.out);
    }
}