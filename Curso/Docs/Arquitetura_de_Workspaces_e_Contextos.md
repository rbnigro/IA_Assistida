# Arquitetura de Workspaces e Contextos

## Tres ambientes

```text
Curso      -> aprender e receber desafios
Projeto    -> implementar e documentar
Avaliacao  -> testar e medir independentemente
```

Os tres sao pastas que fazem parte de um unico repositorio Git (UNIPDS), centralizados na mesma tela para governança do Harness.

## Workspace Curso

Contem aulas, referencias, exercicios e requisitos liberados. O agente tutor pode consultar o material didatico. O agente de codificacao nao deve receber automaticamente respostas, solucoes ou material futuro.

## Espaço de Desenvolvimento (Multi-Repo)

Localizado na pasta `/Codigo` na raiz do projeto. O CRUD em JDK 21 (sem Lombok), Angular 19 e MySQL é desenvolvido aqui de forma isolada, contendo os repositórios `academic-crud-backend` e `academic-crud-frontend`.

## Workspace Projeto

Contem a inteligência do sistema: agentes, prompts, especificações de serviços, testes arquiteturais e documentação tecnica. Ele orquestra e analisa o código gerado em `/Codigo`.

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
	- Codigo/academic-crud-backend/
	- Codigo/academic-crud-frontend/
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

### Documentos de Contexto Técnico
- [[Decisao_Tecnologica.md]]: Define a stack de execução (Java 21, Angular 19, MySQL) e a estratégia Multi-Repo do projeto.
