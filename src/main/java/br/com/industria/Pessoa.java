package br.com.industria;

import java.time.LocalDate;
import java.util.Objects;

public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = Objects.requireNonNull(nome, "O nome não pode ser nulo");
        this.dataNascimento = Objects.requireNonNull(
                dataNascimento, "A data de nascimento não pode ser nula");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Objects.requireNonNull(nome, "O nome não pode ser nulo");
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = Objects.requireNonNull(
                dataNascimento, "A data de nascimento não pode ser nula");
    }
}
