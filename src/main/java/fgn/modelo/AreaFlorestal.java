package fgn.modelo;

/**
 * Classe que representa uma Área Florestal monitorada pelo sistema
 * Contém informações de localização e distância da estação
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class AreaFlorestal {
    private int id;
    private String nome;
    private String localizacao;
    private int distanciaKm;
    private int idEstacaoResponsavel;

    /**
     * Construtor da AreaFlorestal
     * @param id Identificador único da área
     * @param nome Nome da área florestal
     * @param localizacao Descrição da localização
     * @param distanciaKm Distância em km da estação responsável
     * @param idEstacaoResponsavel ID da estação de bombeiros responsável
     */
    public AreaFlorestal(int id, String nome, String localizacao, int distanciaKm, int idEstacaoResponsavel) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.distanciaKm = distanciaKm;
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public int getDistanciaKm() {
        return distanciaKm;
    }

    public int getIdEstacaoResponsavel() {
        return idEstacaoResponsavel;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setDistanciaKm(int distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public void setIdEstacaoResponsavel(int idEstacaoResponsavel) {
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    /**
     * Exibe informações da área florestal para seleção
     */
    public void exibirInformacoes() {
        System.out.println(id + ". " + nome);
        System.out.println("\tLocalização: " + localizacao);
        System.out.println("\tDistância: " + distanciaKm + " km");
        System.out.println();
    }
}