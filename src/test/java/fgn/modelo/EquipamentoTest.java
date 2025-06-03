package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe abstrata Equipamento
 * Testado através das subclasses concretas
 */
public class EquipamentoTest {

    @Test
    @DisplayName("Deve criar drone como equipamento")
    void testDroneComoEquipamento() {
        Equipamento equipamento = new Drone(101, "DJI Phantom", 1001);

        assertEquals(101, equipamento.getId());
        assertEquals("DJI Phantom", equipamento.getNome());
        assertEquals("Drone de Varredura", equipamento.getTipo());

        assertInstanceOf(Equipamento.class, equipamento);
        assertInstanceOf(Drone.class, equipamento);
    }

    @Test
    @DisplayName("Deve criar sensor como equipamento")
    void testSensorComoEquipamento() {
        Equipamento equipamento = new Sensor(201, "ThermoDetect", "Térmico");

        assertEquals(201, equipamento.getId());
        assertEquals("ThermoDetect", equipamento.getNome());
        assertEquals("Térmico", equipamento.getTipo());

        assertInstanceOf(Equipamento.class, equipamento);
        assertInstanceOf(Sensor.class, equipamento);
    }

    @Test
    @DisplayName("Deve demonstrar polimorfismo com operar")
    void testPolimorfismoOperar() {
        Equipamento drone = new Drone(101, "DJI Phantom", 1001);
        Equipamento sensor = new Sensor(201, "ThermoDetect", "Térmico");

        // Ambos devem executar operar() sem erro
        assertDoesNotThrow(() -> drone.operar());
        assertDoesNotThrow(() -> sensor.operar());
    }

    @Test
    @DisplayName("Deve demonstrar polimorfismo com exibirInformacoes")
    void testPolimorfismoExibirInformacoes() {
        Equipamento drone = new Drone(101, "DJI Phantom", 1001);
        Equipamento sensor = new Sensor(201, "ThermoDetect", "Térmico");

        // Ambos devem executar exibirInformacoes() sem erro
        assertDoesNotThrow(() -> drone.exibirInformacoes());
        assertDoesNotThrow(() -> sensor.exibirInformacoes());
    }

    @Test
    @DisplayName("Deve permitir array polimórfico")
    void testArrayPolimorfico() {
        Equipamento[] equipamentos = {
                new Drone(101, "DJI Phantom", 1001),
                new Sensor(201, "ThermoDetect", "Térmico"),
                new Drone(102, "DJI Mavic", 1002)
        };

        assertEquals(3, equipamentos.length);

        // Todos devem ser equipamentos
        for (Equipamento eq : equipamentos) {
            assertInstanceOf(Equipamento.class, eq);
            assertDoesNotThrow(() -> eq.operar());
        }
    }

    @Test
    @DisplayName("Deve verificar tipos específicos em polimorfismo")
    void testVerificarTiposEspecificos() {
        Equipamento drone = new Drone(101, "DJI Phantom", 1001);
        Equipamento sensor = new Sensor(201, "ThermoDetect", "Térmico");

        if (drone instanceof Drone) {
            Drone d = (Drone) drone;
            assertEquals(1001, d.getIdEstacaoBase());
        }

        if (sensor instanceof Sensor) {
            Sensor s = (Sensor) sensor;
            assertEquals("ThermoDetect", s.getNomeSensor());
        }
    }
}