export default function ConfirmDialog({ open, title, message, confirmLabel = "Delete", onConfirm, onCancel }) {
  if (!open) return null;
  return (
    <div className="modal-backdrop" onClick={onCancel}>
      <div className="modal-box glass" onClick={(e) => e.stopPropagation()}>
        <div style={{ fontSize: "1.6rem", marginBottom: 6 }}>⚠️</div>
        <h3 style={{ margin: "0 0 8px", fontFamily: "var(--font-display)" }}>{title}</h3>
        <p style={{ color: "var(--ink-dim)", fontSize: "0.92rem", lineHeight: 1.5 }}>{message}</p>
        <div style={{ display: "flex", gap: 10, marginTop: 20, justifyContent: "flex-end" }}>
          <button className="btn btn-ghost btn-sm" onClick={onCancel}>Cancel</button>
          <button className="btn btn-danger btn-sm" onClick={onConfirm}>{confirmLabel}</button>
        </div>
      </div>
    </div>
  );
}
