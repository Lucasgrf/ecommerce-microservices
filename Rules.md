<agent_rules>
You generally act as a senior software engineer. When working on this project, you MUST strictly adhere to the following rules:

1. **Branching Strategy**
   - **Base Branch**: ALWAYS start new work from the `develop` branch. NEVER commit directly to `main`.
   - **Naming Convention**: You MUST name your branches using the pattern: `type/ID-description`.
     - Use the Backlog ID if available (e.g., `PB-123`).
     - Examples: `feat/PB-002-user-login`, `fix/PB-015-calc-error`, `chore/setup-docker`.
   - **Allowed Types**: `feat`, `fix`, `chore`, `refactor`, `style`, `test`, `hotfix`.

2. **Commit Message Standards**
   - You MUST strictly follow the **Conventional Commits** specification.
   - **Format**: `type(scope): description`
   - **Examples**:
     - `feat(api): add new endpoint for user profile`
     - `fix(ui): resolve button alignment issue in dashboard`
     - `docs: update deployment guide`
   - The description must be concise and meaningful.

3. **Pull Request & Merge Rules**
   - You MUST create a Pull Request against `develop` (or `main` for hotfixes) when a task is complete.
   - You MUST fill out the PR template with Ticket ID, Description and Checklist.
   - You MUST NOT introduce merge commits. Use `rebase` or `squash` to maintain a linear history.

4. **Code Quality & Testing**
   - **Unit Tests**: JUnit 5 + Mockito are MANDATORY for all services.
   - **Integration Tests**: You MUST use **Testcontainers** (RabbitMQ, Postgres, Mongo) for integration tests.
   - **Architecture Tests**: You MUST use **ArchUnit** to validate Clean Architecture layers.
   - You MUST write clean, readable code and include comments for complex logic.

5. **Technical Stack Usage**
   - **Language**: Java 17+
   - **Framework**: Spring Boot 3.2+
   - **Boilerplate**: Lombok (Required for DTOs/Entities).
   - **Build**: Maven.
   - **Logging**: You MUST use SLF4J (via `@Slf4j`). Usage of `System.out.println` is FORBIDDEN.
   - **Documentation**: All Controllers MUST have `@Tag` and `@Operation` (Swagger/OpenAPI).

6. **Architecture Guidelines (Clean Architecture)**
   - You MUST strictly follow this folder/package structure for Microservices:
     - `domain`: Entities, Value Objects, Repository Interfaces, Exceptions. (NO Spring/Framework dependencies here!).
     - `application`: Use Cases, Service Interfaces, DTOs.
     - `infrastructure`: Persistence (JPA/Mongo), External APIs, Messaging (RabbitMQ), Config, Security.
     - `presentation`: REST Controllers, Exception Handlers.
   - **Error Handling**: You MUST use a `@ControllerAdvice` / `GlobalExceptionHandler` for standardized errors.

7. **Database & Configuration**
   - **User/Product Services**: PostgreSQL.
   - **Order Service**: MongoDB.
   - **Identifiers**: You MUST use UUIDs for all primary keys (except static Roles).
   - **Config Format**: You MUST use `YAML` (`application.yml`) for all configurations. `.properties` files are FORBIDDEN.
   - **Profiles**: Support `dev`, `docker`, `prod` profiles.

8. **Service Infrastructure (Ports)**
   - **API Gateway**: 8080
   - **User Service**: 8081
   - **Product Service**: 8082
   - **Order Service**: 8083
   - **Notification Service**: 8084
   - **RabbitMQ**: 5672

9. **Project Management & Workflow**
   - **Board States**: `To Do` -> `In Progress` -> `Code Review` -> `Done`.
   - **Ticket IDs**: ALWAYS reference `PB-XXX` in Branch Names, Commit Messages, and PR Titles.
   - **Definition of Done (DoD)**:
     - 1. Code Review approved.
     - 2. CI/CD Pipeline (Build + Tests) Passed.
     - 3. Functionality meets Acceptance Criteria.
     - 4. Documentation updated.
</agent_rules>
