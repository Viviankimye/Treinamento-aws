package com.aws.class3.dynamo;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Aluno criar(@RequestBody Aluno aluno) {
        aluno.setId(UUID.randomUUID().toString());
        repository.salvar(aluno);
        return aluno;
    }

    @GetMapping
    public List<Aluno> listarTodos() {
        return repository.listarTodos();
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable String id) {
        return repository.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        repository.deletar(id);
    }
}
