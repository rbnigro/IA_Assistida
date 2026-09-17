# Harness de Baseline

`run_harness.py` e o avaliador independente do CRUD. Ele nao depende de IA nem da declaracao do agente autor.

## Execucao

Na raiz do repositorio:

```powershell
python Avaliacao/harness/run_harness.py --skip-backend
python Avaliacao/harness/run_harness.py --report Avaliacao/reports/baseline.json
python Avaliacao/harness/run_harness.py --strict-frontend
```

`--skip-backend` e apropriado quando Java/MySQL nao estao disponiveis. `--strict-frontend` transforma a ausencia de implementacao em bloqueio; sem essa opcao, a ausencia e registrada como ressalva durante a etapa de arquitetura.

## Verificacoes

- consistencia da documentacao com TypeScript/HTML/CSS;
- presenca dos tres tipos de arquivo do frontend;
- ausencia de artefatos do framework anterior;
- `npm run test`, quando o frontend possuir `package.json`;
- `mvn -q test` no backend;
- busca de padroes de segredos versionados;
- decisao reproduzivel: `approved`, `approved_with_remarks` ou `blocked`;
- relatorio JSON com contagem por status, detalhes e comandos.

O harness nao declara persistencia MySQL como verificada sem um teste de integracao executado. Essa evidencia continua sendo responsabilidade da etapa 03.
