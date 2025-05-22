package br.com.lumepath.utils;

import java.util.InputMismatchException;

/**
 * Classe utilitária para validação e formatação de CPF.
 *
 * <p>Esta classe fornece métodos para verificar se um CPF é válido e para formatar o CPF
 * no padrão brasileiro (###.###.###-##).</p>
 *
 * <p>Um CPF é considerado válido se respeitar os critérios estabelecidos para o cálculo dos
 * dígitos verificadores e não for formado por uma sequência de números iguais.</p>
 *
 * @author Ricardo
 * @version 1.0
 */

public class ValidaCpf {

    /**
     * Verifica se um CPF é válido.
     *
     * @param cpf CPF a ser validado, representado como uma string.
     * @return {@code true} se o CPF for válido, {@code false} caso contrário.
     */
    public static boolean isCPF(String cpf){
        // Considera-se erro CPFs formados por uma sequência de números iguais
        if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11)) {
            return false;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            // Cálculo do primeiro dígito verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int)(cpf.charAt(i) - 48); // Converte o caractere para número
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char)(r + 48);

            // Cálculo do segundo dígito verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char)(r + 48);

            // Verifica se os dígitos calculados conferem com os informados
            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));

        } catch (InputMismatchException erro) {
            return false;
        }
    }

    /**
     * Formata um CPF no padrão brasileiro (###.###.###-##).
     *
     * @param cpf CPF a ser formatado, representado como uma string.
     * @return CPF formatado no padrão brasileiro.
     */
    public static String imprimeCPF(String cpf) {
        return (cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11));
    }
}