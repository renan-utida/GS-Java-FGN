package fgn.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

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
    private Drone droneVarredura;
    private LocalDateTime dataHoraDeteccao;
    private int tempoChegadaMinutos;
    private String statusOcorrencia;
    private Usuario usuarioDenunciante;

    /**
     * Construtor da Ocorrência
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
            System.out.println("🔍 Detectado por: Nada detectado");
        }

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
     * Calcula hectares baseado no nível de risco percebido
     */
    private static int calcularHectaresPorRisco(int nivelRisco) {
        switch (nivelRisco) {
            case 1: return 25;  // Investigação (1-40 hectares)
            case 2: return 70;  // Alerta Ativo (41-100 hectares)
            case 3: return 150; // Emergência (101+ hectares)
            default: return 25;
        }
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
            System.out.println("    🔍 Nada detectado");
        }

        if (droneVarredura != null) {
            System.out.println("    🚁 " + droneVarredura.getModeloDrone() + " (#" + droneVarredura.getIdDrone() + ")");
        }

        if (usuarioDenunciante != null) {
            System.out.println("    👤 Denúncia: " + usuarioDenunciante.getNome());
        }

        System.out.println();
    }

    /**
     * Registra uma nova ocorrência de incêndio
     * @param areasFlorestais Lista de áreas florestais
     * @param sensores Lista de sensores
     * @param drones Lista de drones
     * @param ocorrencias Lista de ocorrências
     * @param proximoIdOcorrencia Próximo ID disponível
     * @param estacaoAtual Estação logada
     * @param scanner Scanner para entrada do usuário
     * @return Novo ID de ocorrência
     */
    public static int registrarNovaOcorrencia(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Sensor> sensores, ArrayList<Drone> drones,
                                              ArrayList<Ocorrencia> ocorrencias, int proximoIdOcorrencia,
                                              EstacaoBombeiros estacaoAtual, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚨 REGISTRAR NOVA OCORRÊNCIA DE INCÊNDIO");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        Drone droneSelecionado = obterDroneDaEstacao(drones, estacaoAtual.getIdEstacao());

        if (droneSelecionado == null) {
            System.out.println("❌ Nenhum drone disponível para esta estação.");
            return proximoIdOcorrencia;
        }

        try {
            // Listar áreas florestais da estação atual
            ArrayList<AreaFlorestal> areasDisponiveis = obterAreasFlorestaisPorEstacao(areasFlorestais, estacaoAtual.getIdEstacao());

            if (areasDisponiveis.isEmpty()) {
                System.out.println("❌ Nenhuma área florestal cadastrada para esta estação.");
                return proximoIdOcorrencia;
            }

            System.out.println("📍 Escolha o local onde teve o incêndio:");
            System.out.println();

            for (AreaFlorestal area : areasDisponiveis) {
                area.exibirInformacoes();
            }

            System.out.print("👉 Escolha uma das opções: ");

            int opcaoArea = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            AreaFlorestal areaSelecionada = buscarAreaPorId(opcaoArea, areasDisponiveis);

            if (areaSelecionada == null) {
                System.out.println("❌ Opção inválida!");
                return proximoIdOcorrencia;
            }

            System.out.println();
            System.out.print("🔥 Quantos hectares estão sendo atingidos em média (1 - 200 hectares): ");

            int hectares = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (hectares < 1 || hectares > 200) {
                System.out.println("❌ Valor inválido! Deve estar entre 1 e 200 hectares.");
                return proximoIdOcorrencia;
            }

            System.out.println();
            System.out.println("🔍 Identificado por:");
            System.out.println();

            for (Sensor sensor : sensores) {
                sensor.exibirInformacoes();
            }

            System.out.print("👉 Escolha o sensor que detectou: ");

            int opcaoSensor = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            Sensor sensorSelecionado = buscarSensorPorId(opcaoSensor, sensores);

            if (sensorSelecionado == null) {
                System.out.println("❌ Sensor inválido!");
                return proximoIdOcorrencia;
            }

            // Calcular tempo de chegada (velocidade média 75 km/h)
            int tempoChegada = calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);

            // Criar nova ocorrência
            Ocorrencia novaOcorrencia = new Ocorrencia(
                    proximoIdOcorrencia,
                    areaSelecionada,
                    hectares,
                    sensorSelecionado,
                    droneSelecionado,
                    tempoChegada
            );

            ocorrencias.add(novaOcorrencia);

            System.out.println();
            novaOcorrencia.exibirRelatorio(estacaoAtual);

            return proximoIdOcorrencia + 1;

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida! Digite apenas números.");
            scanner.nextLine(); // Limpa o buffer em caso de erro
            return proximoIdOcorrencia;
        }
    }

    /**
     * Registra uma área como segura após averiguação
     * @param areasFlorestais Lista de áreas florestais
     * @param drones Lista de drones
     * @param ocorrencias Lista de ocorrências
     * @param proximoIdOcorrencia Próximo ID disponível
     * @param estacaoAtual Estação logada
     * @param scanner Scanner para entrada do usuário
     * @return Novo ID de ocorrência
     */
    public static int registrarAreaSegura(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Drone> drones,
                                          ArrayList<Ocorrencia> ocorrencias, int proximoIdOcorrencia,
                                          EstacaoBombeiros estacaoAtual, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("✅ REGISTRAR ÁREA SEGURA");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        Drone droneSelecionado = obterDroneDaEstacao(drones, estacaoAtual.getIdEstacao());

        if (droneSelecionado == null) {
            System.out.println("❌ Nenhum drone disponível para esta estação.");
            return proximoIdOcorrencia;
        }

        try {
            // Listar áreas florestais da estação atual
            ArrayList<AreaFlorestal> areasDisponiveis = obterAreasFlorestaisPorEstacao(areasFlorestais, estacaoAtual.getIdEstacao());

            if (areasDisponiveis.isEmpty()) {
                System.out.println("❌ Nenhuma área florestal cadastrada para esta estação.");
                return proximoIdOcorrencia;
            }

            System.out.println("📍 Escolha o local onde foi feita a averiguação:");
            System.out.println();

            for (AreaFlorestal area : areasDisponiveis) {
                area.exibirInformacoes();
            }

            System.out.print("👉 Escolha uma das opções: ");


            int opcaoArea = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            AreaFlorestal areaSelecionada = buscarAreaPorId(opcaoArea, areasDisponiveis);

            if (areaSelecionada == null) {
                System.out.println("❌ Opção inválida!");
                return proximoIdOcorrencia;
            }

            // Criar registro de área segura
            Ocorrencia areaSegura = new Ocorrencia(
                    proximoIdOcorrencia,
                    areaSelecionada,
                    droneSelecionado
            );

            ocorrencias.add(areaSegura);

            System.out.println();
            areaSegura.exibirRelatorio(estacaoAtual);

            return proximoIdOcorrencia + 1;

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida! Digite apenas números.");
            scanner.nextLine(); // Limpa o buffer em caso de erro
            return proximoIdOcorrencia;
        }
    }

    /**
     * Processa denúncia de usuário
     * @param areasFlorestais Lista de áreas florestais
     * @param drones Lista de drones
     * @param ocorrencias Lista de ocorrências
     * @param proximoIdOcorrencia Próximo ID disponível
     * @param estacaoAtual Estação logada
     * @param scanner Scanner para entrada do usuário
     * @return Novo ID de ocorrência
     */
    public static int relatarDenunciaUsuario(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Drone> drones,
                                             ArrayList<Ocorrencia> ocorrencias, int proximoIdOcorrencia,
                                             EstacaoBombeiros estacaoAtual, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📱 RELATAR DENÚNCIA DE USUÁRIO");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        try {
            // 1. Coletar dados do usuário
            System.out.println("👤 DADOS DO DENUNCIANTE:");
            System.out.println();

            System.out.print("Nome completo: ");
            String nome = scanner.nextLine();

            if (nome.trim().isEmpty()) {
                System.out.println("❌ Nome não pode estar vazio!");
                return proximoIdOcorrencia;
            }

            long cpf = Usuario.solicitarCPF(scanner);
            String dataNascimento = Usuario.solicitarDataNascimento(scanner);

            Usuario usuario = new Usuario(nome, cpf, dataNascimento);

            System.out.println();
            System.out.println("✅ Dados do usuário registrados com sucesso!");
            System.out.println();

            // 2. Listar áreas florestais da estação atual
            ArrayList<AreaFlorestal> areasDisponiveis = obterAreasFlorestaisPorEstacao(areasFlorestais, estacaoAtual.getIdEstacao());

            if (areasDisponiveis.isEmpty()) {
                System.out.println("❌ Nenhuma área florestal cadastrada para esta estação.");
                return proximoIdOcorrencia;
            }

            System.out.println("📍 Escolha o local onde ocorreu o possível incêndio:");
            System.out.println();

            for (AreaFlorestal area : areasDisponiveis) {
                area.exibirInformacoes();
            }

            System.out.print("👉 Escolha uma das opções: ");

            int opcaoArea = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            AreaFlorestal areaSelecionada = buscarAreaPorId(opcaoArea, areasDisponiveis);

            if (areaSelecionada == null) {
                System.out.println("❌ Opção inválida!");
                return proximoIdOcorrencia;
            }

            System.out.println();

            // 3. Perguntar sobre nível de risco percebido pelo usuário
            System.out.println("⚠️  Qual o nível de risco que você percebe?");
            System.out.println("1. Investigação (Pequeno foco, pouca fumaça)");
            System.out.println("2. Alerta Ativo (Fogo visível, fumaça densa)");
            System.out.println("3. Emergência (Fogo intenso, área extensa)");
            System.out.print("👉 Escolha o nível de risco: ");

            int nivelRisco = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (nivelRisco < 1 || nivelRisco > 3) {
                System.out.println("❌ Opção inválida!");
                return proximoIdOcorrencia;
            }

            // 4. Selecionar drone para varredura
            Drone droneSelecionado = obterDroneDaEstacao(drones, estacaoAtual.getIdEstacao());

            if (droneSelecionado == null) {
                System.out.println("❌ Nenhum drone disponível para esta estação.");
                return proximoIdOcorrencia;
            }

            // 5. Mostrar que drone está a caminho
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🚁 DRONE DESPACHADO PARA VERIFICAÇÃO");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🚁 " + droneSelecionado.getModeloDrone() + " (#" + droneSelecionado.getIdDrone() + ") está a caminho!");
            System.out.println("📍 Destino: " + areaSelecionada.getNomeArea());
            System.out.println("⏱️  Aguarde enquanto realizamos a verificação...");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de verificação
            System.out.println("🔍 Verificação em andamento...");
            try {
                Thread.sleep(2000); // Simula 2 segundos de verificação
            } catch (InterruptedException e) {
                // Ignorar interrupção
            }

            // 6. Perguntar se área de fogo é verídica
            System.out.println();
            System.out.println("🔍 RESULTADO DA VERIFICAÇÃO:");
            System.out.println();
            System.out.print("A área de fogo é verídica? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if ("S".equals(resposta) || "SIM".equals(resposta)) {
                // 7a. Área é verídica - registrar ocorrência com risco informado
                int hectares = calcularHectaresPorRisco(nivelRisco);
                int tempoChegada = calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);

                Ocorrencia novaOcorrencia = new Ocorrencia(
                        proximoIdOcorrencia,
                        areaSelecionada,
                        hectares,
                        droneSelecionado,
                        usuario,
                        tempoChegada
                );

                droneSelecionado.setStatusDrone("Em Missão");
                ocorrencias.add(novaOcorrencia);

                System.out.println();
                System.out.println("🚨 DENÚNCIA CONFIRMADA! Registrando ocorrência...");
                System.out.println();
                novaOcorrencia.exibirRelatorio(estacaoAtual);

            } else if ("N".equals(resposta) || "NÃO".equals(resposta) || "NAO".equals(resposta)) {
                // 7b. Área não é verídica - perguntar hectares
                System.out.println();
                System.out.println("📝 Quantos hectares foram realmente afetados?");
                System.out.print("Digite 0 se a área está completamente segura (0-200 hectares): ");

                int hectaresReais = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                if (hectaresReais < 0 || hectaresReais > 200) {
                    System.out.println("❌ Valor inválido! Deve estar entre 0 e 200 hectares.");
                    return proximoIdOcorrencia;
                }

                Ocorrencia ocorrencia;

                if (hectaresReais == 0) {
                    // Área segura
                    ocorrencia = new Ocorrencia(proximoIdOcorrencia, areaSelecionada, droneSelecionado);
                    System.out.println();
                    System.out.println("✅ ÁREA CONFIRMADA COMO SEGURA!");
                } else {
                    // Ocorrência com hectares reais
                    int tempoChegada = calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);
                    ocorrencia = new Ocorrencia(
                            proximoIdOcorrencia,
                            areaSelecionada,
                            hectaresReais,
                            droneSelecionado,
                            usuario,
                            tempoChegada
                    );
                    System.out.println();
                    System.out.println("🚨 OCORRÊNCIA REGISTRADA COM DADOS CORRIGIDOS!");
                }

                droneSelecionado.setStatusDrone("Em Missão");
                ocorrencias.add(ocorrencia);

                System.out.println();
                ocorrencia.exibirRelatorio(estacaoAtual);

            } else {
                System.out.println("❌ Resposta inválida! Digite S para Sim ou N para Não.");
                return proximoIdOcorrencia;
            }

            return proximoIdOcorrencia + 1;

        } catch (Exception e) {
            System.out.println("❌ Erro durante o processamento da denúncia!");
            scanner.nextLine(); // Limpa o buffer em caso de erro
            return proximoIdOcorrencia;
        }
    }

    /**
     * Lista todas as ocorrências registradas
     * @param ocorrencias Lista de ocorrências
     * @param estacoes Lista de estações
     */
    public static void listarTodasOcorrencias(ArrayList<Ocorrencia> ocorrencias, ArrayList<EstacaoBombeiros> estacoes) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📋 TODAS AS OCORRÊNCIAS REGISTRADAS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        if (ocorrencias.isEmpty()) {
            System.out.println("📭 Nenhuma ocorrência registrada ainda.");
            System.out.println();
            return;
        }

        System.out.println("Total de registros: " + ocorrencias.size());
        System.out.println();

        for (Ocorrencia ocorrencia : ocorrencias) {
            EstacaoBombeiros estacaoResponsavel = buscarEstacaoPorId(ocorrencia.getAreaAfetada().getIdEstacaoResponsavel(), estacoes);
            if (estacaoResponsavel != null) {
                ocorrencia.exibirResumo(estacaoResponsavel);
            }
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();
    }

    /**
     * Obtém o drone específico de uma estação (agora apenas 1 por estação)
     * @param drones Lista de drones
     * @param idEstacao ID da estação
     * @return Drone da estação ou null
     */
    private static Drone obterDroneDaEstacao(ArrayList<Drone> drones, int idEstacao) {
        for (Drone drone : drones) {
            if (drone.getIdEstacaoBase() == idEstacao) {
                return drone;
            }
        }
        return null;
    }

    // Métodos auxiliares privados
    private static ArrayList<AreaFlorestal> obterAreasFlorestaisPorEstacao(ArrayList<AreaFlorestal> areasFlorestais, int idEstacao) {
        ArrayList<AreaFlorestal> areas = new ArrayList<>();
        for (AreaFlorestal area : areasFlorestais) {
            if (area.getIdEstacaoResponsavel() == idEstacao) {
                areas.add(area);
            }
        }
        return areas;
    }

    /**
     * Busca uma área florestal por ID na lista disponível
     * @param idArea ID da área
     * @param areasDisponiveis Lista de áreas disponíveis
     * @return AreaFlorestal encontrada ou null
     */
    private static AreaFlorestal buscarAreaPorId(int idArea, ArrayList<AreaFlorestal> areasDisponiveis) {
        for (AreaFlorestal area : areasDisponiveis) {
            if (area.getIdArea() == idArea) {
                return area;
            }
        }
        return null;
    }

    private static Sensor buscarSensorPorId(int idSensor, ArrayList<Sensor> sensores) {
        for (Sensor sensor : sensores) {
            if (sensor.getIdSensor() == idSensor) {
                return sensor;
            }
        }
        return null;
    }

    private static EstacaoBombeiros buscarEstacaoPorId(int idEstacao, ArrayList<EstacaoBombeiros> estacoes) {
        for (EstacaoBombeiros estacao : estacoes) {
            if (estacao.getIdEstacao() == idEstacao) {
                return estacao;
            }
        }
        return null;
    }

    /**
     * Calcula tempo de chegada baseado na distância e velocidade
     * @param distanciaKm Distância em km
     * @param velocidadeKmH Velocidade em km/h
     * @return Tempo em minutos
     */
    private static int calcularTempoChegada(int distanciaKm, int velocidadeKmH) {
        double tempoHoras = (double) distanciaKm / velocidadeKmH;
        return (int) Math.ceil(tempoHoras * 60); // Converter para minutos e arredondar para cima
    }
}