# Desafio Java — Funcionários

Solução do teste prático usando Java 8+, `LocalDate`, `BigDecimal`, Streams e coleções.

## Estrutura

- `Pessoa`: nome e data de nascimento;
- `Funcionario`: herda de `Pessoa` e acrescenta salário e função;
- `Principal`: cadastra os dados e executa todos os itens do enunciado.

## Como executar

É necessário ter um JDK e o Maven instalados. Na raiz do projeto, execute:

```bash
mvn compile exec:java
```

Também é possível importar a pasta como projeto Maven no Eclipse, IntelliJ IDEA ou
NetBeans e executar o método `main` da classe `Principal`.

Os salários são armazenados em `BigDecimal`. O aumento de 10% é arredondado para
duas casas decimais com `RoundingMode.HALF_UP`, por se tratar de valores monetários.
