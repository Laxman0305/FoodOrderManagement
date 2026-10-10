export default function EmptyState({ icon = "🍽️", title = "Nothing here yet", hint }) {
  return (
    <div style={{ textAlign: "center", padding: "26px 10px", color: "var(--ink-dim)" }}>
      <div style={{ fontSize: "2rem", marginBottom: 8, opacity: 0.8 }}>{icon}</div>
      <div style={{ fontWeight: 600, color: "var(--ink)" }}>{title}</div>
      {hint && <div style={{ fontSize: "0.82rem", marginTop: 4 }}>{hint}</div>}
    </div>
  );
}
