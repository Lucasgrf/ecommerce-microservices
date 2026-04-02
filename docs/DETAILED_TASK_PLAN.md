# 📋 Plano Detalhado das Tasks

O que a gente vai fazer agora, alinhado com o `KANBAN.md` e os arquivos de regras `XP_METHODOLOGY.md` e `SDD_METHODOLOGY.md`, tá anotado aqui pra gente não se perder nessa Sprint 1.

**Regrinha sagrada:** Nada de codar feature nova sem antes desenhar no Swagger (SDD) e sem fazer o esquema do Red-Green-Refactor (XP).

## Fase 1: Fundação & Identidade (Nossa Sprint Atual)

### 1. Fechar a Configuração de Segurança (Já tá rolando)
- **Task lá no Kanban:** PB-004
- **Serviço:** `user-service`
- **O que é:** Configurar o `SecurityFilterChain` pra checar token JWT e trancar as rotas certinho.
- **Passos (XP/TDD):**
  1. Arrumar testes de integração (jogando com e sem token).
  2. Finalizar o filtro do JWT (pegar os dados, ver se expirou).
  3. Liberar as rotas de deboa tipo `/register` e `/login` e trancar o resto.
  4. Refatorar tudo até ficar verdinho nos testes.

### 2. Contrato da API e Swagger (Feito! ✅)
- **Task Kanban:** PB-0XX
- **Serviço:** `user-service`
- **O que é:** Colocar o Swagger focado no Design-First (SDD).
- **Passos que a gente fez:**
  1. Definimos os contratos antes de fechar o código do Controller.
  2. Configurou o bean global do Swagger pra aceitar o Bearer Token.
  3. Socamos anotações de erro e sucesso pra já deixar a Spec clara.
  4. Testamos a UI pra garantir que n tava dando pau de 500 no Springdoc (e ajeitamos a treta do cors/auth no `SecurityConfig`).

### 3. API Gateway (Próximo)
- **Task Kanban:** PB-025
- **Serviço:** `api-gateway`
- **O que é:** Fazer a rota central da parada usando o Spring Cloud Gateway.
- **Passos:**
  1. Iniciar o projeto no Spring Initializr.
  2. Arrumar o YAML pro `/api/users/**` apontar pra porta certa do `user-service`.
  3. Fazer bater certinho na rota testando no Postman ou afins.

---

## Fase 2: Catálogo de Produtos (Sprint 2)

Agora o foco é o `product-service`. A gente precisa entregar o Caso de Uso UC02 (Catálogo e Busca de Produtos).

### 4. Setup Inicial do Product Service
- **Task Kanban:** PB-005
- **Serviço:** `product-service`
- **O que é:** Criar a fundação do microserviço de produtos conectando com banco (PostgreSQL ou MongoDB, avaliar necessidade de NoSQL pelo JSON de variações) e registrar ele no Gateway.
- **Passos (XP/TDD):**
  1. Gerar projeto Spring Boot (`web`, `data-jpa/mongodb`, `validation`).
  2. Arrumar `application.yaml` para rodar na porta `8082`.
  3. Ajustar o `api-gateway` pra passar a rotear `/api/v1/products/**` pra ele.

### 5. Spec do Catálogo (Swagger/SDD)
- **Task Kanban:** PB-006
- **Serviço:** `product-service`
- **O que é:** Fazer o Design-First (SDD) das rotas de busca e visualização de detalhes (UC02).
- **Passos:**
  1. Desenhar a interface do Controller definindo endpoints de listagem paginada (`GET /api/v1/products`) e detalhes (`GET /api/v1/products/{id}`).
  2. Documentar filtros (`?category=X`, `?priceMax=Y`) e DTOs de retorno (fotos, variações de cor/tamanho).
  3. Checar a UI do Swagger na 8082 pra ver se o contrato tá certinho.

