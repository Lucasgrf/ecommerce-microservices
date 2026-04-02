# 🛍️ E-commerce Microservices

> **Status do Projeto**: 🎉 Completo (Todos os Serviços Implementados e Kubernetes/Cloud Ready)

Sistema de E-commerce escalável construído com arquitetura de Microserviços, focado em boas práticas de engenharia de software, alta performance e separação de responsabilidades (Clean Architecture e DDD).

## 🚀 Tecnologias

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3.x
- **Bancos de Dados**: PostgreSQL (Relacional), MongoDB (NoSQL)
- **Cache**: Redis
- **Mensageria**: RabbitMQ
- **Infraestrutura**: Docker Multi-stage & GitHub Actions (CI/CD)
- **Segurança**: API Gateway com JJWT e Propagação de Headers de Autenticação Interna (Zero Trust)

## 📂 Estrutura do Projeto

O projeto é organizado como um monorepo contendo os seguintes serviços e documentações:

```bash
ecommerce-microservices/
├── api-gateway/          # Porta de entrada com JWT WebFilter (Routing & Security) [COMPLETO]
├── user-service/         # Gestão de Usuários e Perfis [COMPLETO]
├── product-service/      # Catálogo de Produtos e Caching (Redis) [COMPLETO]
├── order-service/        # Processamento e Fechamento de Pedidos (Mensageria) [COMPLETO]
├── notification-service/ # Envio Assíncrono de E-mails via RabbitMQ [COMPLETO]
├── infrastructure/       # Configuração de Deploy Local & Produção (Docker)
└── docs/                 # Documentação Abrangente do Projeto
```

## 📚 Documentação e Planejamento

- [📋 Kanban Board](docs/KANBAN.md) - Progresso e tarefas consolidadas.
- [🗓️ Sprints Roadmap](docs/SPRINTS.md) - Roteiro de entregas iterativas.
- [📝 Implementation Plan](docs/ECOMMERCE_IMPLEMENTATION_PLAN.md) - Manual extenso de arquitetura.

## 🛠️ Como rodar o projeto

### Pré-requisitos
- Docker & Docker Compose
- Java 17+
- Maven (Opcional, `mvnw` incluso)

### 1. Subir Localmente (Desenvolvimento)
Execute o comando abaixo para iniciar todas as dependências (Postgres, Mongo, RabbitMQ, Redis, Mailhog) na sua máquina:

```bash
docker-compose -f infrastructure/docker/docker-compose.yml up -d
```
Após isso, você pode levantar cada serviço via `./mvnw spring-boot:run`.

### 2. Deploy em Produção (AWS Free Tier / EC2)
Existe um Pipeline CI/CD em `.github/workflows/cd.yml` programado para compilar os serviços e fazer o push para o EC2. Ou, via terminal:
```bash
docker-compose -f infrastructure/docker/docker-compose.prod.yml up -d
```

## 🤝 Repositório e Fluxo

Este projeto segue o fluxo **Gitflow**.
- `main`: Produção (Build Docker nativo e AWS CI/CD).
- `develop`: Desenvolvimento Contínuo e Integração.

---
Desenvolvido por **Lucas**