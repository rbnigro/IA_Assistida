# ADR-001 - CRUD como Nucleo do Projeto IA First

```yaml
adr_id: ADR-001
status: accepted
created_at: 2026-08-24T11:00:00Z
author_agent_id: project-owner
reviewer_agent_id: explore-01
reviewer_role: documentation-review
last_reviewed_at: 2026-08-24T11:30:00Z
```

## Objetivo

Registrar por que o produto central e um CRUD full-stack antes da introducao de capacidades avancadas de IA.

## Escopo

Esta decisao cobre a fundacao tecnica e a ordem de evolucao do projeto academico.

## Contexto

O projeto precisa demonstrar engenharia de software e engenharia de IA. Um sistema que depende de um modelo desde o primeiro passo dificulta separar falhas do dominio, da integracao e da IA.

## Decisao

Implementar primeiro um CRUD funcional com:

- backend em JDK 21 (sem Lombok);
- frontend em Angular 19;
- persistencia em MySQL;
- API REST documentada;
- validacao, tratamento de erros e testes.

Depois, adicionar RAG, agentes, MCP e orquestracao somente quando cada capacidade possuir caso de uso, contrato, teste e evidencia.

## Alternativas consideradas

### Comecar diretamente pelo multiagente

Rejeitada porque aumenta a complexidade antes de existir uma linha de base funcional.

### Tornar a IA obrigatoria no CRUD

Rejeitada porque impede degradacao segura e mistura o dominio principal com uma dependencia externa.

### Criar projetos separados para cada tecnologia

Rejeitada porque o projeto integrador deve demonstrar evolucao incremental em uma mesma base.

## Consequencias

- existe uma linha de base sem IA para comparacao;
- os ganhos de produtividade podem ser medidos por etapa;
- cada nova capacidade precisa justificar sua complexidade;
- o CRUD continua demonstravel mesmo durante indisponibilidade da IA;
- o repositorio evolui por tags publicaveis.

## Criterios

A decisao deve ser revisitada somente se ocorrer pelo menos uma destas condicoes verificaveis. O procedimento e abrir uma nova entrada no diario episodico, anexar o requisito ou teste correspondente e criar um novo ADR que substitua este:

- **ADR-001-C1:** um requisito aprovado nao puder ser atendido com JDK 21 (sem Lombok), Angular 19 e MySQL;
- **ADR-001-C2:** um teste de persistencia versionado demonstrar que MySQL nao atende ao escopo definido;
- **ADR-001-C3:** uma capacidade de IA for indispensavel para um requisito identificado e houver evidencia reproduzivel;
- **ADR-001-C4:** a degradacao sem IA deixar de atender um criterio de aceite alterado e identificado.

O estado desta decisao comprova apenas a decisao arquitetural, nao a implementacao completa do CRUD.

## Status

Accepted
