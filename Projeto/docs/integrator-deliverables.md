# Entregaveis do Projeto Integrador

O projeto integrador sera um CRUD full-stack com JDK 21 (sem Lombok), Angular 19 e MySQL, evoluido progressivamente com capacidades IA First. Cada entrega devera ser executavel, avaliada e documentada.

## Produto funcional

- API REST com criar, consultar, atualizar e remover;
- validacao de entrada e erros padronizados;
- persistencia MySQL e transacoes;
- interface Angular 19 responsiva e acessivel;
- instrucoes de execucao local;
- comportamento funcional sem servico de IA.

## Entregaveis tecnicos

| Entregavel | Conteudo minimo | Evidencia |
| --- | --- | --- |
| Relatorio tecnico | problema, arquitetura, decisoes, limites e riscos | `docs/architecture/` e ADR |
| API | endpoints, DTOs, erros e contrato versionado | testes de contrato |
| Frontend | telas, estados de carregamento/erro e acessibilidade | teste de fluxo |
| Persistencia | entidades, schema, transacoes e inicializacao MySQL | testes de integracao |
| IA First | tarefas apoiadas por IA, contexto, limites e validacao humana | registro de execucao |
| RAG | ingestao, busca, fontes e abstencao, se habilitado | dataset e metricas |
| Agentes | papeis, estado, ferramentas e limites, se habilitado | traces e cenarios |
| MCP | schemas, autorizacao e adaptador externo, se habilitado | testes positivos/negativos |
| Seguranca | threat model, scopes, rate limit e resposta a incidentes | Security Review |
| Avaliacao | cenarios, resultados, custo, latencia e falhas | relatorio do harness |
| Demo | roteiro e execucao funcional reproduzivel | video ou roteiro versionado |

## Relatorio tecnico

O relatorio deve explicar:

1. problema e publico;
2. escopo e fora de escopo;
3. arquitetura e fluxo de dados;
4. modelo de dominio e contrato da API;
5. estrategia de testes;
6. onde IA foi usada e onde foi deliberadamente evitada;
7. riscos, controles e limitacoes;
8. metricas de qualidade, horas, custo e latencia;
9. decisoes e alternativas descartadas;
10. como executar e reproduzir a avaliacao.

## Metricas obrigatorias

Registre por etapa e no acumulado:

- testes passados e falhos;
- cobertura relevante;
- p50 e p95 de latencia;
- taxa de erro;
- tokens de entrada e saida;
- custo estimado;
- horas sem IA e com IA;
- ganho de horas;
- produtividade percentual;
- SpeedUp;
- retrabalho;
- qualidade funcional e, quando aplicavel, qualidade do RAG.

Formulas:

```text
Ganho de horas = horas_sem_IA - horas_com_IA
Produtividade (%) = ganho_de_horas / horas_sem_IA * 100
SpeedUp = horas_sem_IA / horas_com_IA
```

Nao declare ganho se a entrega com IA tiver escopo menor, qualidade inferior ou testes ausentes.

## Demo funcional

A demonstracao deve seguir este roteiro:

1. iniciar MySQL, backend e frontend;
2. criar um registro;
3. consultar o registro;
4. atualizar o registro;
5. remover ou desativar o registro conforme a regra de negocio;
6. demonstrar validacao e erro;
7. demonstrar uma capacidade IA, quando existente;
8. demonstrar uma falha controlada;
9. exibir evidencia de teste, trace ou relatorio;
10. mostrar a decisao do Code Review independente.

## Criterio de aceite do integrador

O integrador sera aprovado quando uma pessoa externa conseguir executar o CRUD, entender a arquitetura, reproduzir testes, observar os limites da IA e consultar os relatorios. A lista deste documento e um contrato de entrega; cada item devera ser produzido e versionado antes de ser marcado como concluido.
