# Stack Minima de Observabilidade

## Objetivo

Produzir dados concretos para diagnosticar o CRUD e medir custo, latencia, erros e uso de IA.

## Escopo

A stack cobre logs estruturados, metricas, traces e armazenamento local reproduzivel. Este documento define a stack-alvo; ela somente sera considerada implementada quando existir configuracao executavel e evidencia de coleta. O primeiro ambiente deve funcionar sem servico pago.

## Criterios

Toda requisicao deve possuir `correlation_id`. Toda chamada de IA ou ferramenta deve registrar duracao, resultado, erro e consumo sem registrar prompt sensivel ou segredo.

## Exemplos

Metricas minimas: `http_server_requests_total`, `http_server_request_duration_seconds`, `ai_tokens_total`, `ai_cost_total`, `agent_tool_calls_total` e `security_denials_total`.

## Stack recomendada

| Necessidade | Ferramenta | Funcao |
| --- | --- | --- |
| Logs | SLF4J + Logback | eventos JSON com correlation ID |
| Metricas | Micrometer | contadores, gauges e histogramas |
| Exposicao | endpoint `/actuator/prometheus` | disponibilizar metricas |
| Coleta | Prometheus | armazenar e consultar series temporais |
| Dashboards | Grafana | visualizar p50, p95, erros e custo |
| Traces | OpenTelemetry | seguir uma tarefa entre API, agente, MCP e MySQL |
| Armazenamento local | Docker Compose | reproduzir Prometheus e Grafana |

A aplicacao deve continuar executando se Prometheus, Grafana ou o coletor de traces estiverem indisponiveis. Observabilidade nao pode derrubar o CRUD.

## Eventos obrigatorios

```json
{
  "event": "agent.tool_call",
  "timestamp": "2026-08-24T12:00:00Z",
  "correlation_id": "request-abc",
  "task_id": "task-001",
  "agent_id": "developer-01",
  "tool": "tickets.read",
  "duration_ms": 120,
  "outcome": "success",
  "tokens_in": 0,
  "tokens_out": 0
}
```

Nunca grave token, senha, conteudo integral de PII ou prompt confidencial. Use nomes de ferramentas, tamanhos, contagens e hashes quando precisar correlacionar dados.

## Metricas e formulas

- p50 e p95 por endpoint e ferramenta;
- taxa de erro por classe;
- chamadas de modelo por tarefa;
- tokens de entrada e saida;
- custo estimado por etapa;
- negacoes de seguranca;
- retries e timeouts;
- horas sem IA e com IA;
- retrabalho.

```text
Ganho de horas = horas_sem_IA - horas_com_IA
Produtividade (%) = ganho_de_horas / horas_sem_IA * 100
SpeedUp = horas_sem_IA / horas_com_IA
```

## Alertas do laboratorio

- p95 HTTP acima de 2 segundos;
- taxa de erro acima de 5% em cinco minutos;
- timeout de ferramenta acima de 1% das chamadas;
- aumento de negacoes de seguranca;
- custo por tarefa acima do limite definido;
- ausencia de traces em tarefas concluidas.

Os limiares sao baselines de laboratorio e devem ser ajustados com dados reais.
