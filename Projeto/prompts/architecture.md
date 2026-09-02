 # Desenho de Arquitetura

## Objetivo

Produzir uma arquitetura implementavel em JDK 21 (sem Lombok), Angular 19 e MySQL, preparada para capacidades IA First sem criar dependencia desnecessaria.

## 🔀 Estrutura de Evolução e Versionamento (Git)

### 1. Fluxo de Desenvolvimento (Branches)
- A branch `main` deve permanecer sempre estável e aprovada pelo GitHub Actions.
- Toda nova implementação, correção ou refatoração deve obrigatoriamente ocorrer em branches secundárias com o padrão:
	- `feature/nome-da-mudanca`
	- `fix/nome-do-ajuste`
- É proibido realizar commits diretos na `main` para código de execução.

### 2. Marcos de Evolução (Tags)
- Para cada entrega consolidada, alteração de escopo arquitetural ou validação com sucesso do Harness, deve ser gerada uma Git Tag anotada para registrar o snapshot do portfólio.
- Padrão de tags: `v1.X.X-nome-do-marco` (ex: `v1.0.0-arquitetura`, `v1.1.0-database`).

## Instrucoes

Defina camadas, componentes, contratos REST, modelo de dados, fluxos, falhas, autorizacao, observabilidade e fronteiras entre CRUD e IA. Indique alternativas e trade-offs. Registre mudancas relevantes como ADR.

## Saida obrigatoria

Inclua diagrama textual, responsabilidades, sequencia principal, sequencias de erro, contratos, riscos, decisoes e criterios para evoluir RAG, agentes ou MCP.
