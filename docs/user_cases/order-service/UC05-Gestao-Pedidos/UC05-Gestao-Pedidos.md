# 📦 UC05 - Gestão de Pedidos

## UC05.1 - Acompanhar Pedidos (Cliente)
**Ator Principal**: Cliente  
**Fluxo Principal**:
1. Cliente acessa "Meus Pedidos".
2. Sistema lista histórico de pedidos.
3. Cliente clica em "Detalhes" para ver status e rastreamento.

## UC05.2 - Atualizar Status do Pedido (Admin)
**Ator Principal**: Admin  
**Fluxo Principal**:
1. Admin acessa lista de vendas.
2. Admin seleciona pedido.
3. Admin altera status (ex: "Em Separação" -> "Enviado").
4. Se "Enviado", Admin insere código de rastreio.
5. Sistema notifica Cliente por email.
