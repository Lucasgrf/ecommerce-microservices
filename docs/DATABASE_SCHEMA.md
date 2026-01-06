# 🗄️ Database Schemas (PostgreSQL)

Documentação dos esquemas relacionais dos microserviços.

---

## 1. User Service
**Responsabilidade**: Gestão de identidades, autenticação e perfis.

```mermaid
erDiagram
    users ||--o{ user_roles : has
    roles ||--o{ user_roles : "assigned to"
    users ||--o{ addresses : "has saved"

    users {
        UUID id PK
        VARCHAR email UK "Unique"
        VARCHAR password_hash
        VARCHAR full_name
        VARCHAR cpf
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    roles {
        INT id PK
        VARCHAR name UK "Ex: ROLE_ADMIN, ROLE_CUSTOMER"
    }

    user_roles {
        UUID user_id FK
        INT role_id FK
    }

    addresses {
        UUID id PK
        UUID user_id FK
        VARCHAR street
        VARCHAR number
        VARCHAR complement
        VARCHAR neighborhood
        VARCHAR city
        VARCHAR state
        VARCHAR zip_code
        BOOLEAN is_default
    }
```

---

## 2. Product Service (Write Model)
**Responsabilidade**: Fonte da verdade dos dados de produtos e controle de estoque transacional.
*Nota*: A leitura tunada pode ser feita via MongoDB (CQRS), mas o Postgres guarda a consistência.

```mermaid
erDiagram
    categories ||--o{ categories : "sub-category of"
    categories ||--o{ products : contains
    products ||--|| inventory : "has availability"

    categories {
        UUID id PK
        VARCHAR name
        VARCHAR slug
        UUID parent_id FK "Auto-relacionamento"
    }

    products {
        UUID id PK
        VARCHAR name
        VARCHAR description
        DECIMAL price
        VARCHAR sku UK
        UUID category_id FK
        BOOLEAN active
        TIMESTAMP created_at
    }

    inventory {
        UUID id PK
        UUID product_id FK
        INT quantity "Estoque físico disponível"
        INT reserved "Estoque em carrinhos/processamento"
    }
```

---

## 3. Order Service
**Responsabilidade**: Orquestração de compras e pagamentos.

```mermaid
erDiagram
    orders ||--|{ order_items : contains
    orders ||--|{ payments : "has transaction"
    orders {
        UUID id PK
        UUID user_id
        STRING status "PENDING, CONFIRMED, SHIPPED, CANCELED"
        DECIMAL total_amount
        TIMESTAMP created_at
    }

    order_items {
        UUID id PK
        UUID order_id FK
        UUID product_id
        VARCHAR product_name "Snapshot do nome"
        DECIMAL unit_price "Snapshot do preço"
        INT quantity
    }

    payments {
        UUID id PK
        UUID order_id FK
        STRING payment_method
        STRING external_id "ID no Gateway (Mercado Pago)"
        STRING status "APPROVED, REJECTED"
        TIMESTAMP paid_at
    }
```
