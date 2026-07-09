# # ViaCEP Lambda

Função AWS Lambda em Java que consulta endereços a partir de um CEP, utilizando a API pública do [ViaCEP](https://viacep.com.br/).

## 📋 Sobre o projeto

Essa Lambda recebe um CEP via requisição HTTP (Function URL), consulta a API do ViaCEP e retorna o endereço completo (rua, bairro, cidade, estado, etc.) em formato JSON.

## 🛠️ Tecnologias utilizadas

- **Java 21**
- **Maven** (gerenciamento de dependências e build)
- **AWS Lambda** (execução serverless)
- **Jackson Databind** `2.22.0` (serialização/desserialização JSON)
- **AWS Lambda Java Core** `1.2.3`
- **Maven Shade Plugin** (empacotamento em fat jar)
- **API ViaCEP** (consulta de endereços)

## 📁 Estrutura do projeto

```
viacep-lambda/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    ├── Handler.java        # Ponto de entrada da Lambda
                    └── CepResponse.java     # Modelo de resposta do ViaCEP
```

## ⚙️ Como funciona

1. A Lambda recebe o evento de invocação (via Function URL ou API Gateway)
2. Extrai o campo `cep` do corpo (`body`) da requisição
3. Valida se o CEP contém 8 dígitos
4. Faz uma chamada HTTP para a API do ViaCEP: `https://viacep.com.br/ws/{cep}/json/`
5. Converte a resposta em um objeto `CepResponse`
6. Retorna o resultado em formato JSON, com o tempo de execução da requisição (`requestTimeMs`)

## 🔧 Configuração da função na AWS

| Configuração | Valor |
|---|---|
| **Runtime** | Java 21 |
| **Handler** | `com.example.Handler::handleRequest` |
| **Arquitetura** | x86_64 |
| **Memória** | 512 MB |

## 🚀 Build do projeto

Para gerar o `.jar` pronto para deploy na AWS Lambda:

```bash
mvn clean package
```

O arquivo final (com todas as dependências empacotadas) será gerado em:

```
target/viacep-lambda-1.0.0-shaded.jar
```

Esse `.jar` deve ser enviado via upload no console da AWS Lambda, na aba **Code**.

## 📡 Como testar

### Via Function URL (Postman ou cURL)

**Método:** `POST`

**URL:** sua Function URL (disponível no console da Lambda, em *Function overview*)

**Body (raw, JSON):**
```json
{
  "cep": "01310-100"
}
```

**Exemplo via cURL:**
```bash
curl -X POST https://SUA-FUNCTION-URL.lambda-url.us-east-2.on.aws/ \
  -d "{\"cep\": \"01310-100\"}"
```

### Via console da AWS (evento de teste)

Na aba **Test**, crie um evento com o seguinte corpo:
```json
{
  "cep": "01310-100"
}
```

### Resposta esperada

```json
{
  "statusCode": 200,
  "headers": {
    "Content-Type": "application/json"
  },
  "body": "{\"cep\":\"01310-100\",\"logradouro\":\"Avenida Paulista\",\"bairro\":\"Bela Vista\",\"localidade\":\"São Paulo\",\"uf\":\"SP\",\"estado\":\"São Paulo\",\"regiao\":\"Sudeste\",\"ibge\":\"3550308\",\"ddd\":\"11\",\"siafi\":\"7107\",\"requestTimeMs\":95}"
}
```

## ⚠️ Tratamento de erros

| Situação | Status Code | Retorno |
|---|---|---|
| CEP não informado | `400` | `{"erro": "Parametro 'cep' nao informado."}` |
| CEP com formato inválido (≠ 8 dígitos) | `400` | `{"erro": "CEP invalido. Deve conter 8 digitos."}` |
| Erro ao consultar o ViaCEP | `500` | `{"erro": "Erro ao consultar ViaCEP: ..."}` |

## 🔮 Próximos passos (melhorias futuras)

- [ ] Suportar requisições `GET` com o CEP na URL (ex: `/cep/01310100`)
- [ ] Implementar cache para evitar chamadas repetidas ao ViaCEP
- [ ] Adicionar testes automatizados (JUnit)
- [ ] Conectar a um API Gateway com documentação OpenAPI/Swagger

## 👩‍💻 Autora

Projeto desenvolvido por Vivian como parte de estudos práticos de AWS Lambda, Java e arquitetura serverless.
