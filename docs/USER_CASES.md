# 👤 Casos de Uso (User Cases) - Fashion E-commerce

> **Projeto**: E-commerce de Moda  
> **Versão**: 1.0 MVP  
> **Referência**: [REQUIREMENTS.md](./REQUIREMENTS.md)

---

## 🎭 Atores

| Ator | Descrição |
|------|-----------|
| **Cliente** | Usuário final que navega e compra produtos |
| **Admin** | Gestor da loja responsável por catálogo e pedidos |
| **Sistema** | Processos automáticos (ex: atualização de pagamento) |

---

## 📂 Visão Geral dos Casos de Uso

Os casos de uso foram divididos em diretórios individuais para melhor organização e detalhamento, agrupados por microserviço:

### 👤 User Service
- [📦 UC01 - Gestão da Conta (Cliente)](./user_cases/user-service/UC01-Gestao-Conta/UC01-Gestao-Conta.md)

### 🛍️ Product Service
- [🛍️ UC02 - Catálogo e produtos (Leitura)](./user_cases/product-service/UC02-Catalogo-Produtos/UC02-Catalogo-Produtos.md)

### 🛒 Order Service
- [🛒 UC03 - Carrinho de Compras](./user_cases/order-service/UC03-Carrinho-Compras/UC03-Carrinho-Compras.md)
- [💳 UC04 - Finalização de Compra (Checkout)](./user_cases/order-service/UC04-Finalizacao-Compra/UC04-Finalizacao-Compra.md)
- [📦 UC05 - Gestão de Pedidos](./user_cases/order-service/UC05-Gestao-Pedidos/UC05-Gestao-Pedidos.md)

### ⚙️ Cross-Service (Admin & Dashboard)
- [🛠️ UC06 - Gestão Administrativa](./user_cases/cross-service/UC06-Gestao-Administrativa/UC06-Gestao-Administrativa.md)

---

## ⚠️ Cenários de Exceção Comuns

| ID | Cenário | Ação do Sistema |
|----|---------|-----------------|
| EXC01 | Estoque insuficiente no Checkout | Informar usuário e impedir finalização |
| EXC02 | Pagamento Recusado | Manter pedido como "Aguardando", notificar cliente para tentar outro meio |
| EXC03 | Falha no Cálculo de Frete | Exibir mensagem de erro e pedir para tentar novamente (ou oferecer frete fixo de contingência) |
