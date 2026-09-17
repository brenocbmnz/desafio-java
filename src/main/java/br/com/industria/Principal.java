package br.com.industria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATO_NUMERO = criarFormatoNumero();
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal FATOR_AUMENTO = new BigDecimal("1.10");

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionarios();

        // 3.2 - Remove João da lista.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase("João"));

        // 3.3 - Imprime todos os funcionários.
        imprimirTitulo("Funcionários");
        funcionarios.forEach(Principal::imprimirFuncionario);

        // 3.4 - Aplica aumento de 10%, arredondando o resultado para centavos.
        funcionarios.forEach(funcionario -> funcionario.setSalario(
                funcionario.getSalario()
                        .multiply(FATOR_AUMENTO)
                        .setScale(2, RoundingMode.HALF_UP)));

        // 3.5 - LinkedHashMap mantém a ordem em que as funções aparecem na lista.
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        LinkedHashMap::new,
                        Collectors.toList()));

        // 3.6 - Imprime os funcionários agrupados por função.
        imprimirTitulo("Funcionários agrupados por função");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao + ":");
            lista.forEach(Principal::imprimirFuncionario);
        });

        // A numeração fornecida no enunciado pula do item 3.6 para o 3.8.

        // 3.8 - Aniversariantes dos meses de outubro e dezembro.
        imprimirTitulo("Aniversariantes de outubro e dezembro");
        funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(Principal::imprimirFuncionario);

        // 3.9 - Localiza e imprime o funcionário mais velho.
        imprimirTitulo("Funcionário com a maior idade");
        funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .ifPresent(funcionario -> {
                    int idade = Period.between(
                            funcionario.getDataNascimento(), LocalDate.now()).getYears();
                    System.out.printf("Nome: %s | Idade: %d anos%n",
                            funcionario.getNome(), idade);
                });

        // 3.10 - Imprime por ordem alfabética sem alterar a lista original.
        imprimirTitulo("Funcionários em ordem alfabética");
        funcionarios.stream()
                .sorted(Comparator.comparing(
                        Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .forEach(Principal::imprimirFuncionario);

        // 3.11 - Soma os salários após o aumento.
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        imprimirTitulo("Total dos salários");
        System.out.println("R$ " + formatarNumero(totalSalarios));

        // 3.12 - Calcula quantos salários mínimos cada funcionário recebe.
        imprimirTitulo("Salários mínimos por funcionário");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidade = funcionario.getSalario()
                    .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.printf("%s: %s salários mínimos%n",
                    funcionario.getNome(), formatarNumero(quantidade));
        });
    }

    // 3.1 - Insere os funcionários na mesma ordem da tabela do enunciado.
    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(novoFuncionario("Maria", 2000, 10, 18, "2009.44", "Operador"));
        funcionarios.add(novoFuncionario("João", 1990, 5, 12, "2284.38", "Operador"));
        funcionarios.add(novoFuncionario("Caio", 1961, 5, 2, "9836.14", "Coordenador"));
        funcionarios.add(novoFuncionario("Miguel", 1988, 10, 14, "19119.88", "Diretor"));
        funcionarios.add(novoFuncionario("Alice", 1995, 1, 5, "2234.68", "Recepcionista"));
        funcionarios.add(novoFuncionario("Heitor", 1999, 11, 19, "1582.72", "Operador"));
        funcionarios.add(novoFuncionario("Arthur", 1993, 3, 31, "4071.84", "Contador"));
        funcionarios.add(novoFuncionario("Laura", 1994, 7, 8, "3017.45", "Gerente"));
        funcionarios.add(novoFuncionario("Heloísa", 2003, 5, 24, "1606.85", "Eletricista"));
        funcionarios.add(novoFuncionario("Helena", 1996, 9, 2, "2799.93", "Gerente"));
        return funcionarios;
    }

    private static Funcionario novoFuncionario(String nome, int ano, int mes, int dia,
                                                String salario, String funcao) {
        return new Funcionario(
                nome,
                LocalDate.of(ano, mes, dia),
                new BigDecimal(salario),
                funcao);
    }

    private static void imprimirFuncionario(Funcionario funcionario) {
        System.out.printf(
                "Nome: %s | Nascimento: %s | Salário: R$ %s | Função: %s%n",
                funcionario.getNome(),
                funcionario.getDataNascimento().format(FORMATO_DATA),
                formatarNumero(funcionario.getSalario()),
                funcionario.getFuncao());
    }

    private static void imprimirTitulo(String titulo) {
        System.out.println("\n=== " + titulo + " ===");
    }

    private static DecimalFormat criarFormatoNumero() {
        DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance(new Locale("pt", "BR"));
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        formato.setRoundingMode(RoundingMode.HALF_UP);
        return formato;
    }

    private static String formatarNumero(BigDecimal valor) {
        return FORMATO_NUMERO.format(valor);
    }
}
