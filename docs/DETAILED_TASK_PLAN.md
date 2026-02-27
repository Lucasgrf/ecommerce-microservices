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

## Fase 2: O que vem depois

Depois que a gente matar essa parte de autenticação na Sprint 1, a gente vai usar a mesma moral de XP para fazer o `product-service`. O Swagger feito logo de cara vai salvar mó tempo na hora de ligar com um Front depois.
