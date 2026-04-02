# 📋 Requisitos do Sistema - Fashion E-commerce

> **Projeto**: E-commerce de Moda (Roupas, Tênis, Bolsas, Acessórios)  
> **Versão**: 1.0 MVP  
> **Última atualização**: 23/12/2024

---

## 📌 Sumário

1. [Visão Geral](#-visão-geral)
2. [Requisitos Funcionais](#-requisitos-funcionais)
3. [Requisitos Não-Funcionais](#-requisitos-não-funcionais)
4. [Restrições e Premissas](#-restrições-e-premissas)

---

## 🎯 Visão Geral

### Descrição do Produto
Plataforma de e-commerce especializada em moda (roupas, tênis e bolsas), construída com arquitetura de microservices para demonstrar boas práticas de desenvolvimento.

### Usuários do Sistema

| Tipo | Descrição |
|------|-----------|
| **Cliente** | Compra produtos, gerencia carrinho, avalia produtos |
| **Admin** | Gerencia produtos, pedidos, cupons e usuários |

### Fases do Projeto

| Fase | Escopo |
|------|--------|
| **MVP** | Cadastro, catálogo, carrinho, pedidos, pagamento |
| **v2.0** | Avaliações, cupons, dashboard admin |
| **v3.0** | Recomendações, wishlist, notificações push |

---

## ✅ Requisitos Funcionais

### RF01 - Autenticação e Usuários

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF01.1 | O sistema deve permitir cadastro de clientes com email e senha | Alta | MVP |
| RF01.2 | O sistema deve autenticar usuários via JWT | Alta | MVP |
| RF01.3 | O sistema deve permitir recuperação de senha via email | Média | MVP |
| RF01.4 | O sistema deve permitir edição de perfil (nome, telefone, endereço) | Média | MVP |
| RF01.5 | O admin deve poder desativar/ativar contas de clientes | Média | v2.0 |

---

### RF02 - Catálogo de Produtos

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF02.1 | O admin deve poder cadastrar produtos com nome, descrição, preço, fotos e categoria | Alta | MVP |
| RF02.2 | O sistema deve categorizar produtos (Roupas, Tênis, Bolsas, Acessórios + subcategorias) | Alta | MVP |
| RF02.3 | O sistema deve permitir busca por nome e filtros (categoria, preço, tamanho, cor) | Alta | MVP |
| RF02.4 | O sistema deve exibir produtos com paginação | Alta | MVP |
| RF02.5 | O admin deve poder gerenciar estoque (quantidade por tamanho/cor) | Alta | MVP |
| RF02.6 | O sistema deve exibir produtos relacionados | Baixa | v3.0 |

---

### RF03 - Carrinho de Compras

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF03.1 | O cliente deve poder adicionar produtos ao carrinho | Alta | MVP |
| RF03.2 | O cliente deve poder alterar quantidade de itens no carrinho | Alta | MVP |
| RF03.3 | O cliente deve poder remover itens do carrinho | Alta | MVP |
| RF03.4 | O carrinho deve persistir entre sessões (usuário logado) | Alta | MVP |
| RF03.5 | O sistema deve validar disponibilidade de estoque ao adicionar item | Alta | MVP |
| RF03.6 | O carrinho deve exibir subtotal atualizado em tempo real | Média | MVP |

---

### RF04 - Pedidos e Checkout

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF04.1 | O cliente deve poder finalizar compra a partir do carrinho | Alta | MVP |
| RF04.2 | O sistema deve solicitar/confirmar endereço de entrega | Alta | MVP |
| RF04.3 | O sistema deve calcular frete (integração com API Melhor Envio) | Alta | MVP |
| RF04.4 | O sistema deve gerar número de pedido único | Alta | MVP |
| RF04.5 | O cliente deve poder visualizar histórico de pedidos | Alta | MVP |
| RF04.6 | O cliente deve poder acompanhar status do pedido | Alta | MVP |
| RF04.7 | O admin deve poder atualizar status de pedidos | Alta | MVP |

---

### RF05 - Pagamento

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF05.1 | O sistema deve integrar com **Mercado Pago** (gateway escolhido por menor taxa) | Alta | MVP |
| RF05.2 | O sistema deve aceitar pagamento via cartão de crédito | Alta | MVP |
| RF05.3 | O sistema deve aceitar pagamento via PIX (taxa 0,99%) | Alta | MVP |
| RF05.4 | O sistema deve aceitar pagamento via boleto bancário | Média | MVP |
| RF05.4 | O sistema deve processar webhooks de confirmação de pagamento | Alta | MVP |
| RF05.5 | O sistema deve tratar falhas de pagamento e notificar cliente | Alta | MVP |

---

### RF06 - Avaliações de Produtos

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF06.1 | O cliente deve poder avaliar produtos comprados (1-5 estrelas + comentário) | Média | v2.0 |
| RF06.2 | O sistema deve exibir média de avaliações na listagem de produtos | Média | v2.0 |
| RF06.3 | O admin deve poder moderar avaliações (aprovar/rejeitar) | Baixa | v2.0 |

---

### RF07 - Cupons de Desconto

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF07.1 | O admin deve poder criar cupons (código, % ou valor fixo, validade) | Média | v2.0 |
| RF07.2 | O cliente deve poder aplicar cupom no checkout | Média | v2.0 |
| RF07.3 | O sistema deve validar cupom (validade, uso único, valor mínimo) | Média | v2.0 |
| RF07.4 | O admin deve poder desativar cupons | Baixa | v2.0 |

---

### RF08 - Notificações

| ID | Requisito | Prioridade | Fase |
|----|-----------|------------|------|
| RF08.1 | O sistema deve enviar email de confirmação de pedido | Alta | MVP |
| RF08.2 | O sistema deve enviar email de atualização de status do pedido | Alta | MVP |
| RF08.3 | O sistema deve enviar email de recuperação de senha | Média | MVP |

---

## ⚙️ Requisitos Não-Funcionais

### RNF01 - Performance

| ID | Requisito | Métrica |
|----|-----------|---------|
| RNF01.1 | Tempo de resposta das APIs | < 500ms (P95) |
| RNF01.2 | Tempo de carregamento de página de produto | < 2 segundos |
| RNF01.3 | Busca de produtos | < 1 segundo para 10.000 produtos |

---

### RNF02 - Disponibilidade

| ID | Requisito | Métrica |
|----|-----------|---------|
| RNF02.1 | Sistema disponível 24/7 | 99.5% uptime mensal |
| RNF02.2 | Tolerância a falhas | Graceful degradation em caso de falha de serviço |
| RNF02.3 | Recovery time | < 5 minutos para rollback |

---

### RNF03 - Segurança e Compliance (LGPD)

| ID | Requisito | Observação |
|----|-----------|------------|
| RNF03.1 | Dados pessoais criptografados em trânsito (HTTPS/TLS) | Obrigatório |
| RNF03.2 | Senhas armazenadas com hash seguro (BCrypt) | Obrigatório |
| RNF03.3 | Dados de pagamento não armazenados (tokenização via gateway) | PCI-DSS Compliance |
| RNF03.4 | Política de privacidade e termos de uso | LGPD - Art. 7 |
| RNF03.5 | Opção de exclusão de dados pelo usuário | LGPD - Art. 18 |
| RNF03.6 | Consentimento explícito para marketing | LGPD - Art. 8 |
| RNF03.7 | Logs de auditoria para acesso a dados sensíveis | LGPD - Art. 37 |
| RNF03.8 | Backup de dados com retenção definida | Proteção contra perda |

---

### RNF04 - Escalabilidade

| ID | Requisito | Observação |
|----|-----------|------------|
| RNF04.1 | Arquitetura de microservices | Escalabilidade horizontal |
| RNF04.2 | Containerização com Docker | Facilita deploy e scaling |
| RNF04.3 | Mensageria assíncrona (RabbitMQ) | Desacoplamento entre serviços |

---

### RNF05 - Manutenibilidade

| ID | Requisito | Observação |
|----|-----------|------------|
| RNF05.1 | Cobertura de testes > 70% | Unitários + Integração |
| RNF05.2 | Documentação de APIs (OpenAPI/Swagger) | Todas as rotas documentadas |
| RNF05.3 | Logs centralizados estruturados | Facilita debugging |
| RNF05.4 | Clean Architecture | Separação clara de camadas |

---

### RNF06 - DevOps & CI/CD

| ID | Requisito | Observação |
|----|-----------|------------|
| RNF06.1 | Pipeline de CI/CD automatizado | GitHub Actions ou AWS CodePipeline |
| RNF06.2 | Deploy automatizado na AWS | EC2 (MVP) ou ECS/EKS (Futuro) |
| RNF06.3 | Monitoramento e Logs | CloudWatch |


### Restrições Técnicas

| Restrição | Descrição |
|-----------|-----------|
| Linguagem Backend | Java 17+ com Spring Boot 3.x |
| Banco de Dados | PostgreSQL (User, Product) + MongoDB (Orders) |
| Mensageria | RabbitMQ |
| Gateway de Pagamento | **Mercado Pago** (PIX: 0,99% / Crédito D+30: 3,99%) |
| API de Frete | **Melhor Envio** (integra Correios, Jadlog, etc.) |

### Premissas

| Premissa | Descrição |
|----------|-----------|
| Hospedagem | **AWS** (Amazon Web Services) |
| CI/CD | GitHub Actions |
| Usuários Iniciais | < 1.000 usuários simultâneos no MVP |
| Catálogo Inicial | < 500 produtos |
| Região | Brasil (pt-BR, BRL, fuso horário) |

---

## 📝 Glossário

| Termo | Definição |
|-------|-----------|
| **MVP** | Minimum Viable Product - versão mínima funcional |
| **JWT** | JSON Web Token - padrão de autenticação |
| **LGPD** | Lei Geral de Proteção de Dados (Lei 13.709/2018) |
| **PCI-DSS** | Padrão de segurança para dados de cartão |
| **Webhook** | Callback HTTP para notificação de eventos |

---

## ✏️ Histórico de Revisões

| Versão | Data | Autor | Descrição |
|--------|------|-------|-----------|
| 1.0 | 23/12/2024 | Lucas | Versão inicial do documento |

