# 💳 UC04 - Finalização de Compra (Checkout)

## UC04.1 - Realizar Pedido
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

## UC04.2 - Processar Pagamento (Sistema)
**Ator Principal**: Sistema / Gateway  
**Fluxo Principal**:
1. Gateway notifica Sistema via Webhook (Pagamento Aprovado).
2. Sistema atualiza status do pedido para "Pago".
3. Sistema envia email de confirmação para o Cliente.
