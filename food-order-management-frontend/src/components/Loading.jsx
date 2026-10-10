export default function Loading({ label = "Loading" }) {
  return (
    <div style={{ display: "flex", alignItems: "center", gap: 10, color: "var(--ink-dim)" }}>
      <span className="spinner" />
      <span style={{ fontSize: "0.85rem" }}>{label}…</span>
    </div>
  );
}
