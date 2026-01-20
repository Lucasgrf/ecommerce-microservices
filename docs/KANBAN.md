# 📋 Kanban Board - Sprint 1: Fundação & Identidade

> **Legenda**:
> - 📝 `To Do`: Aguardando início.
> - 🚧 `In Progress`: Sendo trabalhado agora.
> - 🔍 `Code Review`: Pull Request aberto.
> - ✅ `Done`: Mergeado na develop/main.

---

## 📝 To Do

### PB-025: API Gateway Config
- **Estimativa**: 1 dia
- **Detalhes**: Roteamento básico para o user-service.

---

## 🚧 In Progress

### PB-004: Configuração de Segurança
- **Estimativa**: 2 dias
- **Detalhes**: Configurar Spring Security Filter Chain.

---

## 🔍 Code Review

*(Nenhum item em review no momento)*

---

## ✅ Done

### PB-003: Login e JWT (`/login`)
- **Concluído em**: 19/01/2026
- **Branch**: `feat/PB-003` (Pending)
- **O que foi feito**:
  - [x] Validar credenciais.
  - [x] Gerar Token JWT com claims (roles).
  - [x] Retornar Token no body ou header.

### PB-002: Cadastro de usuários (`/register`)
- **Concluído em**: 19/01/2026
- **Branch**: `feat/PB-002` (Merged)
- **O que foi feito**:
  - [x] Validar campos obrigatórios (email, senha).
  - [x] Encriptar senha antes de salvar.
  - [x] Retornar 201 Created.
  - [x] Testes Unitários (Service).

### PB-001: Setup inicial `user-service`
- **Concluído em**: 19/01/2026
- **Branch**: `feat/PB-001-setup-user-service` (Merged)
- **O que foi feito**:
  - [x] Criar estrutura do projeto (Spring Initializr/Manual).
  - [x] Configurar `pom.xml` (JPA, Security, Lombok).
  - [x] Configurar `application.yaml` (Conexão DB).
  - [x] Criar pacote `com.lucasgrf.userservice`.
  - [x] Rodar aplicação sem erros.

### PB-026: Subir ambiente local (Docker Compose)
- **Concluído em**: 05/01/2026
- **Branch**: `fix/PB-026-mongo-optimization` (Merged)
- **O que foi feito**:
  - [x] Criado `docker-compose.yml` com Postgres (5432), Mongo (27017) e RabbitMQ (5672/15672).
  - [x] Otimização de logs e memória.
