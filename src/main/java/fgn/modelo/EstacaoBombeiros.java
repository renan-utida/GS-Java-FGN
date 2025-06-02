package fgn.modelo;

/**
 * Classe que representa uma Estação do Corpo de Bombeiros
 * Contém informações básicas de identificação e localização
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class EstacaoBombeiros {
    private int idEstacao;
    private String nomeEstacao;
    private String endereco;
    private String cidade;
    private String estado;
    private String nomeComandante;
    private int idComandante;

    /**
     * Construtor da EstacaoBombeiros
     * @param idEstacao Identificador único da estação (4 dígitos)
     * @param nomeEstacao Nome oficial da estação
     * @param endereco Endereço completo da estação
     * @param cidade Cidade onde está localizada
     * @param estado Estado onde está localizada
     * @param nomeComandante Nome do comandante responsável pela estação
     * @param idComandante ID do comandante (5 dígitos)
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
    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public String getNomeEstacao() {
        return nomeEstacao;
    }

    public void setNomeEstacao(String nomeEstacao) {
        this.nomeEstacao = nomeEstacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNomeComandante() {
        return nomeComandante;
    }

    public void setNomeComandante(String nomeComandante) {
        this.nomeComandante = nomeComandante;
    }

    public int getIdComandante() {
        return idComandante;
    }

    public void setIdComandante(int idComandante) {
        this.idComandante = idComandante;
    }

    /**
     * Exibe as informações formatadas da estação
     */
    public void exibirInformacoes() {
        System.out.println("ID Estação: " + idEstacao);
        System.out.println("\tLocal: " + endereco);
        System.out.println("\tCidade: " + cidade + "-" + estado);
        System.out.println("\tComandante: " + nomeComandante + " (ID: " + idComandante + ")");
        System.out.println();
    }

    /**
     * Exibe as informações formatadas da estação para relatórios
     * Formato: Nome da estação como título, detalhes com tabs
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
