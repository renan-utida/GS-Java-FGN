package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Arquivo
 */
public class ArquivoTest {

    private EstacaoBombeiros estacao;
    private ArrayList<Ocorrencia> ocorrencias;
    private ArrayList<AreaFlorestal> areas;
    private ArrayList<Drone> drones;
    private AreaFlorestal area;
    private Drone drone;
    private String nomeArquivo;

    @BeforeEach
    void setUp() {
        estacao = new EstacaoBombeiros(1001, "1ª Companhia", "Rua A, 123",
                "São Paulo", "SP", "Capitão Silva", 10001);
        ocorrencias = new ArrayList<>();
        areas = new ArrayList<>();
        drones = new ArrayList<>();

        area = new AreaFlorestal(1, "Parque Nacional", "Zona Norte", 25, 1001);
        drone = new Drone(101, "DJI Phantom", 1001);

        areas.add(area);
        drones.add(drone);

        nomeArquivo = "historico_são_paulo.txt";
    }

    @AfterEach
    void tearDown() {
        // Limpar arquivo de teste
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    @Test
    @DisplayName("Deve salvar histórico vazio")
    void testSalvarHistoricoVazio() {
        ArrayList<Ocorrencia> ocorrenciasVazias = new ArrayList<>();

        Arquivo.salvarHistoricoDaCidade(ocorrenciasVazias, estacao);

        File arquivo = new File(nomeArquivo);
        assertTrue(arquivo.exists());
    }

    @Test
    @DisplayName("Deve salvar histórico com ocorrências")
    void testSalvarHistoricoComOcorrencias() {
        Ocorrencia ocorrencia = new Ocorrencia(1, area, drone);
        ArrayList<Ocorrencia> lista = new ArrayList<>();
        lista.add(ocorrencia);

        Arquivo.salvarHistoricoDaCidade(lista, estacao);

        File arquivo = new File(nomeArquivo);
        assertTrue(arquivo.exists());
        assertTrue(arquivo.length() > 0);
    }

    @Test
    @DisplayName("Deve carregar histórico inexistente")
    void testCarregarHistoricoInexistente() {
        EstacaoBombeiros novaEstacao = new EstacaoBombeiros(2001, "Nova Estação",
                "Rua B, 456", "Rio de Janeiro",
                "RJ", "Major Santos", 20001);

        assertDoesNotThrow(() -> {
            Arquivo.carregarHistoricoDaCidade(novaEstacao, ocorrencias,
                    new ArrayList<>(), areas, drones);
        });
    }

    @Test
    @DisplayName("Deve carregar histórico existente")
    void testCarregarHistoricoExistente() {
        // Primeiro salvar um histórico
        Ocorrencia ocorrencia = new Ocorrencia(1, area, drone);
        ArrayList<Ocorrencia> lista = new ArrayList<>();
        lista.add(ocorrencia);

        Arquivo.salvarHistoricoDaCidade(lista, estacao);

        // Depois carregar
        ArrayList<Ocorrencia> ocorrenciasCarregadas = new ArrayList<>();

        assertDoesNotThrow(() -> {
            Arquivo.carregarHistoricoDaCidade(estacao, ocorrenciasCarregadas,
                    new ArrayList<>(), areas, drones);
        });
    }
}