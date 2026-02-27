import { useState, useEffect } from "react";

const BASE = "http://localhost:3001";

function getHeaders() {
  const token = localStorage.getItem("accessToken");
  return {
    "Content-Type": "application/json",
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
  };
}

function euro(v: number | null) {
  return v != null
    ? "€ " + Number(v).toLocaleString("it-IT", { minimumFractionDigits: 2 })
    : "—";
}

// ─── TYPES ──────────────────────────────────────────────────────────────────

interface Cliente {
  id: string;
  ragioneSociale: string;
  email: string;
  telefono: string;
  tipo: string;
  fatturatoAnnuale: number;
  nomeContatto: string;
  cognomeContatto: string;
}

interface Fattura {
  idFattura: string;
  numeroFattura: number;
  data: string;
  importo: number;
  stato?: { stato: string };
}

const emptyForm = {
  ragioneSociale: "",
  partitaIva: "",
  tipo: "SRL",
  email: "",
  pec: "",
  nomeContatto: "",
  cognomeContatto: "",
  telefono: "",
};

// ─── SHARED STYLES ──────────────────────────────────────────────────────────

const TH: React.CSSProperties = {
  padding: "8px 12px",
  fontSize: 11,
  fontWeight: 500,
  color: "#999",
  textAlign: "left",
  textTransform: "uppercase",
  letterSpacing: "0.05em",
};

const TD: React.CSSProperties = {
  padding: "11px 12px",
  fontSize: 13,
  color: "#333",
  borderBottom: "1px solid #f0f0f0",
};

const inputStyle: React.CSSProperties = {
  padding: "8px 10px",
  border: "1px solid #e5e5e5",
  borderRadius: 6,
  fontSize: 13,
  color: "#111",
  outline: "none",
  width: "100%",
  background: "#fff",
};

// ─── COMPONENTS ─────────────────────────────────────────────────────────────

function StatoBadge({ stato }: { stato?: string }) {
  const v = stato?.toUpperCase();
  const map: Record<string, React.CSSProperties> = {
    PAGATA: { background: "#dcfce7", color: "#16a34a" },
    INSOLUTA: { background: "#fee2e2", color: "#dc2626" },
    default: { background: "#fef9c3", color: "#ca8a04" },
  };
  const s = map[v ?? ""] ?? map.default;
  return (
    <span
      style={{
        ...s,
        padding: "2px 10px",
        borderRadius: 20,
        fontSize: 11,
        fontWeight: 500,
      }}>
      {stato ?? "In attesa"}
    </span>
  );
}

function Btn({
  onClick,
  ghost,
  disabled,
  children,
}: {
  onClick?: () => void;
  ghost?: boolean;
  disabled?: boolean;
  children: React.ReactNode;
}) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      style={{
        padding: "7px 14px",
        borderRadius: 6,
        fontSize: 12,
        fontWeight: 500,
        cursor: disabled ? "default" : "pointer",
        outline: "none",
        transition: "background 0.15s",
        background: ghost ? "#fff" : "#111",
        color: ghost ? "#555" : "#fff",
        border: ghost ? "1px solid #e5e5e5" : "none",
        opacity: disabled ? 0.4 : 1,
      }}>
      {children}
    </button>
  );
}

function Field({
  label,
  children,
}: {
  label: string;
  children: React.ReactNode;
}) {
  return (
    <div style={{ display: "flex", flexDirection: "column", gap: 5 }}>
      <label
        style={{
          fontSize: 11,
          fontWeight: 500,
          color: "#777",
          textTransform: "uppercase",
          letterSpacing: "0.05em",
        }}>
        {label}
      </label>
      {children}
    </div>
  );
}

function Toast({ msg, ok }: { msg: string; ok: boolean }) {
  return (
    <div
      style={{
        position: "fixed",
        bottom: 20,
        left: "50%",
        transform: "translateX(-50%)",
        background: ok ? "#111" : "#dc2626",
        color: "#fff",
        padding: "10px 18px",
        borderRadius: 6,
        fontSize: 12,
        zIndex: 999,
        whiteSpace: "nowrap",
        boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
      }}>
      {msg}
    </div>
  );
}

// ─── MODAL NUOVO CLIENTE ─────────────────────────────────────────────────────

function ModalCliente({
  open,
  onClose,
  onSave,
}: {
  open: boolean;
  onClose: () => void;
  onSave: (form: typeof emptyForm) => Promise<void>;
}) {
  const [form, setForm] = useState(emptyForm);
  const [saving, setSaving] = useState(false);

  const set =
    (k: keyof typeof emptyForm) =>
    (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
      setForm((p) => ({ ...p, [k]: e.target.value }));

  if (!open) return null;

  const handleSave = async () => {
    setSaving(true);
    await onSave(form);
    setSaving(false);
    setForm(emptyForm);
  };

  return (
    <div
      onClick={(e) => {
        if (e.target === e.currentTarget) onClose();
      }}
      style={{
        position: "fixed",
        inset: 0,
        background: "rgba(0,0,0,0.25)",
        zIndex: 50,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}>
      <div
        style={{
          background: "#fff",
          borderRadius: 10,
          padding: 24,
          width: 440,
          maxWidth: "95vw",
          boxShadow: "0 8px 32px rgba(0,0,0,0.12)",
        }}>
        <p style={{ fontSize: 14, fontWeight: 500, marginBottom: 18 }}>
          Nuovo cliente
        </p>

        <div style={{ marginBottom: 12 }}>
          <Field label="Ragione Sociale">
            <input
              style={inputStyle}
              value={form.ragioneSociale}
              onChange={set("ragioneSociale")}
              placeholder="Acme S.r.l."
            />
          </Field>
        </div>

        <div
          style={{
            display: "grid",
            gridTemplateColumns: "1fr 1fr",
            gap: 10,
            marginBottom: 12,
          }}>
          <Field label="Partita IVA">
            <input
              style={inputStyle}
              value={form.partitaIva}
              onChange={set("partitaIva")}
              placeholder="12345678901"
            />
          </Field>
          <Field label="Tipo">
            <select style={inputStyle} value={form.tipo} onChange={set("tipo")}>
              <option>SPA</option>
              <option>SRL</option>
              <option>SS</option>
              <option>SNC</option>
            </select>
          </Field>
        </div>

        <div
          style={{
            display: "grid",
            gridTemplateColumns: "1fr 1fr",
            gap: 10,
            marginBottom: 12,
          }}>
          <Field label="Email">
            <input
              style={inputStyle}
              type="email"
              value={form.email}
              onChange={set("email")}
              placeholder="info@acme.it"
            />
          </Field>
          <Field label="PEC">
            <input
              style={inputStyle}
              type="email"
              value={form.pec}
              onChange={set("pec")}
              placeholder="pec@acme.it"
            />
          </Field>
        </div>

        <div
          style={{
            display: "grid",
            gridTemplateColumns: "1fr 1fr",
            gap: 10,
            marginBottom: 12,
          }}>
          <Field label="Nome Contatto">
            <input
              style={inputStyle}
              value={form.nomeContatto}
              onChange={set("nomeContatto")}
              placeholder="Mario"
            />
          </Field>
          <Field label="Cognome Contatto">
            <input
              style={inputStyle}
              value={form.cognomeContatto}
              onChange={set("cognomeContatto")}
              placeholder="Rossi"
            />
          </Field>
        </div>

        <Field label="Telefono">
          <input
            style={inputStyle}
            value={form.telefono}
            onChange={set("telefono")}
            placeholder="02 1234567"
          />
        </Field>

        <div
          style={{
            display: "flex",
            justifyContent: "flex-end",
            gap: 8,
            marginTop: 20,
          }}>
          <Btn ghost onClick={onClose}>
            Annulla
          </Btn>
          <Btn onClick={handleSave} disabled={saving}>
            {saving ? "Salvataggio..." : "Salva"}
          </Btn>
        </div>
      </div>
    </div>
  );
}

// ─── DASHBOARD ──────────────────────────────────────────────────────────────

type Tab = "clienti" | "fatture";

export default function Dashboard() {
  const [activeTab, setActiveTab] = useState<Tab>("clienti");

  // clienti
  const [clienti, setClienti] = useState<Cliente[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [totalElements, setTotalElements] = useState(0);
  const [loadingC, setLoadingC] = useState(false);
  const [showModal, setShowModal] = useState(false);

  // fatture
  const [fatture, setFatture] = useState<Fattura[]>([]);
  const [loadingF, setLoadingF] = useState(false);

  // toast
  const [toast, setToast] = useState<{ msg: string; ok: boolean } | null>(null);
  const showToast = (msg: string, ok = true) => {
    setToast({ msg, ok });
    setTimeout(() => setToast(null), 2500);
  };

  // GET /clienti?page=&size=&orderBy=&sortCriteria=
  const loadClienti = async (p = 0) => {
    setLoadingC(true);
    try {
      const res = await fetch(
        `${BASE}/clienti?page=${p}&size=10&orderBy=nomeContatto&sortCriteria=asc`,
        { headers: getHeaders() },
      );
      if (!res.ok) throw new Error();
      const data = await res.json();
      setClienti(data.content ?? []);
      setTotalPages(data.totalPages ?? 1);
      setTotalElements(data.totalElements ?? 0);
      setPage(p);
    } catch {
      showToast("Errore nel caricamento clienti", false);
    } finally {
      setLoadingC(false);
    }
  };

  // GET /fatture
  const loadFatture = async () => {
    setLoadingF(true);
    try {
      const res = await fetch(`${BASE}/fatture`, { headers: getHeaders() });
      if (!res.ok) throw new Error();
      setFatture(await res.json());
    } catch {
      showToast("Errore nel caricamento fatture", false);
    } finally {
      setLoadingF(false);
    }
  };

  // POST /clienti  (richiede ROLE_ADMIN o ROLE_UTENTE)
  const salvaCliente = async (form: typeof emptyForm) => {
    try {
      const res = await fetch(`${BASE}/clienti`, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const errorBody = await res.json();
        console.error(
          "Errore dal backend:",
          JSON.stringify(errorBody, null, 2),
        );
        throw new Error("Errore nella creazione del cliente");
      }
      setShowModal(false);
      showToast("Cliente aggiunto");
      loadClienti(0);
    } catch (e: any) {
      showToast("Errore: " + e.message, false);
    }
  };

  useEffect(() => {
    loadClienti(0);
  }, []);
  useEffect(() => {
    if (activeTab === "fatture") loadFatture();
  }, [activeTab]);

  // ── styles ──
  const tabStyle = (active: boolean): React.CSSProperties => ({
    padding: "12px 20px",
    fontSize: 13,
    cursor: "pointer",
    background: "none",
    border: "none",
    outline: "none",
    color: active ? "#111" : "#888",
    fontWeight: active ? 500 : 400,
    borderBottom: active ? "2px solid #111" : "2px solid transparent",
    marginBottom: -1,
    transition: "color 0.15s",
  });

  const pageBtn = (disabled: boolean): React.CSSProperties => ({
    padding: "4px 10px",
    border: "1px solid #e5e5e5",
    borderRadius: 5,
    background: "#fff",
    color: "#555",
    fontSize: 12,
    cursor: disabled ? "default" : "pointer",
    opacity: disabled ? 0.3 : 1,
  });

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "#fff",
        fontFamily: "'Inter', sans-serif",
        fontSize: 14,
      }}>
      <link
        href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500&display=swap"
        rel="stylesheet"
      />

      {toast && <Toast {...toast} />}

      {/* Header */}
      <header
        style={{
          padding: "16px 32px",
          borderBottom: "1px solid #e5e5e5",
          display: "flex",
          alignItems: "center",
        }}>
        <span style={{ fontSize: 15, fontWeight: 500 }}>
          Epic<span style={{ color: "#2563eb" }}>Energy</span>
        </span>
      </header>

      {/* Tabs */}
      <nav
        style={{
          display: "flex",
          padding: "0 32px",
          borderBottom: "1px solid #e5e5e5",
        }}>
        <button
          style={tabStyle(activeTab === "clienti")}
          onClick={() => setActiveTab("clienti")}>
          Clienti
        </button>
        <button
          style={tabStyle(activeTab === "fatture")}
          onClick={() => setActiveTab("fatture")}>
          Fatture
        </button>
      </nav>

      {/* Content */}
      <div style={{ padding: "28px 32px", maxWidth: 960 }}>
        {/* ── CLIENTI ── */}
        {activeTab === "clienti" && (
          <>
            <div
              style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
                marginBottom: 16,
              }}>
              <div>
                <span style={{ fontWeight: 500 }}>Clienti</span>
                <span style={{ color: "#999", fontSize: 12, marginLeft: 8 }}>
                  {totalElements} totali
                </span>
              </div>
              <Btn onClick={() => setShowModal(true)}>+ Aggiungi</Btn>
            </div>

            <table style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
                <tr style={{ borderBottom: "1px solid #e5e5e5" }}>
                  <th style={TH}>Ragione Sociale</th>
                  <th style={TH}>Contatto</th>
                  <th style={TH}>Email</th>
                  <th style={TH}>Telefono</th>
                  <th style={TH}>Tipo</th>
                  <th style={TH}>Fatturato</th>
                </tr>
              </thead>
              <tbody>
                {loadingC ? (
                  <tr>
                    <td
                      colSpan={6}
                      style={{
                        ...TD,
                        textAlign: "center",
                        color: "#bbb",
                        padding: 32,
                      }}>
                      Caricamento...
                    </td>
                  </tr>
                ) : clienti.length === 0 ? (
                  <tr>
                    <td
                      colSpan={6}
                      style={{
                        ...TD,
                        textAlign: "center",
                        color: "#bbb",
                        padding: 32,
                      }}>
                      Nessun cliente trovato
                    </td>
                  </tr>
                ) : (
                  clienti.map((c) => (
                    <tr
                      key={c.id}
                      onMouseEnter={(e) =>
                        (e.currentTarget.style.background = "#fafafa")
                      }
                      onMouseLeave={(e) =>
                        (e.currentTarget.style.background = "")
                      }>
                      <td style={TD}>{c.ragioneSociale ?? "—"}</td>
                      <td style={{ ...TD, color: "#666" }}>
                        {c.nomeContatto} {c.cognomeContatto}
                      </td>
                      <td style={{ ...TD, color: "#666" }}>{c.email ?? "—"}</td>
                      <td style={{ ...TD, color: "#666" }}>
                        {c.telefono ?? "—"}
                      </td>
                      <td style={TD}>{c.tipo ?? "—"}</td>
                      <td style={TD}>{euro(c.fatturatoAnnuale)}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>

            {/* Paginazione */}
            <div
              style={{
                display: "flex",
                justifyContent: "flex-end",
                alignItems: "center",
                gap: 8,
                marginTop: 14,
              }}>
              <button
                style={pageBtn(page === 0)}
                disabled={page === 0}
                onClick={() => loadClienti(page - 1)}>
                ←
              </button>
              <span style={{ fontSize: 12, color: "#999" }}>
                {page + 1} di {totalPages}
              </span>
              <button
                style={pageBtn(page >= totalPages - 1)}
                disabled={page >= totalPages - 1}
                onClick={() => loadClienti(page + 1)}>
                →
              </button>
            </div>
          </>
        )}

        {/* ── FATTURE ── */}
        {activeTab === "fatture" && (
          <>
            <div
              style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
                marginBottom: 16,
              }}>
              <div>
                <span style={{ fontWeight: 500 }}>Fatture</span>
                <span style={{ color: "#999", fontSize: 12, marginLeft: 8 }}>
                  {fatture.length} totali
                </span>
              </div>
            </div>

            <table style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
                <tr style={{ borderBottom: "1px solid #e5e5e5" }}>
                  <th style={TH}>N°</th>
                  <th style={TH}>Data</th>
                  <th style={TH}>Importo</th>
                  <th style={TH}>Stato</th>
                </tr>
              </thead>
              <tbody>
                {loadingF ? (
                  <tr>
                    <td
                      colSpan={4}
                      style={{
                        ...TD,
                        textAlign: "center",
                        color: "#bbb",
                        padding: 32,
                      }}>
                      Caricamento...
                    </td>
                  </tr>
                ) : fatture.length === 0 ? (
                  <tr>
                    <td
                      colSpan={4}
                      style={{
                        ...TD,
                        textAlign: "center",
                        color: "#bbb",
                        padding: 32,
                      }}>
                      Nessuna fattura trovata
                    </td>
                  </tr>
                ) : (
                  fatture.map((f) => (
                    <tr
                      key={f.idFattura}
                      onMouseEnter={(e) =>
                        (e.currentTarget.style.background = "#fafafa")
                      }
                      onMouseLeave={(e) =>
                        (e.currentTarget.style.background = "")
                      }>
                      <td style={{ ...TD, color: "#999", fontSize: 12 }}>
                        #{f.numeroFattura}
                      </td>
                      <td style={{ ...TD, color: "#666" }}>{f.data ?? "—"}</td>
                      <td style={TD}>{euro(f.importo)}</td>
                      <td style={TD}>
                        <StatoBadge stato={f.stato?.stato} />
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </>
        )}
      </div>

      <ModalCliente
        open={showModal}
        onClose={() => setShowModal(false)}
        onSave={salvaCliente}
      />
    </div>
  );
}
