package fgn.modelo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe responsável pela lógica de negócio das ocorrências
 * Contém métodos para registrar, processar e gerenciar ocorrências
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Casos {

    /**
     * Registra uma nova ocorrência de incêndio
     * @param areasFlorestais Lista de áreas florestais
     * @param sensores Lista de sensores
     * @param drones Lista de drones
     * @param ocorrencias Lista de ocorrências
     * @param proximoIdOcorrencia Próximo ID disponível
     * @param estacaoAtual Estação logada
     * @param estacoes Lista de todas as estações
     * @param scanner Scanner para entrada do usuário
     * @return Novo ID de ocorrência
     */
    public static int registrarNovaOcorrencia(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Sensor> sensores, ArrayList<Drone> drones,
                                              ArrayList<Ocorrencia> ocorrencias, int proximoIdOcorrencia,
                                              EstacaoBombeiros estacaoAtual, ArrayList<EstacaoBombeiros> estacoes, Scanner scanner) {
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
            // Drone operando antes de escolher local
            System.out.println("🚁 DRONE SELECIONADO PARA MISSÃO:");
            droneSelecionado.exibirInformacoes();
            System.out.println("👀 INICIANDO VARREDURA AÉREA:");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            droneSelecionado.operar(); // Metodo da classe pai Equipamento!
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de varredura
            System.out.println("⏳ Processando dados da varredura...");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Mensagem personalizada do drone
            droneSelecionado.exibirAreaIdentificada();
            System.out.println();

            // Listar áreas florestais da estação atual
            ArrayList<AreaFlorestal> areasDisponiveis = obterAreasFlorestaisPorEstacao(areasFlorestais, estacaoAtual.getIdEstacao());

            if (areasDisponiveis.isEmpty()) {
                System.out.println("❌ Nenhuma área florestal cadastrada para esta estação.");
                return proximoIdOcorrencia;
            }

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

            // Sensor operando após seleção
            System.out.println();
            System.out.println("🔬 INICIANDO ANÁLISE DO SENSOR:");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println();
            sensorSelecionado.operar(); // Metodo da classe pai Equipamento!
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Calcular tempo de chegada (velocidade média 75 km/h)
            int tempoChegada = Ocorrencia.calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);

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

            ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
            Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

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
     * @param estacoes Lista de todas as estações
     * @param scanner Scanner para entrada do usuário
     * @return Novo ID de ocorrência
     */
    public static int registrarAreaSegura(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Drone> drones,
                                          ArrayList<Ocorrencia> ocorrencias, int proximoIdOcorrencia,
                                          EstacaoBombeiros estacaoAtual, ArrayList<EstacaoBombeiros> estacoes, Scanner scanner) {
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
            // 🚀 Drone operando para verificação
            System.out.println("🚁 DRONE SELECIONADO PARA VERIFICAÇÃO:");
            droneSelecionado.exibirInformacoes();
            System.out.println("👀 INICIANDO VERIFICAÇÃO DE SEGURANÇA:");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            droneSelecionado.operar(); // Metodo da classe pai Equipamento!
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de verificação
            System.out.println("⏳ Analisando condições de segurança...");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("✅ Verificação concluída!");
            System.out.println();

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

            ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
            Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

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
     * @param estacoes Lista de todas as estações
     * @param scanner Scanner para entrada do usuário
     * @return Novo ID de ocorrência
     */
    public static int relatarDenunciaUsuario(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Drone> drones,
                                             ArrayList<Ocorrencia> ocorrencias, int proximoIdOcorrencia,
                                             EstacaoBombeiros estacaoAtual, ArrayList<EstacaoBombeiros> estacoes, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📱 RELATAR DENÚNCIA DE USUÁRIO");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        try {
            // 1. Coletar dados do usuário com validação
            System.out.println("👤 DADOS DO DENUNCIANTE:");
            System.out.println();

            String nome;
            boolean nomeValido = false;

            do {
                System.out.print("Nome completo: ");
                nome = scanner.nextLine();

                if (!Usuario.validarNome(nome)) {
                    System.out.println("❌ Nome inválido! Use apenas letras e espaços, começando por uma letra.");
                    System.out.println("Retornando ao menu anterior...");
                    return proximoIdOcorrencia;
                }
                nomeValido = true;
            } while (!nomeValido);

            // Formatar nome para exibição
            nome = Usuario.formatarNome(nome);

            long cpf;
            String dataNascimento;

            try {
                cpf = Usuario.solicitarCPF(scanner);
                dataNascimento = Usuario.solicitarDataNascimento(scanner);
            } catch (Exception e) {
                System.out.println("❌ Dados pessoais inválidos! Retornando ao menu anterior...");
                return proximoIdOcorrencia;
            }

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

            AreaFlorestal areaSelecionada = null;
            boolean areaValida = false;

            // Loop até escolher área válida
            while (!areaValida) {
                System.out.println("📍 Escolha o local onde ocorreu o possível incêndio:");
                System.out.println();

                for (AreaFlorestal area : areasDisponiveis) {
                    area.exibirInformacoes();
                }

                System.out.print("👉 Escolha uma das opções: ");

                try {
                    int opcaoArea = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    areaSelecionada = buscarAreaPorId(opcaoArea, areasDisponiveis);

                    if (areaSelecionada != null) {
                        areaValida = true;
                    } else {
                        System.out.println("❌ Opção inválida! Tente novamente.");
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("❌ Digite apenas números! Tente novamente.");
                    scanner.nextLine(); // Limpa o buffer
                    System.out.println();
                }
            }

            System.out.println();

            // 3. Perguntar sobre nível de risco percebido pelo usuário
            int nivelRisco = 0;
            boolean riscoValido = false;

            // Loop até escolher nível de risco válido
            while (!riscoValido) {
                System.out.println("⚠️  Qual o nível de risco que você percebe?");
                System.out.println("1. Investigação (Pequeno foco, pouca fumaça)");
                System.out.println("2. Alerta Ativo (Fogo visível, fumaça densa)");
                System.out.println("3. Emergência (Fogo intenso, área extensa)");
                System.out.print("👉 Escolha o nível de risco: ");

                try {
                    nivelRisco = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    if (nivelRisco >= 1 && nivelRisco <= 3) {
                        riscoValido = true;
                    } else {
                        System.out.println("❌ Opção inválida! Escolha entre 1, 2 ou 3.");
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("❌ Digite apenas números! Tente novamente.");
                    scanner.nextLine(); // Limpa o buffer
                    System.out.println();
                }
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

            droneSelecionado.operar(); // Metodo da classe pai Equipamento!
            System.out.println("🚁 " + droneSelecionado.getModeloDrone() + " (#" + droneSelecionado.getIdDrone() + ") está a caminho!");
            System.out.println("📍 Destino: " + areaSelecionada.getNomeArea());
            System.out.println("⏱️  Aguarde enquanto realizamos a verificação...");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de verificação
            System.out.println("🔍 Verificação em andamento...");
            try {
                Thread.sleep(3000); // Simula 3 segundos de verificação
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
                int tempoChegada = Ocorrencia.calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);

                Ocorrencia novaOcorrencia = new Ocorrencia(
                        proximoIdOcorrencia,
                        areaSelecionada,
                        hectares,
                        droneSelecionado,
                        usuario,
                        tempoChegada
                );

                ocorrencias.add(novaOcorrencia);

                System.out.println();
                System.out.println("🚨 DENÚNCIA CONFIRMADA! Registrando ocorrência...");
                System.out.println();
                novaOcorrencia.exibirRelatorio(estacaoAtual);

                ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
                Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

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
                    ocorrencia = new Ocorrencia(proximoIdOcorrencia, areaSelecionada, droneSelecionado, usuario);
                    System.out.println();
                    System.out.println("✅ ÁREA CONFIRMADA COMO SEGURA!");
                } else {
                    // Ocorrência com hectares reais
                    int tempoChegada = Ocorrencia.calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);
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

                ocorrencias.add(ocorrencia);

                System.out.println();
                ocorrencia.exibirRelatorio(estacaoAtual);

                ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
                Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

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
     * Lista todas as ocorrências de uma estação específica e salva no arquivo da cidade
     * @param ocorrencias Lista de ocorrências
     * @param estacoes Lista de estações
     * @param estacaoAtual Estação logada atualmente
     */
    public static void listarOcorrenciasDaEstacao(ArrayList<Ocorrencia> ocorrencias, ArrayList<EstacaoBombeiros> estacoes, EstacaoBombeiros estacaoAtual) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📋 OCORRÊNCIAS DE " + estacaoAtual.getCidade().toUpperCase());
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Filtrar ocorrências apenas da estação atual
        ArrayList<Ocorrencia> ocorrenciasDaEstacao = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getAreaAfetada().getIdEstacaoResponsavel() == estacaoAtual.getIdEstacao()) {
                ocorrenciasDaEstacao.add(ocorrencia);
            }
        }

        if (ocorrenciasDaEstacao.isEmpty()) {
            System.out.println("📭 Nenhuma ocorrência registrada ainda em " + estacaoAtual.getCidade() + ".");
            System.out.println();
            return;
        }

        System.out.println("Total de registros em " + estacaoAtual.getCidade() + ": " + ocorrenciasDaEstacao.size());
        System.out.println();

        for (Ocorrencia ocorrencia : ocorrenciasDaEstacao) {
            ocorrencia.exibirResumo(estacaoAtual);
        }

        // Salvar arquivo atualizado
        Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);
        System.out.println();
        System.out.println("💾 Histórico atualizado em 'historico_" + estacaoAtual.getCidade().toLowerCase().replace(" ", "_") + ".txt'");
        System.out.println();

    }

    /**
     * Calcula hectares baseado no nível de risco percebido
     * @param nivelRisco Nível de risco (1-3)
     * @return Hectares estimados
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
     * Obtém o drone específico de uma estação
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

    /**
     * Filtra ocorrências de uma estação específica
     * @param ocorrencias Lista completa de ocorrências
     * @param idEstacao ID da estação a filtrar
     * @return Lista filtrada apenas da estação
     */
    private static ArrayList<Ocorrencia> filtrarOcorrenciasPorEstacao(ArrayList<Ocorrencia> ocorrencias, int idEstacao) {
        ArrayList<Ocorrencia> ocorrenciasFiltradas = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getAreaAfetada().getIdEstacaoResponsavel() == idEstacao) {
                ocorrenciasFiltradas.add(ocorrencia);
            }
        }
        return ocorrenciasFiltradas;
    }

    /**
     * Obtém áreas florestais de uma estação específica
     * @param areasFlorestais Lista completa de áreas
     * @param idEstacao ID da estação
     * @return Lista de áreas da estação
     */
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

    /**
     * Busca um sensor por ID
     * @param idSensor ID do sensor
     * @param sensores Lista de sensores
     * @return Sensor encontrado ou null
     */
    private static Sensor buscarSensorPorId(int idSensor, ArrayList<Sensor> sensores) {
        for (Sensor sensor : sensores) {
            if (sensor.getIdSensor() == idSensor) {
                return sensor;
            }
        }
        return null;
    }

    /**
     * Busca uma estação por ID
     * @param idEstacao ID da estação
     * @param estacoes Lista de estações
     * @return EstacaoBombeiros encontrada ou null
     */
    private static EstacaoBombeiros buscarEstacaoPorId(int idEstacao, ArrayList<EstacaoBombeiros> estacoes) {
        for (EstacaoBombeiros estacao : estacoes) {
            if (estacao.getIdEstacao() == idEstacao) {
                return estacao;
            }
        }
        return null;
    }
}