# 🧠 Super AGENTS.md

## 🎯 Contexto do Projeto
Este repositório integra a formação prática **IA First**, cujo objetivo é aplicar Inteligência Artificial para ampliar a capacidade de engenharia sem substituir responsabilidade técnica, testes ou revisão humana.  
O produto central é um **CRUD full‑stack** com backend em **JDK 21**, frontend em **Angular 19** e banco **H2**.

---

## 📑 Estrutura Normativa
- **AGENTS.md** → índice normativo e visão geral.  
- **.agent.md individuais** → fichas técnicas completas de cada papel.  
- Este documento consolida visão geral e fichas técnicas em um só lugar para referência rápida.

---

## 🧩 Papéis e Responsabilidades (Visão Geral)
| **Agente** | **Responsabilidade principal** |
|-------------|--------------------------------|
| Architect | Define arquitetura e contratos técnicos. |
| Developer | Implementa requisitos e mantém CRUD funcional. |
| Code Reviewer | Revisa código e garante independência. |
| Evaluator | Valida resultados e consolida auditoria. |
| MCP Engineer | Gerencia integração com MCP. |
| RAG Engineer | Otimiza pipelines de RAG. |
| Security Reviewer | Audita segurança e conformidade. |

---

## 📋 Fichas Técnicas

### Architect
- **Responsabilidades detalhadas:** Definir arquitetura, contratos técnicos, ADRs.  
- **Limites claros:** Não implementar código, apenas definir padrões.  
- **Entregáveis esperados:** ADRs atualizados, contratos técnicos versionados.  
- **Critérios de auditoria:** Cada ADR deve referenciar requisito; cada contrato deve ter rastreabilidade.  
- **Exemplo prático:** Alteração de persistência exige ADR atualizado e comunicação ao Developer.

### Developer
- **Responsabilidades detalhadas:** Implementar requisitos em JDK 21/Angular 19, manter CRUD funcional.  
- **Limites claros:** Não aprovar código próprio; não alterar arquitetura sem ADR.  
- **Entregáveis esperados:** Código compilável, testes unitários e integração, documentação de alterações.  
- **Critérios de auditoria:** Cada commit deve referenciar requisito; cada alteração deve ter evidência de teste.  
- **Exemplo prático:** Implementar novo endpoint CRUD e registrar teste de persistência H2.

### Code Reviewer
- **Responsabilidades detalhadas:** Validar código, padrões e critérios de aceite.  
- **Limites claros:** Não revisar código próprio (`author_agent_id == reviewer_agent_id` bloqueia).  
- **Entregáveis esperados:** Parecer técnico registrado, comentários resolvidos.  
- **Critérios de auditoria:** Cada revisão deve anexar evidência; cada aprovação deve ter justificativa.  
- **Exemplo prático:** Revisar pull request e registrar parecer com evidência de testes.

### Evaluator
- **Responsabilidades detalhadas:** Consolidar auditoria, verificar critérios de aceite.  
- **Limites claros:** Não implementar nem revisar código.  
- **Entregáveis esperados:** Registro de estados de verificação (`observed`, `planned`, `decision`, `verified`).  
- **Critérios de auditoria:** Cada avaliação deve anexar evidência; cada estado deve ser documentado.  
- **Exemplo prático:** Validar que CRUD funciona após integração e registrar estado `verified`.

### MCP Engineer
- **Responsabilidades detalhadas:** Integrar modelos IA via MCP, garantir isolamento.  
- **Limites claros:** Não alterar código de negócio.  
- **Entregáveis esperados:** Logs de integração, documentação de compatibilidade.  
- **Critérios de auditoria:** Cada integração deve ter evidência de teste; cada modelo deve ser isolado.  
- **Exemplo prático:** Configurar MCP para orquestrar agentes sem afetar CRUD.

### RAG Engineer
- **Responsabilidades detalhadas:** Implementar pipelines de Recuperação‑Aumentada‑por‑Geração.  
- **Limites claros:** Não alterar arquitetura sem ADR.  
- **Entregáveis esperados:** Pipeline otimizado, métricas de precisão registradas.  
- **Critérios de auditoria:** Cada fonte deve ser verificável; cada pipeline deve ter métricas anexadas.  
- **Exemplo prático:** Ajustar pipeline RAG para recuperar requisitos técnicos de ADRs.

### Security Reviewer
- **Responsabilidades detalhadas:** Auditar segurança, privacidade e conformidade.  
- **Limites claros:** Não aprovar código sem auditoria; não ignorar dependências externas.  
- **Entregáveis esperados:** Relatórios de vulnerabilidade, validação de políticas de dados.  
- **Critérios de auditoria:** Cada dependência deve ser auditada; cada política deve ser validada.  
- **Exemplo prático:** Bloquear publicação se houver dependência externa sem justificativa.

---

## ⚙️ Fluxo Operacional
1. Identificar requisito e critério de aceite.  
2. Definir ou atualizar contrato técnico.  
3. Projetar a mudança.  
4. Implementar em JDK 21 e Angular 19 conforme aplicável.  
5. Executar testes de backend, frontend e persistência H2.  
6. Registrar metadados de auditoria:  
   - `author_agent_id`  
   - `reviewer_agent_id`  
   - `timestamp` (ISO 8601)  
   - `source` (artefato ou evidência utilizada)  
   - `verification_state` (`observed`, `planned`, `decision`, `verified`)  
7. Solicitar Code Review a outro agente.  
8. Executar avaliação e registrar evidências.  
9. Publicar somente após aprovação independente.

---

## 🧮 Governança de Auditoria
- Cada commit deve referenciar requisito.  
- Cada revisão deve registrar evidência.  
- Cada auditoria deve anexar metadados.  
- Checklist por agente deve ser preenchido e anexado ao artefato.  
- O Evaluator consolida todos os checklists antes da aprovação final.

---

## 🧱 Independência e Critérios de Bloqueio
A avaliação deve **bloquear** quando:
```text
author_agent_id == reviewer_agent_id
