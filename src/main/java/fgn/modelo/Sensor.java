package fgn.modelo;

/**
 * Classe que representa um Sensor do sistema FGN
 * HERDA de Equipamento - implementa herança
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Sensor extends Equipamento {

    /**
     * Construtor do Sensor
     *
     * @param idSensor   Identificador único do sensor
     * @param nomeSensor Nome do sensor
     * @param tipo       Tipo de sensor (Térmico, Fumaça, Químico)
     */
    public Sensor(int idSensor, String nomeSensor, String tipo) {
        super(idSensor, nomeSensor, tipo); // Chama construtor da classe pai
    }

    // Métodos específicos do Sensor (mantém compatibilidade)
    public int getIdSensor() {
        return getId(); // Usa metodo da classe pai
    }

    public void setIdSensor(int idSensor) {
        this.id = idSensor; // Acesso direto ao atributo protected
    }

    public String getNomeSensor() {
        return getNome(); // Usa metodo da classe pai
    }

    public void setNomeSensor(String nomeSensor) {
        this.nome = nomeSensor; // Acesso direto ao atributo protected
    }

    /**
     * Implementação específica - como o sensor opera
     */
    @Override
    public void operar() {
        if (ativo) {
            System.out.println("🔍 " + nome + " iniciando análise da área...");

            switch (tipo) {
                case "Térmico":
                    System.out.println("🌡️ Escaneando variações de temperatura...");
                    System.out.println("📊 Detectando pontos de calor anômalos...");
                    break;
                case "Fumaça":
                    System.out.println("💨 Analisando densidade de partículas no ar...");
                    System.out.println("🔬 Identificando composição química da fumaça...");
                    break;
                case "Químico":
                    System.out.println("🧪 Detectando gases de combustão...");
                    System.out.println("⚗️ Analisando concentração de monóxido de carbono...");
                    break;
            }
            System.out.println("📋 Dados coletados e transmitidos para central!");
        } else {
            System.out.println("❌ " + nome + " está fora de operação!");
        }
    }

    /**
     * Implementação específica - exibe informações do sensor
     */
    @Override
    public void exibirInformacoes() {
        System.out.println(id + ". " + nome + " (" + tipo + ")");
    }
}