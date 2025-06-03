package fgn.modelo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <p>
 * Esta classe é responsável por organizar e gerenciar todas as operações de varredura aérea
 * realizadas pelos drones do sistema Forest Guardian Network. Atua como um coordenador
 * central para funcionalidades relacionadas ao gerenciamento de drones e execução de
 * missões de monitoramento florestal.
 * </p>
 * <p>
 * A classe delega operações específicas para outras classes especializadas (como Casos)
 * mantendo uma interface organizada e simplificada para o usuário. Gerencia o fluxo
 * principal de varreduras, desde a seleção de áreas até o registro de resultados.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class Varredura {

    /**
     * Gerencia todas as operações relacionadas aos drones da estação atual.
     * Oferece menu completo para varredura de áreas, processamento de denúncias
     * de usuários e listagem de ocorrências. Coordena o fluxo principal das
     * atividades de monitoramento aéreo.
     *
     * @param areasFlorestais lista de áreas florestais disponíveis para varredura
     * @param sensores lista de sensores disponíveis para equipar os drones
     * @param drones lista de drones disponíveis na estação
     * @param ocorrencias lista de ocorrências registradas no sistema
     * @param proximoIdOcorrencia próximo ID disponível para novas ocorrências
     * @param estacaoAtual estação de bombeiros atualmente logada
     * @param estacoes lista completa de estações para referência
     * @param scanner objeto Scanner para captura de entrada do usuário
     * @return ID atualizado para próxima ocorrência após operações realizadas
     */
    public static int gerenciarDrones(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Sensor> sensores,
                                      ArrayList<Drone> drones, ArrayList<Ocorrencia> ocorrencias,
                                      int proximoIdOcorrencia, EstacaoBombeiros estacaoAtual,
                                      ArrayList<EstacaoBombeiros> estacoes, Scanner scanner) {
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
                        proximoIdOcorrencia = iniciarVarredura(areasFlorestais, sensores, drones,
                                ocorrencias, proximoIdOcorrencia,
                                estacaoAtual, estacoes, scanner);
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

        return proximoIdOcorrencia;
    }

    /**
     * Inicia processo especializado de varredura de área florestal por drones.
     * Oferece opções para registrar novas ocorrências de incêndio ou confirmar
     * áreas seguras após varredura. Delega operações específicas para a classe Casos.
     *
     * @param areasFlorestais lista de áreas florestais disponíveis para varredura
     * @param sensores lista de sensores disponíveis para detecção
     * @param drones lista de drones disponíveis para a missão
     * @param ocorrencias lista de ocorrências registradas no sistema
     * @param proximoIdOcorrencia próximo ID disponível para novas ocorrências
     * @param estacaoAtual estação de bombeiros executando a varredura
     * @param estacoes lista completa de estações para referência
     * @param scanner objeto Scanner para captura de entrada do usuário
     * @return ID atualizado para próxima ocorrência após varredura realizada
     */
    public static int iniciarVarredura(ArrayList<AreaFlorestal> areasFlorestais, ArrayList<Sensor> sensores,
                                       ArrayList<Drone> drones, ArrayList<Ocorrencia> ocorrencias,
                                       int proximoIdOcorrencia, EstacaoBombeiros estacaoAtual,
                                       ArrayList<EstacaoBombeiros> estacoes, Scanner scanner) {
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

        return proximoIdOcorrencia;
    }
}
