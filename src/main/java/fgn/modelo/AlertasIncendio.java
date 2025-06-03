package fgn.modelo;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * Esta classe é responsável pelo monitoramento e atendimento especializado de alertas de incêndio
 * no sistema Forest Guardian Network. Oferece funcionalidades para listagem, atendimento individual
 * e atendimento em massa de ocorrências de incêndio, com foco em casos ativos que requerem
 * intervenção imediata dos bombeiros.
 * </p>
 * <p>
 * A classe gerencia operações específicas para casos críticos de incêndio, permitindo que as
 * estações de bombeiros respondam rapidamente a emergências e coordenem esforços de combate.
 * Integra-se com o sistema de arquivos para persistência automática de dados.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class AlertasIncendio {

    /**
     * Exibe o menu principal de monitoramento de alertas de incêndio.
     * Oferece opções para listagem, atendimento individual e atendimento por local
     * de ocorrências ativas. Mantém loop até o usuário escolher voltar.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacaoAtual estação de bombeiros atualmente logada
     * @param scanner objeto Scanner para captura de entrada do usuário
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
     * Lista exclusivamente os casos de incêndio ativos da cidade atual.
     * Filtra e exibe apenas ocorrências com status "Ativo" e nível de risco
     * diferente de "Seguro", fornecendo visão focada em emergências.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacaoAtual estação de bombeiros para filtrar por jurisdição
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
     * Exibe um resumo compacto específico para alertas de incêndio.
     * Apresenta informações essenciais da ocorrência em formato otimizado
     * para tomada rápida de decisões em situações de emergência.
     *
     * @param ocorrencia ocorrência de incêndio a ser exibida
     * @param estacaoResponsavel estação responsável pela ocorrência
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
     * Atende uma ocorrência específica de incêndio, realizando processo completo
     * de combate. Permite seleção de incêndio ativo, simula operação de combate
     * e marca automaticamente a ocorrência como segura ao final.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacaoAtual estação de bombeiros executando o atendimento
     * @param scanner objeto Scanner para captura de entrada do usuário
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
     * Atende todas as ocorrências de incêndio de um local específico simultaneamente.
     * Agrupa incêndios por área florestal e permite operação focada para resolver
     * múltiplos focos de uma vez, otimizando recursos de combate.
     *
     * @param ocorrencias lista completa de ocorrências do sistema
     * @param estacaoAtual estação de bombeiros executando a operação
     * @param scanner objeto Scanner para captura de entrada do usuário
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

        // Agrupar incêndios por local (área florestal)
        HashMap<Integer, ArrayList<Ocorrencia>> incendiosPorLocal = new HashMap<>();
        HashMap<Integer, String> nomesLocais = new HashMap<>();

        for (Ocorrencia ocorrencia : incendiosAtivos) {
            int idArea = ocorrencia.getAreaAfetada().getIdArea();
            String nomeArea = ocorrencia.getAreaAfetada().getNomeArea();

            if (!incendiosPorLocal.containsKey(idArea)) {
                incendiosPorLocal.put(idArea, new ArrayList<>());
                nomesLocais.put(idArea, nomeArea);
            }
            incendiosPorLocal.get(idArea).add(ocorrencia);
        }

        // Mostrar casos de incêndio agrupados por local
        System.out.println("🚨 CASOS DE INCÊNDIO ATIVOS POR LOCAL:");
        System.out.println();

        for (Map.Entry<Integer, ArrayList<Ocorrencia>> entry : incendiosPorLocal.entrySet()) {
            int idArea = entry.getKey();
            ArrayList<Ocorrencia> ocorrenciasDoLocal = entry.getValue();
            String nomeLocal = nomesLocais.get(idArea);

            System.out.println("📍 " + nomeLocal + " (ID: " + idArea + "):");
            for (Ocorrencia ocorrencia : ocorrenciasDoLocal) {
                System.out.println("   🚨 #" + ocorrencia.getIdOcorrencia() + " - " + ocorrencia.getHectaresAfetados() + " hectares | " + ocorrencia.getNivelRisco());
            }
            System.out.println();
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("🚒 SELECIONE UM LOCAL PARA ATENDIMENTO:");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println();

        // Mostrar menu de locais disponíveis
        for (Map.Entry<Integer, String> entry : nomesLocais.entrySet()) {
            int idArea = entry.getKey();
            String nomeLocal = entry.getValue();
            int quantidadeIncendios = incendiosPorLocal.get(idArea).size();

            System.out.println(idArea + ". " + nomeLocal + " (" + quantidadeIncendios + " incêndio(s) ativo(s))");
        }

        System.out.println();
        System.out.print("🚒 Digite o ID da área para atender TODOS os incêndios do local: ");

        try {
            int idAreaEscolhida = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            // Verificar se o ID da área existe
            if (!incendiosPorLocal.containsKey(idAreaEscolhida)) {
                System.out.println("❌ ID inválido! Nenhuma área encontrada com este ID.");
                System.out.println();
                return;
            }

            ArrayList<Ocorrencia> ocorrenciasDoLocal = incendiosPorLocal.get(idAreaEscolhida);
            String nomeLocalEscolhido = nomesLocais.get(idAreaEscolhida);

            // Mostrar processo de atendimento do local específico
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("🚒 OPERAÇÃO LOCAL - " + nomeLocalEscolhido.toUpperCase());
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println("📍 Local: " + nomeLocalEscolhido);
            System.out.println("🚨 Incêndios no local: " + ocorrenciasDoLocal.size());
            System.out.println();
            System.out.println("🔥 Ocorrências sendo atendidas:");

            int hectaresTotais = 0;
            for (Ocorrencia ocorrencia : ocorrenciasDoLocal) {
                System.out.println("   • #" + ocorrencia.getIdOcorrencia() + " - " + ocorrencia.getHectaresAfetados() + " hectares (" + ocorrencia.getNivelRisco() + ")");
                hectaresTotais += ocorrencia.getHectaresAfetados();
            }

            System.out.println();
            System.out.println("📊 Total de hectares afetados no local: " + hectaresTotais + " hectares");
            System.out.println("🚨 Despachando equipes especializadas para " + nomeLocalEscolhido + "!");
            System.out.println("🚒 Operação focada em andamento...");
            System.out.println("═══════════════════════════════════════════════════════════════════════════");
            System.out.println();

            // Simular tempo de combate focado no local (4 segundos)
            System.out.println("🔥 Combate focado aos incêndios de " + nomeLocalEscolhido + " em andamento...");

            try {
                Thread.sleep(4000); // Espera 4 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Mostrar sucesso
            System.out.println();
            System.out.println("✅ TODOS OS INCÊNDIOS DE " + nomeLocalEscolhido.toUpperCase() + " APAGADOS!");
            System.out.println("🌿 " + ocorrenciasDoLocal.size() + " ocorrência(s) do local agora estão seguras!");
            System.out.println("📊 " + hectaresTotais + " hectares protegidos!");
            System.out.println("👨‍🚒 Equipes retornando à base...");
            System.out.println();

            // Marcar APENAS as ocorrências do local como seguras
            for (Ocorrencia ocorrencia : ocorrenciasDoLocal) {
                ocorrencia.marcarComoSegura();
            }

            // Atualizar arquivo automaticamente
            ArrayList<Ocorrencia> ocorrenciasDaEstacao = filtrarOcorrenciasPorEstacao(ocorrencias, estacaoAtual.getIdEstacao());
            Arquivo.salvarHistoricoDaCidade(ocorrenciasDaEstacao, estacaoAtual);

            System.out.println("💾 Registros do local atualizados automaticamente!");
            System.out.println("🎉 Operação em " + nomeLocalEscolhido + " concluída com sucesso!");
            System.out.println();

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida! Digite apenas números.");
            scanner.nextLine(); // Limpa o buffer em caso de erro
            System.out.println();
        }
    }

    /**
     * Filtra ocorrências pertencentes a uma estação específica.
     * Utilizado para operações que devem considerar apenas a jurisdição
     * da estação atualmente logada no sistema.
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
}