package fgn.modelo;

/**
 * Classe que representa uma Área Florestal monitorada pelo sistema
 * Contém informações de localização e distância da estação
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class AreaFlorestal {
    private int idArea;
    private String nomeArea;
    private String localizacao;
    private int distanciaKm;
    private int idEstacaoResponsavel;

    /**
     * Construtor da AreaFlorestal
     * @param idArea Identificador único da área
     * @param nomeArea Nome da área florestal
     * @param localizacao Descrição da localização
     * @param distanciaKm Distância em km da estação responsável
     * @param idEstacaoResponsavel ID da estação de bombeiros responsável
     */
    public AreaFlorestal(int idArea, String nomeArea, String localizacao, int distanciaKm, int idEstacaoResponsavel) {
        this.idArea = idArea;
        this.nomeArea = nomeArea;
        this.localizacao = localizacao;
        this.distanciaKm = distanciaKm;
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Getters e Setters
    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNomeArea() {
        return nomeArea;
    }

    public void setNomeArea(String nomeArea) {
        this.nomeArea = nomeArea;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(int distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public int getIdEstacaoResponsavel() {
        return idEstacaoResponsavel;
    }

    public void setIdEstacaoResponsavel(int idEstacaoResponsavel) {
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    /**
     * Exibe informações da área florestal para seleção
     */
    public void exibirInformacoes() {
        System.out.println(idArea + ". " + nomeArea);
        System.out.println("\tLocalização: " + localizacao);
        System.out.println("\tDistância: " + distanciaKm + " km");
        System.out.println();
    }
}