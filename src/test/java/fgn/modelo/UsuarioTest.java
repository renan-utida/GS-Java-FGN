package fgn.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Usuario
 */
public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João Silva", 12345678901L, "15/03/1990");
    }

    @Test
    @DisplayName("Deve criar usuário com dados válidos")
    void testConstrutor() {
        assertEquals("João Silva", usuario.getNome());
        assertEquals(12345678901L, usuario.getCpf());
        assertEquals("15/03/1990", usuario.getDataNascimento());
    }

    @Test
    @DisplayName("Deve validar nome corretamente")
    void testValidarNome() {
        assertTrue(Usuario.validarNome("João Silva"));
        assertTrue(Usuario.validarNome("Maria"));
        assertFalse(Usuario.validarNome("123João"));
        assertFalse(Usuario.validarNome("João@Silva"));
        assertFalse(Usuario.validarNome(""));
        assertFalse(Usuario.validarNome(null));
    }

    @Test
    @DisplayName("Deve formatar nome corretamente")
    void testFormatarNome() {
        assertEquals("João Silva", Usuario.formatarNome("joão silva"));
        assertEquals("Maria Santos", Usuario.formatarNome("MARIA SANTOS"));
        assertEquals("Ana", Usuario.formatarNome("ana"));
        assertEquals("", Usuario.formatarNome(""));
        assertNull(Usuario.formatarNome(null));
    }

    @Test
    @DisplayName("Deve validar CPF com 11 dígitos")
    void testValidarCPF() {
        assertTrue(Usuario.validarCPF(12345678901L));
        assertFalse(Usuario.validarCPF(123456789L)); // 9 dígitos
        assertFalse(Usuario.validarCPF(123456789012L)); // 12 dígitos
    }

    @Test
    @DisplayName("Deve formatar CPF corretamente")
    void testGetCpfFormatado() {
        assertEquals("123.456.789-01", usuario.getCpfFormatado());

        Usuario usuarioComCpfMenor = new Usuario("Test", 1234567890L, "01/01/2000");
        assertEquals("012.345.678-90", usuarioComCpfMenor.getCpfFormatado());
    }
}