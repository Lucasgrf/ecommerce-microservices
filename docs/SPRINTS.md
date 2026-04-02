# 🗓️ Planejamento de Sprints - Fashion E-commerce

> **Duracão da Sprint**: 2 semanas (10 dias úteis)  
> **Início Previsto**: Imediato  
> **Objetivo**: MVP funcional em ~2.5 meses

---

## 🏃 Sprint 1: Fundação & Identidade
**Foco**: Configurar ambiente, bancos de dados e garantir que o usuário consiga se cadastrar e logar.

| ID | Item | Estimativa para Dev Solo |
|----|------|--------------------------|
| PB-026 | Subir ambiente local (Docker Compose: Postgres, Mongo, RabbitMQ) | 1 dia |
| PB-001 | Setup inicial `user-service` | 0.5 dia |
| PB-002 | Cadastro de usuários (`/register`) | 2 dias |
| PB-003 | Login e JWT (`/login`) | 2 dias |
| PB-004 | Configuração de Segurança (Spring Security) | 2 dias |
| PB-025 | Configuração básica do API Gateway | 1 dia |
| **Meta** | Ambiente rodando e autenticação funcionando via Postman. | **8.5 dias** |

---

## 🏃 Sprint 2: Catálogo de Produtos
**Foco**: Permitir que o Admin cadastre produtos e o Cliente visualize o catálogo.

| ID | Item | Estimativa |
|----|------|------------|
| PB-007 | Setup inicial `product-service` | 0.5 dia |
| PB-008 | CRUD de Categorias (Roupas, Tênis, Acessórios) | 2 dias |
| PB-009 | CRUD de Produtos (Admin) | 3 dias |
| PB-010 | Listagem Publica de Produtos (Filtros/Paginação) | 3 dias |
| **Meta** | Catálogo populado e consultável via API. | **8.5 dias** |

---

## 🏃 Sprint 3: Carrinho e Pedidos (Core)
**Foco**: O ciclo de vida da compra (sem integração real de pagamento ainda).

| ID | Item | Estimativa |
|----|------|------------|
| PB-012 | Setup inicial `order-service` | 0.5 dia |
| PB-011 | Controle de Baixa de Estoque (comunicação síncrona/assíncrona) | 2 dias |
| PB-013 | Carrinho de Compras (Redis/Mongo) | 3 dias |
| PB-014 | Criação do Pedido (Checkout Básico - Mock Pagamento) | 4 dias |
| **Meta** | Cliente consegue colocar itens no carrinho e gerar um pedido "Conclu�do". | **9.5 dias** |

---

## 🏃 Sprint 4: Integrações Externas (Pagamento & Logística)
**Foco**: Fazer o e-commerce funcionar no mundo real (Dinheiro e Frete).

| ID | Item | Estimativa |
|----|------|------------|
| PB-017 | Integração **Melhor Envio** (Cotação de frete no Checkout) | 3 dias |
| PB-018 | Integração **Mercado Pago** (Gerar PIX/Preference) | 3 dias |
| PB-019 | Webhook Mercado Pago (Callback) | 2 dias |
| PB-020 | Atualização de Status (Confirmar Pagamento) | 1 dia |
| **Meta** | Ciclo completo: Escolher Frete -> Pagar no MP -> Pedido Confirmado Automaticamente. | **9 dias** |

---

## 🏃 Sprint 5: Notificações & Experiência do Usuário
**Foco**: Feedback para o usuário e recursos adicionais.

| ID | Item | Estimativa |
|----|------|------------|
| PB-021 | Setup `notification-service` | 0.5 dia |
| PB-022 | Configurar RabbitMQ (Producers/Consumers) | 2 dias |
| PB-023 | Emails Transacionais (Boas vindas, Pedido Criado) | 3 dias |
| PB-015 | Listagem "Meus Pedidos" (Cliente) | 1.5 dias |
| PB-016 | Painel Admin (Listagem de Vendas) | 2 dias |
| **Meta** | Usuário recebe emails e pode acompanhar seus pedidos. | **9 dias** |

---

## 🏃 Sprint 6: DevOps & Cloud (Go Live)
**Foco**: Levar a aplicação para a nuvem da AWS.

| ID | Item | Estimativa |
|----|------|------------|
| PB-027 | Pipeline CI/CD (GitHub Actions) | 2 dias |
| PB-028 | Provisionamento AWS (EC2, RDS, Docker) | 3 dias |
| PB-029 | Deploy Automatizado | 2 dias |
| - | Testes de Carga e Segurança básicos | 2 dias |
| **Meta** | Sistema em produção, acessível publicamente (MVP Lançado). | **9 dias** |

---

## 📅 Resumo do Cronograma

| Sprint | Foco | Status |
|--------|------|--------|
| **Sprint 1** | Auth, Gateway & Setup | ✅ Concluído |
| **Sprint 2** | Catálogo (Base SDK) | ✅ Concluído (Base Architectured) |
| **Sprint 3** | Carrinho & Pedidos | ✅ Concluído (Messaging Architectured) |
| **Sprint 4** | Pagamento & Frete | 🚧 Conclu�do |
| **Sprint 5** | Notificações | ✅ Concluído |
| **Sprint 6** | Deploy AWS CI/CD | ✅ Concluído |

