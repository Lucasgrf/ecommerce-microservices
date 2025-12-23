# 🛒 E-commerce Microservices - Plano de Implementação

> **Objetivo**: Construir um sistema de e-commerce utilizando arquitetura de microservices para demonstrar conhecimentos em Java, Spring Boot, Clean Architecture, Mensageria e DevOps.

---

## 📋 Índice

1. [Visão Geral da Arquitetura](#-visão-geral-da-arquitetura)
2. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
3. [Estrutura dos Microservices](#-estrutura-dos-microservices)
4. [Guia de Implementação](#-guia-de-implementação)
5. [Schemas de Banco de Dados](#-schemas-de-banco-de-dados)
6. [Endpoints da API](#-endpoints-da-api)
7. [Comunicação entre Serviços](#-comunicação-entre-serviços)
8. [Docker e Infraestrutura](#-docker-e-infraestrutura)
9. [Estratégia de Testes](#-estratégia-de-testes)
10. [Checklist de Implementação](#-checklist-de-implementação)

---

## 🏗 Visão Geral da Arquitetura

```
                                    ┌─────────────────┐
                                    │    FRONTEND     │
                                    │   (Opcional)    │
                                    │    Next.js      │
                                    └────────┬────────┘
                                             │
                                             ▼
┌────────────────────────────────────────────────────────────────────────────┐
│                             API GATEWAY                                     │
│                       Spring Cloud Gateway                                  │
│                         (Porta: 8080)                                       │
│  • Roteamento de requisições    • Rate Limiting    • Load Balancing        │
└────────────────────────────────────────────────────────────────────────────┘
         │                    │                    │                    │
         ▼                    ▼                    ▼                    ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│  USER SERVICE   │  │ PRODUCT SERVICE │  │  ORDER SERVICE  │  │ NOTIFICATION    │
│   (Porta 8081)  │  │   (Porta 8082)  │  │   (Porta 8083)  │  │    SERVICE      │
│                 │  │                 │  │                 │  │   (Porta 8084)  │
│ • Registro      │  │ • CRUD Produtos │  │ • Criar Pedidos │  │                 │
│ • Login (JWT)   │  │ • Categorias    │  │ • Listar Pedidos│  │ • Enviar Email  │
│ • Perfil        │  │ • Estoque       │  │ • Status        │  │ • Logs          │
│                 │  │                 │  │                 │  │                 │
│  PostgreSQL     │  │   PostgreSQL    │  │    MongoDB      │  │   (Stateless)   │
└─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘
                              │                    │
                              │    ┌───────────────┘
                              │    │
                              ▼    ▼
                    ┌──────────────────────┐
                    │      RabbitMQ        │
                    │   (Porta: 5672)      │
                    │                      │
                    │  Exchanges/Queues:   │
                    │  • order.created     │
                    │  • order.updated     │
                    │  • stock.updated     │
                    └──────────────────────┘
```

### Fluxo Principal

1. **Cliente** faz requisição via API Gateway
2. **Gateway** roteia para o serviço correto baseado no path
3. **User Service** valida o JWT token
4. **Serviço específico** processa a requisição
5. **Eventos** são publicados no RabbitMQ quando necessário
6. **Notification Service** consome eventos e envia notificações

---

## 🛠 Tecnologias Utilizadas

### Backend (Core)
| Tecnologia | Versão | Propósito |
|------------|--------|-----------|
| Java | 17+ | Linguagem principal |
| Spring Boot | 3.2+ | Framework base |
| Spring Security | 6.x | Segurança e JWT |
| Spring Data JPA | 3.x | ORM para PostgreSQL |
| Spring Data MongoDB | 4.x | ODM para MongoDB |
| Spring Cloud Gateway | 4.x | API Gateway |
| Spring AMQP | 3.x | Integração RabbitMQ |

### Bancos de Dados
| Banco | Serviço | Justificativa |
|-------|---------|---------------|
| PostgreSQL | User, Product | Dados relacionais, ACID compliance |
| MongoDB | Order | Documentos flexíveis, histórico de pedidos |

### Mensageria & Infra
| Tecnologia | Propósito |
|------------|-----------|
| RabbitMQ | Message broker para eventos |
| Docker | Containerização |
| Docker Compose | Orquestração local |

### Qualidade & Documentação
| Tecnologia | Propósito |
|------------|-----------|
| JUnit 5 | Testes unitários |
| Mockito | Mocking em testes |
| Testcontainers | Testes de integração |
| Springdoc OpenAPI | Documentação Swagger |

---

## 📦 Estrutura dos Microservices

Cada microservice seguirá **Clean Architecture** com a seguinte estrutura:

```
service-name/
├── src/
│   ├── main/
│   │   ├── java/com/seuusuario/servicename/
│   │   │   ├── domain/                    # 🟢 CAMADA DE DOMÍNIO
│   │   │   │   ├── entity/                # Entidades de negócio
│   │   │   │   ├── repository/            # Interfaces de repositório
│   │   │   │   ├── exception/             # Exceções de domínio
│   │   │   │   └── valueobject/           # Value Objects
│   │   │   │
│   │   │   ├── application/               # 🔵 CAMADA DE APLICAÇÃO
│   │   │   │   ├── usecase/               # Casos de uso
│   │   │   │   ├── dto/                   # DTOs de entrada/saída
│   │   │   │   └── service/               # Serviços de aplicação
│   │   │   │
│   │   │   ├── infrastructure/            # 🟠 CAMADA DE INFRAESTRUTURA
│   │   │   │   ├── persistence/           # Implementação JPA/Mongo
│   │   │   │   │   ├── entity/            # Entidades JPA
│   │   │   │   │   ├── mapper/            # Mapeadores Domain ↔ JPA
│   │   │   │   │   └── repository/        # Implementação dos repos
│   │   │   │   ├── messaging/             # RabbitMQ producers/consumers
│   │   │   │   ├── security/              # Configurações de segurança
│   │   │   │   └── config/                # Beans e configurações
│   │   │   │
│   │   │   └── presentation/              # 🟣 CAMADA DE APRESENTAÇÃO
│   │   │       ├── controller/            # REST Controllers
│   │   │       ├── request/               # Request DTOs
│   │   │       ├── response/              # Response DTOs
│   │   │       └── handler/               # Exception handlers
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-dev.yml
│   │
│   └── test/
│       └── java/com/seuusuario/servicename/
│           ├── unit/                      # Testes unitários
│           ├── integration/               # Testes de integração
│           └── architecture/              # Testes de arquitetura (ArchUnit)
│
├── Dockerfile
└── pom.xml
```

### Princípios SOLID Aplicados

| Princípio | Aplicação no Projeto |
|-----------|---------------------|
| **S**ingle Responsibility | Cada Use Case tem uma única responsabilidade |
| **O**pen/Closed | Novas features via extensão, não modificação |
| **L**iskov Substitution | Interfaces bem definidas entre camadas |
| **I**nterface Segregation | Repositórios específicos por agregado |
| **D**ependency Inversion | Domain não depende de Infrastructure |

---

## 📝 Guia de Implementação

### Fase 1: Setup do Projeto (Dia 1)

#### 1.1 Criar Estrutura Base

```bash
# Criar diretório raiz
mkdir ecommerce-microservices
cd ecommerce-microservices

# Criar estrutura de pastas
mkdir -p {api-gateway,user-service,product-service,order-service,notification-service}
mkdir -p infrastructure/docker
```

#### 1.2 Inicializar Cada Serviço

Acesse [start.spring.io](https://start.spring.io) e configure:

**Todos os serviços (comum):**
- Project: Maven
- Language: Java
- Spring Boot: 3.2.x
- Packaging: Jar
- Java: 17

#### Campos do Spring Initializr

| Campo | Valor |
|-------|-------|
| **Group** | `com.lucasgrf` (seu pacote base) |
| **Artifact** | Nome do serviço (veja tabela abaixo) |
| **Name** | Mesmo valor que Artifact |
| **Package name** | Gerado automaticamente |

#### Artifact por Serviço

| Serviço | Artifact | Package name gerado |
|---------|----------|---------------------|
| API Gateway | `api-gateway` | `com.lucasgrf.apigateway` |
| User Service | `user-service` | `com.lucasgrf.userservice` |
| Product Service | `product-service` | `com.lucasgrf.productservice` |
| Order Service | `order-service` | `com.lucasgrf.orderservice` |
| Notification Service | `notification-service` | `com.lucasgrf.notificationservice` |

> **Dica**: Use nomes em **kebab-case** (`user-service`) para o Artifact — o Spring converte automaticamente para o package name correto.

---

### 🌐 API Gateway - Dependências

| Dependência Spring Initializr | Artifact Maven |
|-------------------------------|----------------|
| **Gateway** | `spring-cloud-starter-gateway` |
| **Actuator** | `spring-boot-starter-actuator` |

---

### 👤 User Service - Dependências

| Dependência Spring Initializr | Artifact Maven |
|-------------------------------|----------------|
| **Spring Web** | `spring-boot-starter-web` |
| **Spring Security** | `spring-boot-starter-security` |
| **Spring Data JPA** | `spring-boot-starter-data-jpa` |
| **PostgreSQL Driver** | `postgresql` |
| **Validation** | `spring-boot-starter-validation` |
| **Lombok** | `lombok` |

**Adicionar manualmente no `pom.xml`:** JWT e Swagger (veja seção abaixo)

---

### 📦 Product Service - Dependências

| Dependência Spring Initializr | Artifact Maven |
|-------------------------------|----------------|
| **Spring Web** | `spring-boot-starter-web` |
| **Spring Data JPA** | `spring-boot-starter-data-jpa` |
| **PostgreSQL Driver** | `postgresql` |
| **Validation** | `spring-boot-starter-validation` |
| **Lombok** | `lombok` |
| **Spring for RabbitMQ** | `spring-boot-starter-amqp` |

---

### 🛒 Order Service - Dependências

| Dependência Spring Initializr | Artifact Maven |
|-------------------------------|----------------|
| **Spring Web** | `spring-boot-starter-web` |
| **Spring Data MongoDB** | `spring-boot-starter-data-mongodb` |
| **Validation** | `spring-boot-starter-validation` |
| **Lombok** | `lombok` |
| **Spring for RabbitMQ** | `spring-boot-starter-amqp` |
| **OpenFeign** | `spring-cloud-starter-openfeign` |

---

### 📧 Notification Service - Dependências

| Dependência Spring Initializr | Artifact Maven |
|-------------------------------|----------------|
| **Spring Web** | `spring-boot-starter-web` |
| **Spring for RabbitMQ** | `spring-boot-starter-amqp` |
| **Java Mail Sender** | `spring-boot-starter-mail` |
| **Lombok** | `lombok` |

---

### 📋 Dependências Manuais (`pom.xml`)

Estas dependências **não estão disponíveis no Spring Initializr** e devem ser adicionadas manualmente:

```xml
<!-- ============================================ -->
<!-- JWT (APENAS User Service e API Gateway)     -->
<!-- ============================================ -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- ============================================ -->
<!-- Swagger/OpenAPI (TODOS menos Gateway)       -->
<!-- ============================================ -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>

<!-- ============================================ -->
<!-- ArchUnit - Testes de Arquitetura (TODOS)    -->
<!-- ============================================ -->
<dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5</artifactId>
    <version>1.2.1</version>
    <scope>test</scope>
</dependency>

<!-- ============================================ -->
<!-- Testcontainers - Testes de Integração       -->
<!-- ============================================ -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
<!-- Para PostgreSQL (User e Product Service) -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <scope>test</scope>
</dependency>
<!-- Para MongoDB (Order Service) -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mongodb</artifactId>
    <scope>test</scope>
</dependency>
<!-- Para RabbitMQ (Product, Order, Notification) -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>rabbitmq</artifactId>
    <scope>test</scope>
</dependency>
```

---

### 📄 YAML vs Properties - Por que usar YAML?

| Aspecto | `.properties` | `.yml` (YAML) |
|---------|---------------|---------------|
| **Legibilidade** | ❌ Repetitivo | ✅ Hierárquico e limpo |
| **Estrutura** | Flat (chave=valor) | Aninhado (indentação) |
| **Microservices** | Ruim para configs complexas | ✅ Ideal para múltiplos profiles |
| **Mercado** | Projetos legados | ✅ Padrão moderno |
| **Recomendação** | — | ✅ **USE YAML** |

#### Comparação Prática

**❌ `application.properties` (Repetitivo):**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
spring.datasource.username=user_admin
spring.datasource.password=user_secret
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
```

**✅ `application.yml` (Organizado):**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/userdb
    username: user_admin
    password: user_secret
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  rabbitmq:
    host: localhost
    port: 5672
```

#### Multi-Profile com YAML

Use esta estrutura para gerenciar `dev`, `docker` e `prod` no mesmo arquivo:

```yaml
# application.yml (configuração base)
spring:
  application:
    name: user-service
  profiles:
    active: dev

---
# ========== PROFILE: DEV ==========
spring:
  config:
    activate:
      on-profile: dev
  datasource:
    url: jdbc:postgresql://localhost:5432/userdb
    username: user_admin
    password: user_secret
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8081

---
# ========== PROFILE: DOCKER ==========
spring:
  config:
    activate:
      on-profile: docker
  datasource:
    url: jdbc:postgresql://postgres-user:5432/userdb
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate

server:
  port: 8081

---
# ========== PROFILE: PROD ==========
spring:
  config:
    activate:
      on-profile: prod
  datasource:
    url: ${DATABASE_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: false
```

**Como usar:**
- Local: `mvn spring-boot:run` (usa profile `dev` por padrão)
- Docker: `SPRING_PROFILES_ACTIVE=docker` no docker-compose
- Produção: `SPRING_PROFILES_ACTIVE=prod` no servidor

---

### Fase 2: User Service - Autenticação (Dias 2-3)

#### 2.1 Entidade de Domínio

```java
// domain/entity/User.java
public class User {
    private UserId id;
    private Email email;
    private Password password;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;
    private boolean active;

    // Regras de negócio aqui
    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
```

#### 2.2 Value Objects

```java
// domain/valueobject/Email.java
public record Email(String value) {
    public Email {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidEmailException(value);
        }
    }
}

// domain/valueobject/Password.java
public record Password(String hashedValue) {
    // Senha já deve vir hasheada para o domínio
}
```

#### 2.3 Interface do Repositório (Domínio)

```java
// domain/repository/UserRepository.java
public interface UserRepository {
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(Email email);
    User save(User user);
    boolean existsByEmail(Email email);
}
```

#### 2.4 Use Cases

```java
// application/usecase/RegisterUserUseCase.java
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserOutputDTO execute(RegisterUserInputDTO input) {
        // 1. Validar se email já existe
        Email email = new Email(input.email());
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(input.email());
        }

        // 2. Criar entidade de domínio
        User user = User.builder()
            .id(UserId.generate())
            .email(email)
            .password(new Password(passwordEncoder.encode(input.password())))
            .name(input.name())
            .role(UserRole.CUSTOMER)
            .createdAt(LocalDateTime.now())
            .active(true)
            .build();

        // 3. Persistir
        User saved = userRepository.save(user);

        // 4. Retornar DTO
        return UserOutputDTO.from(saved);
    }
}

// application/usecase/AuthenticateUserUseCase.java
@RequiredArgsConstructor
public class AuthenticateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthResponseDTO execute(LoginInputDTO input) {
        Email email = new Email(input.email());
        
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(input.password(), user.getPassword().hashedValue())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenProvider.generateToken(user);
        
        return new AuthResponseDTO(token, user.getName(), user.getRole().name());
    }
}
```

#### 2.5 Controller

```java
// presentation/controller/AuthController.java
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints de autenticação")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<UserOutputDTO> register(@Valid @RequestBody RegisterRequest request) {
        RegisterUserInputDTO input = new RegisterUserInputDTO(
            request.name(),
            request.email(),
            request.password()
        );
        UserOutputDTO output = registerUserUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequest request) {
        LoginInputDTO input = new LoginInputDTO(request.email(), request.password());
        AuthResponseDTO response = authenticateUserUseCase.execute(input);
        return ResponseEntity.ok(response);
    }
}
```

---

### Fase 3: Product Service - Catálogo (Dias 4-5)

#### 3.1 Entidade de Domínio

```java
// domain/entity/Product.java
public class Product {
    private ProductId id;
    private String name;
    private String description;
    private Money price;
    private Integer stockQuantity;
    private CategoryId categoryId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Regras de negócio
    public void decreaseStock(int quantity) {
        if (quantity > this.stockQuantity) {
            throw new InsufficientStockException(this.id, this.stockQuantity, quantity);
        }
        this.stockQuantity -= quantity;
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isAvailable() {
        return this.active && this.stockQuantity > 0;
    }
}

// domain/valueobject/Money.java
public record Money(BigDecimal amount, String currency) {
    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyException("Amount cannot be null or negative");
        }
        if (currency == null || currency.length() != 3) {
            throw new InvalidMoneyException("Currency must be a valid ISO code");
        }
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount, "BRL");
    }

    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money multiply(int quantity) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)), this.currency);
    }
}
```

#### 3.2 Use Cases

```java
// application/usecase/CreateProductUseCase.java
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductOutputDTO execute(CreateProductInputDTO input) {
        // Validar categoria existe
        CategoryId categoryId = new CategoryId(input.categoryId());
        categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(input.categoryId()));

        Product product = Product.builder()
            .id(ProductId.generate())
            .name(input.name())
            .description(input.description())
            .price(Money.of(input.price()))
            .stockQuantity(input.stockQuantity())
            .categoryId(categoryId)
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Product saved = productRepository.save(product);
        return ProductOutputDTO.from(saved);
    }
}

// application/usecase/ListProductsUseCase.java
@RequiredArgsConstructor
public class ListProductsUseCase {
    private final ProductRepository productRepository;

    public Page<ProductOutputDTO> execute(ProductFilterInputDTO filter, Pageable pageable) {
        return productRepository.findAll(filter.toSpecification(), pageable)
            .map(ProductOutputDTO::from);
    }
}
```

---

### Fase 4: Order Service - Pedidos (Dias 6-7)

#### 4.1 Documento MongoDB

```java
// domain/entity/Order.java
public class Order {
    private OrderId id;
    private UserId userId;
    private List<OrderItem> items;
    private Money totalAmount;
    private OrderStatus status;
    private ShippingAddress shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderStatusHistory> statusHistory;

    // Regras de negócio
    public void confirm() {
        if (this.status != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("Only pending orders can be confirmed");
        }
        this.status = OrderStatus.CONFIRMED;
        addStatusHistory(OrderStatus.CONFIRMED, "Order confirmed");
    }

    public void cancel(String reason) {
        if (!canBeCancelled()) {
            throw new OrderCannotBeCancelledException(this.id);
        }
        this.status = OrderStatus.CANCELLED;
        addStatusHistory(OrderStatus.CANCELLED, reason);
    }

    private boolean canBeCancelled() {
        return this.status == OrderStatus.PENDING || this.status == OrderStatus.CONFIRMED;
    }

    public Money calculateTotal() {
        return items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(Money.zero(), Money::add);
    }
}

// domain/valueobject/OrderItem.java
public record OrderItem(
    ProductId productId,
    String productName,
    Money unitPrice,
    Integer quantity
) {
    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }
}
```

#### 4.2 Integração com Product Service

```java
// infrastructure/client/ProductServiceClient.java
@FeignClient(name = "product-service", url = "${services.product.url}")
public interface ProductServiceClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDTO getProduct(@PathVariable String id);

    @PostMapping("/api/v1/products/{id}/reserve")
    void reserveStock(@PathVariable String id, @RequestBody StockReservationRequest request);

    @PostMapping("/api/v1/products/{id}/release")
    void releaseStock(@PathVariable String id, @RequestBody StockReleaseRequest request);
}
```

#### 4.3 Use Case com Mensageria

```java
// application/usecase/CreateOrderUseCase.java
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductServiceClient productClient;
    private final OrderEventPublisher eventPublisher;

    @Transactional
    public OrderOutputDTO execute(CreateOrderInputDTO input) {
        // 1. Validar e buscar produtos
        List<OrderItem> items = input.items().stream()
            .map(item -> {
                ProductDTO product = productClient.getProduct(item.productId());
                if (product.stockQuantity() < item.quantity()) {
                    throw new InsufficientStockException(item.productId());
                }
                return new OrderItem(
                    new ProductId(item.productId()),
                    product.name(),
                    Money.of(product.price()),
                    item.quantity()
                );
            })
            .toList();

        // 2. Criar pedido
        Order order = Order.builder()
            .id(OrderId.generate())
            .userId(new UserId(input.userId()))
            .items(items)
            .status(OrderStatus.PENDING)
            .shippingAddress(input.shippingAddress().toValueObject())
            .createdAt(LocalDateTime.now())
            .build();

        order.setTotalAmount(order.calculateTotal());

        // 3. Reservar estoque
        items.forEach(item -> 
            productClient.reserveStock(
                item.productId().value(),
                new StockReservationRequest(item.quantity())
            )
        );

        // 4. Salvar pedido
        Order saved = orderRepository.save(order);

        // 5. Publicar evento
        eventPublisher.publish(new OrderCreatedEvent(saved));

        return OrderOutputDTO.from(saved);
    }
}
```

---

### Fase 5: Notification Service - Eventos (Dia 8)

#### 5.1 Consumer de Eventos

```java
// infrastructure/messaging/OrderEventConsumer.java
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.order-created}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent for order: {}", event.orderId());

        EmailMessage email = EmailMessage.builder()
            .to(event.userEmail())
            .subject("Pedido #" + event.orderId() + " recebido!")
            .template("order-confirmation")
            .variables(Map.of(
                "orderId", event.orderId(),
                "userName", event.userName(),
                "totalAmount", event.totalAmount(),
                "items", event.items()
            ))
            .build();

        emailService.send(email);
    }

    @RabbitListener(queues = "${rabbitmq.queues.order-status-changed}")
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        log.info("Order {} status changed to {}", event.orderId(), event.newStatus());

        // Enviar notificação de atualização de status
        emailService.sendStatusUpdate(event);
    }
}
```

#### 5.2 Serviço de Email (Mock para desenvolvimento)

```java
// application/service/EmailService.java
public interface EmailService {
    void send(EmailMessage message);
    void sendStatusUpdate(OrderStatusChangedEvent event);
}

// infrastructure/email/MockEmailService.java
@Service
@Profile("dev")
@Slf4j
public class MockEmailService implements EmailService {

    @Override
    public void send(EmailMessage message) {
        log.info("========== MOCK EMAIL ==========");
        log.info("To: {}", message.to());
        log.info("Subject: {}", message.subject());
        log.info("Template: {}", message.template());
        log.info("Variables: {}", message.variables());
        log.info("=================================");
    }
}

// infrastructure/email/SmtpEmailService.java
@Service
@Profile("prod")
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void send(EmailMessage message) {
        // Implementação real com SMTP
    }
}
```

---

### Fase 6: API Gateway (Dia 9)

#### 6.1 Configuração de Rotas

```yaml
# api-gateway/src/main/resources/application.yml
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/v1/auth/**, /api/v1/users/**
          filters:
            - StripPrefix=0

        - id: product-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/v1/products/**, /api/v1/categories/**
          filters:
            - StripPrefix=0

        - id: order-service
          uri: http://localhost:8083
          predicates:
            - Path=/api/v1/orders/**
          filters:
            - StripPrefix=0
            - AuthenticationFilter

      globalcors:
        cors-configurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
              - OPTIONS
            allowedHeaders: "*"

server:
  port: 8080
```

#### 6.2 Filtro de Autenticação

```java
// infrastructure/filter/AuthenticationFilter.java
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final JwtTokenValidator tokenValidator;
    private final List<String> publicPaths = List.of(
        "/api/v1/auth/login",
        "/api/v1/auth/register",
        "/api/v1/products" // GET público
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);
        
        try {
            Claims claims = tokenValidator.validate(token);
            
            // Adicionar user info nos headers para os serviços downstream
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", claims.getSubject())
                .header("X-User-Role", claims.get("role", String.class))
                .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (JwtException e) {
            return unauthorized(exchange);
        }
    }
}
```

---

## 🗄 Schemas de Banco de Dados

### PostgreSQL - User Service

```sql
-- schema-user-service.sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_active ON users(active);
```

### PostgreSQL - Product Service

```sql
-- schema-product-service.sql
CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id UUID REFERENCES categories(id),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'BRL',
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    category_id UUID NOT NULL REFERENCES categories(id),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_active ON products(active);
CREATE INDEX idx_products_name ON products(name);
```

### MongoDB - Order Service

```javascript
// Collection: orders
{
  "_id": "ObjectId",
  "orderId": "UUID string",
  "userId": "UUID string",
  "items": [
    {
      "productId": "UUID string",
      "productName": "string",
      "unitPrice": {
        "amount": "Decimal128",
        "currency": "string"
      },
      "quantity": "int"
    }
  ],
  "totalAmount": {
    "amount": "Decimal128",
    "currency": "string"
  },
  "status": "string (PENDING|CONFIRMED|PROCESSING|SHIPPED|DELIVERED|CANCELLED)",
  "shippingAddress": {
    "street": "string",
    "number": "string",
    "complement": "string",
    "neighborhood": "string",
    "city": "string",
    "state": "string",
    "zipCode": "string",
    "country": "string"
  },
  "statusHistory": [
    {
      "status": "string",
      "changedAt": "ISODate",
      "reason": "string"
    }
  ],
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}

// Indexes
db.orders.createIndex({ "userId": 1 })
db.orders.createIndex({ "status": 1 })
db.orders.createIndex({ "createdAt": -1 })
```

---

## 🔌 Endpoints da API

### User Service (8081)

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/v1/auth/register` | Registrar usuário | ❌ |
| POST | `/api/v1/auth/login` | Autenticar | ❌ |
| GET | `/api/v1/users/me` | Perfil do usuário logado | ✅ |
| PUT | `/api/v1/users/me` | Atualizar perfil | ✅ |
| PUT | `/api/v1/users/me/password` | Alterar senha | ✅ |

### Product Service (8082)

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/api/v1/products` | Listar produtos (paginado) | ❌ |
| GET | `/api/v1/products/{id}` | Detalhes do produto | ❌ |
| POST | `/api/v1/products` | Criar produto | ✅ Admin |
| PUT | `/api/v1/products/{id}` | Atualizar produto | ✅ Admin |
| DELETE | `/api/v1/products/{id}` | Desativar produto | ✅ Admin |
| GET | `/api/v1/categories` | Listar categorias | ❌ |
| POST | `/api/v1/categories` | Criar categoria | ✅ Admin |

### Order Service (8083)

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/v1/orders` | Criar pedido | ✅ |
| GET | `/api/v1/orders` | Meus pedidos (paginado) | ✅ |
| GET | `/api/v1/orders/{id}` | Detalhes do pedido | ✅ |
| POST | `/api/v1/orders/{id}/cancel` | Cancelar pedido | ✅ |
| PUT | `/api/v1/orders/{id}/status` | Atualizar status | ✅ Admin |

---

## 📨 Comunicação entre Serviços

### Eventos RabbitMQ

```java
// Configuração de Exchanges e Queues
@Configuration
public class RabbitMQConfig {

    // Exchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order.exchange");
    }

    // Queues
    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order.created.queue", true);
    }

    @Bean
    public Queue orderStatusChangedQueue() {
        return new Queue("order.status.changed.queue", true);
    }

    // Bindings
    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderCreatedQueue)
            .to(orderExchange)
            .with("order.created");
    }
}
```

### Fluxo de Eventos

```
┌─────────────────┐    order.created    ┌──────────────────┐
│  Order Service  │ ──────────────────► │    RabbitMQ      │
│                 │                      │                  │
│ (Publica evento │                      │ order.exchange   │
│  após criar     │                      │                  │
│  pedido)        │                      └────────┬─────────┘
└─────────────────┘                               │
                                                  │ Routing Key:
                                                  │ order.created
                                                  │
                            ┌─────────────────────┘
                            │
                            ▼
              ┌──────────────────────────┐
              │   Notification Service   │
              │                          │
              │   @RabbitListener        │
              │   (Consome e envia       │
              │    email de confirmação) │
              └──────────────────────────┘
```

---

## 🐳 Docker e Infraestrutura

### docker-compose.yml

```yaml
version: '3.8'

services:
  # ==================== DATABASES ====================
  postgres-user:
    image: postgres:15-alpine
    container_name: postgres-user
    environment:
      POSTGRES_DB: userdb
      POSTGRES_USER: user_admin
      POSTGRES_PASSWORD: user_secret
    ports:
      - "5433:5432"
    volumes:
      - postgres_user_data:/var/lib/postgresql/data
    networks:
      - ecommerce-network

  postgres-product:
    image: postgres:15-alpine
    container_name: postgres-product
    environment:
      POSTGRES_DB: productdb
      POSTGRES_USER: product_admin
      POSTGRES_PASSWORD: product_secret
    ports:
      - "5434:5432"
    volumes:
      - postgres_product_data:/var/lib/postgresql/data
    networks:
      - ecommerce-network

  mongodb:
    image: mongo:7
    container_name: mongodb
    environment:
      MONGO_INITDB_ROOT_USERNAME: order_admin
      MONGO_INITDB_ROOT_PASSWORD: order_secret
      MONGO_INITDB_DATABASE: orderdb
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db
    networks:
      - ecommerce-network

  # ==================== MESSAGE BROKER ====================
  rabbitmq:
    image: rabbitmq:3-management-alpine
    container_name: rabbitmq
    environment:
      RABBITMQ_DEFAULT_USER: rabbit_user
      RABBITMQ_DEFAULT_PASS: rabbit_secret
    ports:
      - "5672:5672"   # AMQP
      - "15672:15672" # Management UI
    volumes:
      - rabbitmq_data:/var/lib/rabbitmq
    networks:
      - ecommerce-network

  # ==================== MICROSERVICES ====================
  api-gateway:
    build: ./api-gateway
    container_name: api-gateway
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
    depends_on:
      - user-service
      - product-service
      - order-service
    networks:
      - ecommerce-network

  user-service:
    build: ./user-service
    container_name: user-service
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - DB_HOST=postgres-user
      - DB_PORT=5432
      - DB_NAME=userdb
      - DB_USER=user_admin
      - DB_PASSWORD=user_secret
    depends_on:
      - postgres-user
    networks:
      - ecommerce-network

  product-service:
    build: ./product-service
    container_name: product-service
    ports:
      - "8082:8082"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - DB_HOST=postgres-product
      - DB_PORT=5432
      - DB_NAME=productdb
      - DB_USER=product_admin
      - DB_PASSWORD=product_secret
      - RABBITMQ_HOST=rabbitmq
    depends_on:
      - postgres-product
      - rabbitmq
    networks:
      - ecommerce-network

  order-service:
    build: ./order-service
    container_name: order-service
    ports:
      - "8083:8083"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - MONGODB_HOST=mongodb
      - MONGODB_PORT=27017
      - MONGODB_DATABASE=orderdb
      - MONGODB_USER=order_admin
      - MONGODB_PASSWORD=order_secret
      - RABBITMQ_HOST=rabbitmq
      - PRODUCT_SERVICE_URL=http://product-service:8082
    depends_on:
      - mongodb
      - rabbitmq
      - product-service
    networks:
      - ecommerce-network

  notification-service:
    build: ./notification-service
    container_name: notification-service
    ports:
      - "8084:8084"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - RABBITMQ_HOST=rabbitmq
    depends_on:
      - rabbitmq
    networks:
      - ecommerce-network

networks:
  ecommerce-network:
    driver: bridge

volumes:
  postgres_user_data:
  postgres_product_data:
  mongodb_data:
  rabbitmq_data:
```

### Dockerfile Padrão

```dockerfile
# Dockerfile (igual para todos os serviços, ajustar nome do jar)
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apk add --no-cache maven && mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🧪 Estratégia de Testes

### Tipos de Testes

| Tipo | Camada | Ferramenta | Cobertura Alvo |
|------|--------|------------|----------------|
| Unitário | Domain, Application | JUnit 5, Mockito | 80%+ |
| Integração | Infrastructure | Testcontainers | 60%+ |
| Arquitetura | Todas | ArchUnit | 100% regras |
| API | Presentation | MockMvc, RestAssured | Endpoints críticos |

### Exemplo de Teste Unitário

```java
// application/usecase/RegisterUserUseCaseTest.java
@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserUseCase useCase;

    @Test
    @DisplayName("Should register a new user successfully")
    void shouldRegisterNewUser() {
        // Arrange
        var input = new RegisterUserInputDTO("John Doe", "john@email.com", "password123");
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        var result = useCase.execute(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("john@email.com");
        assertThat(result.name()).isEqualTo("John Doe");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowWhenEmailExists() {
        // Arrange
        var input = new RegisterUserInputDTO("John Doe", "existing@email.com", "password123");
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(input))
            .isInstanceOf(EmailAlreadyExistsException.class)
            .hasMessageContaining("existing@email.com");
    }
}
```

### Exemplo de Teste de Arquitetura

```java
// architecture/CleanArchitectureTest.java
class CleanArchitectureTest {

    private static final JavaClasses classes = new ClassFileImporter()
        .importPackages("com.seuusuario.userservice");

    @Test
    @DisplayName("Domain should not depend on any other layer")
    void domainShouldNotDependOnOtherLayers() {
        noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..application..", "..infrastructure..", "..presentation..")
            .check(classes);
    }

    @Test
    @DisplayName("Application should only depend on domain")
    void applicationShouldOnlyDependOnDomain() {
        classes()
            .that().resideInAPackage("..application..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..domain..", "..application..", "java..", "lombok..")
            .check(classes);
    }
}
```

---

## ✅ Checklist de Implementação

### Fase 1: Setup (Dia 1)
- [ ] Criar estrutura de diretórios do projeto
- [ ] Inicializar cada microservice com Spring Initializr
- [ ] Configurar arquivos `application.yml` base
- [ ] Criar `docker-compose.yml` com databases e RabbitMQ
- [ ] Testar: `docker-compose up -d` sobe infraestrutura

### Fase 2: User Service (Dias 2-3)
- [ ] Implementar camada de Domain (User, ValueObjects)
- [ ] Implementar repositórios e entidades JPA
- [ ] Implementar Use Cases (Register, Authenticate)
- [ ] Configurar Spring Security + JWT
- [ ] Implementar Controllers e Exception Handlers
- [ ] Adicionar Swagger/OpenAPI
- [ ] Escrever testes unitários (mínimo 80% cobertura)
- [ ] Testar endpoints manualmente via Postman/cURL

### Fase 3: Product Service (Dias 4-5)
- [ ] Implementar camada de Domain (Product, Category, Money)
- [ ] Implementar repositórios JPA
- [ ] Implementar Use Cases (CRUD produtos/categorias)
- [ ] Configurar producer RabbitMQ para eventos de estoque
- [ ] Implementar Controllers
- [ ] Adicionar Swagger/OpenAPI
- [ ] Escrever testes
- [ ] Popular banco com produtos de exemplo

### Fase 4: Order Service (Dias 6-7)
- [ ] Configurar Spring Data MongoDB
- [ ] Implementar camada de Domain (Order, OrderItem)
- [ ] Implementar integração com Product Service (Feign/RestTemplate)
- [ ] Implementar Use Cases (Create, List, Cancel)
- [ ] Configurar producer e consumer RabbitMQ
- [ ] Implementar Controllers
- [ ] Escrever testes
- [ ] Testar fluxo completo: criar pedido → evento → logs

### Fase 5: Notification Service (Dia 8)
- [ ] Configurar consumer RabbitMQ
- [ ] Implementar EmailService (Mock para dev)
- [ ] Processar eventos OrderCreated e OrderStatusChanged
- [ ] Implementar logging detalhado
- [ ] Escrever testes do consumer

### Fase 6: API Gateway (Dia 9)
- [ ] Configurar rotas para cada serviço
- [ ] Implementar filtro de autenticação JWT
- [ ] Configurar CORS
- [ ] Configurar rate limiting (opcional)
- [ ] Testar todas as rotas via Gateway

### Fase 7: Docker & Polimento (Dia 10)
- [ ] Criar Dockerfile para cada serviço
- [ ] Atualizar docker-compose para incluir serviços
- [ ] Testar: `docker-compose up --build` sobe tudo
- [ ] Escrever README.md profissional
- [ ] Adicionar diagrama de arquitetura no README
- [ ] Gravar GIF/vídeo demo (opcional)

### Fase 8: CI/CD (Dia 11-12)
- [ ] Criar workflow GitHub Actions
- [ ] Build e testes automáticos em PRs
- [ ] Deploy automático (Render/Railway/Fly.io)
- [ ] Documentar processo de deploy no README

---

## 📚 Recursos Adicionais

### Documentação Oficial
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)
- [Spring AMQP (RabbitMQ)](https://docs.spring.io/spring-amqp/docs/current/reference/html/)
- [Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)

### Artigos Recomendados
- Clean Architecture by Uncle Bob
- Building Microservices by Sam Newman
- Domain-Driven Design by Eric Evans

---

> **Lembrete**: Este documento é seu guia. Siga o checklist, implemente passo a passo, e você terá um projeto sólido para seu portfólio. Boa codificação! 🚀
