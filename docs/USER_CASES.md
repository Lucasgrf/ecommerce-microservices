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

## 📦 UC01 - Gestão da Conta (Cliente)

### UC01.1 - Registrar Conta
**Ator Principal**: Cliente  
**Pré-condição**: Nenhuma  
**Fluxo Principal**:
1. Cliente acessa opção "Criar Conta".
2. Cliente informa Nome, Email e Senha.
3. Sistema valida se email já existe.
4. Sistema cria conta e envia email de boas-vindas.
5. Cliente é redirecionado para Login.

### UC01.2 - Autenticar (Login)
**Ator Principal**: Cliente / Admin  
**Fluxo Principal**:
1. Usuário informa Email e Senha.
2. Sistema valida credenciais.
3. Sistema gera token JWT.
4. Usuário ganha acesso às funcionalidades protegidas.

---

## 🛍️ UC02 - Catálogo e produtos (Leitura)

### UC02.1 - Buscar/Filtrar Produtos
**Ator Principal**: Cliente  
**Fluxo Principal**:
1. Cliente digita termo de busca ou seleciona Categoria (Tênis, Roupas, Acessórios).
2. Cliente aplica filtros (Preço, Tamanho, Cor).
3. Sistema exibe lista paginada de produtos disponíveis.

### UC02.2 - Visualizar Detalhes
**Ator Principal**: Cliente  
**Fluxo Principal**:
1. Cliente clica em um produto.
2. Sistema exibe fotos, descrição, preço e variações (tamanho/cor) disponíveis.
3. Sistema exibe produtos relacionados (se houver).

---

## 🛒 UC03 - Carrinho de Compras

### UC03.1 - Adicionar ao Carrinho
**Ator Principal**: Cliente  
**Fluxo Principal**:
1. Na tela de detalhes, cliente seleciona Tamanho/Cor.
2. Cliente clica em "Adicionar ao Carrinho".
3. Sistema valida estoque.
4. Sistema adiciona item e atualiza contador do carrinho.

### UC03.2 - Gerenciar Carrinho
**Ator Principal**: Cliente  
**Fluxo Principal**:
1. Cliente acessa Carrinho.
2. Cliente pode aumentar/diminuir quantidade ou remover item.
3. Sistema recalcula subtotal automaticamente.

---

## 💳 UC04 - Finalização de Compra (Checkout)

### UC04.1 - Realizar Pedido
**Ator Principal**: Cliente  
**Pré-condição**: Carrinho com itens e Usuário Logado  
**Fluxo Principal**:
1. Cliente clica em "Finalizar Compra".
2. Cliente seleciona/cadastra Endereço de Entrega.
3. Sistema calcula Frete (Melhor Envio) e exibe opções/prazos.
4. Cliente escolhe opção de frete.
5. Cliente seleciona Pagamento (PIX, Crédito ou Boleto).
6. Cliente confirma pedido.
7. Sistema reserva estoque e cria pedido com status "Aguardando Pagamento".
8. Sistema redireciona para pagamento (Mercado Pago).

### UC04.2 - Processar Pagamento (Sistema)
**Ator Principal**: Sistema / Gateway  
**Fluxo Principal**:
1. Gateway notifica Sistema via Webhook (Pagamento Aprovado).
2. Sistema atualiza status do pedido para "Pago".
3. Sistema envia email de confirmação para o Cliente.

---

## 📦 UC05 - Gestão de Pedidos

### UC05.1 - Acompanhar Pedidos (Cliente)
**Ator Principal**: Cliente  
**Fluxo Principal**:
1. Cliente acessa "Meus Pedidos".
2. Sistema lista histórico de pedidos.
3. Cliente clica em "Detalhes" para ver status e rastreamento.

### UC05.2 - Atualizar Status do Pedido (Admin)
**Ator Principal**: Admin  
**Fluxo Principal**:
1. Admin acessa lista de vendas.
2. Admin seleciona pedido.
3. Admin altera status (ex: "Em Separação" -> "Enviado").
4. Se "Enviado", Admin insere código de rastreio.
5. Sistema notifica Cliente por email.

---

## 🛠️ UC06 - Gestão Administrativa

### UC06.1 - Gerenciar Produtos (CRUD)
**Ator Principal**: Admin  
**Fluxo Principal**:
1. Admin acessa "Catálogo".
2. Admin pode Criar, Editar ou Desativar produto.
3. Ao criar/editar, Admin define Nome, Descrição, Preço, Categoria, Estoque e Fotos.

### UC06.2 - Dashboard
**Ator Principal**: Admin  
**Fluxo Principal**:
1. Admin acessa Home.
2. Sistema exibe métricas rápidas: Vendas do dia, Pedidos pendentes, Produtos com estoque baixo.

---

## ⚠️ Cenários de Exceção Comuns

| ID | Cenário | Ação do Sistema |
|----|---------|-----------------|
| EXC01 | Estoque insuficiente no Checkout | Informar usuário e impedir finalização |
| EXC02 | Pagamento Recusado | Manter pedido como "Aguardando", notificar cliente para tentar outro meio |
| EXC03 | Falha no Cálculo de Frete | Exibir mensagem de erro e pedir para tentar novamente (ou oferecer frete fixo de contingência) |
