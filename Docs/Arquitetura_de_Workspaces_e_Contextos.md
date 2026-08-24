# Arquitetura de Workspaces e Contextos

Este documento explica como separar o ambiente de aprendizagem, o ambiente de implementacao e o ambiente de avaliacao. A separacao foi pensada para simular uma pos-graduacao pratica e, ao mesmo tempo, ensinar uma arquitetura realista de controle de contexto para agentes.

## Objetivo

O projeto deve reproduzir tres atividades diferentes:

1. aprender conceitos e receber desafios;
2. aplicar os conceitos em codigo;
3. ser avaliado por testes e revisores independentes.

Essas atividades nao devem depender do mesmo contexto completo. Se o agente de codificacao puder consultar todo o material do curso, as respostas e os criterios de avaliacao, ele podera executar a tarefa sem demonstrar que compreendeu o conceito.

## Os tres workspaces

```text
C:\UNIPDS\
  Curso\
    Docs\
      Plano_Portfolio_UniPDS.md
      Arquitetura_Agentes_e_Artefatos.md
      aulas\
      referencias\
      exercicios\

  Projeto\
    CONSTITUTION.md
    AGENTS.md
    agents\
    prompts\
    services\
    harness\
    docs\
    tests\

  Avaliacao\
    scenarios\
    rubrics\
    evaluators\
    reports\
```

Os nomes podem mudar, mas as responsabilidades devem permanecer separadas.

## 1. Workspace do curso

### Funcao

Representa o ambiente academico. E onde ficam os materiais usados para aprender, estudar e receber novos desafios.

### Conteudo

- cronograma;
- aulas;
- conceitos fundamentais;
- referencias;
- exercicios;
- desafios ainda nao realizados;
- criterios pedagogicos;
- explicacoes e exemplos.

### Quem deve ter acesso

O estudante deve ter acesso ao material liberado para a etapa atual. O agente tutor pode consultar o conteudo didatico, mas o agente de codificacao nao deve receber automaticamente o workspace inteiro.

## 2. Workspace do projeto

### Funcao

Representa o ambiente profissional em que os conhecimentos serao aplicados.

### Conteudo

- codigo;
- testes;
- configuracoes;
- agentes especializados;
- prompts operacionais;
- contratos de contexto;
- servidor MCP;
- documentacao tecnica;
- decisoes de arquitetura;
- dados de exemplo autorizados.

### Quem deve ter acesso

O agente de codificacao trabalha principalmente neste workspace. Ele recebe requisitos e artefatos liberados, mas nao deve consultar solucoes didaticas ou respostas de avaliacao que ainda nao foram disponibilizadas.

## 3. Workspace de avaliacao

### Funcao

Representa a banca, o laboratorio de testes ou o ambiente de validacao independente.

### Conteudo

- cenarios de teste;
- rubricas;
- avaliadores;
- fixtures;
- testes de seguranca;
- relatorios;
- verificacao de autoria e revisao;
- metricas de qualidade, custo e latencia.

### Quem deve ter acesso

O harness deve ter acesso ao projeto necessario para executar os testes, mas seus criterios internos nao precisam ser expostos integralmente ao agente que implementa a demanda. O Code Reviewer e o avaliador devem receber somente o contexto adequado a sua funcao.

## Regra de isolamento

O agente de codificacao nao deve acessar diretamente:

- o cronograma completo do curso;
- as respostas dos exercicios;
- o material de aulas futuras;
- a solucao de referencia;
- os casos privados da avaliacao;
- a rubrica completa antes do momento definido.

Ele pode receber artefatos liberados, como:

- requisito da tarefa;
- contrato de API;
- definicao de pronto;
- restricoes tecnicas;
- criterios publicos de qualidade;
- documentacao necessaria para implementar.

A diferenca e entre **ter acesso ao conhecimento necessario** e **ter acesso a resposta da atividade**.

## A ponte entre os workspaces

Os workspaces nao devem compartilhar todos os arquivos. A comunicacao deve ocorrer por entregas controladas.

```text
Workspace Curso
    |
    | conceito, exercicio e requisito liberado
    v
Entrega controlada
    |
    | contrato, objetivo e restricoes
    v
Workspace Projeto
    |
    | implementacao independente
    v
Workspace Avaliacao
    |
    | testes, Code Review e metricas
    v
Feedback pedagogico
```

Uma entrega controlada pode ser um pacote contendo:

```text
deliveries/
  module-03/
    requirements.md
    api-contract.yaml
    definition-of-done.md
    constraints.md
```

Esse pacote deve conter o que e necessario para trabalhar, mas nao a implementacao esperada.

## Fluxo de uma atividade

### Etapa 1: aprender

O estudante consulta o material da aula e entende o conceito. Por exemplo, aprende RAG, tool calling ou MCP.

### Etapa 2: receber o desafio

O curso libera um requisito e os contratos necessarios. O desafio deve ser suficientemente claro para ser implementado, mas nao deve descrever cada linha da solucao.

### Etapa 3: implementar

O agente de codificacao trabalha no workspace do projeto. Ele registra mudancas, testes, decisoes e identidade de autoria.

### Etapa 4: revisar

Um Code Reviewer diferente do agente autor analisa o diff, os testes, os contratos e os riscos. Ele nao deve alterar diretamente o codigo revisado.

### Etapa 5: avaliar

O workspace de avaliacao executa cenarios funcionais, testes de seguranca, verificacoes de contexto, metricas e regras de autoria.

### Etapa 6: aprender com o resultado

O feedback retorna ao ambiente do curso como uma nova oportunidade de estudo, correcao ou aprofundamento.

## Por que nao usar um unico workspace

Um workspace unico mistura papeis diferentes:

- material de ensino;
- solucao do estudante;
- regras de avaliacao;
- codigo em desenvolvimento;
- resultados de testes.

Essa mistura cria tres problemas:

1. o agente pode receber contexto demais e gastar tokens desnecessarios;
2. a avaliacao pode perder independencia;
3. o estudante pode consultar a solucao em vez de demonstrar dominio.

A separacao melhora a simulacao academica e tambem se aproxima de ambientes profissionais, nos quais desenvolvimento, documentacao interna, testes e governanca possuem acessos diferentes.

## Separar pastas nao e isolamento suficiente

Colocar arquivos em diretorios diferentes ajuda na organizacao, mas nao impede acesso. Se um agente tiver permissao de leitura em `Curso`, `Projeto` e `Avaliacao`, ele ainda podera consultar tudo.

Para isolamento real, controle:

- qual workspace esta aberto;
- quais diretorios sao montados no contexto;
- quais ferramentas estao habilitadas;
- quais arquivos podem ser lidos;
- quais memorias sao recuperadas;
- quais prompts estao disponiveis;
- quais documentos foram liberados;
- quais acessos foram registrados.

## Contexto permitido por agente

| Agente | Curso | Projeto | Avaliacao | Responsabilidade |
| --- | --- | --- | --- | --- |
| Tutor | leitura ampla | leitura limitada | nao | explicar conceitos e orientar estudos |
| Codificacao | entrega liberada | leitura e escrita | nao | implementar a demanda |
| Code Reviewer | requisitos liberados | leitura do diff | testes selecionados | revisar por outra identidade |
| Revisor de seguranca | politicas liberadas | leitura | cenarios de abuso | encontrar vulnerabilidades |
| Avaliador | criterios necessarios | leitura controlada | leitura e escrita | executar testes e gerar metricas |

O acesso deve seguir a necessidade do papel, nao a conveniencia de entregar todo o repositorio para todos os agentes.

## Controle de contexto e tokens

O orquestrador deve montar um pacote de contexto especifico para cada chamada:

```yaml
context_package:
  task_id: task-001
  allowed_artifacts:
    - deliveries/module-03/requirements.md
    - deliveries/module-03/api-contract.yaml
    - Projeto/docs/architecture/components.md
  excluded_artifacts:
    - Curso/referencias/solution-key.md
    - Avaliacao/rubrics/private-cases.yaml
  token_budget: 12000
```

O pacote evita enviar o workspace inteiro ao modelo. Isso reduz custo, melhora foco e torna possivel auditar quais informacoes estavam disponiveis durante uma decisao.

## O que deve ser registrado

Cada etapa deve gerar evidencias:

- identidade do agente;
- workspace utilizado;
- artefatos acessados;
- versao dos prompts;
- versao do modelo;
- tokens consumidos;
- ferramentas chamadas;
- arquivos alterados;
- testes executados;
- resultado do Code Review;
- resultado do harness.

Sem esse registro, nao sera possivel saber se uma atividade foi resolvida pelo conhecimento demonstrado ou por acesso indevido a uma solucao.

## Simulacao de uma pos-graduacao

Essa arquitetura simula melhor uma pos-graduacao porque separa:

- **aprender:** receber teoria e referencias;
- **aplicar:** resolver um problema em um ambiente de projeto;
- **ser avaliado:** demonstrar qualidade e dominio sem controlar os testes;
- **corrigir:** usar o feedback para aprofundar o conhecimento.

O objetivo nao e dificultar artificialmente o desenvolvimento. E criar uma fronteira clara entre conhecimento, aplicacao e avaliacao.

## Regra final

O projeto deve usar workspaces separados por responsabilidade, entregas controladas entre eles e contexto minimo necessario para cada agente. O isolamento deve ser aplicado por permissoes, montagem de contexto e ferramentas, nao apenas por nomes de pastas.
