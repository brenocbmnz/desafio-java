package br.com.industria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FuncionarioServiceTest {
    private FuncionarioService service;
    private List<Funcionario> funcionarios;

    @BeforeEach
    void prepararCenario() {
        service = new FuncionarioService();
        funcionarios = service.criarFuncionarios();
    }

    @Test
    @DisplayName("Deve cadastrar todos os funcionários na ordem do enunciado")
    void deveCriarFuncionariosNaOrdemDoEnunciado() {
        assertEquals(10, funcionarios.size());
        assertEquals("Maria", funcionarios.get(0).getNome());
        assertEquals("João", funcionarios.get(1).getNome());
        assertEquals("Helena", funcionarios.get(9).getNome());
    }

    @Test
    @DisplayName("Deve remover João sem afetar os demais funcionários")
    void deveRemoverJoao() {
        boolean removeu = service.removerPorNome(funcionarios, "João");

        assertTrue(removeu);
        assertEquals(9, funcionarios.size());
        assertFalse(funcionarios.stream()
                .anyMatch(funcionario -> funcionario.getNome().equals("João")));
    }

    @Test
    @DisplayName("Deve aplicar aumento de dez por cento com precisão monetária")
    void deveAplicarAumentoDeDezPorCento() {
        service.aplicarAumento(funcionarios, new BigDecimal("10"));

        assertEquals(new BigDecimal("2210.38"), funcionarios.get(0).getSalario());
        assertEquals(new BigDecimal("10819.75"), funcionarios.get(2).getSalario());
    }

    @Test
    @DisplayName("Deve agrupar os funcionários por função")
    void deveAgruparPorFuncao() {
        service.removerPorNome(funcionarios, "João");

        Map<String, List<Funcionario>> grupos = service.agruparPorFuncao(funcionarios);

        assertEquals(7, grupos.size());
        assertEquals(Arrays.asList("Maria", "Heitor"), Arrays.asList(
                grupos.get("Operador").get(0).getNome(),
                grupos.get("Operador").get(1).getNome()));
        assertEquals(2, grupos.get("Gerente").size());
    }

    @Test
    @DisplayName("Deve encontrar aniversariantes de outubro e dezembro")
    void deveEncontrarAniversariantes() {
        List<Funcionario> aniversariantes =
                service.buscarAniversariantes(funcionarios, 10, 12);

        assertEquals(Arrays.asList("Maria", "Miguel"), Arrays.asList(
                aniversariantes.get(0).getNome(),
                aniversariantes.get(1).getNome()));
    }

    @Test
    @DisplayName("Deve encontrar Caio como o funcionário mais velho")
    void deveEncontrarFuncionarioMaisVelho() {
        Funcionario maisVelho = service.buscarMaisVelho(funcionarios).orElseThrow(
                () -> new AssertionError("A lista deveria possuir um funcionário"));

        assertEquals("Caio", maisVelho.getNome());
        assertEquals(65, service.calcularIdade(maisVelho, LocalDate.of(2026, 9, 17)));
    }

    @Test
    @DisplayName("Deve ordenar por nome sem modificar a lista original")
    void deveOrdenarSemModificarListaOriginal() {
        service.removerPorNome(funcionarios, "João");

        List<Funcionario> ordenados = service.ordenarPorNome(funcionarios);

        assertNotSame(funcionarios, ordenados);
        assertEquals("Maria", funcionarios.get(0).getNome());
        assertEquals("Alice", ordenados.get(0).getNome());
        assertEquals("Miguel", ordenados.get(8).getNome());
    }

    @Test
    @DisplayName("Deve calcular o total após remoção e aumento")
    void deveCalcularTotalDosSalarios() {
        service.removerPorNome(funcionarios, "João");
        service.aplicarAumento(funcionarios, new BigDecimal("10"));

        BigDecimal total = service.calcularTotalSalarios(funcionarios);

        assertEquals(new BigDecimal("50906.82"), total);
    }

    @Test
    @DisplayName("Deve calcular quantos salários mínimos recebe o funcionário")
    void deveCalcularQuantidadeDeSalariosMinimos() {
        Funcionario maria = funcionarios.get(0);

        BigDecimal quantidade = service.calcularQuantidadeSalariosMinimos(
                maria, new BigDecimal("1212.00"));

        assertEquals(new BigDecimal("1.66"), quantidade);
    }
}
