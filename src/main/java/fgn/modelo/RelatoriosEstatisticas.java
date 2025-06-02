package fgn.modelo;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe responsável pela geração de relatórios e estatísticas avançadas
 * Gerencia análises detalhadas do sistema de monitoramento
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class RelatoriosEstatisticas {

    /**
     * Exibe o menu principal de relatórios e estatísticas
     * @param ocorrencias Lista de ocorrências
     * @param estacoes Lista de estações
     * @param areasFlorestais Lista de áreas florestais
     * @param estacaoAtual Estação atual logada
     * @param scanner Scanner para entrada do usuário
     */
    public static void exibirMenuRelatorios(ArrayList<Ocorrencia> ocorrencias, ArrayList<EstacaoBombeiros> estacoes,
                                            ArrayList<AreaFlorestal> areasFlorestais, EstacaoBombeiros estacaoAtual, Scanner scanner) {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("📊 RELATÓRIOS E ESTATÍSTICAS - " + estacaoAtual.getCidade().toUpperCase());
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1.  🏢 Estações de Bombeiros Cadastradas");
            System.out.println("2.  📈 Verificar Dados da Estação Atual");
            System.out.println("3.  🗑️  Limpar Todas Ocorrências da Cidade");
            System.out.println("4.  🔙 Voltar");
            System.out.print("👉 Digite sua opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        System.out.println();
                        exibirEstacoesCadastradas(estacoes);
                        break;

                    case 2:
                        System.out.println();
                        verificarDadosDaEstacaoAtual(ocorrencias, estacaoAtual, areasFlorestais);
                        break;

                    case 3:
                        System.out.println();
                        limparOcorrenciasDaCidade(ocorrencias, estacaoAtual, scanner);
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
     * Exibe todas as estações de bombeiros cadastradas no sistema
     * @param estacoes Lista de estações
     */
    public static void exibirEstacoesCadastradas(ArrayList<EstacaoBombeiros> estacoes) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🏢 ESTAÇÕES DE BOMBEIROS CADASTRADAS NO SISTEMA");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        if (estacoes.isEmpty()) {
            System.out.println("❌ Nenhuma estação cadastrada no sistema.");
            System.out.println();
            return;
        }

        System.out.println("📊 Total de estações cadastradas: " + estacoes.size());
        System.out.println("🌍 Área de cobertura: Interior de São Paulo");
        System.out.println();

        for (EstacaoBombeiros estacao : estacoes) {
            System.out.println("🏢 #" + estacao.getIdEstacao() + " - " + estacao.getNomeEstacao());
            System.out.println("    🌆 Cidade: " + estacao.getCidade() + ", " + estacao.getEstado());
            System.out.println("    👮 Comandante: " + estacao.getNomeComandante() + ", ID Comandante: " + estacao.getIdComandante());
            System.out.println();
        }
    }

    /**
     * Verifica dados detalhados da estação atual (sem perguntar ID)
     * @param ocorrencias Lista de ocorrências
     * @param estacaoAtual Estação atual logada
     * @param areasFlorestais Lista de áreas florestais
     */
    public static void verificarDadosDaEstacaoAtual(ArrayList<Ocorrencia> ocorrencias, EstacaoBombeiros estacaoAtual, ArrayList<AreaFlorestal> areasFlorestais) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📈 DADOS DETALHADOS DA ESTAÇÃO ATUAL");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");

        // Filtrar ocorrências e áreas da estação atual
        ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
        ArrayList<AreaFlorestal> areasDaEstacao = obterAreasFlorestaisPorEstacao(areasFlorestais, estacaoAtual.getIdEstacao());

        exibirEstatisticasCompletasDaEstacao(estacaoAtual, ocorrenciasDaEstacao, areasDaEstacao);
    }

    /**
     * Exibe estatísticas completas da estação atual (com últimas atividades e avaliação)
     * @param estacao Estação atual
     * @param ocorrencias Lista de ocorrências da estação
     * @param areas Lista de áreas da estação
     */
    private static void exibirEstatisticasCompletasDaEstacao(EstacaoBombeiros estacao, ArrayList<Ocorrencia> ocorrencias, ArrayList<AreaFlorestal> areas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("📊 ESTATÍSTICAS COMPLETAS - " + estacao.getNomeEstacao().toUpperCase());
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🌆 Cidade: " + estacao.getCidade() + ", " + estacao.getEstado());
        System.out.println("👮 Comandante: " + estacao.getNomeComandante());
        System.out.println();

        // Estatísticas de áreas
        System.out.println("📍 ÁREAS FLORESTAIS SUPERVISIONADAS:");
        System.out.println("   • Total de áreas: " + areas.size());
        if (!areas.isEmpty()) {
            System.out.println("   • Principais áreas:");
            for (AreaFlorestal area : areas) {
                System.out.println("     - " + area.getNomeArea() + " (" + area.getDistanciaKm() + "km da estação)");
            }
        }
        System.out.println();

        // Estatísticas gerais
        System.out.println("📊 ESTATÍSTICAS GERAIS:");
        System.out.println("   • Total de casos: " + ocorrencias.size());

        if (ocorrencias.isEmpty()) {
            System.out.println("   • Nenhuma ocorrência registrada ainda.");
            System.out.println();
            System.out.println("🏆 AVALIAÇÃO DE DESEMPENHO:");
            System.out.println("   • Classificação: 🌿 PREVENTIVO");
            System.out.println("   • Estação em estado preventivo, sem incidentes.");
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            return;
        }

        // Contadores por status
        int ativos = 0, concluidos = 0;
        for (Ocorrencia ocorrencia : ocorrencias) {
            if ("Ativo".equals(ocorrencia.getStatusOcorrencia())) {
                ativos++;
            } else {
                concluidos++;
            }
        }

        System.out.println("   • Casos ativos: " + ativos);
        System.out.println("   • Casos concluídos: " + concluidos);
        System.out.println();

        // Estatísticas por nível de risco
        Map<String, Integer> contadorRisco = new HashMap<>();
        contadorRisco.put("Seguro", 0);
        contadorRisco.put("Investigação", 0);
        contadorRisco.put("Alerta Ativo", 0);
        contadorRisco.put("Emergência", 0);

        int totalHectares = 0;
        int denuncias = 0;
        int deteccaoAutomatica = 0;

        for (Ocorrencia ocorrencia : ocorrencias) {
            String nivelRisco = ocorrencia.getNivelRisco();
            contadorRisco.put(nivelRisco, contadorRisco.get(nivelRisco) + 1);

            totalHectares += ocorrencia.getHectaresAfetados();

            if (ocorrencia.getUsuarioDenunciante() != null) {
                denuncias++;
            } else {
                deteccaoAutomatica++;
            }
        }

        System.out.println("⚠️  DISTRIBUIÇÃO POR NÍVEL DE RISCO:");
        System.out.println("   • Áreas Seguras: " + contadorRisco.get("Seguro"));
        System.out.println("   • Investigação: " + contadorRisco.get("Investigação"));
        System.out.println("   • Alerta Ativo: " + contadorRisco.get("Alerta Ativo"));
        System.out.println("   • Emergência: " + contadorRisco.get("Emergência"));
        System.out.println();

        System.out.println("🔥 ESTATÍSTICAS DE COMBATE:");
        System.out.println("   • Total de hectares afetados: " + totalHectares);
        if (ocorrencias.size() > 0) {
            System.out.println("   • Média de hectares por caso: " + (totalHectares / ocorrencias.size()));
        }
        System.out.println("   • Denúncias de usuários: " + denuncias);
        System.out.println("   • Detecções automáticas: " + deteccaoAutomatica);
        System.out.println();

        // Taxa de eficiência
        if (ocorrencias.size() > 0) {
            double taxaResolucao = (double) concluidos / ocorrencias.size() * 100;
            System.out.println("🎯 EFICIÊNCIA:");
            System.out.printf("   • Taxa de resolução: %.1f%%\n", taxaResolucao);

            // Análise das áreas mais afetadas
            Map<String, Integer> contadorAreas = new HashMap<>();
            for (Ocorrencia ocorrencia : ocorrencias) {
                String nomeArea = ocorrencia.getAreaAfetada().getNomeArea();
                contadorAreas.put(nomeArea, contadorAreas.getOrDefault(nomeArea, 0) + 1);
            }

            if (!contadorAreas.isEmpty()) {
                String areaMaisAfetada = "";
                int maxOcorrencias = 0;
                for (Map.Entry<String, Integer> entry : contadorAreas.entrySet()) {
                    if (entry.getValue() > maxOcorrencias) {
                        maxOcorrencias = entry.getValue();
                        areaMaisAfetada = entry.getKey();
                    }
                }
                System.out.println("   • Área com mais ocorrências: " + areaMaisAfetada + " (" + maxOcorrencias + " caso(s))");
            }

            System.out.println();
        }

        // STATUS ATUAL E ÚLTIMAS ATIVIDADES (novo)
        System.out.println("🚨 STATUS ATUAL:");
        if (ativos == 0) {
            System.out.println("   • ✅ CIDADE SEGURA - Todos os incêndios controlados!");
            System.out.println("   • 🌿 Operações de monitoramento ativas.");
        } else {
            System.out.println("   • 🚨 ATENÇÃO NECESSÁRIA - " + ativos + " incêndio(s) ativo(s)!");
            System.out.println("   • 🚒 Equipes em operação de combate.");
        }
        System.out.println();

        // Últimas atividades (novo)
        System.out.println("📋 ÚLTIMAS ATIVIDADES:");
        int mostrarUltimas = Math.min(3, ocorrencias.size());
        for (int i = ocorrencias.size() - 1; i >= ocorrencias.size() - mostrarUltimas; i--) {
            Ocorrencia ocorrencia = ocorrencias.get(i);
            String status = "Seguro".equals(ocorrencia.getNivelRisco()) ? "✅ Área Segura" : "🚨 Incêndio";
            System.out.println("   • " + status + " #" + ocorrencia.getIdOcorrencia() + " - " +
                    ocorrencia.getAreaAfetada().getNomeArea() + " (" +
                    ocorrencia.getDataHoraDeteccao().format(formatter) + ")");
        }
        System.out.println();

        // Avaliação de desempenho (novo)
        System.out.println("🏆 AVALIAÇÃO DE DESEMPENHO:");
        if (ativos == 0 && ocorrencias.size() > 0) {
            System.out.println("   • Classificação: ⭐⭐⭐ EXCELENTE");
            System.out.println("   • Todos os incêndios controlados com sucesso!");
        } else if (ativos > 0 && ativos <= 2) {
            System.out.println("   • Classificação: ⭐⭐ BOA");
            System.out.println("   • Poucos incêndios ativos, situação controlável.");
        } else if (ativos > 2) {
            System.out.println("   • Classificação: ⭐ ATENÇÃO REQUERIDA");
            System.out.println("   • Múltiplos incêndios ativos, priorizar recursos.");
        } else {
            System.out.println("   • Classificação: 🌿 PREVENTIVO");
            System.out.println("   • Estação em estado preventivo, sem incidentes.");
        }
        System.out.println();
    }

    /**
     * Limpa todas as ocorrências da cidade atual
     * @param ocorrencias Lista de ocorrências
     * @param estacaoAtual Estação atual logada
     * @param scanner Scanner para entrada do usuário
     */
    public static void limparOcorrenciasDaCidade(ArrayList<Ocorrencia> ocorrencias, EstacaoBombeiros estacaoAtual, Scanner scanner) {
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🗑️  LIMPAR TODAS OCORRÊNCIAS DA CIDADE");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Contar ocorrências da cidade
        ArrayList<Ocorrencia> ocorrenciasDaCidade = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());

        if (ocorrenciasDaCidade.isEmpty()) {
            System.out.println("📭 Nenhuma ocorrência encontrada em " + estacaoAtual.getCidade() + " para limpar.");
            System.out.println("✅ A cidade já está com histórico limpo!");
            System.out.println();
            return;
        }

        System.out.println("⚠️  ATENÇÃO: Esta operação é IRREVERSÍVEL!");
        System.out.println("🌆 Cidade: " + estacaoAtual.getCidade());
        System.out.println("📊 Ocorrências a serem removidas: " + ocorrenciasDaCidade.size());
        System.out.println();
        System.out.println("📋 Resumo das ocorrências:");

        for (Ocorrencia ocorrencia : ocorrenciasDaCidade) {
            String tipo = "Seguro".equals(ocorrencia.getNivelRisco()) ? "✅ Área Segura" : "🚨 Incêndio";
            System.out.println("   • " + tipo + " #" + ocorrencia.getIdOcorrencia() + " - " + ocorrencia.getAreaAfetada().getNomeArea());
        }

        System.out.println();
        System.out.print("❓ Tem certeza que deseja limpar TODAS as ocorrências de " + estacaoAtual.getCidade() + "? (S/N): ");

        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if ("S".equals(confirmacao) || "SIM".equals(confirmacao)) {
            // Contar quantas ocorrências serão removidas para exibir
            int quantidadeRemovida = ocorrenciasDaCidade.size();

            // Remover todas as ocorrências da cidade
            ocorrencias.removeIf(ocorrencia ->
                    ocorrencia.getAreaAfetada().getIdEstacaoResponsavel() == estacaoAtual.getIdEstacao());

            // Salvar arquivo vazio
            ArrayList<Ocorrencia> listaVazia = new ArrayList<>();
            Arquivo.salvarHistoricoDaCidade(listaVazia, estacaoAtual);

            System.out.println();
            System.out.println("✅ LIMPEZA CONCLUÍDA COM SUCESSO!");
            System.out.println("🗑️  Todas as " + quantidadeRemovida + " ocorrências foram removidas.");
            System.out.println("📄 Arquivo histórico limpo: historico_" + estacaoAtual.getCidade().toLowerCase().replace(" ", "_") + ".txt");
            System.out.println("🆕 Sistema pronto para novos registros!");
            System.out.println();

        } else if ("N".equals(confirmacao) || "NÃO".equals(confirmacao) || "NAO".equals(confirmacao)) {
            System.out.println();
            System.out.println("❌ Operação cancelada!");
            System.out.println("📊 Todas as ocorrências foram preservadas.");
            System.out.println();

        } else {
            System.out.println();
            System.out.println("❌ Resposta inválida! Operação cancelada por segurança.");
            System.out.println("📊 Todas as ocorrências foram preservadas.");
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
}