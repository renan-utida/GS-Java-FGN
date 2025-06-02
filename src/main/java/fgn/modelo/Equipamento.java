package fgn.modelo;

/**
 * Classe abstrata que representa um equipamento do sistema FGN
 * Classe pai para Drone e Sensor - implementa herança
 *
 * @author Equipe FGN
 * @version 1.0
 */
public abstract class Equipamento {
    protected int id;
    protected String nome;
    protected boolean ativo;
    protected String tipo;

    /**
     * Construtor da classe Equipamento
     * @param id Identificador único do equipamento
     * @param nome Nome do equipamento
     * @param tipo Tipo do equipamento
     */
    public Equipamento(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.ativo = true; // Por padrão, equipamentos iniciam ativos
    }

    // Getters e Setters comuns
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Ativa o equipamento
     */
    public void ativar() {
        this.ativo = true;
        System.out.println("✅ " + nome + " ativado com sucesso!");
    }

    /**
     * Desativa o equipamento
     */
    public void desativar() {
        this.ativo = false;
        System.out.println("❌ " + nome + " desativado!");
    }

    /**
     * Metodo abstrato - cada equipamento tem sua forma específica de operar
     */
    public abstract void operar();

    /**
     * Metodo abstrato - cada equipamento exibe informações de forma específica
     */
    public abstract void exibirInformacoes();
}