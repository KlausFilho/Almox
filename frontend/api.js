const BASE = (typeof API_BASE_URL !== 'undefined') ? API_BASE_URL : 'http://localhost:8080/api';

async function req(method, path, body) {
  const opts = {
    method,
    headers: { 'Content-Type': 'application/json' }
  };
  if (body) opts.body = JSON.stringify(body);
  const res = await fetch(BASE + path, opts);
  if (!res.ok) {
    const msg = await res.text();
    throw new Error(msg || `Erro ${res.status}`);
  }
  const ct = res.headers.get('content-type') || '';
  return ct.includes('json') ? res.json() : res.text();
}

const api = {
  // FERRAMENTAS
  ferramentas: {
    listar:  () => req('GET',  '/ferramentas'),
    buscar:  (id) => req('GET', `/ferramentas/${id}`),
    criar:   (d) => req('POST', '/ferramentas', d),
    editar:  (id, d) => req('PUT', `/ferramentas/${id}`, d),
    deletar: (id) => req('DELETE', `/ferramentas/${id}`),
  },

  // FUNCIONÁRIOS
  funcionarios: {
    listar:  () => req('GET',  '/funcionarios'),
    buscar:  (id) => req('GET', `/funcionarios/${id}`),
    criar:   (d) => req('POST', '/funcionarios', d),
    editar:  (id, d) => req('PUT', `/funcionarios/${id}`, d),
    deletar: (id) => req('DELETE', `/funcionarios/${id}`),
  },

  // MOVIMENTAÇÕES
  movimentacoes: {
    listar:    () => req('GET',  '/movimentacoes'),
    registrar: (d) => req('POST', '/movimentacoes', d),
  },

  // DASHBOARD
  dashboard: {
    resumo: () => req('GET', '/dashboard'),
  },
};

// ── Utilitários ──────────────────────────────────────────────────────────────

function toast(msg, type = 'success') {
  let container = document.querySelector('.toast-container');
  if (!container) {
    container = document.createElement('div');
    container.className = 'toast-container';
    document.body.appendChild(container);
  }
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.innerHTML = `<span>${type === 'success' ? '✓' : '✕'}</span> ${msg}`;
  container.appendChild(el);
  setTimeout(() => el.remove(), 3500);
}

function activateNav(page) {
  document.querySelectorAll('.nav-link').forEach(a => {
    a.classList.toggle('active', a.dataset.page === page);
  });
}

// ── Menu mobile (sidebar retrátil) ──────────────────────────────────────────
function toggleSidebar() {
  const sidebar = document.querySelector('.sidebar');
  const overlay = document.querySelector('.sidebar-overlay');
  if (!sidebar) return;
  sidebar.classList.toggle('open');
  if (overlay) overlay.classList.toggle('open');
}

function closeSidebar() {
  const sidebar = document.querySelector('.sidebar');
  const overlay = document.querySelector('.sidebar-overlay');
  if (sidebar) sidebar.classList.remove('open');
  if (overlay) overlay.classList.remove('open');
}

document.addEventListener('DOMContentLoaded', () => {
  const overlay = document.querySelector('.sidebar-overlay');
  if (overlay) overlay.addEventListener('click', closeSidebar);
  document.querySelectorAll('.nav-link').forEach(a => a.addEventListener('click', closeSidebar));
});

function formatDate(str) {
  if (!str) return '—';
  const d = new Date(str);
  return d.toLocaleString('pt-BR', { dateStyle: 'short', timeStyle: 'short' });
}
