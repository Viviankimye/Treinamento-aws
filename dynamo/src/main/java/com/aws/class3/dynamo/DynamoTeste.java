package com.aws.class3.dynamo;

import java.util.UUID;

public class DynamoTeste {
    public static void main(String[] args) {
        AlunoRepository repo = new AlunoRepository();

        Aluno aluno = new Aluno();
        aluno.setId(UUID.randomUUID().toString());
        aluno.setNome("Vivian Teste");
        aluno.setEmail("vivian@teste.com");
        aluno.setIdade(28);

        repo.salvar(aluno);
        System.out.println("Aluno salvo: " + aluno.getId());

        Aluno encontrado = repo.buscarPorId(aluno.getId());
        System.out.println("Aluno encontrado: " + encontrado.getNome());

        System.out.println("Total de alunos: " + repo.listarTodos().size());
    }
}