package fgn.modelo;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe responsável pelo monitoramento e atendimento de alertas de incêndio
 * Gerencia operações específicas para casos ativos de incêndio
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class AlertasIncendio {

    /**
     * Exibe o menu principal de monitoramento de alertas
     * @param ocorrencias Lista de ocorrências
     * @param estacaoAtual Estação atual logada
     * @param scanner Scanner para entrada do usuário
     */
    public static void exibirMenuAlertas(ArrayList<Ocorrencia> ocorrencias, EstacaoBombeiros estacaoAtual, Scanner scanner) {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🔥 MONITORAR ALERTAS DE INCÊNDIO - " + estacaoAtual.getCidade().toUpperCase());
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1.  📋 Listar Todos os Casos de Incêndio da Cidade");
            System.out.println("2.  🎯 Atender Ocorrência Específica");
            System.out.println("3.  🚒 Atender Ocorrência no Local");
            System.out.println("4.  🔙 Voltar");
            System.out.print("👉 Digite sua opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        System.out.println();
                        listarCasosIncendio(ocorrencias, estacaoAtual);
                        break;

                    case 2:
                        System.out.println();
                        atenderOcorrencia(ocorrencias, estacaoAtual, scanner);
                        break;

                    case 3:
                        System.out.println();
                        atenderOcorrenciaNoLocal(ocorrencias, estacaoAtual, scanner);
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
     * Lista apenas os casos de incêndio ativos da cidade (não exibe áreas seguras)
     * @param ocorrencias Lista de ocorrências
     * @param estacaoAtual Estação atual logada
     */
    public static void listarCasosIncendio(ArrayList<Ocorrencia> ocorrencias, EstacaoBombeiros estacaoAtual) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🔥 CASOS DE INCÊNDIO ATIVOS - " + estacaoAtual.getCidade().toUpperCase());
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Filtrar apenas ocorrências de incêndio da estação atual (não áreas seguras)
        ArrayList<Ocorrencia> incendiosAtivos = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getAreaAfetada().getIdEstacaoResponsavel() == estacaoAtual.getIdEstacao() &&
                    !ocorrencia.getNivelRisco().equals("Seguro") &&
                    ocorrencia.getStatusOcorrencia().equals("Ativo")) {
                incendiosAtivos.add(ocorrencia);
            }
        }

        if (incendiosAtivos.isEmpty()) {
            System.out.println("✅ Nenhum caso de incêndio ativo em " + estacaoAtual.getCidade() + "!");
            System.out.println("🌿 Todas as áreas estão seguras no momento.");
            System.out.println();
            return;
        }

        System.out.println("🚨 Total de incêndios ativos: " + incendiosAtivos.size());
        System.out.println();

        for (Ocorrencia ocorrencia : incendiosAtivos) {
            exibirResumoIncendio(ocorrencia, estacaoAtual);
        }
    }

    /**
     * Exibe resumo específico para alertas de incêndio (formato compacto)
     * @param ocorrencia Ocorrência de incêndio
     * @param estacaoResponsavel Estação responsável
     */
    private static void exibirResumoIncendio(Ocorrencia ocorrencia, EstacaoBombeiros estacaoResponsavel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("🚨 #" + ocorrencia.getIdOcorrencia() + " - INCÊNDIO");
        System.out.println("    📍 " + ocorrencia.getAreaAfetada().getNomeArea());
        System.out.println("    🏢 " + estacaoResponsavel.getCidade() + ", " + estacaoResponsavel.getEstado());
        System.out.println("    ⚠️  " + ocorrencia.getNivelRisco() + " | 📊 " + ocorrencia.getStatusOcorrencia() + " | ⏰ " + ocorrencia.getDataHoraDeteccao().format(formatter));
        System.out.println("    🔥 " + ocorrencia.getHectaresAfetados() + " hectares afetados");
        System.out.println();
    }

    /**
     * Atende uma ocorrência específica, enviando reforços e resolvendo o incêndio
     * @param ocorrencias Lista de ocorrências
     * @param estacaoAtual Estação atual logada
     * @param scanner Scanner para entrada do usuário
     */
    public static void atenderOcorrencia(ArrayList<Ocorrencia> ocorrencias, EstacaoBombeiros estacaoAtual, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚒 ATENDER OCORRÊNCIA DE INCÊNDIO");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Filtrar apenas incêndios ativos da estação
        ArrayList<Ocorrencia> incendiosAtivos = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getAreaAfetada().getIdEstacaoResponsavel() == estacaoAtual.getIdEstacao() &&
                    !ocorrencia.getNivelRisco().equals("Seguro") &&
                    ocorrencia.getStatusOcorrencia().equals("Ativo")) {
                incendiosAtivos.add(ocorrencia);
            }
        }

        if (incendiosAtivos.isEmpty()) {
            System.out.println("✅ Nenhum incêndio ativo para atender em " + estacaoAtual.getCidade() + "!");
            System.out.println("🌿 Todas as áreas estão seguras no momento.");
            System.out.println();
            return;
        }

        System.out.println("🚨 INCÊNDIOS ATIVOS PARA ATENDIMENTO:");
        System.out.println();

        // Mostrar incêndios disponíveis para atendimento
        for (Ocorrencia ocorrencia : incendiosAtivos) {
            exibirResumoIncendio(ocorrencia, estacaoAtual);
        }

        System.out.print("🚒 Digite o ID da ocorrência que deseja atender: ");

        try {
            int idEscolhido = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            // Buscar a ocorrência pelo ID
            Ocorrencia ocorrenciaEscolhida = null;
            for (Ocorrencia ocorrencia : incendiosAtivos) {
                if (ocorrencia.getIdOcorrencia() == idEscolhido) {
                    ocorrenciaEscolhida = ocorrencia;
                    break;
                }
            }

            if (ocorrenciaEscolhida == null) {
                System.out.println("❌ ID inválido! Nenhuma ocorrência ativa encontrada com este ID.");
                System.out.println();
                return;
            }

            // Mostrar processo de atendimento
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🚒 DESPACHANDO REFORÇOS PARA OCORRÊNCIA #" + idEscolhido);
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("📍 Local: " + ocorrenciaEscolhida.getAreaAfetada().getNomeArea());
            System.out.println("🔥 Área Afetada: " + ocorrenciaEscolhida.getHectaresAfetados() + " hectares");
            System.out.println("⚠️  Nível de Risco: " + ocorrenciaEscolhida.getNivelRisco());
            System.out.println();
            System.out.println("🚨 Estamos enviando reforços para a área!");

            // Mostrar tempo estimado de chegada
            if (ocorrenciaEscolhida.getTempoChegadaMinutos() > 0) {
                System.out.println("⏱️  Tempo estimado de chegada: " + ocorrenciaEscolhida.getTempoChegadaMinutos() + " minutos");
            } else {
                System.out.println("⏱️  Tempo estimado de chegada: 15 minutos");
            }

            System.out.println("🚒 Equipes de combate a incêndio a caminho...");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de combate ao incêndio (3 segundos)
            System.out.println("🔥 Combate ao incêndio em andamento...");

            try {
                Thread.sleep(5000); // Espera 5 segundos
            } catch (InterruptedException e) {
                // Ignorar interrupção
            }

            // Mostrar sucesso
            System.out.println();
            System.out.println("✅ INCÊNDIO APAGADO COM SUCESSO!");
            System.out.println("🌿 Ocorrência  #" + idEscolhido + " agora está segura!");
            System.out.println("👨‍🚒 Equipes retornando à base...");
            System.out.println();

            // Converter ocorrência para "Área Segura"
            ocorrenciaEscolhida.marcarComoSegura();

            // Atualizar arquivo automaticamente
            ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
            Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

            System.out.println("💾 Registro atualizado automaticamente!");
            System.out.println();

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida! Digite apenas números.");
            scanner.nextLine(); // Limpa o buffer em caso de erro
            System.out.println();
        }
    }

    /**
     * Atende todas as ocorrências no local - resolve todos os incêndios ativos de uma vez
     * @param ocorrencias Lista de ocorrências
     * @param estacaoAtual Estação atual logada
     * @param scanner Scanner para entrada do usuário
     */
    public static void atenderOcorrenciaNoLocal(ArrayList<Ocorrencia> ocorrencias, EstacaoBombeiros estacaoAtual, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚒 ATENDER OCORRÊNCIA NO LOCAL");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Filtrar apenas incêndios ativos da estação
        ArrayList<Ocorrencia> incendiosAtivos = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getAreaAfetada().getIdEstacaoResponsavel() == estacaoAtual.getIdEstacao() &&
                    !ocorrencia.getNivelRisco().equals("Seguro") &&
                    ocorrencia.getStatusOcorrencia().equals("Ativo")) {
                incendiosAtivos.add(ocorrencia);
            }
        }

        if (incendiosAtivos.isEmpty()) {
            System.out.println("✅ Nenhum incêndio ativo para atender em " + estacaoAtual.getCidade() + "!");
            System.out.println("🌿 Todas as áreas estão seguras no momento.");
            System.out.println();
            return;
        }

        // Mostrar casos de incêndio primeiro
        System.out.println("🚨 CASOS DE INCÊNDIO ATIVOS:");
        System.out.println();
        for (Ocorrencia ocorrencia : incendiosAtivos) {
            exibirResumoIncendio(ocorrencia, estacaoAtual);
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚒 SELECIONE UM LOCAL PARA ATENDIMENTO GERAL:");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Mostrar menu simples com IDs e locais
        for (Ocorrencia ocorrencia : incendiosAtivos) {
            System.out.println("#" + ocorrencia.getIdOcorrencia() + " - " + ocorrencia.getAreaAfetada().getNomeArea());
        }

        System.out.println();
        System.out.print("🚒 Digite o ID de qualquer área para atender TODOS os incêndios da cidade: ");

        try {
            int idEscolhido = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            // Verificar se o ID escolhido existe
            boolean idValido = false;
            for (Ocorrencia ocorrencia : incendiosAtivos) {
                if (ocorrencia.getIdOcorrencia() == idEscolhido) {
                    idValido = true;
                    break;
                }
            }

            if (!idValido) {
                System.out.println("❌ ID inválido! Nenhuma ocorrência ativa encontrada com este ID.");
                System.out.println();
                return;
            }

            // Mostrar processo de atendimento geral
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🚒 OPERAÇÃO ESPECIAL - ATENDIMENTO GERAL DE INCÊNDIOS");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🌆 Cidade: " + estacaoAtual.getCidade());
            System.out.println("🚨 Total de incêndios a serem atendidos: " + incendiosAtivos.size());
            System.out.println();
            System.out.println("📍 Locais em atendimento:");

            for (Ocorrencia ocorrencia : incendiosAtivos) {
                System.out.println("   • #" + ocorrencia.getIdOcorrencia() + " - " + ocorrencia.getAreaAfetada().getNomeArea() + " (" + ocorrencia.getHectaresAfetados() + " hectares)");
            }

            System.out.println();
            System.out.println("🚨 Despachando todas as equipes disponíveis!");
            System.out.println("🚒 Operação coordenada em andamento...");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de combate coordenado (3 segundos)
            System.out.println("🔥 Combate coordenado aos incêndios em andamento...");

            try {
                Thread.sleep(5000); // Espera 5 segundos
            } catch (InterruptedException e) {
                // Ignorar interrupção
            }

            // Mostrar sucesso
            System.out.println();
            System.out.println("✅ TODOS OS INCÊNDIOS APAGADOS COM SUCESSO!");
            System.out.println("🌿 Todas as " + incendiosAtivos.size() + " ocorrências agora estão seguras!");
            System.out.println("👨‍🚒 Todas as equipes retornando à base...");
            System.out.println();

            // Marcar TODAS as ocorrências como seguras
            for (Ocorrencia ocorrencia : incendiosAtivos) {
                ocorrencia.marcarComoSegura();
            }

            // Atualizar arquivo automaticamente
            ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
            Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

            System.out.println("💾 Todos os registros atualizados automaticamente!");
            System.out.println("🎉 Operação concluída com sucesso em " + estacaoAtual.getCidade() + "!");
            System.out.println();

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida! Digite apenas números.");
            scanner.nextLine(); // Limpa o buffer em caso de erro
            System.out.println();
        }
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
}