# Runbook de Incidente de Seguranca

## Quando usar

Use este procedimento para prompt injection confirmada, vazamento de dado, chamada de ferramenta indevida, excesso de tentativas, segredo exposto ou falha de autorizacao.

## Contencao

1. Preserve `correlation_id`, timestamp, identidade e versao.
2. Desabilite a ferramenta ou rota afetada.
3. Revogue credencial ou scope comprometido.
4. Congele o trace e os artefatos da execucao.
5. Nao apague evidencias nem exponha dados sensiveis no relatorio.

Responsavel inicial: quem detectar o incidente deve registrar o evento e acionar o Security Reviewer. Para severidade alta ou critica, o operador deve desabilitar a rota ou ferramenta afetada e registrar o horario da acao.

## Diagnostico

Verifique, nesta ordem:

- entrada recebida e contexto autorizado;
- identidade do usuario e do agente;
- scopes calculados;
- chamadas de modelo e ferramentas;
- payload enviado ao MCP ou ao legado;
- resposta, erro, timeout e retries;
- logs, metricas e traces relacionados.

Classifique a severidade:

- **low:** tentativa bloqueada sem impacto;
- **medium:** comportamento anormal sem evidencia de exposicao;
- **high:** dados expostos, scope indevido ou chamada externa nao autorizada;
- **critical:** credencial comprometida, escrita indevida confirmada ou impacto amplo.

## Recuperacao

1. Corrija a causa no componente responsavel.
2. Adicione teste de regressao ao harness.
3. Execute Code Review e Security Review independentes.
4. Rode os cenarios de abuso e o fluxo funcional.
5. Reative o componente com limites conservadores.
6. Registre risco residual e acao preventiva.

Reative somente quando o teste de regressao passar, o Security Reviewer aprovar a correcao e o operador confirmar que credenciais, scopes e limites foram revisados. Para incidente high ou critical, exija aprovacao humana documentada.

## Relatorio minimo

```yaml
incident_id: INC-001
severity: high
correlation_id: request-abc
summary: descricao factual
impact: impacto observado
containment: medida aplicada
root_cause: causa confirmada
evidence: traces, logs e testes
regression_test: nome do teste
residual_risk: risco remanescente
status: closed
```
