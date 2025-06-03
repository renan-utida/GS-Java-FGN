
package fgn.modelo;

/**
 * <p>
 * Esta classe representa um sensor de detecção automática integrado ao sistema
 * Forest Guardian Network. Herda da classe abstrata {@link Equipamento},
 * implementando funcionalidades específicas para monitoramento contínuo,
 * detecção de anomalias e análise de dados ambientais.
 * </p>
 * <p>
 * A classe suporta múltiplos tipos de sensores (Térmico, Fumaça, Químico),
 * cada um com comportamentos operacionais específicos. Implementa sobrescrita
 * de métodos abstratos da superclasse para fornecer análises especializadas
 * conforme o tipo de sensor instanciado.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class Sensor extends Equipamento {

    /**
     * Construtor para criação de sensor com tipo específico de detecção.
     * Inicializa sensor através da superclasse Equipamento, definindo
     * tipo especializado para determinar comportamento operacional.
     *
     * @param idSensor identificador único do sensor no sistema
     * @param nomeSensor denominação específica ou modelo do sensor
     * @param tipo categoria especializada do sensor ("Térmico", "Fumaça", "Químico")
     */
    public Sensor(int idSensor, String nomeSensor, String tipo) {
        super(idSensor, nomeSensor, tipo); // Chama construtor da classe pai
    }

    // Getters e Setters
    /**
     * Obtém o identificador do sensor (compatibilidade com código legado).
     * Metodo de conveniência que mapeia para ID da superclasse.
     *
     * @return ID único do sensor
     */
    public int getIdSensor() {
        return getId(); // Usa metodo da classe pai
    }

    /**
     * Define novo identificador para o sensor.
     * Acesso direto ao atributo protegido da superclasse.
     *
     * @param idSensor novo ID do sensor
     */
    public void setIdSensor(int idSensor) {
        this.id = idSensor; // Acesso direto ao atributo protected
    }

    /**
     * Obtém o nome do sensor (compatibilidade com código legado).
     * Metodo de conveniência que mapeia para nome da superclasse.
     *
     * @return nome/modelo do sensor
     */
    public String getNomeSensor() {
        return getNome(); // Usa metodo da classe pai
    }

    /**
     * Define novo nome para o sensor.
     * Acesso direto ao atributo protegido da superclasse.
     *
     * @param nomeSensor novo nome do sensor
     */
    public void setNomeSensor(String nomeSensor) {
        this.nome = nomeSensor; // Acesso direto ao atributo protected
    }

    /**
     * Implementação específica da operação de análise do sensor.
     * Sobrescreve metodo abstrato da superclasse {@link Equipamento#operar()},
     * executando sequência de análise especializada conforme tipo do sensor.
     * Comportamento varia dinamicamente baseado no tipo (polimorfismo interno).
     */
    @Override
    public void operar() {
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
    }

    /**
     * Implementação específica para exibição de informações do sensor.
     * Sobrescreve metodo abstrato da superclasse {@link Equipamento#exibirInformacoes()},
     * apresentando dados formatados em linha única incluindo ID, nome e tipo
     * para seleção em interfaces interativas.
     */
    @Override
    public void exibirInformacoes() {
        System.out.println(id + ". " + nome + " (" + tipo + ")");
    }
}