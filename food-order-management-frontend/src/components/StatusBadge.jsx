const ORDER_STATUS_COLORS = {
  PENDING: { bg: "rgba(239,143,43,0.18)", fg: "#f7cd9a", dot: "#ef8f2b" },
  CONFIRMED: { bg: "rgba(63,154,146,0.18)", fg: "#a9dcd6", dot: "#3f9a92" },
  PREPARING: { bg: "rgba(227,171,74,0.18)", fg: "#f3d9a2", dot: "#e3ab4a" },
  OUT_FOR_DELIVERY: { bg: "rgba(63,120,154,0.18)", fg: "#a9c6dc", dot: "#3f78a0" },
  DELIVERED: { bg: "rgba(111,156,92,0.2)", fg: "#c3dcb4", dot: "#6f9c5c" },
  CANCELLED: { bg: "rgba(194,63,63,0.18)", fg: "#edb0ac", dot: "#c23f3f" },
};

const PAYMENT_STATUS_COLORS = {
  PENDING: ORDER_STATUS_COLORS.PENDING,
  SUCCESS: ORDER_STATUS_COLORS.DELIVERED,
  FAILED: ORDER_STATUS_COLORS.CANCELLED,
  REFUNDED: ORDER_STATUS_COLORS.CONFIRMED,
};

const AVAILABILITY_COLORS = {
  AVAILABLE: ORDER_STATUS_COLORS.DELIVERED,
  NOT_AVAILABLE: ORDER_STATUS_COLORS.CANCELLED,
};

export function StatusBadge({ status, kind = "order" }) {
  if (!status) return <span className="badge" style={{ background: "rgba(255,255,255,0.1)", color: "var(--ink-dim)" }}>UNKNOWN</span>;
  const map =
    kind === "payment" ? PAYMENT_STATUS_COLORS : kind === "availability" ? AVAILABILITY_COLORS : ORDER_STATUS_COLORS;
  const c = map[status?.toUpperCase()] || { bg: "rgba(255,255,255,0.1)", fg: "var(--ink)", dot: "#fff" };
  return (
    <span className="badge" style={{ background: c.bg, color: c.fg }}>
      <span style={{ width: 7, height: 7, borderRadius: "50%", background: c.dot, display: "inline-block" }} />
      {status.replace(/_/g, " ")}
    </span>
  );
}

const METHOD_ICONS = { UPI: "📱", CARD: "💳", CASH: "💵", NET_BANKING: "🏦", WALLET: "👛" };
export function PaymentMethodBadge({ method }) {
  if (!method) return null;
  return (
    <span className="badge" style={{ background: "rgba(63,154,146,0.16)", color: "var(--cardamom-soft)" }}>
      <span>{METHOD_ICONS[method?.toUpperCase()] || "💰"}</span>
      {method}
    </span>
  );
}
