package fgn.modelo;

/**
 * <p>
 * Esta classe representa uma unidade operacional do Corpo de Bombeiros integrada
 * ao sistema Forest Guardian Network. Encapsula todas as informações
 * administrativas e operacionais necessárias para identificação, localização
 * e gestão de uma estação de bombeiros responsável por área territorial específica.
 * </p>
 * <p>
 * Cada estação possui comando designado, jurisdição territorial definida e
 * capacidade de resposta a emergências florestais. A classe serve como entidade
 * central para organização hierárquica e distribuição de responsabilidades
 * operacionais no combate a incêndios florestais.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class EstacaoBombeiros {
    /**
     * Identificador único da estação (4 dígitos padrão)
     */
    private int idEstacao;
    /**
     * Denominação oficial da unidade operacional
     */
    private String nomeEstacao;
    /**
     * Endereço completo da sede da estação
     */
    private String endereco;
    /**
     * Município onde está localizada a estação
     */
    private String cidade;
    /**
     * Unidade federativa da localização
     */
    private String estado;
    /**
     * Nome completo do oficial responsável pela estação
     */
    private String nomeComandante;
    /**
     * Identificação funcional do comandante (5 dígitos padrão)
     */
    private int idComandante;

    /**
     * Construtor para criação de estação de bombeiros com dados administrativos completos.
     * Estabelece unidade operacional com identificação, localização, comando e
     * jurisdição territorial definida para operações de emergência florestal.
     *
     * @param idEstacao identificador único da estação (4 dígitos padrão)
     * @param nomeEstacao denominação oficial da unidade operacional
     * @param endereco endereço completo da sede da estação
     * @param cidade município onde está localizada a estação
     * @param estado unidade federativa da localização
     * @param nomeComandante nome completo do oficial responsável pela estação
     * @param idComandante identificação funcional do comandante (5 dígitos padrão)
     */
    public EstacaoBombeiros(int idEstacao, String nomeEstacao, String endereco, String cidade, String estado,
                            String nomeComandante, int idComandante) {
        this.idEstacao = idEstacao;
        this.nomeEstacao = nomeEstacao;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.nomeComandante = nomeComandante;
        this.idComandante = idComandante;
    }

    // Getters e Setters
    /**
     * Obtém o identificador único da estação de bombeiros.
     * Código utilizado para referenciamento em operações e relatórios.
     *
     * @return ID numérico único da estação (formato padrão 4 dígitos)
     */
    public int getIdEstacao() {
        return idEstacao;
    }

    /**
     * Define novo identificador para a estação de bombeiros.
     * Utilizado para reorganização administrativa ou padronização de códigos.
     *
     * @param idEstacao novo ID numérico da estação
     */
    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    /**
     * Obtém a denominação oficial da estação de bombeiros.
     * Nome utilizado em documentos oficiais e comunicações.
     *
     * @return string com nome oficial completo da estação
     */
    public String getNomeEstacao() {
        return nomeEstacao;
    }

    /**
     * Define nova denominação oficial para a estação.
     * Utilizado para atualizações administrativas ou reestruturações.
     *
     * @param nomeEstacao nova denominação oficial da estação
     */
    public void setNomeEstacao(String nomeEstacao) {
        this.nomeEstacao = nomeEstacao;
    }

    /**
     * Obtém o endereço completo da sede da estação.
     * Localização física para acionamento e referência geográfica.
     *
     * @return string com endereço completo da estação
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define novo endereço para a estação de bombeiros.
     * Utilizado para relocações ou correções de endereçamento.
     *
     * @param endereco novo endereço completo da estação
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Obtém o município de localização da estação.
     * Informação para definição de jurisdição territorial.
     *
     * @return string com nome do município da estação
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define novo município para a estação de bombeiros.
     * Utilizado para relocações ou correções administrativas.
     *
     * @param cidade novo município de localização
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Obtém a unidade federativa onde está localizada a estação.
     * Estado para identificação de jurisdição regional.
     *
     * @return string com sigla ou nome do estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define nova unidade federativa para a estação.
     *
     * @param estado nova unidade federativa de localização
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtém o nome completo do comandante responsável pela estação.
     * Oficial superior responsável pelas operações e comando da unidade.
     *
     * @return string com nome completo do comandante
     */
    public String getNomeComandante() {
        return nomeComandante;
    }

    /**
     * Define novo comandante responsável pela estação.
     * Utilizado para mudanças de comando ou atualizações administrativas.
     *
     * @param nomeComandante nome completo do novo comandante
     */
    public void setNomeComandante(String nomeComandante) {
        this.nomeComandante = nomeComandante;
    }

    /**
     * Obtém a identificação funcional do comandante da estação.
     * Código de identificação administrativa do oficial responsável.
     *
     * @return ID numérico do comandante (formato padrão 5 dígitos)
     */
    public int getIdComandante() {
        return idComandante;
    }

    /**
     * Define nova identificação funcional para o comandante.
     * Utilizado para mudanças de comando ou atualizações de códigos.
     *
     * @param idComandante novo ID funcional do comandante
     */
    public void setIdComandante(int idComandante) {
        this.idComandante = idComandante;
    }

    /**
     * Exibe informações básicas formatadas da estação para consulta operacional.
     * Apresenta dados essenciais em formato compacto para uso durante
     * operações correntes e consultas rápidas do sistema.
     */
    public void exibirInformacoes() {
        System.out.println("ID Estação: " + idEstacao);
        System.out.println("\tLocal: " + endereco);
        System.out.println("\tCidade: " + cidade + "-" + estado);
        System.out.println("\tComandante: " + nomeComandante + " (ID: " + idComandante + ")");
        System.out.println();
    }

    /**
     * Exibe relatório administrativo completo da estação com formatação hierárquica.
     * Apresenta informações detalhadas em formato oficial para documentação,
     * relatórios administrativos e comunicações formais do sistema.
     */
    public void exibirRelatorio() {
        System.out.println(nomeEstacao + ":");
        System.out.println("\tID Estação: " + idEstacao);
        System.out.println("\tLocalização: " + endereco);
        System.out.println("\tCidade: " + cidade + "-" + estado);
        System.out.println("\tComandante Responsável: " + nomeComandante + " (ID: " + idComandante + ")");
        System.out.println();
    }
}
