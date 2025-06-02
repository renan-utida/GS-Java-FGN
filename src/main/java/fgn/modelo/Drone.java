package fgn.modelo;

/**
 * Classe que representa um Drone do sistema FGN
 * Responsável por monitoramento e detecção de incêndios
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Drone {
    private int idDrone;
    private String modeloDrone;
    private int autonomiaHoras;
    private int velocidadeMaxKmH;
    private int alcanceKm;
    private String statusDrone;
    private int idEstacaoBase;

    /**
     * Construtor do Drone
     * @param idDrone Identificador único do drone
     * @param modeloDrone Modelo do drone
     * @param idEstacaoBase ID da estação base do drone
     */
    public Drone(int idDrone, String modeloDrone, int idEstacaoBase) {
        this.idDrone = idDrone;
        this.modeloDrone = modeloDrone;
        this.autonomiaHoras = 6;
        this.velocidadeMaxKmH = 90;
        this.alcanceKm = 45;
        this.statusDrone = "Disponível";
        this.idEstacaoBase = idEstacaoBase;
    }

    // Getters
    public int getIdDrone() {
        return idDrone;
    }

    public String getModeloDrone() {
        return modeloDrone;
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

    public String getStatusDrone() {
        return statusDrone;
    }

    public int getIdEstacaoBase() {
        return idEstacaoBase;
    }

    // Setters
    public void setStatusDrone(String statusDrone) {
        this.statusDrone = statusDrone;
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
        if ("Disponível".equals(this.statusDrone)) {
            this.statusDrone = "Em Missão";
            System.out.println("🚁 Drone #" + idDrone + " iniciando varredura na " + area.getNomeArea());
            return true;
        }
        return false;
    }

    /**
     * Finaliza missão atual
     */
    public void finalizarMissao() {
        this.statusDrone = "Disponível";
        System.out.println("🏠 Drone #" + idDrone + " retornando à base para recarga");
    }
}