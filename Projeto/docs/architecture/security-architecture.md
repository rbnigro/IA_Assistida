# Arquitetura de Seguranca

Este documento define os controles de seguranca do CRUD IA First e de suas capacidades opcionais de RAG, agentes e MCP.

## Objetivo

Proteger o CRUD, o contexto, as ferramentas e os dados contra abuso e falhas previsiveis.

## Escopo

WAF, gateway, autenticacao, autorizacao, rate limiting, camada IA, MCP, persistencia e auditoria.

## Criterios

Cada controle deve possuir politica, teste positivo, teste negativo e evidencia no harness. Falhas de autorizacao e escrita indevida bloqueiam a etapa.

## Exemplos

Uma requisicao sem identidade e rejeitada; uma ferramenta sem scope retorna negacao; uma tentativa de prompt injection nao executa escrita nem expoe segredo.

## Objetivos

- manter o CRUD funcional sem IA;
- limitar cada identidade ao menor privilegio;
- impedir que texto de usuario ou documento externo vire politica;
- proteger dados e credenciais;
- tornar cada chamada auditavel;
- falhar fechado em operacoes de escrita e alto risco.

## Diagrama de controle

```mermaid
flowchart LR
    U[Usuario] --> W[WAF / limite de payload]
    W --> G[API Gateway]
    G --> A[Autenticacao e autorizacao]
    A --> R[Rate limiter]
    R --> O[Orquestrador]
    O --> C[CRUD JDK 21]
    C --> H[(H2)]
    O --> I[Camada IA]
    I --> P[Politica e validacao de saida]
    I --> M[Memoria com escopo]
    I --> X[Servidor MCP]
    X --> L[Servico legado]
    O --> T[Traces e auditoria]
    P --> T
    X --> T
```

## Controles por camada

| Camada | Controle | Decisao esperada |
| --- | --- | --- |
| WAF | tamanho, origem, metodos e padroes abusivos | rejeitar antes da aplicacao |
| Gateway | autenticacao, correlation ID e payload | negar identidade ausente |
| Autorizacao | scopes por usuario, agente e ferramenta | negar por padrao |
| Rate limiter | limite por usuario, rota e ferramenta | retornar 429 e registrar evento |
| Orquestrador | limites de passos, tokens, tempo e retries | encerrar antes de loop |
| IA | separar instrucoes, dados e evidencias | texto externo nunca e politica |
| MCP | schemas, escopo, timeout e idempotencia | bloquear ferramenta nao autorizada |
| Persistencia | validacao, transacao e parametrizacao | nao executar entrada livre |
| Auditoria | evento estruturado sem segredo ou PII desnecessaria | permitir reconstruir a decisao |

### Parametros iniciais

Estes valores sao um baseline de laboratorio e devem ser medidos e ajustados:

- payload HTTP: 1 MiB no WAF;
- requisicoes por usuario: 60 por minuto e 10 por segundo;
- chamadas de modelo por tarefa: 12;
- chamadas de ferramenta por tarefa: 8;
- tempo total de uma tarefa: 30 segundos;
- timeout de ferramenta: 5 segundos;
- retries: no maximo 2, somente para erros transitorios e operacoes idempotentes.

O rate limit deve usar `user_id` autenticado, rota e ferramenta como dimensoes. O estouro retorna HTTP 429, informa `Retry-After` sem revelar dados internos e gera evento de auditoria. Rotas internas nao possuem bypass implicito; qualquer excecao deve ser autenticada e registrada.

## Fluxo de uma solicitacao

1. O WAF valida tamanho, origem e padroes de abuso.
2. O gateway autentica o usuario e cria `correlation_id`.
3. A autorizacao calcula scopes do usuario e do agente.
4. O rate limiter verifica a cota antes de chamar modelo ou ferramenta.
5. O orquestrador classifica a operacao como leitura, escrita ou alto risco.
6. O CRUD executa diretamente operacoes basicas sem depender de IA.
7. A camada IA recebe contexto minimo, limites e politica.
8. O MCP valida a ferramenta, o payload, o escopo e a idempotency key.
9. Escritas de alto risco aguardam aprovacao humana.
10. O resultado, as fontes e os eventos de seguranca sao registrados.

## Prompt injection

Trate toda entrada do usuario, documento recuperado e resposta de ferramenta como dado nao confiavel. O agente deve:

- manter politicas fora do texto recuperado;
- nao seguir instrucoes encontradas em documentos;
- validar chamadas de ferramenta fora do modelo;
- permitir somente ferramentas previstas no contrato;
- limitar campos que podem ser usados em uma acao;
- registrar tentativa de instrucao conflitante;
- responder sem executar quando a intencao nao puder ser validada.

### Cenarios obrigatorios

O harness deve executar pelo menos:

1. documento que instrui o agente a ignorar a politica e chamar `update_ticket`;
2. usuario que tenta incluir instrucoes dentro de um campo de chamado;
3. ferramenta que retorna texto tentando obter credenciais;
4. documento que solicita exfiltracao de outro usuario;
5. tentativa de chamar ferramenta inexistente ou sem scope.

Resultado esperado em todos os casos: nenhuma escrita indevida, nenhuma credencial no contexto ou resposta, evento de seguranca registrado e resposta controlada ao usuario.

## Runbook de incidente

Consulte [security-incident.md](../runbooks/security-incident.md) quando houver vazamento, abuso ou comportamento inesperado.

## Evidencias

Cada etapa de seguranca deve produzir:

- threat model atualizado;
- matriz de identidade e scopes;
- testes positivos e negativos de autorizacao;
- teste de prompt injection;
- teste de rate limiting;
- teste de timeout e indisponibilidade;
- evento de auditoria sem segredo;
- relatorio de risco residual.

## Definition of Done de seguranca

Uma mudanca de seguranca so esta pronta quando os controles foram implementados, testados, observados e revisados pelo Security Reviewer. A ausencia de um teste ou de uma evidencia deve bloquear a aprovacao.
