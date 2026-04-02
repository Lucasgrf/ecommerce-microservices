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

---

## ✅ Production Hardening (Sprints 1-7)

### PB-034: Documentation Polish & Final Hardening
- **Done**: Abril/2026
- [x] README rewritten in English with architecture diagram, API docs table, getting-started guide
- [x] Added springdoc/Swagger config to all services
- [x] Standardized springdoc version to 2.8.6 across all services
- [x] KANBAN updated with production hardening work

### PB-033: Integration Tests (Testcontainers)
- **Done**: Abril/2026
- [x] PostgreSQL Testcontainers for product-service (ProductDatabaseGatewayIntegrationTest)
- [x] MongoDB Testcontainers for order-service (OrderGatewayIntegrationTest)
- [x] Testcontainers dependencies added to product-service, order-service, notification-service
- [x] CI pipeline updated: `mvn test` → `mvn verify`, actions/checkout@v4, fail-fast: false

### PB-032: Observability & Monitoring
- **Done**: Abril/2026
- [x] Spring Actuator added to product-service, order-service, notification-service
- [x] Micrometer Prometheus registry added to all services
- [x] Standardized management config (health probes, info, prometheus endpoint)
- [x] Fixed: 4 missing gateway routes (categories, stock, cart, payments)

### PB-031: Resilience4j Inter-Service Communication
- **Done**: Abril/2026
- [x] CircuitBreaker + Retry on ProductServiceAdapter (productService instance)
- [x] CircuitBreaker + Retry on MelhorEnvioAdapter (shippingService instance)
- [x] Resilience4j configured in order-service application.yaml
- [x] Replaced silent catch(Exception) blocks with structured fallbacks + logging

### PB-030: Redis Product Caching
- **Done**: Abril/2026
- [x] spring-boot-starter-data-redis added to product-service
- [x] RedisCacheConfig with TTLs: products (15 min), categories (1 hour)
- [x] @Cacheable on ProductDatabaseGateway.findById()
- [x] @CacheEvict on save() and delete() methods
- [x] @Cacheable on CategoryDatabaseGateway.findAll()

### PB-029: Infrastructure Fixes & Configuration Hardening
- **Done**: Abril/2026
- [x] Fixed YAML bug: spring.data.mongodb/redis was outside spring root in order-service
- [x] Fixed Feign URL bug: ProductServiceClient pointed to port 8081 (user-service), not 8082
- [x] Fixed Feign path bug: /products/{id} → /api/v1/products/{id}
- [x] Externalized JWT secret in api-gateway to ${JWT_SECRET}
- [x] Added MongoDB container to local docker-compose.yml (was missing — broke local dev)
- [x] Completed .env.dist with all 15+ missing variables (Redis, JWT, SMTP, MP, Melhor Envio)
- [x] Externalized all hardcoded config in notification-service
- [x] Removed git tracking of debug files; improved .gitignore
- [x] Updated POM descriptions for all 5 services

