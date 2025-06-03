package fgn.modelo;

import java.util.Scanner;

/**
 * <p>
 * Esta classe representa um cidadão usuário do sistema Forest Guardian Network
 * que possui capacidade de realizar denúncias de possíveis incêndios florestais.
 * Encapsula informações pessoais essenciais para identificação e contato,
 * além de fornecer métodos robustos de validação e formatação de dados.
 * </p>
 * <p>
 * A classe implementa validações rigorosas para nome, CPF e data de nascimento,
 * garantindo integridade dos dados coletados durante o processo de denúncia.
 * Oferece métodos estáticos utilitários para formatação e validação que podem
 * ser utilizados em toda a aplicação para manter consistência de dados.
 * </p>
 *
 * @author Renan Dias Utida, Fernanda Rocha Menon e Luiza Macena Dantas
 * @version 1.0
 */
public class Usuario {
    private String nome;
    private long cpf;
    private String dataNascimento;

    /**
     * Construtor para criação de usuário denunciante com dados validados.
     * Recomenda-se utilizar métodos de validação estáticos antes da instanciação
     * para garantir integridade dos dados fornecidos.
     *
     * @param nome nome completo do usuário, preferencialmente formatado
     * @param cpf documento CPF em formato numérico (11 dígitos)
     * @param dataNascimento data no formato dd/MM/yyyy validada
     */
    public Usuario(String nome, long cpf, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // Getters
    /**
     * Obtém o nome completo do usuário.
     *
     * @return nome completo do usuário denunciante
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o CPF em formato numérico.
     * Documento de identificação único do usuário denunciante.
     *
     * @return CPF como número longo de 11 dígitos
     */
    public long getCpf() {
        return cpf;
    }

    /**
     * Obtém a data de nascimento formatada.
     * Data no padrão brasileiro dd/MM/yyyy.
     *
     * @return string com data de nascimento no formato dd/MM/yyyy
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Valida se nome contém apenas caracteres alfabéticos e espaços.
     * Implementa regras rigorosas: deve iniciar com letra, aceitar apenas
     * letras e espaços, e não permitir campos vazios ou nulos.
     *
     * @param nome string a ser validada como nome de pessoa
     * @return true se nome atende critérios de validação, false caso contrário
     */
    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        nome = nome.trim();

        // Deve começar com letra
        if (!Character.isLetter(nome.charAt(0))) {
            return false;
        }

        // Deve conter apenas letras e espaços
        for (char c : nome.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }

        return true;
    }

    /**
     * Formata nome para exibição padronizada com capitalização adequada.
     * Converte primeira letra de cada palavra para maiúscula e demais
     * para minúscula, removendo espaços excessivos e normalizando formato.
     *
     * @param nome string com nome a ser formatado
     * @return nome formatado no padrão Título (primeira letra maiúscula)
     */
    public static String formatarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return nome;
        }

        String[] palavras = nome.trim().toLowerCase().split("\\s+");
        StringBuilder nomeFormatado = new StringBuilder();

        for (int i = 0; i < palavras.length; i++) {
            if (i > 0) {
                nomeFormatado.append(" ");
            }
            if (palavras[i].length() > 0) {
                nomeFormatado.append(Character.toUpperCase(palavras[i].charAt(0)));
                if (palavras[i].length() > 1) {
                    nomeFormatado.append(palavras[i].substring(1));
                }
            }
        }

        return nomeFormatado.toString();
    }

    /**
     * Valida CPF em formato básico verificando quantidade de dígitos.
     * Implementa validação simples de comprimento para garantir
     * formato mínimo esperado de documento brasileiro.
     *
     * @param cpf número do CPF a ser validado
     * @return true se CPF possui exatamente 11 dígitos, false caso contrário
     */
    public static boolean validarCPF(long cpf) {
        String cpfStr = String.valueOf(cpf);
        return cpfStr.length() == 11;
    }

    /**
     * Formata CPF para exibição com pontuação padrão brasileira.
     * Converte número longo em string formatada no padrão XXX.XXX.XXX-XX
     * para melhor legibilidade em relatórios e interfaces.
     *
     * @return CPF formatado com pontos e traço no padrão brasileiro
     */
    public String getCpfFormatado() {
        String cpfStr = String.format("%011d", cpf);
        return cpfStr.substring(0, 3) + "." + cpfStr.substring(3, 6) + "." +
                cpfStr.substring(6, 9) + "-" + cpfStr.substring(9, 11);
    }

    /**
     * Solicita e valida data de nascimento através de interação controlada.
     * Implementa loop de validação com tratamento de exceções, solicitando
     * dia, mês e ano separadamente para melhor controle de entrada.
     * Continua solicitando até receber data válida.
     *
     * @param scanner objeto Scanner configurado para entrada do usuário
     * @return string com data válida no formato dd/MM/yyyy
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
     * Solicita e valida CPF através de interação controlada com usuário.
     * Implementa loop de validação com tratamento de exceções, continuando
     * a solicitar entrada até receber CPF válido conforme critérios estabelecidos.
     * Limpa automaticamente buffer em caso de erro de entrada.
     *
     * @param scanner objeto Scanner configurado para entrada do usuário
     * @return CPF válido como número longo de 11 dígitos
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
     * Valida data de nascimento verificando intervalos válidos e coerência.
     * Implementa validação abrangente incluindo anos válidos (1900-2024),
     * meses (1-12), dias por mês considerando variações do calendário.
     * Metodo privado utilizado internamente para validação.
     *
     * @param dia dia do mês (1-31 conforme o mês)
     * @param mes mês do ano (1-12)
     * @param ano ano de nascimento (1900-2024)
     * @return true se data é válida e coerente, false caso contrário
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
