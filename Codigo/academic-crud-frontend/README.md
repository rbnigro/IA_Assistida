# Academic CRUD Frontend

Frontend nativo em TypeScript, HTML e CSS para o CRUD de pacientes.

## Executar localmente

Com o backend Spring Boot disponível em `http://localhost:8080`:

```powershell
npm install
npm run dev
```

Abra `http://localhost:5173`. O Vite encaminha as requisicoes `/api` para o backend.

## Validar

```powershell
npm run build
```

A interface oferece listagem, busca por nome ou CPF, cadastro, edicao e exclusao. Os campos obrigatorios e os limites de tamanho seguem `Codigo/academic-crud-backend/API_PAYLOADS.md` e os DTOs do backend.