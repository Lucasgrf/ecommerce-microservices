# 🐙 GitHub Flow & Padrões de Código

> **Objetivo**: Padronizar o versionamento e garantir qualidade de código via Code Review.

---

## 🌿 Estratégia de Branches

Utilizaremos uma versão simplificada do Git Flow:

| Branch | Descrição | Proteção |
|--------|-----------|----------|
| `main` | Código em produção/estável. Deploy automático para Prod. | 🔒 Requires PR |
| `develop` | Branch de integração. Deploy automático para Staging. | 🔒 Requires PR |
| `feat/...` | Novas funcionalidades. Baseada em `develop`. | 🔓 Aberta |
| `fix/...` | Correções de bugs. Baseada em `develop`. | 🔓 Aberta |
| `hotfix/...` | Correção crítica em Prod. Baseada em `main`. | 🔓 Aberta |

### 🏷️ Nomenclatura de Branches

Use o ID do backlog quando possível:

- `feat/PB-002-cadastro-usuario`
- `fix/PB-015-corrigir-calculo-frete`
- `chore/setup-docker`

---

## 💬 Padrão de Commits (Conventional Commits)

Os commits devem seguir o padrão: `tipo(escopo): descrição curta`

| Tipo | Uso | Exemplo |
|------|-----|---------|
| `feat` | Nova funcionalidade | `feat(user): implementa login via jwt` |
| `fix` | Correção de bug | `fix(cart): corrige erro ao remover item` |
| `docs` | Documentação | `docs: atualiza readme com setup` |
| `style` | Formatação (sem mudança lógica) | `style: formata User.java` |
| `refactor` | Refatoração de código | `refactor(order): extrai validação de estoque` |
| `test` | Adição/Correção de testes | `test(product): adiciona teste unitário de preço` |
| `chore` | Configurações/Build | `chore: atualiza versão do spring boot` |

---

## 🔀 Processo de Pull Request (PR)

1. **Crie a branch** a partir da `develop`.
2. **Desenvolva** e faça commits pequenos e atômicos.
3. **Abra o PR** para a `develop` quando terminar.
4. **Preencha o Template do PR** (veja abaixo).
5. **Solicite Review**.
6. **Deploy** acontece após o Merge.

### 📋 Template de Pull Request

```markdown
## 🎫 Ticket
Relacionado a: #PB-XXX

## 📝 Descrição
O que foi feito? Ex: Implementei o endpoint de cadastro...

## ✅ Checklist
- [ ] Testes unitários passaram?
- [ ] Nova funcionalidade testada manualmente?
- [ ] Documentação atualizada (se necessário)?
```

---

## 🛡️ Políticas de Qualidade

- **Review Obrigatório**: Pelo menos 1 aprovação para merge em `develop` ou `main`.
- **CI Passing**: Todos os checks do GitHub Actions (Build + Tests) devem passar.
- **Sem Commits de Merge**: Use `Rebase` ou `Squash` para manter o histórico limpo.

