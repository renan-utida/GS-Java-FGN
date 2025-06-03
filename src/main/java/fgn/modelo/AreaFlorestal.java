package fgn.modelo;

/**
 * <p>
 * Esta classe representa uma área florestal específica monitorada pelo sistema
 * Forest Guardian Network. Cada área possui identificação única, localização
 * detalhada e está associada a uma estação de bombeiros responsável por sua
 * supervisão e resposta a emergências.
 * </p>
 * <p>
 * A classe encapsula informações geográficas essenciais para operações de
 * combate a incêndios, incluindo distância da estação responsável para
 * cálculo de tempo de resposta. Serves como entidade fundamental para
 * mapeamento territorial e organização jurisdicional do sistema.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class AreaFlorestal {
    private int idArea;
    private String nomeArea;
    private String localizacao;
    private int distanciaKm;
    private int idEstacaoResponsavel;

    /**
     * Construtor para criação de área florestal com todos os dados identificatórios.
     * Estabelece área completa com localização, distância da estação responsável
     * e vínculo jurisdicional para operações de emergência.
     *
     * @param idArea identificador único sequencial da área florestal
     * @param nomeArea denominação oficial ou popular da área florestal
     * @param localizacao descrição detalhada da localização geográfica
     * @param distanciaKm distância em quilômetros da estação de bombeiros responsável
     * @param idEstacaoResponsavel ID da estação de bombeiros com jurisdição sobre a área
     */
    public AreaFlorestal(int idArea, String nomeArea, String localizacao, int distanciaKm, int idEstacaoResponsavel) {
        this.idArea = idArea;
        this.nomeArea = nomeArea;
        this.localizacao = localizacao;
        this.distanciaKm = distanciaKm;
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Getters e Setters
    /**
     * Obtém o identificador único da área florestal.
     * Utilizado para referenciamento em ocorrências e operações do sistema.
     *
     * @return ID numérico único da área florestal
     */
    public int getIdArea() {
        return idArea;
    }

    /**
     * Define novo identificador para a área florestal.
     * Utilizado para reorganização de IDs ou correções administrativas.
     *
     * @param idArea novo ID numérico a ser atribuído
     */
    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    /**
     * Obtém o nome oficial ou denominação da área florestal.
     * Nome utilizado em relatórios e comunicações oficiais.
     *
     * @return string com nome completo da área florestal
     */
    public String getNomeArea() {
        return nomeArea;
    }

    /**
     * Define novo nome para a área florestal.
     * Utilizado para atualizações de denominação ou correções.
     *
     * @param nomeArea nova denominação da área florestal
     */
    public void setNomeArea(String nomeArea) {
        this.nomeArea = nomeArea;
    }

    /**
     * Obtém descrição detalhada da localização da área.
     * Informação geográfica complementar para identificação precisa.
     *
     * @return string descritiva da localização da área florestal
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * Define nova descrição de localização da área florestal.
     * Utilizado para atualizações de informações geográficas.
     *
     * @param localizacao nova descrição da localização
     */
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Obtém distância em quilômetros da estação responsável.
     * Valor utilizado para cálculo de tempo de resposta em emergências.
     *
     * @return distância em quilômetros da estação de bombeiros
     */
    public int getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Define nova distância da estação de bombeiros responsável.
     * Utilizado para correções de medições ou mudanças logísticas.
     *
     * @param distanciaKm nova distância em quilômetros
     */
    public void setDistanciaKm(int distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    /**
     * Obtém ID da estação de bombeiros com jurisdição sobre a área.
     * Determina qual estação é responsável por responder a emergências.
     *
     * @return ID numérico da estação de bombeiros responsável
     */
    public int getIdEstacaoResponsavel() {
        return idEstacaoResponsavel;
    }

    /**
     * Define nova estação responsável pela área florestal.
     * Utilizado para reorganização territorial ou transferência de jurisdição.
     *
     * @param idEstacaoResponsavel ID da nova estação responsável
     */
    public void setIdEstacaoResponsavel(int idEstacaoResponsavel) {
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    /**
     * Exibe informações formatadas da área florestal para seleção interativa.
     * Apresenta dados essenciais em formato amigável para escolha pelo usuário
     * durante registro de ocorrências ou operações do sistema.
     */
    public void exibirInformacoes() {
        System.out.println(idArea + ". " + nomeArea);
        System.out.println("\tLocalização: " + localizacao);
        System.out.println("\tDistância: " + distanciaKm + " km");
        System.out.println();
    }
}