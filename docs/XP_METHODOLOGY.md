# 🚀 Como a gente vai de XP (Extreme Programming) - Resumo

Anota aí: a gente tá usando umas práticas de XP nesse projeto pra conseguir entregar código rápido, que funciona bem e sem surtar na hora de mudar as regras no meio do caminho.

## 1. O que importa mesmo (Os Valores)
- **Comunicação**: Não fazer as coisas no escuro. Deixar os docs claros (tipo esse aqui) e fazer um código que dê pra ler sem precisar de tradutor.
- **Simplicidade (KISS - Keep It Simple, Stupid)**: Mano, faz o simples primeiro. Nada de inventar moda com arquitetura cabulosa logo de cara se não precisa.
- **Feedback**: Ouvir o que o professor/cliente acha sempre, e olhar pro retorno rápido da nossa pipeline (CI) e dos testes.
- **Coragem**: Não ter medo de refatorar. Viu código feio? Joga fora e faz melhor. Refatorar sem dó.
- **Respeito**: Respeitar o coleguinha, não fazer commit que quebre o build de propósito (o Gitflow tá aí pra isso).

## 2. O que a gente tá usando na prática?

### TDD (Test-Driven Development)
A moral aqui é fazer o bagulho na vibe Red -> Green -> Refactor.
- A gente cria o teste primeiro.
- Deixa ele gritar erro lá (`Red`).
- Faz só o básico do código pra fazer o teste passar (`Green`).
- Depois limpa a bagunça pro código ficar bonitão (`Refactor`).

### Limpar a bagunça toda hora (Refatoração Contínua)
Sabe aquela regra dos escoteiros, de deixar o acampamento mais limpo do que tava? Então. Qualquer "code smell" ou código duplicado (DRY) a gente mata na hora. Mete um Design Pattern (Strategy, Observer...) se ficar muito zoado.

### Design Simples e SOLID
A gente tá tentando seguir SOLID na marra e um pouco de Domain-Driven Design (DDD). O sistema tá separado bonitinho em Microsserviços (User, Product, Order) pra não virar um monólito espaguete.

### CI (Integração Contínua)
Qualquer coisinha nova a gente roda na pipeline pra ver se os builds e testes automatizados quebram.

### Na manha (Ritmo Sustentável)
A gente faz isso em Sprints pra não virar madrugadas a fio bolando código correndo risco de burnout.

## 3. O nosso Workflow todo dia

1. Puchar uma Task do [KANBAN.md](./KANBAN.md).
2. Fazer o Teste Unitário (se for Service) ou de Integração (se for Controller).
3. Botar a mão na massa até o teste ficar verde.
4. Refatorar tudo, botar uns padrões legais e deixar limpo.
5. Atualizar o Swagger/OpenAPI se rolar rota nova (Aquele lance do SDD).
6. Fazer a PR (Pull Request) / Meter o Merge suave.
