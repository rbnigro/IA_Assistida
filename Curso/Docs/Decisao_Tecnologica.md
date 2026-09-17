# Decisão Tecnológica - CRUD Clínico de Pacientes

## Arquitetura de Repositórios
- **Abordagem:** Multi-Repo
- **Localização:** Pasta `/Codigo` na raiz do workspace UNIPDS

## Stack Tecnológica
- **Backend:** Java 21 (Spring Boot)
  - *Restrição Crítica:* **PROIBIDO** o uso de Lombok. Utilizar encapsulamento nativo do Java (Getters, Setters, Construtores explícitos).
- **Frontend:** TypeScript sem framework, HTML semântico e CSS nativo
- **Build e testes do frontend:** Node.js/npm quando houver scripts versionados; o harness independente valida a estrutura mesmo sem dependências instaladas.
- **Banco de Dados:** MySQL

