package fgn.modelo;

/**
 * Classe que representa um Drone do sistema FGN
 * HERDA de Equipamento - implementa herança
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Drone extends Equipamento {
    private int idEstacaoResponsavel;

    /**
     * Construtor do Drone
     * @param idDrone Identificador único do drone
     * @param modeloDrone Modelo do drone
     * @param idEstacaoResponsavel ID da estação responsável
     */
    public Drone(int idDrone, String modeloDrone, int idEstacaoResponsavel) {
        super(idDrone, modeloDrone, "Drone de Varredura"); // Chama construtor da classe pai
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Métodos específicos do Drone (mantém compatibilidade)
    public int getIdDrone() {
        return getId();
    }

    public void setIdDrone(int idDrone) {
        this.id = idDrone;
    }

    public String getModeloDrone() {
        return getNome();
    }

    public void setModeloDrone(String modeloDrone) {
        this.nome = modeloDrone;
    }

    public int getIdEstacaoResponsavel() {
        return idEstacaoResponsavel;
    }

    public void setIdEstacaoResponsavel(int idEstacaoResponsavel) {
        this.idEstacaoResponsavel = idEstacaoResponsavel;
    }

    // Metodo para compatibilidade com código existente
    public int getIdEstacaoBase() {
        return idEstacaoResponsavel;
    }

    /**
     * Implementação específica - como o drone opera
     */
    @Override
    public void operar() {
        if (ativo) {
            System.out.println("🚁 " + nome + " decolando para varredura aérea...");
            System.out.println("📡 Ativando sistema de câmeras térmicas...");
            System.out.println("🗺️ Mapeando área e coletando dados visuais...");
            System.out.println("📊 Transmitindo imagens em tempo real para base...");
        } else {
            System.out.println("❌ " + nome + " indisponível - em manutenção!");
        }
    }

    /**
     * Implementação específica - exibe informações do drone
     */
    @Override
    public void exibirInformacoes() {
        System.out.println("🚁 #" + id + " - " + nome + "ID Estação: " + idEstacaoResponsavel);
        System.out.println();
    }

    /**
     * Mensagem específica para identificação de área
     */
    public void exibirAreaIdentificada() {
        System.out.println("📍 Área identificada! Escolha o local onde teve o incêndio:");
    }
}