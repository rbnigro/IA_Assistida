# Portfolio Academico de Engenharia de IA First

Este repositorio representa uma formacao pratica e independente em Engenharia de IA aplicada. O produto central e um CRUD full-stack construido com JDK 21, Angular 19 e banco H2. A abordagem IA First usa inteligencia artificial ao longo do ciclo de engenharia, sem substituir requisitos, testes, revisao humana ou responsabilidade tecnica.

O projeto tambem evolui para RAG, agentes autonomos, orquestracao multiagente e MCP quando essas capacidades resolverem um problema real do CRUD.

O projeto foi organizado para simular uma pos-graduacao real:

```text
aprender -> aplicar -> testar -> revisar -> avaliar -> registrar -> publicar
```

O resultado esperado nao e somente uma demo que responde perguntas. E um portfolio tecnico reproduzivel, com decisoes registradas, testes, metricas, seguranca, observabilidade e evidencias suficientes para uma entrevista tecnica.

## Como navegar

Este repositorio possui tres workspaces do VS Code, mas somente um repositorio Git. O conteudo publico foi organizado de forma neutra, sem referencias a instituicoes, cursos comerciais ou materiais de origem.

```text
portfolio-ia/
  Curso\
  Projeto\
  Avaliacao\
  Workspaces\
  .git\
```

Abra o ambiente correspondente conforme a atividade:

| Arquivo | Ambiente | Finalidade |
| --- | --- | --- |
| `Workspaces/Curso.code-workspace` | Curso | Estudar conceitos e receber desafios |
| `Workspaces/Projeto.code-workspace` | Projeto | Implementar codigo e documentacao |
| `Workspaces/Avaliacao.code-workspace` | Avaliacao | Executar testes e medir resultados |

Os arquivos `.code-workspace` sao configuracoes do VS Code. Eles nao sao repositorios separados e nao criam isolamento de seguranca por si mesmos.

## Objetivo do projeto

### Baseline tecnico obrigatorio

- backend em JDK 21;
- frontend em Angular 19;
- persistencia em H2;
- API REST documentada;
- testes automatizados;
- arquitetura preparada para evoluir sem acoplar IA ao dominio central.

Ao concluir a formacao, o sistema integrador devera:

- implementar um CRUD completo com validacao, persistencia e tratamento de erros;
- oferecer uma interface Angular responsiva e acessivel;
- manter contratos consistentes entre frontend, backend e banco;
- usar IA First para apoiar analise, implementacao, testes, documentacao e operacao;

- responder perguntas usando uma base de conhecimento com RAG;
- citar as fontes utilizadas e recusar respostas sem evidencia suficiente;
- decompor tarefas e executar planos controlados;
- diferenciar agente reativo, ReAct e Plan and Execute;
- usar memoria de tarefa e memoria persistente de forma seletiva;
- orquestrar agentes com papeis e contratos explicitos;
- acessar um servico legado por ferramentas MCP tipadas;
- aplicar autenticacao, autorizacao, scopes e rate limiting;
- lidar com timeout, retry, idempotencia e circuit breaker;
- proteger dados sensiveis e resistir a prompt injection;
- registrar traces, custos, latencia, decisoes e falhas;
- exigir aprovacao humana para operacoes de maior risco;
- ser avaliado por um harness reproduzivel;
- ser revisado por um Code Reviewer diferente do agente que codificou.

O sistema deve continuar funcional como CRUD quando qualquer servico de IA estiver indisponivel. IA First significa aplicar IA ao ciclo de engenharia e ao produto com limites explicitos, nao criar uma dependencia invisivel do modelo.

## Mapa da documentacao

A documentacao foi dividida por responsabilidade para evitar um Markdown unico e excessivo.

| Documento | O que ensina |
| --- | --- |
| [Plano do Portfolio](Curso/Docs/Plano_Portfolio.md) | Competencias, projetos, etapas e criterios de passagem |
| [Arquitetura de Agentes e Artefatos](Curso/Docs/Arquitetura_Agentes_e_Artefatos.md) | Funcao de constituicao, agentes, prompts, memoria, MCP e harness |
| [Arquitetura de Workspaces e Contextos](Curso/Docs/Arquitetura_de_Workspaces_e_Contextos.md) | Isolamento entre estudo, implementacao e avaliacao |
| [Metodologia](Projeto/docs/methodology.md) | Processo completo de aprendizagem, desenvolvimento e avaliacao |
| `Projeto/CONSTITUTION.md` | Principios e limites fundamentais do sistema |
| `Projeto/AGENTS.md` | Regras operacionais para agentes no repositorio |

A constituicao e o manual operacional devem continuar separados da metodologia. A primeira define principios; o segundo define como trabalhar; a metodologia define o ciclo de aprendizagem e entrega.

## Estrutura do repositorio

```text
portfolio-ia/
  Curso/
    Docs/
      Plano_Portfolio.md
      Arquitetura_Agentes_e_Artefatos.md
      Arquitetura_de_Workspaces_e_Contextos.md
      aulas/
      exercicios/
      referencias/

  Projeto/
    CONSTITUTION.md
    AGENTS.md
    agents/
      architect.agent.md
      developer.agent.md
      rag-engineer.agent.md
      mcp-engineer.agent.md
      code-reviewer.agent.md
      security-reviewer.agent.md
      evaluator.agent.md
    prompts/
      requirements.md
      architecture.md
      implementation.md
      testing.md
      review.md
      incident-report.md
    services/
      mcp-legacy/
      ai-assistant/
    docs/
      architecture/
      decisions/
      methodology.md
      runbooks/
    harness/
    tests/

  Avaliacao/
    scenarios/
    rubrics/
    evaluators/
      review_independence.py
    reports/

  Workspaces/
    Curso.code-workspace
    Projeto.code-workspace
    Avaliacao.code-workspace
```

As pastas representam areas de responsabilidade. Os arquivos dentro delas sao artefatos que documentam, executam ou avaliam o processo.

## Funcao de cada camada

### `Curso/`

E o ambiente pedagogico. Contem conceitos, aulas, exercicios, referencias e desafios. Seu papel e ensinar o que sera aplicado no projeto.

O curso nao deve entregar automaticamente a solucao pronta ao agente de codificacao. A cada etapa, deve liberar requisitos, contratos e criterios suficientes para trabalhar sem revelar a implementacao esperada.

### `Projeto/`

E o ambiente de aplicacao profissional. Contem o codigo do sistema integrador, agentes, prompts, servicos, testes e documentacao tecnica.

O agente de codificacao trabalha principalmente aqui. Ele registra o que alterou, quais testes executou e qual identidade foi responsavel pela implementacao.

### `Avaliacao/`

E o ambiente independente de validacao. Contem cenarios, rubricas, avaliadores e relatorios.

O harness verifica o comportamento real do projeto. Ele nao deve depender apenas da afirmacao do agente de que a tarefa foi concluida.

### `Workspaces/`

Contem os arquivos de configuracao do VS Code. Cada arquivo abre somente uma pasta principal:

```json
{
  "folders": [
    {
      "path": "../Projeto"
    }
  ]
}
```

Isso melhora a organizacao e a simulacao academica. Isolamento efetivo ainda depende de permissoes, ferramentas habilitadas e contexto enviado ao modelo.

## Governanca

### `CONSTITUTION.md`

Define principios que nenhum agente pode violar, independentemente da tarefa. Exemplos:

- nao inventar evidencias;
- proteger dados sensiveis;
- respeitar autorizacao;
- nao executar escrita de alto risco sem aprovacao;
- registrar decisoes e efeitos;
- respeitar limites de custo e autonomia.

### `AGENTS.md`

Define como os agentes trabalham neste repositorio. Deve conter comandos, convencoes, estrutura, regras de alteracao, requisitos de testes e limites de uso das ferramentas.

### `agents/*.agent.md`

Define papeis especializados:

| Agente | Funcao |
| --- | --- |
| `architect.agent.md` | Requisitos, componentes, contratos e diagramas |
| `developer.agent.md` | Implementacao e testes da demanda |
| `rag-engineer.agent.md` | Ingestao, busca, citacoes e avaliacao do RAG |
| `mcp-engineer.agent.md` | Ferramentas MCP e adaptacao do legado |
| `code-reviewer.agent.md` | Revisao independente de codigo, testes e contratos |
| `security-reviewer.agent.md` | Vulnerabilidades, abuso e privilegios excessivos |
| `evaluator.agent.md` | Cenarios, metricas, qualidade e conformidade |

O Code Reviewer e obrigatoriamente diferente do agente autor. O revisor produz parecer e nao altera diretamente o codigo que esta avaliando.

## Prompts

Os arquivos em `Projeto/prompts/` sao instrucoes reutilizaveis para uma tarefa especifica:

- `requirements.md`: levantar e tornar requisitos verificaveis;
- `architecture.md`: produzir arquitetura e decisoes;
- `implementation.md`: implementar respeitando contratos;
- `testing.md`: planejar e executar testes;
- `review.md`: realizar Code Review independente;
- `incident-report.md`: registrar falhas, impacto e prevencao.

Um arquivo de agente define **quem executa** e seus limites. Um prompt define **qual tarefa sera executada agora**. Essa separacao permite reutilizar prompts sem alterar a identidade dos agentes.

## Servicos e MCP

`Projeto/services/mcp-legacy/` encapsula o sistema legado. O agente nao acessa diretamente banco, credenciais ou endpoints internos.

A camada MCP deve:

- expor somente ferramentas necessarias;
- validar schemas e limites;
- aplicar autorizacao por ferramenta;
- separar identidade do usuario e do agente;
- controlar timeout, retry e idempotencia;
- mascarar dados sensiveis;
- normalizar respostas e erros;
- registrar auditoria.

O modelo solicita uma ferramenta. O servidor MCP decide se a operacao e permitida e como sera traduzida para o legado.

## Stack da aplicacao

```text
Angular 19 -> API REST em JDK 21 -> dominio/aplicacao -> repositorios -> H2
                                      |
                                      +-> camada de IA First, quando aplicavel
```

O backend deve separar dominio, aplicacao, infraestrutura e entrada HTTP. O frontend deve separar componentes, servicos, modelos e apresentacao. H2 sera o banco inicial, com uma abstracao de persistencia que permita evolucao futura.

As capacidades de RAG, agentes e MCP entram por etapas e precisam de um caso de uso, criterio de aceite e avaliacao. O CRUD basico nao deve depender delas para criar, consultar, atualizar ou remover registros.

## Contexto e memoria

O sistema nao deve enviar todo o repositorio ao modelo. Cada agente recebe um pacote de contexto minimo:

```yaml
context_package:
  task_id: task-001
  workspace: Projeto
  allowed_artifacts:
    - requirements/REQ-001.md
    - docs/architecture/components.md
  excluded_artifacts:
    - Curso/referencias/solution-key.md
    - Avaliacao/rubrics/private-cases.yaml
  token_budget: 12000
  tool_call_budget: 12
```

O estado da tarefa deve ser estruturado e pequeno, contendo etapa, requisitos, identidade dos agentes, arquivos alterados, testes e proximos passos.

A memoria episodica registra o que aconteceu em uma tarefa. A memoria semantica registra fatos e decisoes reutilizaveis. Ambas devem ser recuperadas seletivamente, com limites de tamanho e validade.

## Fluxo de uma demanda

```text
1. Curso libera conceito, desafio e requisitos
2. Arquiteto define contratos e arquitetura
3. Desenvolvedor implementa em branch temporaria
4. Testes verificam comportamento e limites
5. Code Reviewer independente analisa o diff
6. Security Reviewer avalia riscos quando aplicavel
7. Harness executa cenarios e mede resultados
8. Autor corrige bloqueios
9. Revisao e avaliacao sao repetidas
10. Mudanca aprovada e mesclada em main
11. Marco da etapa recebe uma tag
12. Feedback retorna ao processo de aprendizagem
```

A regra de independencia e verificavel por identificadores:

```yaml
author_agent_id: developer-01
reviewer_agent_id: code-reviewer-01
```

Avaliacao deve ser bloqueada se os identificadores forem iguais ou estiverem ausentes.

## Conhecimentos adquiridos

### Fundamentos de IA aplicada

- tokens e janela de contexto;
- embeddings e busca semantica;
- reranking;
- structured output;
- tool calling;
- streaming;
- custo e latencia de modelos;
- diferenca entre dado, instrucao e evidencia.

### RAG

- ingestao e limpeza de documentos;
- chunking e metadados;
- filtros e citacoes;
- atualizacao do indice;
- abstencao quando nao existe evidencia;
- precision@k, recall@k, faithfulness e regressao;
- diagnostico de falhas de recuperacao.

### Agentes

- agente reativo e agente com memoria episodica;
- ReAct;
- Plan and Execute;
- tool calling;
- estado e retomada;
- limites de autonomia;
- aprovacao humana;
- idempotencia e recuperacao de falhas.

### Multiagente

- papeis e contratos;
- coordenacao;
- contexto compartilhado minimo;
- correlation ID;
- timeout e encerramento;
- prevencao de loops;
- isolamento de identidades;
- distribuicao de tarefas.

### MCP e sistemas legados

- recursos, prompts e ferramentas MCP;
- schemas de entrada e saida;
- adaptadores para APIs legadas;
- scopes e autorizacao;
- timeout, retry e circuit breaker;
- idempotency key;
- auditoria e normalizacao de erros.

### Seguranca e operacao

- prompt injection;
- data exfiltration;
- tool abuse;
- escalada de privilegio;
- PII e redacao de logs;
- envenenamento de documentos;
- denial of wallet;
- WAF, rate limiting e gestao de segredos;
- p50, p95, taxa de erro e custo por tarefa;
- traces e post-mortems.

### Engenharia de software

- requisitos verificaveis;
- arquitetura e ADRs;
- contratos de servico;
- testes unitarios, integracao e contrato;
- testes de seguranca e regressao;
- Code Review independente;
- branches, tags e Releases;
- documentacao reproduzivel.

### Desenvolvimento full-stack

- JDK 21 e recursos modernos da plataforma Java;
- API REST, DTOs, validacao e tratamento global de erros;
- Angular 19, componentes, formularios, servicos e acessibilidade;
- H2, schema, transacoes e testes de persistencia;
- testes unitarios, integracao, contrato e ponta a ponta;
- uso verificavel de IA para acelerar engenharia sem remover revisao humana.

## Etapas publicaveis

As branches servem para desenvolver. As tags representam estados oficiais, congelados e baixaveis:

```text
etapa-00-baseline
etapa-01-crud-backend
etapa-02-crud-angular
etapa-03-crud-h2-testado
etapa-04-rag-minimo
etapa-05-agente-reativo
etapa-06-plan-and-execute
etapa-07-mcp
etapa-08-multiagente
etapa-09-ia-first-final
etapa-09-final
```

Depois de aprovar uma etapa:

```powershell
git switch main
git merge --no-ff feature/nome-da-etapa
git tag -a etapa-01-crud-backend -m "Etapa 01 - CRUD backend"
git push origin main --tags
```

Nao reescreva tags que ja foram publicadas. Qualquer pessoa podera baixar uma etapa pelo GitHub usando `Code > Download ZIP` ou a URL da tag.

## Carga horaria e indicadores

As horas abaixo sao uma estimativa pedagogica de esforco por etapa. Elas nao representam uma promessa de produtividade. A medicao oficial devera substituir as estimativas pelos tempos reais registrados no projeto.

| Etapa | Entrega principal | Sem IA (h) | Com IA (h) | Ganho estimado (h) | Produtividade estimada | SpeedUp estimado |
| --- | --- | ---: | ---: | ---: | ---: | ---: |
| 00 | Baseline e requisitos | 8 | 6 | 2 | 25,0% | 1,33x |
| 01 | CRUD backend JDK 21 | 32 | 22 | 10 | 31,3% | 1,45x |
| 02 | CRUD frontend Angular 19 | 36 | 25 | 11 | 30,6% | 1,44x |
| 03 | Persistencia H2 e testes | 24 | 16 | 8 | 33,3% | 1,50x |
| 04 | RAG minimo | 40 | 28 | 12 | 30,0% | 1,43x |
| 05 | Agente reativo | 32 | 22 | 10 | 31,3% | 1,45x |
| 06 | Plan and Execute | 36 | 25 | 11 | 30,6% | 1,44x |
| 07 | Integracao MCP | 44 | 31 | 13 | 29,5% | 1,42x |
| 08 | Orquestracao multiagente | 48 | 34 | 14 | 29,2% | 1,41x |
| 09 | Integracao IA First e entrega final | 56 | 40 | 16 | 28,6% | 1,40x |
| **Total** | **Formacao completa** | **356** | **249** | **107** | **30,1%** | **1,43x** |

### Definicao dos indicadores

Para cada etapa, use:

```text
Ganho de horas = horas_sem_IA - horas_com_IA
Produtividade (%) = ganho_de_horas / horas_sem_IA * 100
SpeedUp = horas_sem_IA / horas_com_IA
```

“Com IA” inclui preparação do contexto, interação com os agentes, revisão humana, correções, testes e documentação. “Sem IA” representa o esforço estimado para realizar a mesma entrega manualmente, mantendo o mesmo escopo e os mesmos critérios de qualidade.

Não compare etapas com escopos diferentes. Registre também retrabalho, falhas, custo dos modelos e tempo de espera para que um ganho de horas não esconda perda de qualidade.

## Definition of Done

Uma demanda ou etapa esta concluida quando:

- requisitos e criterios de aceite estao identificados;
- arquitetura e contratos estao documentados;
- codigo e testes foram implementados;
- testes relevantes passaram;
- Code Review independente foi concluido;
- revisao de seguranca foi executada quando aplicavel;
- harness foi executado;
- custo, latencia e falhas foram registrados;
- documentacao foi atualizada;
- autoria e revisao estao rastreaveis;
- nao existem segredos no repositorio;
- a etapa foi publicada com tag quando for um marco do curso.

## Primeiros passos

1. Abra `Workspaces/Curso.code-workspace` para estudar o conceito da etapa.
2. Leia o requisito liberado e a definicao de pronto.
3. Abra `Workspaces/Projeto.code-workspace` para implementar.
4. Registre requisitos, arquitetura, testes e identidade de autoria.
5. Solicite o Code Review a um agente diferente.
6. Abra `Workspaces/Avaliacao.code-workspace` para executar o harness.
7. Corrija bloqueios e repita a avaliacao.
8. Mescle a etapa em `main` e crie a tag correspondente.
9. Consulte o relatorio e registre o aprendizado obtido.

## Estado atual

O repositorio possui a estrutura inicial dos tres workspaces, os agentes especializados, os prompts operacionais, a metodologia e os arquivos de configuracao do VS Code. A implementacao do sistema integrador, dos avaliadores e dos servicos sera conduzida progressivamente conforme as etapas do curso.
