# Portfolio de Engenharia de IA Aplicada

Este plano converte a transcricao de divulgacao da pos-graduacao em uma formacao pratica. O objetivo nao e apenas construir demos, mas demonstrar que voce sabe projetar, proteger, observar, testar e explicar sistemas de IA que se conectam a software existente.

## Resultado esperado

Ao final, o portfolio deve conter um sistema integrador publicado e documentado, acompanhado de projetos menores que comprovem as decisoes tecnicas. O sistema deve ser capaz de:

- responder perguntas sobre uma base de conhecimento usando RAG;
- decompor uma solicitacao em tarefas e executar um plano;
- usar mais de um agente com papeis e limites claros;
- acessar um microservico legado por uma camada MCP segura;
- compartilhar contexto sem misturar dados de usuarios ou tarefas;
- exigir autenticacao, aplicar autorizacao e rate limiting;
- registrar custos, latencia, erros, decisoes e rastreabilidade;
- degradar com seguranca quando modelo, ferramenta ou servico legado falhar.

## O que precisa ser aprendido

### 1. Fundamentos de aplicacoes com LLM

Estude tokens e janela de contexto, embeddings, busca semantica, reranking, structured output, tool calling, streaming, avaliacao e controle de custos. Implemente primeiro um assistente deterministico com uma unica ferramenta. Ele sera a linha de base para comparar as versoes seguintes.

### 2. RAG de qualidade

Aprenda ingestao, limpeza, chunking, metadados, filtros, citacoes, atualizacao da base e avaliacao de recuperacao. O RAG deve responder "nao encontrei evidencia" quando a fonte nao sustentar a resposta.

Entregaveis:

- pipeline de ingestao reproduzivel;
- indice vetorial com metadados e controle de versao;
- conjunto de perguntas com resposta esperada e fontes esperadas;
- relatorio de precision@k, recall@k, faithfulness e latencia;
- demonstracao de uma falha corrigida por uma mudanca no pipeline.

### 3. Agentes autonomos

Estude diferenca entre chatbot, agente reativo e agente com memoria episodica. Pratique tool calling, estado, memoria de curto e longo prazo, limites de autonomia, aprovacao humana, idempotencia e recuperacao de falhas.

Implemente dois fluxos para a mesma tarefa:

- **ReAct:** o agente alterna raciocinio observavel, acao e observacao para resolver um problema;
- **Plan and Execute:** um agente cria um plano, executores realizam as etapas e um verificador valida o resultado.

Compare custo, latencia, qualidade, facilidade de auditoria e risco de cada padrao. Nao trate o texto interno de raciocinio como requisito de produto; registre somente decisoes, entradas, saidas e justificativas operacionais necessarias.

### 4. Orquestracao multiagente

Crie agentes com contratos explicitos, por exemplo:

- **Triagem:** classifica a solicitacao e decide quais capacidades sao necessarias;
- **Pesquisa:** recupera evidencias no RAG;
- **Operacoes:** chama o sistema legado apenas quando autorizado;
- **Code Reviewer:** revisa codigo, testes e contratos. Deve ser um agente independente do agente que implementou a demanda;
- **Revisor de resultado:** verifica evidencias, formato, politica e resultado da tarefa;
- **Coordenador:** controla estado, timeout, retries e encerramento.

O coordenador deve impedir loops, limitar numero de chamadas, propagar correlation ID e definir o que acontece quando um agente falha. Descreva o protocolo de mensagens e o modelo de contexto compartilhado em um diagrama e em um contrato versionado.

### Regra de revisao independente

Toda demanda de codigo deve registrar `author_agent_id` e `reviewer_agent_id`. O orquestrador deve rejeitar a revisao quando os identificadores forem iguais. O Code Reviewer recebe o diff, os requisitos, os testes e os contratos relevantes, mas nao deve alterar diretamente o codigo revisado.

O resultado da revisao deve classificar achados por severidade, indicar arquivo e evidencia, e terminar com uma decisao: aprovado, aprovado com ressalvas ou bloqueado. O agente que codificou pode corrigir os achados, mas uma nova revisao deve ser feita novamente por outro agente.

### 5. MCP e integracao com legado

Aprenda a diferenca entre recurso, prompt e ferramenta MCP. Construa um servidor MCP que encapsule um microservico legado simulado, com validacao de entrada, schemas, timeout, logs estruturados e respostas normalizadas. O legado nunca deve receber diretamente instrucoes livres do modelo.

O servidor deve:

- expor somente operacoes necessarias;
- validar tipos, limites e autorizacao por ferramenta;
- separar identidade do usuario de identidade do agente;
- aplicar timeout, retry limitado, circuit breaker e idempotency key;
- mascarar dados sensiveis nos logs;
- retornar erros acionaveis sem vazar detalhes internos.

### 6. Governanca, seguranca e desempenho

Documente ameacas e controles para prompt injection, data exfiltration, tool abuse, escalada de privilegio, vazamento de PII, envenenamento de documentos e denial of wallet.

Implemente e demonstre:

- autenticacao e autorizacao por escopo;
- rate limiting por usuario e ferramenta;
- WAF ou camada equivalente no ponto de entrada;
- validacao de origem e tamanho de payload;
- segredo fora do codigo;
- politica de retencao e redacao de logs;
- limites de tokens, tempo, custo e numero de ferramentas;
- testes de abuso e de prompt injection;
- metricas de p50, p95, taxa de erro e custo por tarefa.

## Projetos do portfolio

### Projeto 1: RAG auditavel

**Tema:** uma base de normas tecnicas, manuais ou politicas publicas.

**Demonstracao:** pergunta, trechos recuperados, resposta com citacoes, confianca operacional e caso de abstencao.

**Prova de maturidade:** avaliacao automatizada, conjunto de regressao e explicacao de como uma mudanca alterou os resultados.

### Projeto 2: Agente de pesquisa com memoria

**Tema:** transformar uma pergunta complexa em pesquisa com fontes.

**Demonstracao:** memoria episodica por tarefa, resumo de estado, retomada apos falha e limite de autonomia.

**Prova de maturidade:** comparacao entre agente reativo e Plan and Execute, incluindo custo e latencia.

### Projeto 3: MCP para servico legado

**Tema:** consultar pedidos, contratos ou chamados em uma API legada.

**Demonstracao:** o agente usa ferramentas MCP tipadas e nunca acessa o banco diretamente.

**Prova de maturidade:** scopes, rate limiting, timeout, circuit breaker, auditoria e testes de autorizacao negativa.

### Projeto integrador: microsite operacional com RAG, agentes e MCP

Construa um microsite que recebe uma solicitacao, consulta a base de conhecimento, verifica dados no legado e produz uma resposta ou acao aprovada.

Fluxo sugerido:

1. Usuario autentica e envia uma solicitacao.
2. Triagem classifica a intencao e o risco.
3. Pesquisa recupera fontes no RAG.
4. Coordenador monta um plano com etapas limitadas.
5. Operacoes consulta o legado via MCP, respeitando scopes.
6. Code Reviewer revisa o codigo e os testes por uma identidade diferente da autoria.
7. Revisor de resultado valida fontes, politica e formato.
8. Acoes de escrita exigem aprovacao humana.
9. O sistema retorna resposta, fontes, status e identificador de auditoria.

O microsite deve ter uma tela de resposta, uma tela de historico/auditoria e uma tela tecnica simples com latencia, erros, custo e chamadas de ferramentas. Evite transformar a interface em uma exibicao de prompts; mostre fatos operacionais que um usuario pode verificar.

## Evidencias obrigatorias

Cada projeto deve incluir:

- README com problema, arquitetura, limites e como executar;
- diagrama de componentes e sequencia;
- ADRs para as principais decisoes;
- codigo, testes e dados de exemplo licenciados ou publicos;
- ambiente reproduzivel com variaveis documentadas;
- avaliacao com metricas e casos de falha;
- registro de autoria e revisao independente para cada mudanca de codigo;
- relatorio do Code Reviewer com achados, severidade e decisao;
- threat model e matriz de permissoes;
- relatorio de latencia e custo;
- demo curta com roteiro;
- post-mortem de pelo menos uma falha real encontrada no desenvolvimento.

## Ordem de estudo e criterio de passagem

Nao avance apenas porque a aplicacao "respondeu". Passe de etapa quando conseguir explicar e demonstrar o criterio abaixo:

| Etapa | Criterio de passagem |
| --- | --- |
| Fundamentos | Diferenciar prompt, contexto, ferramenta, memoria e conhecimento recuperado. |
| RAG | Medir recuperacao e identificar resposta sem evidencia. |
| Agentes | Limitar autonomia e recuperar falhas sem duplicar efeitos. |
| Multiagente | Explicar contratos, estado, timeouts e encerramento. |
| MCP | Expor ferramenta tipada, autorizada e observavel sobre o legado. |
| Producao | Mostrar seguranca, custo, latencia, testes e plano de operacao. |
| Entrevista | Defender trade-offs com numeros e reconhecer limites do sistema. |

## Sequencia de execucao

Use ciclos curtos e publicaveis. Ao terminar cada ciclo, atualize o README, rode a avaliacao e registre uma decisao ou falha aprendida.

1. **Ciclo 0 - Baseline:** escolha um dominio publico, defina personas, riscos, perguntas de avaliacao e uma API legada simulada.
2. **Ciclo 1 - RAG minimo:** implemente ingestao, busca e citacoes. Crie o primeiro conjunto de regressao.
3. **Ciclo 2 - RAG avaliado:** adicione metadados, filtros, reranking, abstencao e metricas de recuperacao.
4. **Ciclo 3 - Agente reativo:** adicione uma ferramenta segura, estado de tarefa, limites e testes de falha.
5. **Ciclo 4 - Planejamento:** implemente Plan and Execute e compare-o com ReAct usando as mesmas tarefas.
6. **Ciclo 5 - MCP:** substitua a integracao direta pela camada MCP, com schemas, scopes e auditoria.
7. **Ciclo 6 - Multiagente:** separe triagem, pesquisa, operacoes e revisao; teste timeouts e mensagens invalidas.
8. **Ciclo 7 - Producao:** adicione autenticacao, rate limiting, WAF, observabilidade, custos e testes de seguranca.
9. **Ciclo 8 - Microsite:** construa a experiencia do usuario, aprovacao humana e historico auditavel.
10. **Ciclo 9 - Entrevista:** grave a demo, publique ADRs e pratique respostas baseadas nos numeros coletados.

## Stack de referencia

Escolha ferramentas que permitam explicar os fundamentos. Uma combinacao possivel e Python com FastAPI, um framework de orquestracao de agentes, PostgreSQL com extensao vetorial, Redis para estado temporario, OpenTelemetry para traces, Docker Compose para reproducao e Playwright para o fluxo do microsite. O provedor do modelo pode variar; mantenha uma interface de adaptacao para trocar o modelo sem reescrever os agentes.

Nao adicione uma tecnologia apenas para parecer sofisticado. Cada componente deve ter uma responsabilidade documentada, um teste e uma razao para existir.

## Estrutura sugerida do repositorio

```text
portfolio-ia/
	apps/web/                 # microsite e fluxo de aprovacao
	services/api/             # autenticacao, entrada e consulta de tarefas
	services/orchestrator/    # estado, planos, timeouts e politicas
	services/mcp-legacy/      # ferramentas MCP e adaptador do legado
	services/rag/             # ingestao, busca e avaliacao
	agents/                   # papeis, contratos e prompts versionados
	evals/                    # dataset, avaliadores e resultados
	security/                 # threat model, scopes e testes de abuso
	docs/                     # arquitetura, sequencias, ADRs e runbooks
	infra/                    # compose, configuracoes e observabilidade
```

## Definicao de pronto

O projeto integrador so esta pronto quando uma pessoa externa consegue executa-lo com instrucoes claras, reproduzir uma avaliacao, observar uma tarefa do inicio ao fim e entender por que uma ferramenta foi ou nao foi chamada. Toda mudanca de codigo deve ter autoria e revisao por agentes diferentes, e o harness deve bloquear a aprovacao quando essa regra for violada. Tambem deve ser possivel demonstrar uma falha controlada, consultar seu trace e explicar o mecanismo que evitou um efeito indevido.

## Perguntas de entrevista que o portfolio deve responder

- Por que usar multiagente em vez de um agente unico?
- Quando ReAct e melhor que Plan and Execute?
- Como evitar que agentes compartilhem contexto indevido?
- Como um agente acessa um legado sem ganhar privilegio excessivo?
- O que acontece quando o MCP fica lento ou indisponivel?
- Como voce mede se o RAG esta correto?
- Como detecta prompt injection e abuso de ferramentas?
- Como reduz p95, custo e numero de chamadas?
- Quais acoes exigem aprovacao humana?
- Como reproduz uma decisao tomada pelo sistema?

## Regra de qualidade do portfolio

Uma demo bonita sem testes, metricas, seguranca e explicacao de falhas vale pouco. A unidade de progresso sera uma capacidade comprovada por codigo, teste, numero, diagrama e decisao registrada.
