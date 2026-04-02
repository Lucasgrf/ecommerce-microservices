# 📋 Kanban Board - Sprint 1-6 (Arquitetura & DevOps)

> **Legenda**:
> - 📝 `Done`: Aguardando início.
> - 🚧 `In Progress`: Sendo trabalhado agora.
> - 🔍 `Code Review`: Pull Request aberto.
> - ✅ `Done`: Mergeado na develop/main.

---

## 📝 Done

*(Aguardando Definição das Camadas de Apresentação Frontend)*

---

## 🚧 In Progress

*(Nenhuma task ativa no momento)*

---

## 🔍 Code Review

*(Nenhuma task aguardando review)*

---

## ✅ Done

### PB-000: Clean Architecture & Domain Driven Design
- **Concluído em**: Abril/2026
- **Detalhes**:
  - [x] Refatoração completa dos domínios em `user-service`, `product-service`, `order-service`, `notification-service`.
  - [x] Extração de Value Objects com validação Self-Contained.
  - [x] Extração e implementação das portas de repositórios/mensageria.

### PB-025: API Gateway & Centralização JWT Auth
- **Concluído em**: Abril/2026
- **Detalhes**:
  - [x] Roteamento para todos os microserviços (User, Order, Product, Notification).
  - [x] Validação JWT Centralizada via `JwtAuthenticationGatewayFilterFactory`.
  - [x] Propagação Segura Interna (Injeção de Headers `X-User-Id`, `X-User-Roles`).
  - [x] Filtros Zero-Trust Downstream na Controller Layer (`@PreAuthorize`).
  
### PB-011 & PB-022: Mensageria e Assincronicidade (RabbitMQ)
- **Concluído em**: Abril/2026
- **Detalhes**:
  - [x] Mensageria inter-serviços no `order-service` -> `notification-service`.
  - [x] Desacoplamento através de Domain Events e EventStore patterns.

### PB-027: DevOps, Infraestrutura Local & CI/CD
- **Concluído em**: Abril/2026
- **Detalhes**:
  - [x] Construção Múltipla `Dockerfile` focada em restrição de Alpine Memory limit (`eclipse-temurin:17-jre-alpine`).
  - [x] Refatoração do `docker-compose.yml` local e de produção (`docker-compose.prod.yml`).
  - [x] GitHub Actions Workflows `.github/workflows/ci.yml` e `cd.yml` para push auto AWS EC2.

### PB-003: Login e JWT (`/login`)
- **Concluído**: Janeiro/2026

### PB-002: Cadastro de usuários (`/register`)
- **Concluído**: Janeiro/2026

### PB-001: Setup inicial `user-service`
- **Concluído**: Janeiro/2026

