package fgn.modelo;

/**
 * Classe que representa um Sensor do sistema FGN
 * Responsável por detectar focos de incêndio
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Sensor {
    private int id;
    private String nome;
    private String tipo;
    private boolean ativo;

    /**
     * Construtor do Sensor
     * @param id Identificador único do sensor
     * @param nome Nome do sensor
     * @param tipo Tipo de sensor (Térmico, Fumaça, Químico)
     */
    public Sensor(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.ativo = true;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Exibe informações do sensor
     */
    public void exibirInformacoes() {
        System.out.println(id + ". " + nome + " (" + tipo + ")");
    }
}
