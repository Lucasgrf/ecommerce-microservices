# 📋 Kanban Board - Sprint 1: Fundação & Identidade

> **Legenda**:
> - 📝 `To Do`: Aguardando início.
> - 🚧 `In Progress`: Sendo trabalhado agora.
> - 🔍 `Code Review`: Pull Request aberto.
> - ✅ `Done`: Mergeado na develop/main.

---

---

## 📝 To Do

*(Aguardando novas Tasks de Implementação UC02)*

---

## 🚧 In Progress

*(Nenhuma task ativa no momento)*

---

## 🔍 Code Review

### PB-005: Setup Inicial do Product Service
- **Estimativa**: 1 dia
- **Branch**: `feat/PB-005-006-product-service` (PR Aberto)
- **Detalhes**: Criar projeto Spring Boot `product-service` ligado com MongoDB e ElasticSearch na porta 8082.

### PB-006: Contratos do Catálogo (Swagger/SDD)
- **Estimativa**: 2 dias
- **Branch**: `feat/PB-005-006-product-service` (PR Aberto)
- **Detalhes**: 
  - [x] Construir interface DTOs (`/api/v1/products`) focadas no UC02 (Busca e Filtro) via Swagger Design-First.

### PB-025: API Gateway Config
- **Estimativa**: 1 dia
- **Branch**: `feat/PB-025-api-gateway` (PR Aberto)
- **Detalhes**: Roteamento base usando Spring Cloud Gateway Netty 8080 -> 8081.

### PB-004: Configurança & Swagger OpenAPI
- **Estimativa**: 2 dias
- **Branch**: `feat/PB-004-swagger-security` (PR Aberto)
- **Detalhes**: 
  - [x] Configurar Spring Security Filter Chain e JWT Auth
  - [x] Extrair Specs e Criar Swagger Docs (`/v3/api-docs`).

---

## ✅ Done

### PB-003: Login e JWT (`/login`)
- **Concluído em**: 19/01/2026
- **Branch**: `feat/PB-003` (Merged)
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
