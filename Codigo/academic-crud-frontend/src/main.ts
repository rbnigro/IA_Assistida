import './styles.css';

type Paciente = {
  id: number;
  nome: string;
  cpf: string;
  dataNascimento: string;
  endereco: string | null;
  numero: number | null;
  complemento: string | null;
  cidade: string | null;
  estado: string | null;
  cep: string | null;
  telefone: string | null;
  email: string | null;
  genero: string | null;
  convenio: string | null;
  especialidade: string | null;
  tipagemSanguinea: string | null;
  fatorRh: string | null;
  alergias: string | null;
  usoContinuoMedicamentos: string | null;
  doencasPreexistentes: string | null;
  observacoes: string | null;
};

type PacientePayload = Omit<Paciente, 'id'>;
type ApiError = { erro?: string };

const fields: Array<{ key: keyof PacientePayload; label: string; type?: string; maxlength?: number }> = [
  { key: 'nome', label: 'Nome completo', maxlength: 50 },
  { key: 'cpf', label: 'CPF', maxlength: 11 },
  { key: 'dataNascimento', label: 'Data de nascimento', type: 'date' },
  { key: 'endereco', label: 'Endereco', maxlength: 50 },
  { key: 'numero', label: 'Numero', type: 'number' },
  { key: 'complemento', label: 'Complemento', maxlength: 5 },
  { key: 'cidade', label: 'Cidade', maxlength: 30 },
  { key: 'estado', label: 'UF', maxlength: 2 },
  { key: 'cep', label: 'CEP', maxlength: 8 },
  { key: 'telefone', label: 'Telefone', maxlength: 11 },
  { key: 'email', label: 'E-mail', type: 'email', maxlength: 30 },
  { key: 'genero', label: 'Genero', maxlength: 1 },
  { key: 'convenio', label: 'Convenio', maxlength: 12 },
  { key: 'especialidade', label: 'Especialidade', maxlength: 30 },
  { key: 'tipagemSanguinea', label: 'Tipagem sanguinea', maxlength: 2 },
  { key: 'fatorRh', label: 'Fator Rh', maxlength: 1 },
  { key: 'alergias', label: 'Alergias', type: 'textarea' },
  { key: 'usoContinuoMedicamentos', label: 'Medicamentos de uso continuo', type: 'textarea' },
  { key: 'doencasPreexistentes', label: 'Doencas preexistentes', type: 'textarea' },
  { key: 'observacoes', label: 'Observacoes', type: 'textarea' }
];

const app = document.querySelector<HTMLDivElement>('#app');
if (!app) throw new Error('Elemento raiz nao encontrado');

app.innerHTML = `
  <header class="topbar">
    <div class="brand"><span class="brand-mark">+</span><span>Prontuario</span></div>
    <div class="connection"><span class="status-dot"></span><span id="health-label">Verificando API</span></div>
  </header>
  <main class="shell">
    <section class="intro">
      <div><p class="eyebrow">Clinica academica</p><h1>Pacientes</h1><p class="lede">Cadastre, consulte e mantenha os registros clinicos em um unico lugar.</p></div>
      <button class="button button-primary" id="new-patient" type="button"><span aria-hidden="true">+</span> Novo paciente</button>
    </section>
    <section class="toolbar" aria-label="Filtros de pacientes">
      <label class="search-field"><span aria-hidden="true">⌕</span><input id="search-input" aria-label="Buscar por nome ou CPF" type="search" placeholder="Buscar por nome ou CPF" autocomplete="off" /><button id="clear-search" class="clear-search" type="button" aria-label="Limpar busca">×</button></label>
      <span class="record-count" id="record-count">0 registros</span>
      <button id="refresh" class="icon-button" type="button" aria-label="Atualizar lista" title="Atualizar lista">↻</button>
    </section>
    <div id="feedback" class="feedback" role="status" aria-live="polite"></div>
    <section class="table-wrap" aria-label="Lista de pacientes">
      <table><thead><tr><th>Paciente</th><th>CPF</th><th>Nascimento</th><th>Contato</th><th>Convenio</th><th><span class="sr-only">Acoes</span></th></tr></thead><tbody id="patient-list"></tbody></table>
      <div id="empty-state" class="empty-state"><span class="empty-icon">⌁</span><h2>Nenhum paciente encontrado</h2><p>Comece adicionando o primeiro registro ou ajuste sua busca.</p></div>
    </section>
  </main>
  <dialog id="patient-dialog"><form id="patient-form" method="dialog"><div class="dialog-header"><div><p class="eyebrow">Registro clinico</p><h2 id="dialog-title">Novo paciente</h2></div><button id="close-dialog" class="close-button" type="button" aria-label="Fechar">×</button></div><div id="form-error" class="form-error" role="alert"></div><div class="form-grid" id="form-fields"></div><div class="dialog-actions"><button id="cancel-dialog" class="button button-quiet" type="button">Cancelar</button><button class="button button-primary" type="submit"><span aria-hidden="true">✓</span> Salvar paciente</button></div></form></dialog>
`;

const formFields = document.querySelector<HTMLDivElement>('#form-fields');
const form = document.querySelector<HTMLFormElement>('#patient-form');
const dialog = document.querySelector<HTMLDialogElement>('#patient-dialog');
const list = document.querySelector<HTMLTableSectionElement>('#patient-list');
const emptyState = document.querySelector<HTMLDivElement>('#empty-state');
const feedback = document.querySelector<HTMLDivElement>('#feedback');
const searchInput = document.querySelector<HTMLInputElement>('#search-input');
const count = document.querySelector<HTMLSpanElement>('#record-count');
const formError = document.querySelector<HTMLDivElement>('#form-error');
const dialogTitle = document.querySelector<HTMLHeadingElement>('#dialog-title');
let patients: Paciente[] = [];
let editingId: number | null = null;

function escapeHtml(value: string | null): string {
  return (value ?? '').replace(/[&<>'"]/g, character => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' })[character] ?? character);
}

function formatDate(value: string): string {
  if (!value) return '—';
  return new Intl.DateTimeFormat('pt-BR', { timeZone: 'UTC' }).format(new Date(`${value}T00:00:00Z`));
}

function formatCpf(value: string): string {
  return value.length === 11 ? `${value.slice(0, 3)}.${value.slice(3, 6)}.${value.slice(6, 9)}-${value.slice(9)}` : value;
}

function renderForm(): void {
  if (!formFields) return;
  formFields.innerHTML = fields.map(field => {
    const required = field.key === 'nome' || field.key === 'cpf' || field.key === 'dataNascimento';
    const wide = field.type === 'textarea' || field.key === 'endereco';
    const control = field.type === 'textarea'
      ? `<textarea id="${field.key}" name="${field.key}" rows="3"></textarea>`
      : `<input id="${field.key}" name="${field.key}" type="${field.type ?? 'text'}"${field.maxlength ? ` maxlength="${field.maxlength}"` : ''}${required ? ' required' : ''}${field.key === 'cpf' ? ' inputmode="numeric" pattern="[0-9]{11}"' : ''}>`;
    return `<label class="field ${wide ? 'field-wide' : ''}" for="${field.key}"><span>${field.label}${required ? ' *' : ''}</span>${control}</label>`;
  }).join('');
}

function showFeedback(message: string, kind: 'success' | 'error' = 'success'): void {
  if (!feedback) return;
  feedback.textContent = message;
  feedback.className = `feedback ${kind} visible`;
  window.setTimeout(() => { feedback.className = 'feedback'; }, 4500);
}

function renderPatients(): void {
  if (!list || !emptyState || !searchInput || !count) return;
  const query = searchInput.value.trim().toLowerCase();
  const visible = patients.filter(patient => patient.nome.toLowerCase().includes(query) || patient.cpf.includes(query.replace(/\D/g, '')));
  count.textContent = `${visible.length} ${visible.length === 1 ? 'registro' : 'registros'}`;
  emptyState.classList.toggle('visible', visible.length === 0);
  list.innerHTML = visible.map(patient => `
    <tr><td><div class="patient-name">${escapeHtml(patient.nome)}</div><div class="patient-city">${escapeHtml(patient.cidade) || 'Cidade nao informada'}</div></td>
    <td class="mono">${escapeHtml(formatCpf(patient.cpf))}</td><td>${formatDate(patient.dataNascimento)}</td>
    <td><div>${escapeHtml(patient.telefone) || 'Sem telefone'}</div><div class="muted">${escapeHtml(patient.email) || 'Sem e-mail'}</div></td>
    <td>${escapeHtml(patient.convenio) || '<span class="muted">Particular</span>'}</td>
    <td><div class="row-actions"><button class="row-action" data-action="edit" data-id="${patient.id}" type="button">Editar</button><button class="row-action danger" data-action="delete" data-id="${patient.id}" type="button">Excluir</button></div></td></tr>`).join('');
}

function setFormValues(patient?: Paciente): void {
  fields.forEach(field => {
    const control = document.querySelector<HTMLInputElement | HTMLTextAreaElement>(`#${field.key}`);
    if (control) control.value = patient?.[field.key]?.toString() ?? '';
  });
}

function openForm(patient?: Paciente): void {
  editingId = patient?.id ?? null;
  if (dialogTitle) dialogTitle.textContent = patient ? 'Editar paciente' : 'Novo paciente';
  if (formError) formError.textContent = '';
  setFormValues(patient);
  dialog?.showModal();
  document.querySelector<HTMLInputElement>('#nome')?.focus();
}

async function request<T>(url: string, options?: RequestInit): Promise<T> {
  const response = await fetch(url, { headers: { 'Content-Type': 'application/json', ...(options?.headers ?? {}) }, ...options });
  if (!response.ok) {
    const body = await response.json().catch(() => ({})) as ApiError;
    throw new Error(body.erro || `Nao foi possivel concluir a operacao (${response.status})`);
  }
  if (response.status === 204) return undefined as T;
  const body = await response.text();
  if (!body) return undefined as T;
  try {
    return JSON.parse(body) as T;
  } catch {
    return body as T;
  }
}

async function loadPatients(): Promise<void> {
  try {
    patients = await request<Paciente[]>('/api/pacientes');
    renderPatients();
  } catch (error) {
    showFeedback(error instanceof Error ? error.message : 'Falha ao carregar pacientes.', 'error');
  }
}

function readPayload(): PacientePayload {
  const data = new FormData(form as HTMLFormElement);
  const payload = {} as Record<keyof PacientePayload, string | number | null>;
  fields.forEach(field => {
    const value = String(data.get(field.key) ?? '').trim();
    payload[field.key] = field.key === 'numero' ? (value ? Number(value) : null) : (value || null);
  });
  return payload as PacientePayload;
}

formFields && renderForm();
document.querySelector('#new-patient')?.addEventListener('click', () => openForm());
document.querySelector('#close-dialog')?.addEventListener('click', () => dialog?.close());
document.querySelector('#cancel-dialog')?.addEventListener('click', () => dialog?.close());
document.querySelector('#refresh')?.addEventListener('click', () => void loadPatients());
document.querySelector('#clear-search')?.addEventListener('click', () => { if (searchInput) searchInput.value = ''; renderPatients(); });
searchInput?.addEventListener('input', renderPatients);

list?.addEventListener('click', event => {
  const button = (event.target as HTMLElement).closest<HTMLButtonElement>('button[data-action]');
  if (!button) return;
  const patient = patients.find(item => item.id === Number(button.dataset.id));
  if (!patient) return;
  if (button.dataset.action === 'edit') openForm(patient);
  if (button.dataset.action === 'delete' && window.confirm(`Excluir o registro de ${patient.nome}?`)) {
    void request<void>(`/api/pacientes/${patient.id}`, { method: 'DELETE' }).then(() => { showFeedback('Paciente excluido.'); return loadPatients(); }).catch(error => showFeedback(error instanceof Error ? error.message : 'Falha ao excluir paciente.', 'error'));
  }
});

form?.addEventListener('submit', event => {
  event.preventDefault();
  const payload = readPayload();
  const url = editingId ? `/api/pacientes/${editingId}` : '/api/pacientes';
  const method = editingId ? 'PUT' : 'POST';
  void request<Paciente>(url, { method, body: JSON.stringify(payload) }).then(() => { dialog?.close(); showFeedback(editingId ? 'Paciente atualizado.' : 'Paciente cadastrado.'); return loadPatients(); }).catch(error => { if (formError) formError.textContent = error instanceof Error ? error.message : 'Revise os campos e tente novamente.'; });
});

void request<string>('/api/pacientes/health').then(() => { const health = document.querySelector('#health-label'); if (health) health.textContent = 'API online'; }).catch(() => { const health = document.querySelector('#health-label'); if (health) health.textContent = 'API indisponivel'; });
void loadPatients();