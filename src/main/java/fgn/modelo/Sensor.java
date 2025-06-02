package fgn.modelo;

/**
 * Classe que representa um Sensor do sistema FGN
 * Responsável por detectar focos de incêndio
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Sensor {
    private int idSensor;
    private String nomeSensor;
    private String tipo;
    private boolean ativo;

    /**
     * Construtor do Sensor
     * @param idSensor Identificador único do sensor
     * @param nomeSensor Nome do sensor
     * @param tipo Tipo de sensor (Térmico, Fumaça, Químico)
     */
    public Sensor(int idSensor, String nomeSensor, String tipo) {
        this.idSensor = idSensor;
        this.nomeSensor = nomeSensor;
        this.tipo = tipo;
        this.ativo = true;
    }

    // Getters e Setters
    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public String getNomeSensor() {
        return nomeSensor;
    }

    public void setNomeSensor(String nomeSensor) {
        this.nomeSensor = nomeSensor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Exibe informações do sensor
     */
    public void exibirInformacoes() {
        System.out.println(idSensor + ". " + nomeSensor + " (" + tipo + ")");
    }
}
