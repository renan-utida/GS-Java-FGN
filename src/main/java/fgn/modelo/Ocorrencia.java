package fgn.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * Esta classe representa uma entidade fundamental do sistema Forest Guardian Network:
 * a Ocorrência de incêndio florestal. Encapsula todos os dados relacionados a um
 * evento de detecção, desde incêndios ativos até confirmações de áreas seguras,
 * incluindo metadados de detecção, equipamentos envolvidos e usuários denunciantes.
 * </p>
 * <p>
 * A classe oferece múltiplos construtores para diferentes cenários de registro:
 * detecção automática por sensores, verificação por drones, processamento de
 * denúncias de usuários e confirmação de áreas seguras. Calcula automaticamente
 * níveis de risco baseados em área afetada e fornece métodos de exibição formatada.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
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
     * Construtor para ocorrências de incêndio detectadas por sensores durante varredura.
     *
     * @param idOcorrencia identificador único sequencial da ocorrência
     * @param areaAfetada área florestal onde foi detectado o incêndio
     * @param hectaresAfetados extensão em hectares da área impactada
     * @param sensorDetector sensor responsável pela detecção inicial
     * @param droneVarredura drone utilizado para confirmação e varredura
     * @param tempoChegadaMinutos tempo estimado em minutos para chegada dos bombeiros
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
     * Construtor para registro de áreas confirmadas como seguras após averiguação.
     *
     * @param idOcorrencia identificador único sequencial da verificação
     * @param areaAfetada área florestal averiguada e confirmada como segura
     * @param droneVarredura drone utilizado para a verificação de segurança
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
     * Construtor para ocorrências reportadas por denúncias de usuários.
     *
     * @param idOcorrencia identificador único sequencial da denúncia
     * @param areaAfetada área florestal reportada na denúncia
     * @param hectaresAfetados extensão em hectares conforme relato ou verificação
     * @param droneVarredura drone enviado para verificação da denúncia
     * @param usuarioDenunciante cidadão que reportou a possível ocorrência
     * @param tempoChegadaMinutos tempo estimado para chegada dos bombeiros se necessário
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
     * Construtor para áreas seguras reportadas inicialmente como denúncias por usuários.
     *
     * @param idOcorrencia identificador único sequencial do registro
     * @param areaAfetada área florestal que foi reportada e averiguada
     * @param droneVarredura drone utilizado para verificação da denúncia
     * @param usuarioDenunciante cidadão que fez o relato inicial
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
     * Calcula automaticamente o nível de risco baseado na extensão da área afetada.
     * Utiliza escala padronizada para classificação de severidade, auxiliando
     * na priorização de recursos e tomada de decisões operacionais.
     *
     * @param hectares extensão em hectares da área impactada pelo incêndio
     * @return string representando o nível de risco calculado
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
     * Calcula tempo estimado de chegada baseado em distância e velocidade.
     * Metodo estático utilitário para cálculos de logística operacional,
     * considerando deslocamento terrestre em condições normais.
     *
     * @param distanciaKm distância em quilômetros até o local da ocorrência
     * @param velocidadeKmH velocidade média de deslocamento em km/h
     * @return tempo estimado em minutos, arredondado para cima
     */
    public static int calcularTempoChegada(int distanciaKm, int velocidadeKmH) {
        double tempoHoras = (double) distanciaKm / velocidadeKmH;
        return (int) Math.ceil(tempoHoras * 60); // Converter para minutos e arredondar para cima
    }

    // Getters
    /**
     * Obtém o identificador único da ocorrência.
     *
     * @return ID numérico sequencial da ocorrência
     */
    public int getIdOcorrencia() {
        return idOcorrencia;
    }

    /**
     * Obtém a área florestal onde ocorreu o evento.
     *
     * @return objeto AreaFlorestal associado à ocorrência
     */
    public AreaFlorestal getAreaAfetada() {
        return areaAfetada;
    }

    /**
     * Obtém a extensão em hectares da área impactada.
     * Valor zero indica área confirmada como segura.
     *
     * @return número de hectares afetados pelo incêndio
     */
    public int getHectaresAfetados() {
        return hectaresAfetados;
    }

    /**
     * Obtém o nível de risco calculado da ocorrência.
     * Baseado na extensão da área afetada segundo escala padronizada.
     *
     * @return string representando nível de risco ("Investigação", "Alerta Ativo", "Emergência", "Seguro")
     */
    public String getNivelRisco() {
        return nivelRisco;
    }

    /**
     * Obtém o sensor responsável pela detecção inicial.
     * Pode ser null para ocorrências detectadas apenas por drone.
     *
     * @return objeto Sensor detector ou null se detecção foi por drone
     */
    public Sensor getSensorDetector() {
        return sensorDetector;
    }

    /**
     * Obtém o drone utilizado para varredura e verificação.
     * Sempre presente em todas as ocorrências do sistema.
     *
     * @return objeto Drone responsável pela varredura
     */
    public Drone getDroneVarredura() {
        return droneVarredura;
    }

    /**
     * Obtém timestamp completo de quando a ocorrência foi detectada.
     * Marcação automática no momento da criação do registro.
     *
     * @return objeto LocalDateTime com data e hora da detecção
     */
    public LocalDateTime getDataHoraDeteccao() {
        return dataHoraDeteccao;
    }

    /**
     * Obtém tempo estimado em minutos para chegada das equipes.
     * Baseado em cálculo de distância e velocidade média de deslocamento.
     *
     * @return tempo estimado de chegada em minutos
     */
    public int getTempoChegadaMinutos() {
        return tempoChegadaMinutos;
    }

    /**
     * Obtém status atual da ocorrência.
     * Indica se requer ação ("Ativo") ou foi resolvida ("Concluído").
     *
     * @return string representando status ("Ativo" ou "Concluído")
     */
    public String getStatusOcorrencia() {
        return statusOcorrencia;
    }

    /**
     * Obtém dados do cidadão que reportou a ocorrência.
     * Pode ser null para detecções automáticas por sensores.
     *
     * @return objeto Usuario denunciante ou null se detecção automática
     */
    public Usuario getUsuarioDenunciante() {
        return usuarioDenunciante;
    }

    // Setters
    /**
     * Define novo status para a ocorrência.
     * Utilizado para atualizar estado conforme progresso das operações.
     *
     * @param statusOcorrencia novo status a ser definido ("Ativo" ou "Concluído")
     */
    public void setStatusOcorrencia(String statusOcorrencia) {
        this.statusOcorrencia = statusOcorrencia;
    }

    /**
     * Marca a ocorrência como resolvida convertendo para área segura.
     * Utilizado quando incêndios são extintos com sucesso pelas equipes
     * de combate, zerando hectares afetados e atualizando status.
     */
    public void marcarComoSegura() {
        this.statusOcorrencia = "Concluído";
        this.nivelRisco = "Seguro";
        this.hectaresAfetados = 0;
    }

    /**
     * Exibe relatório completo e formatado da ocorrência.
     * Apresenta todos os dados relevantes em formato estruturado para
     * documentação oficial e comunicação com equipes de combate.
     *
     * @param estacaoResponsavel estação de bombeiros responsável pela área
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
     * Exibe resumo compacto da ocorrência para listagens e consultas rápidas.
     * Apresenta informações essenciais em formato condensado, ideal para
     * visualização de múltiplas ocorrências em relatórios consolidados.
     *
     * @param estacaoResponsavel estação de bombeiros responsável pela área
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