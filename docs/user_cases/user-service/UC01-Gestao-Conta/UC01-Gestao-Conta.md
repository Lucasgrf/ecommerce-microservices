# 📦 UC01 - Gestão da Conta (Cliente)

## UC01.1 - Registrar Conta
**Ator Principal**: Cliente  
**Pré-condição**: Nenhuma  
**Fluxo Principal**:
1. Cliente acessa opção "Criar Conta".
2. Cliente informa Nome, Email e Senha.
3. Sistema valida se email já existe.
4. Sistema cria conta e envia email de boas-vindas.
5. Cliente é redirecionado para Login.

## UC01.2 - Autenticar (Login)
**Ator Principal**: Cliente / Admin  
**Fluxo Principal**:
1. Usuário informa Email e Senha.
2. Sistema valida credenciais.
3. Sistema gera token JWT.
4. Usuário ganha acesso às funcionalidades protegidas.
