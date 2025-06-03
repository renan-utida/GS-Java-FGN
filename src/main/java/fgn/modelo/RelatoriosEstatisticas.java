package fgn.modelo;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * Esta classe é responsável pela geração de relatórios detalhados e estatísticas avançadas
 * do sistema Forest Guardian Network. Oferece análises completas de desempenho das estações,
 * métricas de eficiência no combate a incêndios e ferramentas administrativas para
 * manutenção do sistema.
 * </p>
 * <p>
 * A classe fornece funcionalidades para visualização de dados consolidados, verificação
 * de estatísticas por estação, análise de tendências e operações de limpeza de dados.
 * Inclui avaliações de desempenho e classificações automáticas baseadas em resultados.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class RelatoriosEstatisticas {

    /**
     * Exibe o menu principal de relatórios e estatísticas do sistema.
     * Oferece opções para visualizar estações cadastradas, dados da estação atual,
     * limpeza de dados e navegação. Mantém loop até o usuário escolher voltar.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacoes lista de todas as estações de bombeiros
     * @param areasFlorestais lista de áreas florestais monitoradas
     * @param estacaoAtual estação de bombeiros atualmente logada
     * @param scanner objeto Scanner para captura de entrada do usuário
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
     * Exibe relatório completo de todas as estações de bombeiros cadastradas.
     * Apresenta informações consolidadas incluindo comandantes, localizações
     * e estatísticas gerais de cobertura do sistema.
     *
     * @param estacoes lista de estações de bombeiros a serem exibidas
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
     * Verifica e exibe dados detalhados da estação atualmente logada.
     * Filtra automaticamente ocorrências e áreas da estação para análise
     * específica sem necessidade de entrada adicional do usuário.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacaoAtual estação de bombeiros atualmente logada
     * @param areasFlorestais lista de áreas florestais monitoradas
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
     * Exibe relatório estatístico completo e avaliação de desempenho da estação.
     * Calcula métricas avançadas incluindo taxa de resolução, distribuição por risco,
     * análise de áreas mais afetadas e classificação de desempenho automatizada.
     *
     * @param estacao estação de bombeiros para análise
     * @param ocorrencias lista de ocorrências filtradas da estação
     * @param areas lista de áreas florestais supervisionadas pela estação
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
     * Executa processo seguro de limpeza de todas as ocorrências da cidade atual.
     * Solicita confirmação do usuário, exibe resumo das ocorrências a serem removidas
     * e atualiza automaticamente o arquivo de histórico após a operação.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacaoAtual estação de bombeiros atualmente logada
     * @param scanner objeto Scanner para captura de entrada do usuário
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
     * Filtra ocorrências pertencentes a uma estação específica.
     * Metodo utilitário usado para análises e operações que requerem
     * dados isolados por jurisdição da estação.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param idEstacao ID da estação de bombeiros para filtrar
     * @return lista contendo apenas ocorrências da estação especificada
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
     * Obtém lista de áreas florestais supervisionadas por uma estação específica.
     * Filtra áreas por responsabilidade jurisdicional para análises regionais
     * e cálculos de cobertura de monitoramento.
     *
     * @param areasFlorestais lista completa de áreas florestais do sistema
     * @param idEstacao ID da estação de bombeiros responsável
     * @return lista contendo apenas áreas supervisionadas pela estação
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