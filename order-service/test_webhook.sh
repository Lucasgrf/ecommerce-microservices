curl -X POST http://localhost:8082/payments/webhook \
  -H "Content-Type: application/json" \
  -d '{
    "type": "payment",
    "data": {
      "id": "123456789",
      "external_reference": "ORDER_ID_PLACEHOLDER"
    }
  }'
