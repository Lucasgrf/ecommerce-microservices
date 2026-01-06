# 🛍️ E-commerce Microservices

> **Status do Projeto**: 🚧 Em Desenvolvimento (Sprint 1)

Sistema de E-commerce escalável construído com arquitetura de Microserviços, focado em boas práticas de engenharia de software, alta performance e separação de responsabilidades.

## 🚀 Tecnologias

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3.x
- **Bancos de Dados**: PostgreSQL (Relacional), MongoDB (NoSQL)
- **Mensageria**: RabbitMQ
- **Infraestrutura**: Docker & Docker Compose
- **Segurança**: Spring Security & JWT (OAuth2 flow planned)

## 📂 Estrutura do Projeto

O projeto é organizado como um monorepo contendo os seguintes serviços e documentações:

```bash
ecommerce-microservices/
├── api-gateway/        # Porta de entrada (Routing & Security) [TODO]
├── user-service/       # Gestão de Usuários e Autenticação [EM PROGRESSO]
├── product-service/    # Catálogo de Produtos [TODO]
├── order-service/      # Gestão de Pedidos [TODO]
├── infrastructure/     # Configurações de Deploy Local (Docker)
└── docs/               # Documentação do Projeto
```

## 📚 Documentação e Planejamento

- [📋 Kanban Board](docs/KANBAN.md) - Acompanhamento em tempo real das tarefas.
- [🗓️ Sprints Roadmap](docs/SPRINTS.md) - Planejamento detalhado de cada fase.

## 🛠️ Como rodar o projeto localmente

### Pré-requisitos
- Docker & Docker Compose
- Java 17+
- Maven (Opcional, `mvnw` incluso)

### 1. Subir a Infraestrutura (Bancos e Broker)
Execute o comando abaixo para iniciar Postgres, Mongo e RabbitMQ Otimizados:

```bash
docker-compose -f infrastructure/docker/docker-compose.yml up -d
```

### 2. Rodar os Serviços
Cada serviço possui seu próprio diretório. Navegue até o serviço desejado e execute:

```bash
cd user-service
./mvnw spring-boot:run
```

## 🤝 Contribuição (Gitflow)

Este projeto segue o fluxo **Gitflow**.
- `main`: Produção (Estável).
- `develop`: Desenvolvimento (Integração).
- `feat/`: Novas funcionalidades.
- `fix/`: Correções na develop.

---
Desenvolvido por **Lucas**