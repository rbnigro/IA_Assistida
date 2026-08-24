# Metodologia de Desenvolvimento e Aprendizagem

Este documento define como o projeto academico sera conduzido do requisito ate a avaliacao. Ele conecta estudo, implementacao, revisao independente e evidencia tecnica sem substituir a constituicao, os papeis dos agentes ou os prompts especificos.

## Proposito

O projeto deve simular um ambiente profissional e uma pos-graduacao pratica. Cada capacidade sera aprendida, aplicada, revisada, avaliada e registrada.

O objetivo nao e apenas obter uma aplicacao funcionando. E produzir uma solucao que seja:

- compreensivel;
- reproduzivel;
- testavel;
- segura;
- observavel;
- economicamente controlada;
- defendivel em uma entrevista tecnica.

## Separacao de responsabilidades

| Camada | Responsabilidade | Artefatos principais |
| --- | --- | --- |
| Curso | Ensinar conceitos e liberar desafios | `Curso/Docs/`, exercicios e referencias |
| Projeto | Implementar a solucao | `Projeto/`, codigo, testes e documentacao |
| Avaliacao | Medir qualidade e conformidade | `Avaliacao/`, cenarios, rubricas e avaliadores |
| Governanca | Definir limites e principios | `CONSTITUTION.md` e `AGENTS.md` |
| Orquestracao | Distribuir tarefas e controlar contexto | coordenador, contratos e estado |

O agente de codificacao nao deve receber automaticamente todo o material do curso nem os casos privados da avaliacao. Ele recebe apenas os requisitos e artefatos liberados para a etapa atual.

## Ciclo de trabalho

Toda demanda deve passar por este ciclo:

```text
Aprender -> Definir -> Projetar -> Implementar -> Testar -> Revisar -> Avaliar -> Registrar -> Publicar
```

Uma etapa nao e considerada concluida apenas porque a aplicacao executa. Ela precisa passar pelo portao correspondente e gerar evidencia.

## Fase 0: baseline

Antes do primeiro codigo, defina:

- problema e dominio;
- usuario e persona;
- resultado esperado;
- limites de autonomia;
- dados permitidos;
- sistema legado simulado;
- perguntas de avaliacao;
- criterios de sucesso;
- riscos conhecidos;
- orcamento inicial de tokens e chamadas.

### Saida obrigatoria

Crie um requisito curto, uma definicao de pronto, um mapa de riscos e um cenario feliz. Registre tambem o estado inicial do repositorio em uma tag, por exemplo `etapa-00-baseline`.

## Fase 1: requisitos

O agente arquiteto transforma o problema em requisitos verificaveis. Cada requisito deve ter identificador, prioridade, criterio de aceite e evidencia esperada.

```yaml
requirement_id: REQ-001
statement: O usuario consegue consultar um chamado autorizado.
priority: must
acceptance_criteria:
  - A resposta inclui o identificador do chamado.
  - A ferramenta MCP valida a permissao de leitura.
evidence: functional-test-001
```

Requisitos devem ser liberados ao projeto sem incluir a solucao de implementacao.

## Fase 2: arquitetura

O agente arquiteto define componentes, fronteiras, contratos, fluxos de dados e falhas esperadas. Decisoes relevantes devem ser registradas como ADR em `Projeto/docs/decisions/`.

A arquitetura deve responder:

- onde o contexto e armazenado;
- quais agentes participam;
- quais ferramentas cada agente pode usar;
- como o MCP protege o legado;
- como sao aplicados timeout, retry e idempotencia;
- quais operacoes exigem aprovacao humana;
- como a execucao sera observada e avaliada.

### Portao de arquitetura

Nao avance quando houver componente sem responsabilidade, ferramenta sem autorizacao ou fluxo sem tratamento de falha.

## Fase 3: implementacao

O agente desenvolvedor implementa em uma branch temporaria criada a partir de `main` ou da ultima etapa publicada. Ele deve:

- seguir os contratos aprovados;
- escrever testes junto com o codigo;
- manter mudancas pequenas e rastreaveis;
- registrar `author_agent_id`;
- atualizar documentacao afetada;
- executar validacoes locais antes de solicitar revisao.

O desenvolvedor nao deve aprovar a propria implementacao.

## Fase 4: testes

Os testes devem cobrir comportamento normal, entradas invalidas, falhas de dependencia, autorizacao e limites operacionais.

Categorias minimas:

- testes unitarios;
- testes de contrato;
- testes de integracao;
- testes de seguranca;
- testes de regressao do RAG;
- testes de fluxo do microsite;
- testes de limite de tokens, tempo e chamadas.

Um teste que falha deve ser preservado como regressao quando o defeito for corrigido.

## Fase 5: Code Review independente

O Code Reviewer deve ser uma identidade diferente do agente que implementou a demanda. O orquestrador deve recusar a revisao quando:

```text
author_agent_id == reviewer_agent_id
```

ou quando qualquer uma das identidades estiver ausente.

O Code Reviewer recebe o diff, os requisitos, os testes e os contratos relevantes. Ele nao altera diretamente o codigo que esta revisando.

### Parecer do Code Reviewer

O parecer deve conter:

- identificador da mudanca;
- identidade do autor;
- identidade do revisor;
- arquivos analisados;
- achados por severidade;
- evidencia de cada achado;
- riscos residuais;
- testes executados;
- decisao: `approved`, `approved_with_remarks` ou `blocked`.

Se houver correcoes, o agente autor pode implementa-las, mas uma nova revisao independente e obrigatoria.

O Code Reviewer verifica qualidade de implementacao, testes, contratos e manutencao. O Security Reviewer verifica vulnerabilidades, abuso, vazamento de dados e privilegios. Sao papeis distintos.

## Fase 6: avaliacao pelo harness

O harness da avaliacao executa cenarios controlados em `Avaliacao/`. Ele deve medir a solucao sem depender da declaracao do agente.

O harness deve:

- fornecer fixtures controladas;
- limitar tokens, tempo e numero de chamadas;
- simular ferramentas e o sistema legado;
- executar cenarios felizes e adversariais;
- verificar citacoes e fundamentacao do RAG;
- validar autorizacao e isolamento de contexto;
- verificar independencia do Code Review;
- medir latencia, custo e taxa de erro;
- gerar relatorio reproduzivel.

Avaliacao privada pode ser mantida fora do contexto do agente de codificacao, mesmo quando o codigo final esta disponivel no mesmo repositorio.

## Fase 7: feedback e aprendizagem

O resultado da avaliacao deve ser convertido em feedback pratico:

- aprovado: avance para a proxima etapa;
- aprovado com ressalvas: registre dividas e defina prazo de correcao;
- bloqueado: corrija os achados e repita a avaliacao.

Cada falha relevante deve produzir um registro curto contendo causa, impacto, deteccao, correcao e prevencao.

## Controle de contexto

Cada agente recebe um pacote de contexto minimo e explicito. O pacote deve identificar o que foi permitido e o que foi excluido.

```yaml
context_package:
  task_id: task-001
  workspace: Projeto
  allowed_artifacts:
    - requirements/REQ-001.md
    - docs/architecture/components.md
    - tests/contracts/test_tickets.py
  excluded_artifacts:
    - Avaliacao/rubrics/private-cases.yaml
    - Curso/referencias/solution-key.md
  token_budget: 12000
  tool_call_budget: 12
```

Regras:

- nao enviar o workspace inteiro por padrao;
- recuperar apenas documentos relevantes;
- resumir historico antigo;
- separar fatos, estado, instrucoes e evidencias;
- registrar versao dos prompts e modelo;
- encerrar a tarefa quando a evidencia for suficiente;
- impedir que texto recuperado ganhe autoridade sobre politicas do sistema.

## Estado da tarefa

O estado deve ser estruturado e persistido, em vez de depender apenas do historico da conversa.

```yaml
task_id: task-001
stage: review
status: pending
author_agent_id: developer-01
reviewer_agent_id: code-reviewer-01
requirements:
  - REQ-001
completed_steps:
  - design
  - implementation
pending_steps:
  - code-review
  - harness-evaluation
artifacts_changed:
  - services/mcp-legacy/tools/search_tickets.py
```

O estado deve ser pequeno, versionado e suficiente para retomar a tarefa sem reenviar toda a conversa.

## Versionamento das etapas

Use branches para desenvolvimento temporario e tags para publicar estados oficiais.

```text
main
  |
  +-- etapa-00-baseline
  +-- etapa-01-rag-minimo
  +-- etapa-02-rag-avaliado
  +-- etapa-03-agente-reativo
  +-- etapa-04-plan-and-execute
  +-- etapa-05-mcp
  +-- etapa-06-multiagente
  +-- etapa-07-producao
  +-- etapa-08-microsite
  +-- etapa-09-final
```

Fluxo:

1. crie uma branch de trabalho;
2. implemente e teste;
3. solicite Code Review independente;
4. execute o harness;
5. corrija bloqueios;
6. mescle em `main`;
7. crie uma tag anotada;
8. publique a tag e, quando fizer sentido, uma Release no GitHub.

Uma tag publicada representa uma etapa congelada e baixavel. Nao reescreva tags de etapas ja divulgadas.

## Definicao de pronto

Uma demanda esta pronta quando:

- requisitos e criterios de aceite estao identificados;
- codigo e testes foram implementados;
- testes relevantes passaram;
- Code Review independente foi concluido;
- revisao de seguranca foi executada quando aplicavel;
- harness foi executado;
- custo, latencia e falhas relevantes foram registrados;
- documentacao e ADRs foram atualizados;
- autoria e revisao estao rastreaveis;
- nao existem segredos no repositorio;
- a etapa foi publicada com tag quando for um marco do curso.

## Registro minimo por demanda

```text
change_id
requirement_ids
author_agent_id
reviewer_agent_id
workspace
prompt_versions
model_version
context_artifacts
tools_called
files_changed
tests_run
review_decision
harness_report
cost
latency
known_limitations
```

Esse registro permite explicar nao somente o resultado, mas como ele foi produzido.

## Ritmo de trabalho

Para cada modulo ou etapa:

- estudar o conceito;
- resolver um exercicio pequeno;
- aplicar no projeto integrador;
- comparar com a linha de base;
- revisar por identidade independente;
- avaliar com cenarios controlados;
- registrar uma decisao e uma falha aprendida;
- publicar o estado da etapa.

A metodologia deve permanecer iterativa. Se uma etapa revelar que o desenho anterior estava errado, registre a mudanca como ADR e preserve a versao anterior para fins de aprendizagem.

## Resultado final esperado

Ao final do curso, qualquer pessoa deve conseguir:

1. baixar uma tag especifica;
2. executar o projeto seguindo o README;
3. reproduzir uma avaliacao;
4. observar uma tarefa por meio de traces;
5. identificar quais agentes participaram;
6. verificar que o Code Reviewer nao era o autor;
7. entender os limites, custos e riscos do sistema;
8. comparar a evolucao entre as etapas.
