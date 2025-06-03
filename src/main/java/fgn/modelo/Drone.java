
package fgn.modelo;

/**
 * <p>
 * Esta classe representa um drone de varredura aérea integrado ao sistema
 * Forest Guardian Network. Herda da classe abstrata {@link Equipamento},
 * implementando funcionalidades específicas para monitoramento aéreo,
 * captação de imagens térmicas e varredura territorial automatizada.
 * </p>
 * <p>
 * A classe implementa sobrescrita de métodos abstratos da superclasse,
 * fornecendo comportamentos específicos para operações de voo, coleta
 * de dados visuais e transmissão em tempo real. Cada drone está vinculado
 * a uma estação de bombeiros específica para organização operacional.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class Drone extends Equipamento {
    /**
     * ID da estação de bombeiros proprietária do drone
     */
    private int idEstacaoResponsavel;

    /**
     * Construtor para criação de drone com vinculação à estação responsável.
     * Inicializa drone através da superclasse Equipamento e estabelece
     * relacionamento com estação de bombeiros para organização operacional.
     *
     * @param idDrone identificador único do drone no sistema
     * @param modeloDrone modelo ou denominação específica do drone
     * @param idEstacaoResponsavel ID da estação de bombeiros proprietária do drone
     */
    public Drone(int idDrone, String modeloDrone, int idEstacaoResponsavel) {
        super(idDrone, modeloDrone, "Drone de Varredura"); // Chama construtor da classe pai
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Métodos específicos do Drone (mantém compatibilidade)
    /**
     * Obtém o identificador do drone (compatibilidade com código legado).
     * Metodo de conveniência que mapeia para ID da superclasse.
     *
     * @return ID único do drone
     */
    public int getIdDrone() {
        return getId();
    }

    /**
     * Define novo identificador para o drone.
     * Acesso direto ao atributo protegido da superclasse.
     *
     * @param idDrone novo ID do drone
     */
    public void setIdDrone(int idDrone) {
        this.id = idDrone;
    }

    /**
     * Obtém o modelo do drone (compatibilidade com código legado).
     * Metodo de conveniência que mapeia para nome da superclasse.
     *
     * @return modelo/nome do drone
     */
    public String getModeloDrone() {
        return getNome();
    }

    /**
     * Define novo modelo para o drone.
     * Acesso direto ao atributo protegido da superclasse.
     *
     * @param modeloDrone novo modelo do drone
     */
    public void setModeloDrone(String modeloDrone) {
        this.nome = modeloDrone;
    }

    /**
     * Obtém ID da estação de bombeiros responsável pelo drone.
     * Determina qual estação possui jurisdição sobre o equipamento.
     *
     * @return ID da estação responsável
     */
    public int getIdEstacaoResponsavel() {
        return idEstacaoResponsavel;
    }

    /**
     * Define nova estação responsável pelo drone.
     * Utilizado para transferências de equipamento entre estações.
     *
     * @param idEstacaoResponsavel ID da nova estação responsável
     */
    public void setIdEstacaoResponsavel(int idEstacaoResponsavel) {
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Metodo para compatibilidade com código existente
    /**
     * Obtém ID da estação base (metodo de compatibilidade).
     * Alias para getIdEstacaoResponsavel() mantendo compatibilidade.
     *
     * @return ID da estação base do drone
     */
    public int getIdEstacaoBase() {
        return idEstacaoResponsavel;
    }

    /**
     * Implementação específica da operação de varredura aérea do drone.
     * Sobrescreve metodo abstrato da superclasse {@link Equipamento#operar()},
     * executando sequência completa de decolagem, ativação de sistemas,
     * mapeamento e transmissão de dados em tempo real.
     */
    @Override
    public void operar() {
        System.out.println("🚁 " + nome + " decolando para varredura aérea...");
        System.out.println("📡 Ativando sistema de câmeras térmicas...");
        System.out.println("🗺️ Mapeando área e coletando dados visuais...");
        System.out.println("📊 Transmitindo imagens em tempo real para base...");
    }

    /**
     * Implementação específica para exibição de informações do drone.
     * Sobrescreve metodo abstrato da superclasse {@link Equipamento#exibirInformacoes()},
     * apresentando dados formatados específicos para drones incluindo
     * identificação, modelo e estação responsável.
     */
    @Override
    public void exibirInformacoes() {
        System.out.println("🚁 #" + id + " - " + nome + "ID Estação: " + idEstacaoResponsavel);
        System.out.println();
    }

    /**
     * Exibe mensagem específica para identificação de área detectada.
     * Metodo especializado utilizado após varredura para solicitar
     * seleção de local específico onde incêndio foi identificado.
     */
    public void exibirAreaIdentificada() {
        System.out.println("📍 Área identificada! Escolha o local onde teve o incêndio:");
    }
}