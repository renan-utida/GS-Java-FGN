package fgn.modelo;

import java.util.Scanner;

/**
 * Classe que representa um Usuário que faz denúncias
 * Contém informações pessoais e validações
 *
 * @author Equipe FGN
 * @version 1.0
 */
public class Usuario {
    private String nome;
    private long cpf;
    private String dataNascimento;

    /**
     * Construtor do Usuario
     * @param nome Nome completo do usuário
     * @param cpf CPF (11 dígitos)
     * @param dataNascimento Data no formato dd/MM/yyyy
     */
    public Usuario(String nome, long cpf, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public long getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Valida CPF (formato básico: 11 dígitos)
     */
    public static boolean validarCPF(long cpf) {
        String cpfStr = String.valueOf(cpf);
        return cpfStr.length() == 11;
    }

    /**
     * Formata CPF para exibição
     */
    public String getCpfFormatado() {
        String cpfStr = String.format("%011d", cpf);
        return cpfStr.substring(0, 3) + "." + cpfStr.substring(3, 6) + "." +
                cpfStr.substring(6, 9) + "-" + cpfStr.substring(9, 11);
    }

    /**
     * Solicita e valida data de nascimento
     */
    public static String solicitarDataNascimento(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Dia de nascimento (1-31): ");
                int dia = scanner.nextInt();

                System.out.print("Mês de nascimento (1-12): ");
                int mes = scanner.nextInt();

                System.out.print("Ano de nascimento (1900-2024): ");
                int ano = scanner.nextInt();
                scanner.nextLine(); // Consome quebra de linha

                if (validarData(dia, mes, ano)) {
                    return String.format("%02d/%02d/%04d", dia, mes, ano);
                } else {
                    System.out.println("❌ ERRO: Data inválida! Verifique os valores informados.");
                }

            } catch (Exception e) {
                System.out.println("❌ ERRO: Digite apenas números para a data!");
                scanner.nextLine(); // Limpa buffer
            }
        }
    }

    /**
     * Solicita e valida CPF
     */
    public static long solicitarCPF(Scanner scanner) {
        while (true) {
            try {
                System.out.print("CPF (apenas números - 11 dígitos): ");
                long cpf = scanner.nextLong();
                scanner.nextLine(); // Consome quebra de linha

                if (Usuario.validarCPF(cpf)) {
                    return cpf;
                } else {
                    System.out.println("❌ ERRO: CPF deve ter exatamente 11 dígitos!");
                }

            } catch (Exception e) {
                System.out.println("❌ ERRO: Digite apenas números para o CPF!");
                scanner.nextLine(); // Limpa buffer
            }
        }
    }

    /**
     * Valida data de nascimento
     */
    private static boolean validarData(int dia, int mes, int ano) {
        if (ano < 1900 || ano > 2024) return false;
        if (mes < 1 || mes > 12) return false;
        if (dia < 1 || dia > 31) return false;

        // Validação básica de dias por mês
        int[] diasPorMes = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return dia <= diasPorMes[mes - 1];
    }
}
