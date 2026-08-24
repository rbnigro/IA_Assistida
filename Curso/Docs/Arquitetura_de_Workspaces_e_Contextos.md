 # Arquitetura de Workspaces e Contextos

## Tres ambientes

```text
Curso      -> aprender e receber desafios
Projeto    -> implementar e documentar
Avaliacao  -> testar e medir independentemente
```

Os tres sao pastas abertas por arquivos `.code-workspace` diferentes, mas fazem parte de um unico repositorio Git.

## Workspace Curso

Contem aulas, referencias, exercicios e requisitos liberados. O agente tutor pode consultar o material didatico. O agente de codificacao nao deve receber automaticamente respostas, solucoes ou material futuro.

## Workspace Projeto

Contem codigo, agentes, prompts, servicos, testes e documentacao tecnica. O CRUD em JDK 21, Angular 19 e H2 e desenvolvido aqui.

## Workspace Avaliacao

Contem cenarios, rubricas, avaliadores e relatorios. O harness verifica o projeto sem depender da declaracao do agente autor.

## Entrega controlada

O Curso fornece ao Projeto somente requisitos, contratos, restricoes e definition of done. A solucao nao deve ser transferida junto com o desafio.

## Contexto minimo

```yaml
workspace: Projeto
allowed:
	- requirements/REQ-001.md
	- docs/architecture/components.md
excluded:
	- Curso/referencias/solution-key.md
	- Avaliacao/rubrics/private-cases.yaml
token_budget: 12000
```

Separar pastas melhora a organizacao, mas isolamento real exige permissoes, ferramentas, montagem de contexto e logs de acesso.

## Fluxo

```text
aprender -> receber desafio -> implementar -> testar -> revisar -> avaliar -> corrigir -> publicar
```

Essa separacao simula a pos-graduacao: aprender, aplicar e ser avaliado sao atividades distintas.
