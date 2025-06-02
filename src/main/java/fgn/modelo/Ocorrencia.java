package fgn.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma Ocorrência de incêndio detectada
 * Contém todas as informações do evento registrado
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Ocorrencia {
    private int idOcorrencia;
    private AreaFlorestal areaAfetada;
    private int hectaresAfetados;
    private String nivelRisco;
    private Sensor sensorDetector;
    private LocalDateTime dataHoraDeteccao;
    private int tempoChegadaMinutos;
    private String statusOcorrencia;

    /**
     * Construtor da Ocorrência
     * @param idOcorrencia Identificador único da ocorrência
     * @param areaAfetada Área florestal onde ocorreu o incêndio
     * @param hectaresAfetados Área em hectares afetada
     * @param sensorDetector Sensor que detectou o incêndio
     * @param tempoChegadaMinutos Tempo estimado para chegada dos bombeiros
     */
    public Ocorrencia(int idOcorrencia, AreaFlorestal areaAfetada, int hectaresAfetados,
                      Sensor sensorDetector, int tempoChegadaMinutos) {
        this.idOcorrencia = idOcorrencia;
        this.areaAfetada = areaAfetada;
        this.hectaresAfetados = hectaresAfetados;
        this.sensorDetector = sensorDetector;
        this.tempoChegadaMinutos = tempoChegadaMinutos;
        this.dataHoraDeteccao = LocalDateTime.now();
        this.statusOcorrencia = "Ativo";
        this.nivelRisco = calcularNivelRisco(hectaresAfetados);
    }

    /**
     * Calcula o nível de risco baseado na área afetada
     * @param hectares Área afetada em hectares
     * @return String com o nível de risco
     */
    private String calcularNivelRisco(int hectares) {
        if (hectares >= 1 && hectares <= 40) {
            return "Investigação";
        } else if (hectares >= 41 && hectares <= 100) {
            return "Alerta Ativo";
        } else {
            return "Emergência";
        }
    }

    // Getters
    public int getIdOcorrencia() {
        return idOcorrencia;
    }

    public AreaFlorestal getAreaAfetada() {
        return areaAfetada;
    }

    public int getHectaresAfetados() {
        return hectaresAfetados;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public Sensor getSensorDetector() {
        return sensorDetector;
    }

    public LocalDateTime getDataHoraDeteccao() {
        return dataHoraDeteccao;
    }

    public int getTempoChegadaMinutos() {
        return tempoChegadaMinutos;
    }

    public String getStatusOcorrencia() {
        return statusOcorrencia;
    }

    // Setters
    public void setStatusOcorrencia(String statusOcorrencia) {
        this.statusOcorrencia = statusOcorrencia;
    }

    /**
     * Exibe relatório completo da ocorrência
     */
    public void exibirRelatorio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚨 OCORRÊNCIA REGISTRADA #" + idOcorrencia);
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📍 Local: " + areaAfetada.getNomeArea());
        System.out.println("🔥 Área Afetada: " + hectaresAfetados + " hectares");
        System.out.println("⚠️  Nível de Risco: " + nivelRisco);
        System.out.println("🔍 Detectado por: " + sensorDetector.getNomeSensor() + " (" + sensorDetector.getTipo() + ")");
        System.out.println("⏰ Data/Hora: " + dataHoraDeteccao.format(formatter));
        System.out.println("🚒 Tempo Estimado Chegada: " + tempoChegadaMinutos + " minutos");
        System.out.println("📊 Status: " + statusOcorrencia);
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚨 Notificação enviada para o Corpo de Bombeiros!");
        System.out.println("🔔 Solicitando reforços para combate ao incêndio...");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();
    }
}