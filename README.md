# Desafio Java — Funcionários

Solução para o teste prático de gerenciamento de funcionários de uma indústria.
O projeto foi desenvolvido com Java 17, Maven, orientação a objetos, Streams,
coleções e testes automatizados com JUnit 5.

## Requisitos atendidos

- cadastro dos funcionários na ordem apresentada no enunciado;
- remoção do funcionário João;
- exibição de datas e valores no formato brasileiro;
- aumento salarial de 10%;
- agrupamento dos funcionários por função em um `Map`;
- exibição dos grupos por função;
- filtro dos aniversariantes de outubro e dezembro;
- identificação do funcionário mais velho;
- ordenação alfabética sem modificar a lista original;
- soma de todos os salários após o aumento;
- cálculo da quantidade de salários mínimos de cada funcionário.

> O enunciado não apresenta o item 3.7 e passa diretamente do 3.6 para o 3.8.

## Estrutura do projeto

```text
src/
├── main/java/br/com/industria/
│   ├── Pessoa.java
│   ├── Funcionario.java
│   ├── FuncionarioService.java
│   └── Principal.java
└── test/java/br/com/industria/
    └── FuncionarioServiceTest.java
```

- `Pessoa`: representa os dados comuns de uma pessoa;
- `Funcionario`: herda de `Pessoa` e acrescenta salário e função;
- `FuncionarioService`: concentra os cadastros e as regras de negócio;
- `Principal`: orquestra os requisitos e apresenta o resultado no console;
- `FuncionarioServiceTest`: valida automaticamente as principais regras.

As pastas seguem a convenção do Maven. O caminho `br/com/industria` corresponde
ao pacote Java `br.com.industria` usado nas classes.

## Decisões técnicas

- `LocalDate` e `Period` são utilizados para datas e cálculo de idade;
- `BigDecimal` evita a imprecisão de `double` em valores monetários;
- `RoundingMode.HALF_UP` arredonda os salários para centavos após o aumento;
- `LinkedHashMap` agrupa por função preservando a ordem original dos grupos;
- Streams são utilizados nos filtros, agrupamentos, ordenação e totalização;
- as regras foram separadas da apresentação para tornar o código reutilizável
  e testável.

## Pré-requisitos

- JDK 17 ou superior;
- Apache Maven 3.8 ou superior.

Para confirmar as versões instaladas:

```bash
java -version
mvn -version
```

## Como executar

Na raiz do projeto, execute:

```bash
mvn clean compile
mvn exec:java
```

Também é possível importar a pasta como projeto Maven no Eclipse, IntelliJ IDEA,
NetBeans ou VS Code e executar o método `main` da classe `Principal`.

## Como executar os testes

```bash
mvn test
```

Os testes verificam o cadastro inicial, a remoção de João, o aumento salarial,
o agrupamento por função, os aniversariantes, o funcionário mais velho, a
ordenação alfabética, o total dos salários e o cálculo em salários mínimos.
