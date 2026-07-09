package com.aws.class3.dynamo;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepository {

    private final DynamoDbTable<Aluno> table;

    public AlunoRepository() {
        DynamoDbClient client = DynamoDbConfig.getClient();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

        this.table = enhancedClient.table("academia-java-aws", TableSchema.fromBean(Aluno.class));
    }

    public void salvar(Aluno aluno) {
        table.putItem(aluno);
    }

    public Aluno buscarPorId(String id) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        table.scan().items().forEach(alunos::add);
        return alunos;
    }

    public void deletar(String id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}