package fgn.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma Ocorrência de incêndio detectada
 * Entidade que contém dados e métodos de exibição
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
    private Drone droneVarredura;
    private LocalDateTime dataHoraDeteccao;
    private int tempoChegadaMinutos;
    private String statusOcorrencia;
    private Usuario usuarioDenunciante;

    /**
     * Construtor da Ocorrência (com sensor)
     * @param idOcorrencia Identificador único da ocorrência
     * @param areaAfetada Área florestal onde ocorreu o incêndio
     * @param hectaresAfetados Área em hectares afetada
     * @param sensorDetector Sensor que detectou o incêndio
     * @param droneVarredura Drone responsável pela varredura
     * @param tempoChegadaMinutos Tempo estimado para chegada dos bombeiros
     */
    public Ocorrencia(int idOcorrencia, AreaFlorestal areaAfetada, int hectaresAfetados,
                      Sensor sensorDetector, Drone droneVarredura, int tempoChegadaMinutos) {
        this.idOcorrencia = idOcorrencia;
        this.areaAfetada = areaAfetada;
        this.hectaresAfetados = hectaresAfetados;
        this.sensorDetector = sensorDetector;
        this.droneVarredura = droneVarredura;
        this.tempoChegadaMinutos = tempoChegadaMinutos;
        this.dataHoraDeteccao = LocalDateTime.now();
        this.statusOcorrencia = "Ativo";
        this.nivelRisco = calcularNivelRisco(hectaresAfetados);
        this.usuarioDenunciante = null;
    }

    /**
     * Construtor para áreas seguras
     * @param idOcorrencia Identificador único da ocorrência
     * @param areaAfetada Área florestal averiguada
     * @param droneVarredura Drone responsável pela varredura
     */
    public Ocorrencia(int idOcorrencia, AreaFlorestal areaAfetada, Drone droneVarredura) {
        this.idOcorrencia = idOcorrencia;
        this.areaAfetada = areaAfetada;
        this.hectaresAfetados = 0;
        this.sensorDetector = null;
        this.droneVarredura = droneVarredura;
        this.tempoChegadaMinutos = 0;
        this.dataHoraDeteccao = LocalDateTime.now();
        this.statusOcorrencia = "Concluído";
        this.nivelRisco = "Seguro";
        this.usuarioDenunciante = null;
    }

    /**
     * Construtor para denúncias de usuários
     * @param idOcorrencia Identificador único da ocorrência
     * @param areaAfetada Área florestal onde ocorreu o incêndio
     * @param hectaresAfetados Área em hectares afetada
     * @param droneVarredura Drone responsável pela varredura
     * @param usuarioDenunciante Usuário que fez a denúncia
     * @param tempoChegadaMinutos Tempo estimado para chegada dos bombeiros
     */
    public Ocorrencia(int idOcorrencia, AreaFlorestal areaAfetada, int hectaresAfetados,
                      Drone droneVarredura, Usuario usuarioDenunciante, int tempoChegadaMinutos) {
        this.idOcorrencia = idOcorrencia;
        this.areaAfetada = areaAfetada;
        this.hectaresAfetados = hectaresAfetados;
        this.sensorDetector = null;
        this.droneVarredura = droneVarredura;
        this.tempoChegadaMinutos = tempoChegadaMinutos;
        this.dataHoraDeteccao = LocalDateTime.now();
        this.usuarioDenunciante = usuarioDenunciante;

        if (hectaresAfetados == 0) {
            this.statusOcorrencia = "Concluído";
            this.nivelRisco = "Seguro";
        } else {
            this.statusOcorrencia = "Ativo";
            this.nivelRisco = calcularNivelRisco(hectaresAfetados);
        }
    }

    /**
     * Construtor para áreas seguras com usuário denunciante
     * @param idOcorrencia Identificador único da ocorrência
     * @param areaAfetada Área florestal averiguada
     * @param droneVarredura Drone responsável pela varredura
     * @param usuarioDenunciante Usuário que fez a denúncia
     */
    public Ocorrencia(int idOcorrencia, AreaFlorestal areaAfetada, Drone droneVarredura, Usuario usuarioDenunciante) {
        this.idOcorrencia = idOcorrencia;
        this.areaAfetada = areaAfetada;
        this.hectaresAfetados = 0;
        this.sensorDetector = null;
        this.droneVarredura = droneVarredura;
        this.tempoChegadaMinutos = 0;
        this.dataHoraDeteccao = LocalDateTime.now();
        this.statusOcorrencia = "Concluído";
        this.nivelRisco = "Seguro";
        this.usuarioDenunciante = usuarioDenunciante;
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

    /**
     * Calcula tempo de chegada baseado na distância e velocidade
     * @param distanciaKm Distância em km
     * @param velocidadeKmH Velocidade em km/h
     * @return Tempo em minutos
     */
    public static int calcularTempoChegada(int distanciaKm, int velocidadeKmH) {
        double tempoHoras = (double) distanciaKm / velocidadeKmH;
        return (int) Math.ceil(tempoHoras * 60); // Converter para minutos e arredondar para cima
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

    public Drone getDroneVarredura() {
        return droneVarredura;
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

    public Usuario getUsuarioDenunciante() {
        return usuarioDenunciante;
    }

    // Setters
    public void setStatusOcorrencia(String statusOcorrencia) {
        this.statusOcorrencia = statusOcorrencia;
    }

    /**
     * Exibe relatório completo da ocorrência
     * @param estacaoResponsavel Estação responsável pela área
     */
    public void exibirRelatorio(EstacaoBombeiros estacaoResponsavel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        if ("Seguro".equals(nivelRisco)) {
            System.out.println("✅ ÁREA AVERIGUADA #" + idOcorrencia);
        } else {
            System.out.println("🚨 OCORRÊNCIA REGISTRADA #" + idOcorrencia);
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📍 Local: " + areaAfetada.getNomeArea());
        System.out.println("🏢 Estação: " + estacaoResponsavel.getNomeEstacao());
        System.out.println("🌆 Cidade: " + estacaoResponsavel.getCidade() + ", " + estacaoResponsavel.getEstado());

        if ("Seguro".equals(nivelRisco)) {
            System.out.println("🔍 Local Averiguado: Área confirmada como segura");
        } else {
            System.out.println("🔥 Área Afetada: " + hectaresAfetados + " hectares");
        }

        System.out.println("⚠️  Nível de Risco: " + nivelRisco);

        if (sensorDetector != null) {
            System.out.println("🔍 Detectado por: " + sensorDetector.getNomeSensor() + " (" + sensorDetector.getTipo() + ")");
        } else {
            System.out.println("🔍 Detectado por: Drone de Varredura");
        }

        System.out.println("🚁 Drone: " + droneVarredura.getModeloDrone() + " (#" + droneVarredura.getIdDrone() + ")");
        System.out.println("⏰ Data/Hora: " + dataHoraDeteccao.format(formatter));

        if (tempoChegadaMinutos > 0) {
            System.out.println("🚒 Tempo Estimado Chegada: " + tempoChegadaMinutos + " minutos");
        }

        System.out.println("📊 Status: " + statusOcorrencia);
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        if ("Seguro".equals(nivelRisco)) {
            System.out.println("✅ Notificação positiva enviada para o Corpo de Bombeiros!");
            System.out.println("🔔 Área confirmada como segura para atividades...");
        } else {
            System.out.println("🚨 Notificação enviada para o Corpo de Bombeiros!");
            System.out.println("🔔 Solicitando reforços para combate ao incêndio...");
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();
    }

    /**
     * Exibe relatório resumido para listagem
     * @param estacaoResponsavel Estação responsável pela área
     */
    public void exibirResumo(EstacaoBombeiros estacaoResponsavel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String emoji = "Seguro".equals(nivelRisco) ? "✅" : "🚨";
        String tipoOcorrencia = "Seguro".equals(nivelRisco) ? "ÁREA SEGURA" : "INCÊNDIO";

        System.out.println(emoji + " #" + idOcorrencia + " - " + tipoOcorrencia);
        System.out.println("    📍 " + areaAfetada.getNomeArea());
        System.out.println("    🏢 " + estacaoResponsavel.getCidade() + ", " + estacaoResponsavel.getEstado());
        System.out.println("    ⚠️  " + nivelRisco + " | 📊 " + statusOcorrencia + " | ⏰ " + dataHoraDeteccao.format(formatter));

        if (hectaresAfetados > 0) {
            System.out.println("    🔥 " + hectaresAfetados + " hectares afetados");
        }

        if (sensorDetector != null) {
            System.out.println("    🔍 " + sensorDetector.getNomeSensor());
        } else {
            System.out.println("    🔍 Drone de Varredura");
        }

        if (droneVarredura != null) {
            System.out.println("    🚁 " + droneVarredura.getModeloDrone() + " (#" + droneVarredura.getIdDrone() + ")");
        }

        if (usuarioDenunciante != null) {
            System.out.println("    👤 Denúncia: " + usuarioDenunciante.getNome());
            System.out.println("        📄 CPF: " + usuarioDenunciante.getCpf() + " | 📅 Nascimento: " + usuarioDenunciante.getDataNascimento());
        } else {
            System.out.println("    👤 Denúncia: Drone");
        }

        System.out.println();
    }
}