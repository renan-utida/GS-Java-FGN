package fgn.modelo;

/**
 * Classe que representa um Drone do sistema FGN
 * Responsável por monitoramento e detecção de incêndios
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Drone {
    private int id;
    private String modelo;
    private int autonomiaHoras;
    private int velocidadeMaxKmH;
    private int alcanceKm;
    private String status;
    private int idEstacaoBase;

    /**
     * Construtor do Drone
     * @param id Identificador único do drone
     * @param modelo Modelo do drone
     * @param idEstacaoBase ID da estação base do drone
     */
    public Drone(int id, String modelo, int idEstacaoBase) {
        this.id = id;
        this.modelo = modelo;
        this.autonomiaHoras = 6;
        this.velocidadeMaxKmH = 90;
        this.alcanceKm = 45;
        this.status = "Disponível";
        this.idEstacaoBase = idEstacaoBase;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAutonomiaHoras() {
        return autonomiaHoras;
    }

    public int getVelocidadeMaxKmH() {
        return velocidadeMaxKmH;
    }

    public int getAlcanceKm() {
        return alcanceKm;
    }

    public String getStatus() {
        return status;
    }

    public int getIdEstacaoBase() {
        return idEstacaoBase;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Calcula tempo estimado para chegada baseado na distância
     * @param distanciaKm Distância até o local em km
     * @param velocidadeVeiculoKmH Velocidade do veículo de bombeiros
     * @return Tempo estimado em minutos
     */
    public int calcularTempoChegada(int distanciaKm, int velocidadeVeiculoKmH) {
        double tempoHoras = (double) distanciaKm / velocidadeVeiculoKmH;
        return (int) Math.ceil(tempoHoras * 60); // Converter para minutos e arredondar para cima
    }

    /**
     * Inicia missão de varredura
     * @param area Área a ser monitorada
     * @return boolean indicando se a missão foi iniciada com sucesso
     */
    public boolean iniciarMissao(AreaFlorestal area) {
        if ("Disponível".equals(this.status)) {
            this.status = "Em Missão";
            System.out.println("🚁 Drone #" + id + " iniciando varredura na " + area.getNome());
            return true;
        }
        return false;
    }

    /**
     * Finaliza missão atual
     */
    public void finalizarMissao() {
        this.status = "Disponível";
        System.out.println("🏠 Drone #" + id + " retornando à base para recarga");
    }
}