# 📋 Kanban Board - Sprint 1: Fundação & Identidade

> **Legenda**:
> - 📝 `To Do`: Aguardando início.
> - 🚧 `In Progress`: Sendo trabalhado agora.
> - 🔍 `Code Review`: Pull Request aberto.
> - ✅ `Done`: Mergeado na develop/main.

---

## 📝 To Do


### PB-003: Login e JWT (`/login`)
- **Estimativa**: 2 dias
- **Critérios de Aceite**:
  - [ ] Validar credenciais.
  - [ ] Gerar Token JWT com claims (roles).
  - [ ] Retornar Token no body ou header.


### PB-025: API Gateway Config
- **Estimativa**: 1 dia
- **Detalhes**: Roteamento básico para o user-service.

---

## 🚧 In Progress

*(Nenhum item em progresso no momento)*

---

## 🔍 Code Review


### PB-001: Setup inicial `user-service`
- **Início**: 06/01/2026
- **Responsável**: Guilherme
- **Branch**: `feat/PB-001-setup-user-service`
- **Checklist de Implementação**:
  - [x] Criar estrutura do projeto (Spring Initializr/Manual).
  - [x] Configurar `pom.xml` (JPA, Security, Lombok).
  - [x] Configurar `application.yaml` (Conexão DB).
  - [x] Criar pacote `com.lucasgrf.userservice`.
  - [x] Rodar aplicação sem erros.


### PB-004: Configuração de Segurança (Spring Security)
- **Estimativa**: 3 dias
- **Branch**: `feat/PB-004-security-config`
- **Critérios de Aceite**:
  - [x] Implementar `JwtAuthenticationFilter`.
  - [x] Validar token em rotas protegidas.
  - [x] Configurar sessão como STATELESS.
- **Testes**:
  - [x] Unitário: Filter.
  - [x] Integração: Tentar acessar rota sem token (403) (Manual).


### PB-001: Setup inicial `user-service`
- **Início**: 05/01/2026
- **Responsável**: Lucas
- **Branch**: `feat/PB-001-setup-user-service`
- **Checklist de Implementação**:
  - [x] Criar estrutura do projeto (Spring Initializr/Manual).
  - [x] Configurar `pom.xml` (JPA, Security, Lombok).
  - [x] Configurar `application.yaml` (Conexão DB).
  - [x] Criar pacote `com.lucasgrf.userservice`.
  - [x] Rodar aplicação sem erros.
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
