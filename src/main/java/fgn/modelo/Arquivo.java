package fgn.modelo;

import java.util.ArrayList;

/**
 * <p>
 * Esta classe é responsável pela camada de persistência de dados do sistema
 * Forest Guardian Network. Gerencia todas as operações de entrada e saída de
 * arquivos relacionadas ao histórico de ocorrências, proporcionando capacidades
 * de carregamento, análise textual e salvamento de dados estruturados.
 * </p>
 * <p>
 * A classe implementa parsing robusto de arquivos de texto formatados com emojis
 * e estrutura hierárquica, convertendo dados textuais em objetos do sistema.
 * Organiza dados por cidade/estação, criando arquivos específicos para cada
 * jurisdição territorial e mantendo histórico persistente entre sessões.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class Arquivo {

    /**
     * Carrega o histórico completo de ocorrências de uma cidade específica.
     * Realiza parsing de arquivo texto estruturado, convertendo registros
     * formatados em objetos Ocorrencia com todos os relacionamentos preservados.
     * Cria arquivo automaticamente se não existir para primeira utilização.
     *
     * @param estacaoAtual estação que está realizando login no sistema
     * @param ocorrencias lista de ocorrências a ser preenchida com dados carregados
     * @param estacoes lista de todas as estações para referência cruzada
     * @param areasFlorestais lista de áreas florestais para associação de ocorrências
     * @param drones lista de drones para associação com registros carregados
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
     * Realiza parsing detalhado de uma ocorrência individual do arquivo texto.
     * Interpreta formato estruturado com emojis e dados hierárquicos, reconstituindo
     * objeto Ocorrencia completo com todos os relacionamentos e metadados.
     * Implementa tratamento robusto de erros para garantir integridade dos dados.
     *
     * @param reader BufferedReader posicionado após linha de cabeçalho da ocorrência
     * @param linhaCabecalho linha contendo emoji identificador e tipo de ocorrência
     * @param estacaoAtual estação responsável pela jurisdição da ocorrência
     * @param areasFlorestais lista de áreas para associação por nome
     * @param drones lista de drones para associação com a estação
     * @return objeto Ocorrencia reconstituído ou null se erro no parsing
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
     * Busca área florestal específica por correspondência exata de nome.
     * Utilizado durante carregamento de arquivos para restabelecer
     * relacionamentos entre ocorrências e áreas florestais.
     *
     * @param nomeArea nome completo da área florestal procurada
     * @param areasFlorestais lista de áreas disponíveis para busca
     * @return objeto AreaFlorestal correspondente ou null se não encontrado
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
     * Pula linhas restantes de uma ocorrência durante erro de parsing.
     * Mecanismo de recuperação que permite continuar processamento do arquivo
     * mesmo quando uma ocorrência específica apresenta problemas de formato.
     *
     * @param reader BufferedReader posicionado em ocorrência com erro
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
     * Obtém drone designado para uma estação específica.
     * Busca por correspondência de ID da estação base para garantir
     * que cada estação utilize seu equipamento designado.
     *
     * @param drones lista de drones disponíveis no sistema
     * @param idEstacao ID da estação proprietária do drone
     * @return objeto Drone da estação ou null se não encontrado
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
     * Salva histórico completo de ocorrências específicas de uma cidade.
     * Gera arquivo texto estruturado com formatação padronizada, incluindo
     * cabeçalhos informativos, metadados da estação e registros detalhados
     * de todas as ocorrências da jurisdição territorial.
     *
     * @param ocorrenciasDaEstacao lista filtrada de ocorrências da estação específica
     * @param estacaoAtual estação responsável pela jurisdição sendo salva
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
     * Formata resumo individual de ocorrência para persistência em arquivo.
     * Converte objeto Ocorrencia em representação textual estruturada mantendo
     * todos os dados essenciais em formato padronizado para posterior carregamento.
     * Preserva emojis e hierarquia para facilitar leitura humana e parsing automático.
     *
     * @param ocorrencia objeto Ocorrencia a ser convertido para texto
     * @param estacaoResponsavel estação responsável pela jurisdição da ocorrência
     * @return string formatada pronta para escrita em arquivo
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