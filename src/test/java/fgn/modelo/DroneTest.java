package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Drone
 */
public class DroneTest {

    private Drone drone;

    @BeforeEach
    void setUp() {
        drone = new Drone(101, "DJI Phantom", 1001);
    }

    @Test
    @DisplayName("Deve criar drone com herança correta")
    void testConstrutor() {
        assertEquals(101, drone.getIdDrone());
        assertEquals("DJI Phantom", drone.getModeloDrone());
        assertEquals(1001, drone.getIdEstacaoResponsavel());
        assertEquals("Drone de Varredura", drone.getTipo());
    }

    @Test
    @DisplayName("Deve alterar dados do drone")
    void testSetters() {
        drone.setIdDrone(102);
        drone.setModeloDrone("DJI Mavic");
        drone.setIdEstacaoResponsavel(1002);

        assertEquals(102, drone.getIdDrone());
        assertEquals("DJI Mavic", drone.getModeloDrone());
        assertEquals(1002, drone.getIdEstacaoResponsavel());
    }

    @Test
    @DisplayName("Deve retornar ID da estação base")
    void testGetIdEstacaoBase() {
        assertEquals(1001, drone.getIdEstacaoBase());

        drone.setIdEstacaoResponsavel(2001);
        assertEquals(2001, drone.getIdEstacaoBase());
    }

    @Test
    @DisplayName("Deve executar operação de varredura")
    void testOperar() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        drone.operar();

        String output = outputStream.toString();
        assertTrue(output.contains("DJI Phantom decolando"));
        assertTrue(output.contains("câmeras térmicas"));
        assertTrue(output.contains("tempo real"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve exibir informações do drone")
    void testExibirInformacoes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        drone.exibirInformacoes();

        String output = outputStream.toString();
        assertTrue(output.contains("#101"));
        assertTrue(output.contains("DJI Phantom"));
        assertTrue(output.contains("ID Estação: 1001"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve exibir área identificada")
    void testExibirAreaIdentificada() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        drone.exibirAreaIdentificada();

        String output = outputStream.toString();
        assertTrue(output.contains("Área identificada"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve ser instância de Equipamento")
    void testHeranca() {
        assertInstanceOf(Equipamento.class, drone);
        assertEquals(101, drone.getId());
        assertEquals("DJI Phantom", drone.getNome());
    }
}