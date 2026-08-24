 # Instrucoes dos Agentes

## Contexto do projeto

Este repositorio e um projeto academico IA First. O produto e um CRUD full-stack com backend em JDK 21, frontend em Angular 19 e banco H2.

## Regras de trabalho

- leia somente o contexto necessario para a tarefa;
- preserve contratos existentes;
- registre requisitos, arquivos alterados e testes;
- prefira mudancas pequenas e verificaveis;
- nao adicione IA sem caso de uso e criterio de aceite;
- mantenha o CRUD funcional quando modelos ou ferramentas de IA estiverem indisponiveis;
- nao introduza segredos, dados pessoais reais ou dependencias sem justificativa;
- atualize documentacao e ADRs quando a arquitetura mudar.

## Fluxo obrigatorio

1. identificar requisito e criterio de aceite;
2. definir ou atualizar contrato;
3. projetar a mudanca;
4. implementar em JDK 21 e Angular 19 conforme aplicavel;
5. testar backend, frontend e persistencia H2;
6. registrar `author_agent_id`;
7. solicitar Code Review a outro agente;
8. executar avaliacao e registrar evidencias;
9. publicar a etapa somente depois da aprovacao.

## Independencia da revisao

O agente autor nunca pode aprovar a propria implementacao. A avaliacao deve bloquear quando:

```text
author_agent_id == reviewer_agent_id
```

O Code Reviewer nao altera diretamente o codigo revisado. Depois de uma correcao, uma nova revisao independente e obrigatoria.

## Contexto permitido

O agente de codificacao recebe requisitos, contratos e documentacao liberada. Ele nao recebe automaticamente casos privados da avaliacao, solucoes de exercicios ou todo o material pedagogico.

## Validacao minima

Antes de concluir uma demanda, confirme:

- compilacao com JDK 21;
- testes do backend;
- testes do frontend Angular;
- testes de persistencia H2;
- validacao de contrato da API;
- Code Review independente;
- ausencia de segredos;
- registro de custo e latencia quando houver IA.
