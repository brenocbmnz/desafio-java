package br.com.industria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Concentra as regras de negócio relacionadas aos funcionários.
 * A saída no console permanece sob responsabilidade da classe Principal.
 */
public class FuncionarioService {

    public List<Funcionario> criarFuncionarios() {
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

    public boolean removerPorNome(List<Funcionario> funcionarios, String nome) {
        return funcionarios.removeIf(
                funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
    }

    public void aplicarAumento(List<Funcionario> funcionarios, BigDecimal percentual) {
        BigDecimal fator = BigDecimal.ONE.add(
                percentual.divide(new BigDecimal("100")));

        funcionarios.forEach(funcionario -> funcionario.setSalario(
                funcionario.getSalario()
                        .multiply(fator)
                        .setScale(2, RoundingMode.HALF_UP)));
    }

    public Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    public List<Funcionario> buscarAniversariantes(
            List<Funcionario> funcionarios, Integer... meses) {
        List<Integer> mesesSelecionados = Arrays.asList(meses);
        return funcionarios.stream()
                .filter(funcionario -> mesesSelecionados.contains(
                        funcionario.getDataNascimento().getMonthValue()))
                .collect(Collectors.toList());
    }

    public Optional<Funcionario> buscarMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento));
    }

    public int calcularIdade(Funcionario funcionario, LocalDate dataReferencia) {
        return Period.between(funcionario.getDataNascimento(), dataReferencia).getYears();
    }

    public List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(
                        Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularQuantidadeSalariosMinimos(
            Funcionario funcionario, BigDecimal salarioMinimo) {
        return funcionario.getSalario()
                .divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    private Funcionario novoFuncionario(String nome, int ano, int mes, int dia,
                                        String salario, String funcao) {
        return new Funcionario(
                nome,
                LocalDate.of(ano, mes, dia),
                new BigDecimal(salario),
                funcao);
    }
}
