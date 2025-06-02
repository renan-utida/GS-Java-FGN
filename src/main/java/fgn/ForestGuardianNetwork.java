package fgn; // Forest Guardian Network

import fgn.modelo.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Classe principal do Sistema Forest Guardian Network
 * Responsável pela interface com o usuário e fluxo principal
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class ForestGuardianNetwork {
    private static ArrayList<EstacaoBombeiros> estacoes;
    private static ArrayList<AreaFlorestal> areasFlorestais;
    private static ArrayList<Sensor> sensores;
    private static ArrayList<Ocorrencia> ocorrencias;
    private static ArrayList<Drone> drones;
    private static Scanner scanner;
    private static EstacaoBombeiros estacaoAtual;
    private static int proximoIdOcorrencia = 1;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        inicializarEstacoes();
        inicializarAreasFlorestais();
        inicializarSensores();
        inicializarDrones();

        exibirTelaInicial();
        exibirEstacoesDisponiveis();
        realizarLogin();
        exibirMenuPrincipal();

        scanner.close();
    }

    /**
     * Inicializa as estações de bombeiros do sistema
     */
    private static void inicializarEstacoes() {
        estacoes = new ArrayList<>();

        estacoes.add(new EstacaoBombeiros(
                1231,
                "1º Batalhão de Bombeiros de Campinas",
                "Av. Francisco Glicério, 935",
                "Campinas",
                "SP",
                "Cap. Carlos Eduardo Silva",
                10245
        ));

        estacoes.add(new EstacaoBombeiros(
                1232,
                "Corpo de Bombeiros de Piracicaba",
                "Av. Independência, 277",
                "Piracicaba",
                "SP",
                "Cap. Ana Paula Santos",
                10367
        ));

        estacoes.add(new EstacaoBombeiros(
                1233,
                "3º Subgrupamento de Bombeiros Limeira",
                "Av. Major José Levy Sobrinho, 485",
                "Limeira",
                "SP",
                "Cap. Roberto Mendes",
                10582
        ));

        estacoes.add(new EstacaoBombeiros(
                1234,
                "6º Batalhão de Bombeiros de Mogi Mirim",
                "Av. Dr. Cândido Rodrigues, 180",
                "Mogi Mirim",
                "SP",
                "Cap. Fernanda Lima",
                10734
        ));
    }

    /**
     * Inicializa as áreas florestais próximas a cada estação
     */
    private static void inicializarAreasFlorestais() {
        areasFlorestais = new ArrayList<>();

        // Áreas próximas a Campinas (1231)
        areasFlorestais.add(new AreaFlorestal(123451, "Floresta Estadual Serra D'Água", "Campinas - Unidade de conservação", 10, 1231));
        areasFlorestais.add(new AreaFlorestal(123452, "Estação Ecológica de Valinhos", "Valinhos - Estação ecológica", 20, 1231));
        areasFlorestais.add(new AreaFlorestal(123453, "Serra das Cabras", "Campinas/Morungaba - APA com 1.078m altitude", 35, 1231));

        // Áreas próximas a Piracicaba (1232)
        areasFlorestais.add(new AreaFlorestal(234564, "Área de Proteção Ambiental (APA) Tanquã", "Região de várzea com vegetação nativa", 30, 1232));
        areasFlorestais.add(new AreaFlorestal(234565, "Serra de São Pedro", "Área de mata atlântica", 35, 1232));
        areasFlorestais.add(new AreaFlorestal(234566, "Mata do Horto Florestal", "Área de pesquisa e conservação", 10, 1232));

        // Áreas próximas a Limeira (1233)
        areasFlorestais.add(new AreaFlorestal(234567, "Parque Ecológico de Limeira", "Área de educação ambiental", 8, 1233));
        areasFlorestais.add(new AreaFlorestal(234568, "Floresta Estadual de Iracemápolis", "Unidade de conservação", 20, 1233));
        areasFlorestais.add(new AreaFlorestal(234569, "Mata do Horto Florestal de Cordeirópolis", "Área de reflorestamento", 25, 1233));

        // Áreas próximas a Mogi Mirim (1234)
        areasFlorestais.add(new AreaFlorestal(345670, "Área de Preservação Permanente do Rio Mogi Guaçu", "Zona ripária", 10, 1234));
        areasFlorestais.add(new AreaFlorestal(345671, "Mata do Horto Florestal de Itapira", "Área de reflorestamento", 30, 1234));
        areasFlorestais.add(new AreaFlorestal(345672, "Reserva Biológica de Estiva Gerbi", "Área de proteção integral", 35, 1234));
    }

    /**
     * Inicializa os sensores disponíveis no sistema
     */
    private static void inicializarSensores() {
        sensores = new ArrayList<>();

        sensores.add(new Sensor(10, "Sensor Térmico FGN-T01", "Térmico"));
        sensores.add(new Sensor(20, "Sensor de Fumaça FGN-F02", "Fumaça"));
        sensores.add(new Sensor(30, "Sensor Químico FGN-Q03", "Químico"));
    }

    /**
     * Inicializa os drones do sistema
     */
    private static void inicializarDrones() {
        drones = new ArrayList<>();
        ocorrencias = new ArrayList<>();

        // 2 drones por estação
        drones.add(new Drone(101, "FGN-Hawk Alpha", 1231));
        drones.add(new Drone(102, "FGN-Hawk Beta", 1231));

        drones.add(new Drone(103, "FGN-Eagle Alpha", 1232));
        drones.add(new Drone(104, "FGN-Eagle Beta", 1232));

        drones.add(new Drone(105, "FGN-Falcon Alpha", 1233));
        drones.add(new Drone(106, "FGN-Falcon Beta", 1233));

        drones.add(new Drone(107, "FGN-Condor Alpha", 1234));
        drones.add(new Drone(108, "FGN-Condor Beta", 1234));
    }

    /**
     * Exibe a tela inicial do sistema com logo e título
     */
    private static void exibirTelaInicial() {
        System.out.println("🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲");
        System.out.println("                     🚁 FOREST GUARDIAN NETWORK 🚁                         ");
        System.out.println(" Sistema Inteligente de Prevenção e Combate a Queimadas Florestais 🔥❌   ");
        System.out.println("🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲");
        System.out.println();
    }

    /**
     * Lista todas as estações de bombeiros disponíveis
     */
    private static void exibirEstacoesDisponiveis() {
        System.out.println("📍 ESTAÇÕES DE BOMBEIROS CADASTRADAS:");
        System.out.println();

        for (EstacaoBombeiros estacao : estacoes) {
            estacao.exibirInformacoes();
        }
    }

    /**
     * Realiza o processo de login do usuário
     * Valida se o ID da estação informado existe
     */
    private static void realizarLogin() {
        boolean loginValido = false;

        while (!loginValido) {
            System.out.print("🔐 Digite o ID da estação que deseja acessar: ");

            try {
                int idEscolhido = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                estacaoAtual = buscarEstacaoPorId(idEscolhido);

                if (estacaoAtual != null) {
                    loginValido = true;
                    System.out.println();
                    System.out.println("✅ Login realizado com sucesso!");
                    System.out.println("🏢 Bem-vindo à " + estacaoAtual.getNomeComandante() + "!");
                    System.out.println("📍 " + estacaoAtual.getNomeEstacao());
                    System.out.println();
                } else {
                    System.out.println("❌ ID inválido! Por favor, escolha um ID da lista acima.");
                    System.out.println();
                }

            } catch (Exception e) {
                System.out.println("❌ Entrada inválida! Digite apenas números.");
                scanner.nextLine(); // Limpa o buffer em caso de erro
                System.out.println();
            }
        }
    }

    /**
     * Busca uma estação pelo ID informado
     * @param idEstacao ID da estação a ser buscada
     * @return EstacaoBombeiros encontrada ou null se não existir
     */
    private static EstacaoBombeiros buscarEstacaoPorId(int idEstacao) {
        for (EstacaoBombeiros estacao : estacoes) {
            if (estacao.getIdEstacao() == idEstacao) {
                return estacao;
            }
        }
        return null;
    }

    /**
     * Exibe o menu principal do sistema
     */
    private static void exibirMenuPrincipal() {
        boolean sistemaAtivo = true;

        while (sistemaAtivo) {
            System.out.println("🎛️  MENU PRINCIPAL - " + estacaoAtual.getNomeEstacao());
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1. 🚁 Gerenciar Drones");
            System.out.println("2. 🔥 Monitorar Alertas de Incêndio");
            System.out.println("3. 📊 Relatórios e Estatísticas");
            System.out.println("4. 🚪 Sair do Sistema");
            System.out.print("👉 Digite sua opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        System.out.println();
                        gerenciarDrones();
                        break;

                    case 2:
                        System.out.println();
                        System.out.println("🚧 Módulo de Monitoramento de Alertas em desenvolvimento...");
                        System.out.println();
                        break;

                    case 3:
                        System.out.println();
                        exibirRelatorioEstacoes();
                        System.out.println();
                        break;

                    case 4:
                        sistemaAtivo = false;
                        exibirMensagemSaida();
                        break;

                    default:
                        System.out.println();
                        System.out.println("❌ Opção inválida! Por favor, escolha uma opção de 1 a 4.");
                        System.out.println();
                        break;
                }

            } catch (Exception e) {
                System.out.println();
                System.out.println("❌ Entrada inválida! Digite apenas números.");
                scanner.nextLine(); // Limpa o buffer em caso de erro
                System.out.println();
            }
        }
    }

    /**
     * Gerencia as operações relacionadas aos drones
     */
    private static void gerenciarDrones() {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🚁 GERENCIAR DRONES - " + estacaoAtual.getNomeComandante());
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1.  🔍 Iniciar Varredura de Área Florestal");
            System.out.println("2.  📱 Relatar Denúncia de Usuário");
            System.out.println("3.  📋 Listar Todas as Ocorrências");
            System.out.println("4.  🔙 Voltar");
            System.out.print("👉 Digite sua opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        System.out.println();
                        iniciarVarredura();
                        break;

                    case 2:
                        System.out.println();
                        System.out.println("🚧 Módulo de Denúncia de Usuário em desenvolvimento...");
                        System.out.println();
                        break;

                    case 3:
                        System.out.println();
                        System.out.println("🚧 Módulo de Listagem de Ocorrências em desenvolvimento...");
                        System.out.println();
                        break;

                    case 4:
                        voltarMenu = true;
                        break;

                    default:
                        System.out.println();
                        System.out.println("❌ Opção inválida! Por favor, escolha uma opção de 1 a 4.");
                        System.out.println();
                        break;
                }

            } catch (Exception e) {
                System.out.println();
                System.out.println("❌ Entrada inválida! Digite apenas números.");
                scanner.nextLine(); // Limpa o buffer em caso de erro
                System.out.println();
            }
        }
    }

    /**
     * Inicia o processo de varredura de área florestal
     */
    private static void iniciarVarredura() {
        boolean voltarVarredura = false;

        while (!voltarVarredura) {
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🔍 VARREDURA DE ÁREA FLORESTAL");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1.  🚨 Registrar Nova Ocorrência");
            System.out.println("2.  ✅ Registrar Área Segura");
            System.out.println("3.  🔙 Voltar");
            System.out.print("👉 Digite sua opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        System.out.println();
                        registrarNovaOcorrencia();
                        break;

                    case 2:
                        System.out.println();
                        System.out.println("🚧 Em desenvolvimento...");
                        System.out.println();
                        break;

                    case 3:
                        voltarVarredura = true;
                        break;

                    default:
                        System.out.println();
                        System.out.println("❌ Opção inválida! Por favor, escolha uma opção de 1 a 3.");
                        System.out.println();
                        break;
                }

            } catch (Exception e) {
                System.out.println();
                System.out.println("❌ Entrada inválida! Digite apenas números.");
                scanner.nextLine(); // Limpa o buffer em caso de erro
                System.out.println();
            }
        }
    }

    /**
     * Registra uma nova ocorrência de incêndio
     */
    private static void registrarNovaOcorrencia() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚨 REGISTRAR NOVA OCORRÊNCIA DE INCÊNDIO");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Listar áreas florestais da estação atual
        ArrayList<AreaFlorestal> areasDisponiveis = obterAreasFlorestaisPorEstacao(estacaoAtual.getIdEstacao());

        if (areasDisponiveis.isEmpty()) {
            System.out.println("❌ Nenhuma área florestal cadastrada para esta estação.");
            return;
        }

        System.out.println("📍 Escolha o local onde teve o incêndio:");
        System.out.println();

        for (AreaFlorestal area : areasDisponiveis) {
            area.exibirInformacoes();
        }

        System.out.print("👉 Escolha uma das opções: ");

        try {
            int opcaoArea = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            AreaFlorestal areaSelecionada = buscarAreaPorId(opcaoArea, areasDisponiveis);

            if (areaSelecionada == null) {
                System.out.println("❌ Opção inválida!");
                System.out.println();
                return;
            }

            System.out.println();
            System.out.print("🔥 Quantos hectares estão sendo atingidos em média (1 - 200 hectares): ");

            int hectares = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (hectares < 1 || hectares > 200) {
                System.out.println("❌ Valor inválido! Deve estar entre 1 e 200 hectares.");
                System.out.println();
                return;
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

            Sensor sensorSelecionado = buscarSensorPorId(opcaoSensor);

            if (sensorSelecionado == null) {
                System.out.println("❌ Sensor inválido!");
                return;
            }

            // Calcular tempo de chegada (velocidade média 75 km/h)
            int tempoChegada = calcularTempoChegada(areaSelecionada.getDistanciaKm(), 75);

            // Criar nova ocorrência
            Ocorrencia novaOcorrencia = new Ocorrencia(
                    proximoIdOcorrencia++,
                    areaSelecionada,
                    hectares,
                    sensorSelecionado,
                    tempoChegada
            );

            ocorrencias.add(novaOcorrencia);

            System.out.println();
            novaOcorrencia.exibirRelatorio();

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida! Digite apenas números.");
            scanner.nextLine(); // Limpa o buffer em caso de erro
        }
    }

    /**
     * Obtém as áreas florestais de uma estação específica
     * @param idEstacao ID da estação
     * @return Lista de áreas florestais da estação
     */
    private static ArrayList<AreaFlorestal> obterAreasFlorestaisPorEstacao(int idEstacao) {
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
     * @return Sensor encontrado ou null
     */
    private static Sensor buscarSensorPorId(int idSensor) {
        for (Sensor sensor : sensores) {
            if (sensor.getIdSensor() == idSensor) {
                return sensor;
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

    /**
     * Exibe relatório completo de todas as estações cadastradas
     */
    private static void exibirRelatorioEstacoes() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📊 RELATÓRIO - ESTAÇÕES DE BOMBEIROS CADASTRADAS");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        for (EstacaoBombeiros estacao : estacoes) {
            estacao.exibirRelatorio();
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📈 Total de estações ativas: " + estacoes.size());
        System.out.println("🌍 Área de cobertura: Interior de São Paulo");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();
    }

    /**
     * Exibe mensagem de agradecimento ao sair do sistema
     */
    private static void exibirMensagemSaida() {
        System.out.println();
        System.out.println("🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲");
        System.out.println("                          🙏 MUITO OBRIGADO! 🙏                           ");
        System.out.println("    Por utilizar o Forest Guardian Network em defesa das florestas!     ");
        System.out.println("                  💚 Juntos protegemos nossas florestas! 💚              ");
        System.out.println("🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲🌲");
        System.out.println();
        System.out.println("Sistema encerrado em " + estacaoAtual.getNomeEstacao() + " com sucesso! ✅");
        System.out.println("👋 Até a próxima, " + estacaoAtual.getNomeComandante() + "!");
    }
}