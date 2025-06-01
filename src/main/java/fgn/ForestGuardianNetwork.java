package fgn; // Forest Guardian Network

import fgn.modelo.EstacaoBombeiros;

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
    private static Scanner scanner;
    private static EstacaoBombeiros estacaoAtual;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        inicializarEstacoes();

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
     * @param id ID da estação a ser buscada
     * @return EstacaoBombeiros encontrada ou null se não existir
     */
    private static EstacaoBombeiros buscarEstacaoPorId(int id) {
        for (EstacaoBombeiros estacao : estacoes) {
            if (estacao.getId() == id) {
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
                        System.out.println("🚧 Módulo de Gerenciamento de Drones em desenvolvimento...");
                        System.out.println();
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