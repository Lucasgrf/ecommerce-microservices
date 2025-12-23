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
| PB-001 | Criar estrutura base do `user-service` (Spring Boot + Postgres) | Alta | P | To Do |
| PB-002 | Implementar cadastro de usuários (Endpoint `/register`) | Alta | M | To Do |
| PB-003 | Implementar Login e Geração de JWT (Endpoint `/login`) | Alta | M | To Do |
| PB-004 | Configurar Spring Security para validar JWT nas rotas | Alta | M | To Do |
| PB-005 | Criar endpoint de "Esqueci minha senha" (Envio de email) | Média | M | To Do |
| PB-006 | Criar endpoints de Perfil (Ver e Editar dados) | Média | P | To Do |

---

## E02 - Catálogo de Produtos

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-007 | Criar estrutura base do `product-service` (Spring Boot + Postgres) | Alta | P | To Do |
| PB-008 | Implementar CRUD de Categorias (Tênis, Roupas, Bolsas, Acessórios) | Alta | P | To Do |
| PB-009 | Implementar CRUD de Produtos (com upload de URLs de imagens) | Alta | M | To Do |
| PB-010 | Implementar Consulta de Produtos com Filtros (Pageable, Specification) | Alta | M | To Do |
| PB-011 | Implementar controle de baixa de estoque (API interna para OrderService) | Alta | M | To Do |

---

## E03 - Carrinho e Pedidos

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-012 | Criar estrutura base do `order-service` (Spring Boot + Mongo) | Alta | P | To Do |
| PB-013 | Implementar endpoints de Carrinho (Redis ou Banco: Add, Remove, List) | Alta | M | To Do |
| PB-014 | Implementar Criação de Pedido (Checkout - Salvar no Mongo) | Alta | G | To Do |
| PB-015 | Implementar listagem de "Meus Pedidos" para o cliente | Alta | P | To Do |
| PB-016 | Implementar endpoint para Admin atualizar status do pedido | Alta | P | To Do |

---

## E04 - Pagamentos e Logística

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-017 | Criar serviço de integração com **Melhor Envio** (Cotação) | Alta | G | To Do |
| PB-018 | Criar serviço de integração com **Mercado Pago** (Criar preferência) | Alta | G | To Do |
| PB-019 | Criar Webhook para receber notificação do Mercado Pago | Alta | M | To Do |
| PB-020 | Lógica para atualizar status do pedido após confirmação de pagamento | Alta | M | To Do |

---

## E05 - Notificações Transacionais

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-021 | Criar estrutura base do `notification-service` (Spring Boot + JavaMail) | Média | P | To Do |
| PB-022 | Configurar Consumer RabbitMQ para fila `order.created` | Média | M | To Do |
| PB-023 | Implementar envio de email HTML (Thymeleaf template) | Média | M | To Do |
| PB-024 | Configurar disparos para eventos de mudança de status de pedido | Média | P | To Do |

---

## E06 - Infraestrutura e DevOps

| ID | Item de Backlog | Prioridade | Est. | Status |
|----|-----------------|------------|------|--------|
| PB-025 | Configurar **API Gateway** (Roteamento para serviços) | Alta | P | To Do |
| PB-026 | Subir ambiente local com **Docker Compose** (Dbs, RabbitMQ) | Alta | P | To Do |
| PB-027 | Configurar **GitHub Actions** para Build e Testes Unitários | Alta | M | To Do |
| PB-028 | Provisionar infraestrutura básica na **AWS** (EC2 + RDS Free Tier) | Alta | G | To Do |
| PB-029 | Configurar pipeline de Deploy Automatizado para AWS | Alta | G | To Do |
