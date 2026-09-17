package br.com.industria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Principal {
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATO_NUMERO = criarFormatoNumero();
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("10");

    public static void main(String[] args) {
        FuncionarioService service = new FuncionarioService();

        // 3.1 - Insere os funcionários na mesma ordem da tabela do enunciado.
        List<Funcionario> funcionarios = service.criarFuncionarios();

        // 3.2 - Remove João da lista.
        service.removerPorNome(funcionarios, "João");

        // 3.3 - Imprime todos os funcionários.
        imprimirTitulo("Funcionários");
        funcionarios.forEach(Principal::imprimirFuncionario);

        // 3.4 - Aplica aumento de 10%, arredondando o resultado para centavos.
        service.aplicarAumento(funcionarios, PERCENTUAL_AUMENTO);

        // 3.5 - LinkedHashMap mantém a ordem em que as funções aparecem na lista.
        Map<String, List<Funcionario>> funcionariosPorFuncao =
                service.agruparPorFuncao(funcionarios);

        // 3.6 - Imprime os funcionários agrupados por função.
        imprimirTitulo("Funcionários agrupados por função");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao + ":");
            lista.forEach(Principal::imprimirFuncionario);
        });

        // A numeração fornecida no enunciado pula do item 3.6 para o 3.8.

        // 3.8 - Aniversariantes dos meses de outubro e dezembro.
        imprimirTitulo("Aniversariantes de outubro e dezembro");
        service.buscarAniversariantes(funcionarios, 10, 12).stream()
                .forEach(Principal::imprimirFuncionario);

        // 3.9 - Localiza e imprime o funcionário mais velho.
        imprimirTitulo("Funcionário com a maior idade");
        service.buscarMaisVelho(funcionarios)
                .ifPresent(funcionario -> {
                    int idade = service.calcularIdade(funcionario, LocalDate.now());
                    System.out.printf("Nome: %s | Idade: %d anos%n",
                            funcionario.getNome(), idade);
                });

        // 3.10 - Imprime por ordem alfabética sem alterar a lista original.
        imprimirTitulo("Funcionários em ordem alfabética");
        service.ordenarPorNome(funcionarios).stream()
                .forEach(Principal::imprimirFuncionario);

        // 3.11 - Soma os salários após o aumento.
        BigDecimal totalSalarios = service.calcularTotalSalarios(funcionarios);
        imprimirTitulo("Total dos salários");
        System.out.println("R$ " + formatarNumero(totalSalarios));

        // 3.12 - Calcula quantos salários mínimos cada funcionário recebe.
        imprimirTitulo("Salários mínimos por funcionário");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidade = service.calcularQuantidadeSalariosMinimos(
                    funcionario, SALARIO_MINIMO);
            System.out.printf("%s: %s salários mínimos%n",
                    funcionario.getNome(), formatarNumero(quantidade));
        });
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
