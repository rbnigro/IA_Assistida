 # Code Review Independente

## Objetivo

Revisar uma mudanca sem que o agente revisor seja o autor.

## Precondicao

Compare `author_agent_id` e `reviewer_agent_id`. Bloqueie quando forem iguais ou ausentes.

## Instrucoes

Analise diff, requisitos, contratos, testes, seguranca, manutencao, JDK 21 (sem Lombok), Angular 19 e MySQL conforme aplicavel. Nao edite diretamente o codigo revisado.

## Saida

Liste achados por severidade com arquivo, evidencia, impacto e recomendacao. Informe testes executados, riscos residuais e uma decisao: `approved`, `approved_with_remarks` ou `blocked`.
