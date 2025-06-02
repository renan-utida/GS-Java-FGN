package fgn; // Forest Guardian Network

import fgn.modelo.*;

import java.util.ArrayList;
import java.util.Scanner;

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
        areasFlorestais.add(new AreaFlorestal(234561, "Área de Proteção Ambiental (APA) Tanquã", "Região de várzea com vegetação nativa", 30, 1232));
        areasFlorestais.add(new AreaFlorestal(234562, "Serra de São Pedro", "Área de mata atlântica", 35, 1232));
        areasFlorestais.add(new AreaFlorestal(234563, "Mata do Horto Florestal", "Área de pesquisa e conservação", 10, 1232));

        // Áreas próximas a Limeira (1233)
        areasFlorestais.add(new AreaFlorestal(345671, "Parque Ecológico de Limeira", "Área de educação ambiental", 8, 1233));
        areasFlorestais.add(new AreaFlorestal(345672, "Floresta Estadual de Iracemápolis", "Unidade de conservação", 20, 1233));
        areasFlorestais.add(new AreaFlorestal(345673, "Mata do Horto Florestal de Cordeirópolis", "Área de reflorestamento", 25, 1233));

        // Áreas próximas a Mogi Mirim (1234)
        areasFlorestais.add(new AreaFlorestal(456781, "Área de Preservação Permanente do Rio Mogi Guaçu", "Zona ripária", 10, 1234));
        areasFlorestais.add(new AreaFlorestal(456782, "Mata do Horto Florestal de Itapira", "Área de reflorestamento", 30, 1234));
        areasFlorestais.add(new AreaFlorestal(456783, "Reserva Biológica de Estiva Gerbi", "Área de proteção integral", 35, 1234));
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
     * Inicializa os drones do sistema - 1 drone por estação
     */
    private static void inicializarDrones() {
        drones = new ArrayList<>();
        ocorrencias = new ArrayList<>();

        // 1 drone por estação - mais simples e eficiente
        drones.add(new Drone(101, "FGN-Hawk Alpha", 1231));
        drones.add(new Drone(103, "FGN-Eagle Alpha", 1232));
        drones.add(new Drone(105, "FGN-Falcon Alpha", 1233));
        drones.add(new Drone(107, "FGN-Condor Alpha", 1234));
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

                    // Carregar histórico da cidade automaticamente
                    System.out.println("🔄 Carregando histórico da estação...");
                    Arquivo.carregarHistoricoDaCidade(estacaoAtual, ocorrencias, estacoes, areasFlorestais, drones);

                    // Ajustar próximo ID baseado nas ocorrências carregadas
                    if (!ocorrencias.isEmpty()) {
                        int maiorId = 0;
                        for (Ocorrencia ocorrencia : ocorrencias) {
                            if (ocorrencia.getIdOcorrencia() > maiorId) {
                                maiorId = ocorrencia.getIdOcorrencia();
                            }
                        }
                        proximoIdOcorrencia = maiorId + 1;
                    }
                    loginValido = true;
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
            System.out.println();
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
                        proximoIdOcorrencia = Varredura.gerenciarDrones(
                                areasFlorestais, sensores, drones, ocorrencias,
                                proximoIdOcorrencia, estacaoAtual, estacoes, scanner
                        );
                        break;

                    case 2:
                        System.out.println();
                        AlertasIncendio.exibirMenuAlertas(ocorrencias, estacaoAtual, scanner);
                        System.out.println();
                        break;

                    case 3:
                        System.out.println();
                        RelatoriosEstatisticas.exibirMenuRelatorios(ocorrencias, estacoes,
                                areasFlorestais, estacaoAtual, scanner);
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
                        proximoIdOcorrencia = Casos.relatarDenunciaUsuario(
                                areasFlorestais, drones, ocorrencias,
                                proximoIdOcorrencia, estacaoAtual, estacoes, scanner
                        );
                        System.out.println();
                        break;

                    case 3:
                        System.out.println();
                        Casos.listarOcorrenciasDaEstacao(ocorrencias, estacoes, estacaoAtual);
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
                        proximoIdOcorrencia = Casos.registrarNovaOcorrencia(
                                areasFlorestais, sensores, drones, ocorrencias,
                                proximoIdOcorrencia, estacaoAtual, estacoes, scanner
                        );
                        break;

                    case 2:
                        System.out.println();
                        proximoIdOcorrencia = Casos.registrarAreaSegura(
                                areasFlorestais, drones, ocorrencias,
                                proximoIdOcorrencia, estacaoAtual, estacoes, scanner
                        );
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