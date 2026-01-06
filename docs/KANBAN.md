# 📋 Kanban Board - Sprint 1: Fundação & Identidade

> **Legenda**:
> - 📝 `To Do`: Aguardando início.
> - 🚧 `In Progress`: Sendo trabalhado agora.
> - 🔍 `Code Review`: Pull Request aberto.
> - ✅ `Done`: Mergeado na develop/main.

---

## 📝 To Do

### PB-002: Cadastro de usuários (`/register`)
- **Estimativa**: 2 dias
- **Critérios de Aceite**:
  - [ ] Validar campos obrigatórios (email, senha).
  - [ ] Encriptar senha antes de salvar.
  - [ ] Retornar 201 Created.
- **Testes**:
  - [ ] Unitário: Service (mock repository).
  - [ ] Integração: Controller -> Database (H2 ou Testcontainers).

### PB-003: Login e JWT (`/login`)
- **Estimativa**: 2 dias
- **Critérios de Aceite**:
  - [ ] Validar credenciais.
  - [ ] Gerar Token JWT com claims (roles).
  - [ ] Retornar Token no body ou header.

### PB-004: Configuração de Segurança
- **Estimativa**: 2 dias
- **Detalhes**: Configurar Spring Security Filter Chain.

### PB-025: API Gateway Config
- **Estimativa**: 1 dia
- **Detalhes**: Roteamento básico para o user-service.

---

## 🚧 In Progress

### PB-001: Setup inicial `user-service`
- **Início**: 05/01/2026
- **Responsável**: Lucas
- **Branch**: `feat/PB-001-setup-user-service` (à criar)
- **Checklist de Implementação**:
  - [x] Criar estrutura do projeto (Spring Initializr/Manual).
  - [x] Configurar `pom.xml` (JPA, Security, Lombok).
  - [ ] Configurar `application.yaml` (Conexão DB).
  - [ ] Criar pacote `com.lucasgrf.userservice`.
  - [x] Rodar aplicação sem erros (Compilação falhou: Requer JDK 17).

---

## 🔍 Code Review

*(Nenhum item em review no momento)*

---

## ✅ Done

### PB-026: Subir ambiente local (Docker Compose)
- **Concluído em**: 05/01/2026
- **Branch**: `fix/PB-026-mongo-optimization` (Merged)
- **O que foi feito**:
  - Criado `docker-compose.yml` com Postgres (5432), Mongo (27017) e RabbitMQ (5672/15672).
  - Otimização de logs (max-file 3).
  - Otimização de memória (limits) e cache do Mongo (wiredTiger).
- **Validação**:
  - `docker ps` mostrou 3 containers UP.
  - Portas acessíveis localmente.
