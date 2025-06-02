package fgn.modelo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe responsável pelas operações de varredura aérea com drones
 * Organiza e gerencia todas as funcionalidades relacionadas a drones e varreduras
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Varredura {

    /**
     * Gerencia as operações relacionadas aos drones
     * @param areasFlorestais Lista de áreas florestais
     * @param sensores Lista de sensores
     * @param drones Lista de drones
     * @param ocorrencias Lista de ocorrências
     * @param proximoIdOcorrencia Próximo ID disponível
     * @param estacaoAtual Estação logada
     * @param estacoes Lista de todas as estações
     * @param scanner Scanner para entrada do usuário
     * @return Novo próximo ID de ocorrência
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
     * Inicia o processo de varredura de área florestal
     * @param areasFlorestais Lista de áreas florestais
     * @param sensores Lista de sensores
     * @param drones Lista de drones
     * @param ocorrencias Lista de ocorrências
     * @param proximoIdOcorrencia Próximo ID disponível
     * @param estacaoAtual Estação logada
     * @param estacoes Lista de todas as estações
     * @param scanner Scanner para entrada do usuário
     * @return Novo próximo ID de ocorrência
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
