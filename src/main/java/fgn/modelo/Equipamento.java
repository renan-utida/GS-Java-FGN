package fgn.modelo;

/**
 * <p>
 * Esta classe abstrata representa a hierarquia fundamental de equipamentos
 * do sistema Forest Guardian Network. Serve como superclasse para todos os
 * dispositivos tecnológicos utilizados no monitoramento e detecção de incêndios
 * florestais, estabelecendo contratos comuns e funcionalidades compartilhadas.
 * </p>
 * <p>
 * A classe implementa o padrão Template Method através de métodos abstratos
 * que devem ser implementados pelas subclasses, garantindo que todos os
 * equipamentos possuam capacidades básicas de operação e exibição de informações,
 * enquanto mantém flexibilidade para comportamentos específicos de cada tipo.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public abstract class Equipamento {
    protected int id;
    protected String nome;
    protected String tipo;

    /**
     * Construtor base para criação de equipamentos do sistema.
     * Inicializa atributos fundamentais compartilhados por todas as subclasses,
     * estabelecendo identidade única e classificação tipológica do equipamento.
     *
     * @param id identificador único numérico do equipamento no sistema
     * @param nome denominação específica ou modelo do equipamento
     * @param tipo classificação categórica do equipamento para organização
     */
    public Equipamento(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    // Getters e Setters
    /**
     * Obtém o identificador único do equipamento.
     * Valor utilizado para referenciamento em operações e relatórios do sistema.
     *
     * @return ID numérico único do equipamento
     */
    public int getId() {
        return id;
    }

    /**
     * Define novo identificador para o equipamento.
     *
     * @param id novo ID numérico do equipamento
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome ou modelo específico do equipamento.
     * Denominação utilizada para identificação em interfaces e relatórios.
     *
     * @return string com nome/modelo do equipamento
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define nome ou modelo específico do equipamento.
     *
     * @param nome novo ID numérico da estação
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a classificação tipológica do equipamento.
     * Categoria utilizada para organização e filtragem de equipamentos.
     *
     * @return string com tipo/categoria do equipamento
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do equipamento.
     *
     * @param tipo novo ID numérico da estação
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo abstrato para execução de operações específicas do equipamento.
     * Cada subclasse deve implementar comportamento operacional próprio,
     * definindo como o equipamento executa suas funções principais no sistema.
     * Implementa padrão Template Method para garantir interface consistente.
     */
    public abstract void operar();

    /**
     * Metodo abstrato para exibição de informações específicas do equipamento.
     * Cada subclasse deve implementar formato de apresentação apropriado,
     * adequado ao tipo de equipamento e contexto de utilização.
     * Permite polimorfismo na apresentação de dados.
     */
    public abstract void exibirInformacoes();
}