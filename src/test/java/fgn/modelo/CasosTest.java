package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Casos
 */
public class CasosTest {

    private ArrayList<AreaFlorestal> areasFlorestais;
    private ArrayList<Sensor> sensores;
    private ArrayList<Drone> drones;
    private ArrayList<Ocorrencia> ocorrencias;
    private ArrayList<EstacaoBombeiros> estacoes;
    private EstacaoBombeiros estacaoAtual;

    @BeforeEach
    void setUp() {
        areasFlorestais = new ArrayList<>();
        sensores = new ArrayList<>();
        drones = new ArrayList<>();
        ocorrencias = new ArrayList<>();
        estacoes = new ArrayList<>();

        estacaoAtual = new EstacaoBombeiros(1001, "1ª Companhia", "Rua A, 123",
                "São Paulo", "SP", "Capitão Silva", 10001);

        AreaFlorestal area = new AreaFlorestal(1, "Parque Nacional", "Zona Norte", 25, 1001);
        Sensor sensor = new Sensor(201, "ThermoDetect", "Térmico");
        Drone drone = new Drone(101, "DJI Phantom", 1001);

        areasFlorestais.add(area);
        sensores.add(sensor);
        drones.add(drone);
        estacoes.add(estacaoAtual);
    }

    @Test
    @DisplayName("Deve listar ocorrências vazias")
    void testListarOcorrenciasVazias() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Casos.listarOcorrenciasDaEstacao(ocorrencias, estacoes, estacaoAtual);

        String output = outputStream.toString();
        assertTrue(output.contains("Nenhuma ocorrência registrada"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve listar ocorrências existentes")
    void testListarOcorrenciasExistentes() {
        // Adicionar uma ocorrência
        AreaFlorestal area = areasFlorestais.get(0);
        Drone drone = drones.get(0);
        Ocorrencia ocorrencia = new Ocorrencia(1, area, drone);
        ocorrencias.add(ocorrencia);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Casos.listarOcorrenciasDaEstacao(ocorrencias, estacoes, estacaoAtual);

        String output = outputStream.toString();
        assertTrue(output.contains("Total de registros"));
        assertTrue(output.contains("#1"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve falhar registro sem drone")
    void testRegistrarSemDrone() {
        // Remover drone da estação
        drones.clear();

        String input = "1\n50\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        int resultado = Casos.registrarNovaOcorrencia(areasFlorestais, sensores, drones,
                ocorrencias, 1, estacaoAtual, estacoes, scanner);

        assertEquals(1, resultado); // ID não deve ser incrementado
    }

    @Test
    @DisplayName("Deve falhar registro sem áreas")
    void testRegistrarSemAreas() {
        // Remover áreas da estação
        areasFlorestais.clear();

        String input = "";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        int resultado = Casos.registrarNovaOcorrencia(areasFlorestais, sensores, drones,
                ocorrencias, 1, estacaoAtual, estacoes, scanner);

        assertEquals(1, resultado); // ID não deve ser incrementado
    }

    @Test
    @DisplayName("Deve registrar área segura com sucesso")
    void testRegistrarAreaSegura() {
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        int resultado = Casos.registrarAreaSegura(areasFlorestais, drones, ocorrencias,
                1, estacaoAtual, estacoes, scanner);

        assertEquals(2, resultado); // ID deve ser incrementado
        assertEquals(1, ocorrencias.size());
        assertEquals("Seguro", ocorrencias.get(0).getNivelRisco());
    }
}