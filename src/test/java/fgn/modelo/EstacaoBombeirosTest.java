package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe EstacaoBombeiros
 */
public class EstacaoBombeirosTest {

    private EstacaoBombeiros estacao;

    @BeforeEach
    void setUp() {
        estacao = new EstacaoBombeiros(1001, "1ª Companhia", "Rua A, 123",
                "São Paulo", "SP", "Capitão Silva", 10001);
    }

    @Test
    @DisplayName("Deve criar estação com todos os dados")
    void testConstrutor() {
        assertEquals(1001, estacao.getIdEstacao());
        assertEquals("1ª Companhia", estacao.getNomeEstacao());
        assertEquals("Rua A, 123", estacao.getEndereco());
        assertEquals("São Paulo", estacao.getCidade());
        assertEquals("SP", estacao.getEstado());
        assertEquals("Capitão Silva", estacao.getNomeComandante());
        assertEquals(10001, estacao.getIdComandante());
    }

    @Test
    @DisplayName("Deve alterar dados da estação")
    void testSetters() {
        estacao.setIdEstacao(1002);
        estacao.setNomeEstacao("2ª Companhia");
        estacao.setCidade("Rio de Janeiro");

        assertEquals(1002, estacao.getIdEstacao());
        assertEquals("2ª Companhia", estacao.getNomeEstacao());
        assertEquals("Rio de Janeiro", estacao.getCidade());
    }

    @Test
    @DisplayName("Deve alterar dados do comandante")
    void testSetComandante() {
        estacao.setNomeComandante("Major Santos");
        estacao.setIdComandante(20001);

        assertEquals("Major Santos", estacao.getNomeComandante());
        assertEquals(20001, estacao.getIdComandante());
    }

    @Test
    @DisplayName("Deve exibir informações básicas")
    void testExibirInformacoes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        estacao.exibirInformacoes();

        String output = outputStream.toString();
        assertTrue(output.contains("ID Estação: 1001"));
        assertTrue(output.contains("Local: Rua A, 123"));
        assertTrue(output.contains("Capitão Silva"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Deve exibir relatório completo")
    void testExibirRelatorio() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        estacao.exibirRelatorio();

        String output = outputStream.toString();
        assertTrue(output.contains("1ª Companhia:"));
        assertTrue(output.contains("Comandante Responsável: Capitão Silva"));

        System.setOut(System.out);
    }
}