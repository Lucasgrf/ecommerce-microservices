# Spec Driven Development (SDD) - Anotações

## 1. O que é essa parada de SDD?
Então, o Spec Driven Development (ou desenvolvimento orientado a especificação) é basicamente a gente abraçar o **API-First Design**. A ideia é simples: a gente primeiro define o contrato da API (a tal da "Spec") bonitinho no papel (ou melhor, no Swagger) **antes** de sair codando igual doido. 

Isso quer dizer que, pros nossos microsserviços REST, a gente vai usar o **OpenAPI (Swagger)** como a nossa única fonte da verdade. O que tá lá é o que vale.

## 2. Como isso se mistura com o XP e TDD que a gente já usa?
Bom, o SDD não vai matar o XP nem o TDD, muito pelo contrário, eles vão trampar juntos:

1. **SDD (O que fazer):** A gente define o contrato. Quais rotas vão ter? O que vai no body? Quais códigos HTTP vão voltar (200, 400, 500...)?
2. **TDD (Como testar):** A gente escreve os testes de integração (a fase Red) tudo com base nesse contrato que acabamos de fechar.
3. **XP (Como codar):** A gente continua no fluxo de refatoração e CI pra fazer o código mais simples que faça o teste passar.

## 3. Como vai ser na prática?

Sempre que a gente for fazer uma funcionalidade nova na API, o roteiro é esse:

### Passo 1: O Design da Spec (Design First)
- Antes de criar `Controllers` e tals, a gente define a interface lá no Swagger.
- A gente pode usar anotações do tipo `@Tag`, `@Operation`, `@ApiResponses` pra já deixar tudo desenhado antes de fazer a lógica de verdade.

### Passo 2: Mostrar pra galera (Frontend/QA)
- Com o contrato gerado, a gente já passa pro pessoal do Frontend. Eles já conseguem mockar as chamadas e ir adiantando o lado deles.

### Passo 3: TDD neles
- Fazemos os testes de integração (`MockMvc`) pra bater exatamente nessas rotas desenhadas, mandando os dados certos e esperando as respostas combinadas.

### Passo 4: Código e Refatoração
- Agora sim a gente escreve a lógica de Clean Architecture/DDD até a barra do teste ficar verde.

## 4. O que a gente vai usar?
- **Springdoc OpenAPI:** Pra garantir que a nossa app sempre mostre a Spec atualizada pelo `/swagger-ui.html` ou `/api-docs`.
- **Postman / Insomnia:** Pra testar se a API tá respeitando o que a gente prometeu no contrato.

## 5. Regrinhas de Ouro pra não dar ruim
1. **Nunca mude o contrato sem avisar.** Quebrou a resposta? Faz uma versão nova (tipo `/api/v2/...`). Na moral, não quebra quem já tá consumindo.
2. **Documentação tem que ser atualizada.** Todo endpoint tem que ter os possíveis retornos documentados (200, 400, 401...).
3. Nenhuma feature tá "Pronta" se o Swagger tá mentindo sobre o que o código faz de verdade.

