package fgn.modelo;

import java.util.ArrayList;

/**
 * Classe responsável pela persistência de dados das ocorrências
 * Gerencia operações de leitura e escrita em arquivos
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Arquivo {

    /**
     * Carrega o histórico de ocorrências de uma cidade específica
     * @param estacaoAtual Estação que está fazendo login
     * @param ocorrencias Lista de ocorrências a ser preenchida
     * @param estacoes Lista de todas as estações
     * @param areasFlorestais Lista de áreas florestais
     * @param drones Lista de drones
     */
    public static void carregarHistoricoDaCidade(EstacaoBombeiros estacaoAtual, ArrayList<Ocorrencia> ocorrencias,
                                                 ArrayList<EstacaoBombeiros> estacoes, ArrayList<AreaFlorestal> areasFlorestais,
                                                 ArrayList<Drone> drones) {
        try {
            String nomeArquivo = "historico_" + estacaoAtual.getCidade().toLowerCase().replace(" ", "_") + ".txt";
            java.io.File arquivo = new java.io.File(nomeArquivo);

            if (!arquivo.exists()) {
                System.out.println("📁 Primeira vez em " + estacaoAtual.getCidade() + " - criando novo histórico...");
                return;
            }

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(arquivo));
            String linha;
            int ocorrenciasCarregadas = 0;

            while ((linha = reader.readLine()) != null) {
                // Procurar linhas que começam com 🚨 ou ✅ (indicam início de ocorrência)
                if (linha.startsWith("🚨") || linha.startsWith("✅")) {
                    Ocorrencia ocorrencia = parseOcorrenciaDoArquivo(reader, linha, estacaoAtual, areasFlorestais, drones);
                    if (ocorrencia != null) {
                        ocorrencias.add(ocorrencia);
                        ocorrenciasCarregadas++;
                    }
                }
            }

            reader.close();

            if (ocorrenciasCarregadas > 0) {
                System.out.println("📁 Carregado histórico de " + estacaoAtual.getCidade() + ": " + ocorrenciasCarregadas + " ocorrências");
            }

        } catch (java.io.IOException e) {
            System.out.println("⚠️ Erro ao carregar histórico: " + e.getMessage());
        }
    }

    /**
     * Faz o parse de uma ocorrência a partir do arquivo de texto
     * @param reader BufferedReader posicionado após a linha de cabeçalho
     * @param linhaCabecalho Linha com emoji e tipo (🚨 #1 - INCÊNDIO)
     * @param estacaoAtual Estação atual
     * @param areasFlorestais Lista de áreas florestais
     * @param drones Lista de drones
     * @return Objeto Ocorrencia ou null se erro
     */
    private static Ocorrencia parseOcorrenciaDoArquivo(java.io.BufferedReader reader, String linhaCabecalho,
                                                       EstacaoBombeiros estacaoAtual, ArrayList<AreaFlorestal> areasFlorestais,
                                                       ArrayList<Drone> drones) {
        try {
            // Parse do ID da ocorrência da linha de cabeçalho
            // Formato: "🚨 #1 - INCÊNDIO" ou "✅ #2 - ÁREA SEGURA"
            String[] partesCabecalho = linhaCabecalho.split("#")[1].split(" ");
            int idOcorrencia = Integer.parseInt(partesCabecalho[0]);

            boolean ehAreaSegura = linhaCabecalho.contains("ÁREA SEGURA");

            // Ler próximas linhas para extrair informações
            String linhaNomeArea = reader.readLine(); // "    📍 Nome da Área"
            String linhaCidade = reader.readLine();   // "    🏢 Cidade, Estado"
            String linhaStatus = reader.readLine();   // "    ⚠️ Nível | 📊 Status | ⏰ Data"

            // Parse do nome da área
            String nomeArea = linhaNomeArea.substring(linhaNomeArea.indexOf("📍") + 2).trim();
            AreaFlorestal area = buscarAreaPorNome(nomeArea, areasFlorestais);

            if (area == null) {
                // Se não encontrar a área, pular esta ocorrência
                pularLinhasOcorrencia(reader);
                return null;
            }

            // Parse dos hectares se houver
            int hectares = 0;
            String proximaLinha = reader.readLine();
            if (proximaLinha.contains("🔥") && proximaLinha.contains("hectares")) {
                String hectaresStr = proximaLinha.split("🔥")[1].split("hectares")[0].trim();
                hectares = Integer.parseInt(hectaresStr);
                proximaLinha = reader.readLine(); // Avançar para próxima linha
            }

            // Pular linha do sensor/drone
            if (proximaLinha.contains("🔍")) {
                proximaLinha = reader.readLine(); // Linha do drone
            }

            // Parse do drone
            Drone drone = obterDroneDaEstacao(drones, estacaoAtual.getIdEstacao());

            // Parse do usuário denunciante
            proximaLinha = reader.readLine(); // Linha do denunciante
            Usuario usuario = null;

            if (proximaLinha.contains("👤 Denúncia:") && !proximaLinha.contains("Drone")) {
                String nomeUsuario = proximaLinha.split("👤 Denúncia:")[1].trim();
                String linhaDados = reader.readLine(); // Linha com CPF e data de nascimento

                if (linhaDados != null && linhaDados.contains("📄 CPF:")) {
                    try {
                        String cpfStr = linhaDados.split("📄 CPF:")[1].split("\\|")[0].trim();
                        String dataNasc = linhaDados.split("📅 Nascimento:")[1].trim();

                        long cpf = Long.parseLong(cpfStr);
                        usuario = new Usuario(nomeUsuario, cpf, dataNasc);
                    } catch (Exception e) {
                        // Se erro no parse do usuário, continuar sem usuário
                    }
                }
            }

            // Ler linha vazia
            reader.readLine();

            // Criar objeto Ocorrencia
            if (ehAreaSegura) {
                return new Ocorrencia(idOcorrencia, area, drone, usuario);
            } else {
                if (usuario != null) {
                    return new Ocorrencia(idOcorrencia, area, hectares, drone, usuario, 0);
                } else {
                    return new Ocorrencia(idOcorrencia, area, hectares, null, drone, 0);
                }
            }

        } catch (Exception e) {
            System.out.println("⚠️ Erro ao processar ocorrência do arquivo: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca uma área florestal pelo nome
     * @param nomeArea Nome da área a buscar
     * @param areasFlorestais Lista de áreas florestais
     * @return AreaFlorestal encontrada ou null
     */
    private static AreaFlorestal buscarAreaPorNome(String nomeArea, ArrayList<AreaFlorestal> areasFlorestais) {
        for (AreaFlorestal area : areasFlorestais) {
            if (area.getNomeArea().equals(nomeArea)) {
                return area;
            }
        }
        return null;
    }

    /**
     * Pula as linhas restantes de uma ocorrência em caso de erro
     * @param reader BufferedReader
     */
    private static void pularLinhasOcorrencia(java.io.BufferedReader reader) {
        try {
            String linha;
            // Pular até encontrar linha vazia ou próxima ocorrência
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty() || linha.startsWith("🚨") || linha.startsWith("✅")) {
                    break;
                }
            }
        } catch (java.io.IOException e) {
            // Ignorar erro ao pular
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
     * Salva o histórico específico de uma cidade
     * @param ocorrenciasDaEstacao Lista de ocorrências filtrada por estação
     * @param estacaoAtual Estação atual logada
     */
    public static void salvarHistoricoDaCidade(ArrayList<Ocorrencia> ocorrenciasDaEstacao, EstacaoBombeiros estacaoAtual) {
        try {
            String nomeArquivo = "historico_" + estacaoAtual.getCidade().toLowerCase().replace(" ", "_") + ".txt";
            java.io.FileWriter writer = new java.io.FileWriter(nomeArquivo);
            java.io.PrintWriter printWriter = new java.io.PrintWriter(writer);

            // Cabeçalho do arquivo específico da cidade
            printWriter.println("═══════════════════════════════════════════════════════════════════════════");
            printWriter.println("📋 HISTÓRICO DE OCORRÊNCIAS - " + estacaoAtual.getCidade().toUpperCase());
            printWriter.println("═══════════════════════════════════════════════════════════════════════════");
            printWriter.println("🏢 " + estacaoAtual.getNomeEstacao());
            printWriter.println("👮 " + estacaoAtual.getNomeComandante());
            printWriter.println("📅 Gerado em: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            printWriter.println();

            if (ocorrenciasDaEstacao.isEmpty()) {
                printWriter.println("📭 Nenhuma ocorrência registrada ainda em " + estacaoAtual.getCidade() + ".");
            } else {
                printWriter.println("📊 Total de registros: " + ocorrenciasDaEstacao.size());
                printWriter.println();

                // Escrever cada ocorrência da estação
                for (Ocorrencia ocorrencia : ocorrenciasDaEstacao) {
                    String resumoFormatado = formatarResumoParaArquivo(ocorrencia, estacaoAtual);
                    printWriter.print(resumoFormatado);
                }
            }

            printWriter.println("═══════════════════════════════════════════════════════════════════════════");
            printWriter.println("🌲 Forest Guardian Network - " + estacaoAtual.getCidade() + " 🌲");
            printWriter.println("═══════════════════════════════════════════════════════════════════════════");

            printWriter.close();

        } catch (java.io.IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    /**
     * Formata o resumo da ocorrência para salvar no arquivo
     * @param ocorrencia Ocorrência a ser formatada
     * @param estacaoResponsavel Estação responsável
     * @return String formatada para arquivo
     */
    private static String formatarResumoParaArquivo(Ocorrencia ocorrencia, EstacaoBombeiros estacaoResponsavel) {
        StringBuilder sb = new StringBuilder();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String emoji = "Seguro".equals(ocorrencia.getNivelRisco()) ? "✅" : "🚨";
        String tipoOcorrencia = "Seguro".equals(ocorrencia.getNivelRisco()) ? "ÁREA SEGURA" : "INCÊNDIO";

        sb.append(emoji).append(" #").append(ocorrencia.getIdOcorrencia()).append(" - ").append(tipoOcorrencia).append("\n");
        sb.append("    📍 ").append(ocorrencia.getAreaAfetada().getNomeArea()).append("\n");
        sb.append("    🏢 ").append(estacaoResponsavel.getCidade()).append(", ").append(estacaoResponsavel.getEstado()).append("\n");
        sb.append("    ⚠️  ").append(ocorrencia.getNivelRisco()).append(" | 📊 ").append(ocorrencia.getStatusOcorrencia()).append(" | ⏰ ").append(ocorrencia.getDataHoraDeteccao().format(formatter)).append("\n");

        if (ocorrencia.getHectaresAfetados() > 0) {
            sb.append("    🔥 ").append(ocorrencia.getHectaresAfetados()).append(" hectares afetados").append("\n");
        }

        if (ocorrencia.getSensorDetector() != null) {
            sb.append("    🔍 ").append(ocorrencia.getSensorDetector().getNomeSensor()).append("\n");
        } else {
            sb.append("    🔍 Drone de varredura").append("\n");
        }

        sb.append("    🚁 ").append(ocorrencia.getDroneVarredura().getModeloDrone()).append(" (#").append(ocorrencia.getDroneVarredura().getIdDrone()).append(")").append("\n");

        if (ocorrencia.getUsuarioDenunciante() != null) {
            sb.append("    👤 Denúncia: ").append(ocorrencia.getUsuarioDenunciante().getNome()).append("\n");
            sb.append("        📄 CPF: ").append(ocorrencia.getUsuarioDenunciante().getCpf()).append(" | 📅 Nascimento: ").append(ocorrencia.getUsuarioDenunciante().getDataNascimento()).append("\n");
        } else {
            sb.append("    👤 Denúncia: Drone").append("\n");
        }

        sb.append("\n");
        return sb.toString();
    }
}