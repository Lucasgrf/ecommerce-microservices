# 🚀 Épicos do Projeto - Fashion E-commerce

> **Projeto**: E-commerce de Moda  
> **Versão**: 1.0 MVP  
> **Referência**: [USER_CASES.md](./USER_CASES.md)

---

## 📌 Visão Geral dos Épicos

Os Épicos agrupam funcionalidades por domínio de negócio, mapeando diretamente para os microservices e a infraestrutura.

| ID | Épico | Microservice Principal | Prioridade |
|----|-------|------------------------|------------|
| **E01** | **Gestão de Identidade e Acesso** | `user-service` | Alta |
| **E02** | **Catálogo de Produtos** | `product-service` | Alta |
| **E03** | **Carrinho e Pedidos** | `order-service` | Alta |
| **E04** | **Pagamentos e Logística** | `order-service` / Integrações | Alta |
| **E05** | **Notificações Transacionais** | `notification-service` | Média |
| **E06** | **Infraestrutura e DevOps** | `api-gateway` / AWS | Alta |

---

## 📝 Detalhamento dos Épicos

### E01 - Gestão de Identidade e Acesso (IAM)
**Objetivo**: Permitir que clientes se cadastrem/loguem e admins gerenciem usuários.  
**Microservice**: `user-service`  
**Casos de Uso Relacionados**: UC01.1, UC01.2

- [ ] Implementar cadastro de usuários (Cliente/Admin)
- [ ] Implementar autenticação via JWT (Login)
- [ ] Criar endpoint de "Esqueci minha senha" (integra com E05)
- [ ] Gerenciar perfis de usuário (Cliente e Admin)

---

### E02 - Catálogo de Produtos
**Objetivo**: Expor produtos para venda e permitir gestão de estoque/catálogo.  
**Microservice**: `product-service`  
**Casos de Uso Relacionados**: UC02.1, UC02.2, UC06.1

- [ ] CRUD de Produtos (Admin)
- [ ] CRUD de Categorias (Tênis, Roupas, Bolsas, Acessórios)
- [ ] Controle de Estoque (Entrada/Saída)
- [ ] Listagem pública com Filtros e Paginação
- [ ] Detalhes do Produto (Fotos, Descrição, Variações)

---

### E03 - Carrinho e Pedidos
**Objetivo**: Gerenciar a intenção de compra (carrinho) e o ciclo de vida do pedido.  
**Microservice**: `order-service`  
**Casos de Uso Relacionados**: UC03.1, UC03.2, UC04.1, UC05.1, UC05.2

- [ ] Implementar Carrinho de Compras (Redis ou Persistido no Mongo)
- [ ] Criação de Pedido (Checkout)
- [ ] Histórico de Pedidos do Cliente
- [ ] Gestão de Status de Pedidos (Admin: Pendente -> Pago -> Enviado -> Entregue)

---

### E04 - Pagamentos e Logística
**Objetivo**: Processar transações financeiras e calcular entregas.  
**Microservice**: `order-service` (módulo de integração)
**Casos de Uso Relacionados**: UC04.1, UC04.2

- [ ] Integração **Melhor Envio** (Cotação de Frete)
- [ ] Integração **Mercado Pago** (Geração de PIX/Boleto/Token Cartão)
- [ ] Processamento de Webhooks (Callback de Pagamento Aprovado/Recusado)
- [ ] Estorno/Cancelamento (Básico)

---

### E05 - Notificações Transacionais
**Objetivo**: Manter o cliente informado sobre ações importantes.  
**Microservice**: `notification-service`
**Casos de Uso Relacionados**: UC01.1, UC04.2, UC05.2

- [ ] Consumer RabbitMQ para eventos (`order.created`, `payment.confirmed`, `order.shipped`)
- [ ] Envio de Email de Boas-vindas
- [ ] Envio de Email de Confirmação de Pedido
- [ ] Envio de Email com Rastreio

---

### E06 - Infraestrutura e DevOps
**Objetivo**: Garantir que o sistema rode em produção de forma segura e escalável.  
**Microservice**: `api-gateway` / Infra Geral

- [ ] Configuração do API Gateway (Roteamento, Rate Limiting)
- [ ] Setup do RabbitMQ (Filas e Exchanges)
- [ ] Setup Banco de Dados (Postgres e MongoDB)
- [ ] Pipeline CI/CD (GitHub Actions)
- [ ] Provisionamento AWS (EC2, RDS ou Containers)
- [ ] Monitoramento Básico (Logs e Health Checks)
