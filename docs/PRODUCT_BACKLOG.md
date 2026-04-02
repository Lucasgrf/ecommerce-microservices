# 📊 Product Backlog - Fashion E-commerce

> **Projeto**: E-commerce de Moda  
> **Versão**: 1.0 MVP  
> **Referência**: [EPICS.md](./EPICS.md)

---

## 📌 Convenções
- **Prioridade**: Alta (MVP), Média (Importante), Baixa (Desejável)
- **Estimativa**: P (Pequeno - 1 dia), M (Médio - 2/3 dias), G (Grande - 1 semana)

---

## E01 - Gestão de Identidade e Acesso (IAM)

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-001 | Criar estrutura base do `user-service` (Spring Boot + Postgres) | Alta | P | Done |
| PB-002 | Implementar cadastro de usuários (Endpoint `/register`) | Alta | M | Done |
| PB-003 | Implementar Login e Geração de JWT (Endpoint `/login`) | Alta | M | Done |
| PB-004 | Configurar Spring Security para validar JWT nas rotas | Alta | M | Done |
| PB-005 | Criar endpoint de "Esqueci minha senha" (Envio de email) | Média | M | Done |
| PB-006 | Criar endpoints de Perfil (Ver e Editar dados) | Média | P | Done |

---

## E02 - Catálogo de Produtos

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-007 | Criar estrutura base do `product-service` (Spring Boot + Postgres) | Alta | P | Done |
| PB-008 | Implementar CRUD de Categorias (Tênis, Roupas, Bolsas, Acessórios) | Alta | P | Done |
| PB-009 | Implementar CRUD de Produtos (com upload de URLs de imagens) | Alta | M | Done |
| PB-010 | Implementar Consulta de Produtos com Filtros (Pageable, Specification) | Alta | M | Done |
| PB-011 | Implementar controle de baixa de estoque (API interna para OrderService) | Alta | M | Done |

---

## E03 - Carrinho e Pedidos

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-012 | Criar estrutura base do `order-service` (Spring Boot + Mongo) | Alta | P | Done |
| PB-013 | Implementar endpoints de Carrinho (Redis ou Banco: Add, Remove, List) | Alta | M | Done |
| PB-014 | Implementar Criação de Pedido (Checkout - Salvar no Mongo) | Alta | G | Done |
| PB-015 | Implementar listagem de "Meus Pedidos" para o cliente | Alta | P | Done |
| PB-016 | Implementar endpoint para Admin atualizar status do pedido | Alta | P | Done |

---

## E04 - Pagamentos e Logística

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-017 | Criar serviço de integração com **Melhor Envio** (Cotação) | Alta | G | Done |
| PB-018 | Criar serviço de integração com **Mercado Pago** (Criar preferência) | Alta | G | Done |
| PB-019 | Criar Webhook para receber notificação do Mercado Pago | Alta | M | Done |
| PB-020 | Lógica para atualizar status do pedido após confirmação de pagamento | Alta | M | Done |

---

## E05 - Notificações Transacionais

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-021 | Criar estrutura base do `notification-service` (Spring Boot + JavaMail) | Média | P | Done |
| PB-022 | Configurar Consumer RabbitMQ para fila `order.created` | Média | M | Done |
| PB-023 | Implementar envio de email HTML (Thymeleaf template) | Média | M | Done |
| PB-024 | Configurar disparos para eventos de mudança de status de pedido | Média | P | Done |

---

## E06 - Infraestrutura e DevOps

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-025 | Configurar **API Gateway** (Roteamento para serviços) | Alta | P | Done |
| PB-026 | Subir ambiente local com **Docker Compose** (Dbs, RabbitMQ) | Alta | P | Done |
| PB-027 | Configurar **GitHub Actions** para Build e Testes Unitários | Alta | M | Done |
| PB-028 | Provisionar infraestrutura básica na **AWS** (EC2 + RDS Free Tier) | Alta | G | Done |
| PB-029 | Configurar pipeline de Deploy Automatizado para AWS | Alta | G | Done |

