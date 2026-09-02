# 📜 CONSTITUTION.md

## 🎯 Propósito
Este documento estabelece a **constituição normativa dos agentes** e ferramentas utilizadas no projeto **IA First**.  
Define princípios, responsabilidades, limites e critérios de auditoria para garantir governança, independência e rastreabilidade em todas as etapas de engenharia.

---

## 🧩 Princípios Fundamentais
- **Independência**: nenhum agente pode revisar ou aprovar seu próprio trabalho.  
- **Rastreabilidade**: cada ação deve estar vinculada a requisito, evidência e auditoria.  
- **Segurança**: nenhum segredo, dado pessoal real ou dependência externa sem justificativa.  
- **Fallback**: o CRUD deve permanecer funcional mesmo se IA ou ferramentas falharem.  
- **Normatividade**: todas as regras aqui descritas são obrigatórias e não opcionais.  

---

## 📑 Estrutura Normativa
- **AGENTS.md** → índice normativo e visão geral dos agentes.  
- **.agent.md individuais** → fichas técnicas completas de cada papel.  
- **CONSTITUTION.md** → princípios, regras gerais, governança e critérios de bloqueio.  

---

## ⚙️ Fluxo Operacional Obrigatório
1. Identificar requisito e critério de aceite.  
2. Definir ou atualizar contrato técnico.  
3. Projetar a mudança.  
4. Implementar em JDK 21 e Angular 19 conforme aplicável.  
5. Executar testes de backend, frontend e persistência MySQL.  
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
