import EmptyState from "./EmptyState";
import { StatusBadge, PaymentMethodBadge } from "./StatusBadge";

const FOOD_EMOJI = ["🍕", "🍔", "🍜", "🥗", "🌮", "🍣", "🍛", "🥪", "🍩", "🍰"];
function foodEmojiFor(name = "") {
  let sum = 0;
  for (const ch of String(name)) sum += ch.charCodeAt(0);
  return FOOD_EMOJI[sum % FOOD_EMOJI.length];
}

function Chip({ children }) {
  return <span className="chip">{children}</span>;
}

function CustomerCard({ c }) {
  return (
    <div className="mini-card">
      <div className="mini-card-row">
        <strong>{c.name || "Unnamed customer"}</strong>
        <Chip>#{c.id}</Chip>
      </div>
      <div className="mini-card-meta">
        {c.email && <span>✉️ {c.email}</span>}
        {c.contact && <span>📞 {c.contact}</span>}
      </div>
      {c.address && <div className="mini-card-sub">📍 {c.address}</div>}
    </div>
  );
}

function RestaurantCard({ r }) {
  return (
    <div className="mini-card">
      <div className="mini-card-row">
        <strong>{r.name || "Unnamed restaurant"}</strong>
        <Chip>#{r.id}</Chip>
      </div>
      <div className="mini-card-meta">
        {r.location && <span>📍 {r.location}</span>}
        {r.rating != null && <span>⭐ {r.rating}</span>}
      </div>
    </div>
  );
}

function MenuItemCard({ m }) {
  return (
    <div className="food-card">
      <div className="food-card-emoji">{foodEmojiFor(m.itemName)}</div>
      <div className="food-card-body">
        <div className="mini-card-row">
          <strong>{m.itemName || "Unnamed item"}</strong>
          <Chip>#{m.itemId}</Chip>
        </div>
        <div className="mini-card-meta">
          <span>₹{m.price}</span>
          {m.restaurant?.id && <span>🏪 Restaurant #{m.restaurant.id}</span>}
        </div>
        <StatusBadge status={m.availability} kind="availability" />
      </div>
    </div>
  );
}

function OrderItemLine({ oi }) {
  return (
    <div className="mini-card">
      <div className="mini-card-row">
        <strong>{oi.menuItem?.itemName || `Menu item #${oi.menuItem?.itemId ?? "?"}`}</strong>
        <Chip>#{oi.orderItemId}</Chip>
      </div>
      <div className="mini-card-meta">
        <span>Qty {oi.quantity}</span>
        <span>Subtotal ₹{oi.subTotal}</span>
      </div>
    </div>
  );
}

function OrderCard({ o }) {
  return (
    <div className="mini-card order-card">
      <div className="mini-card-row">
        <strong>Order #{o.orderId}</strong>
        <StatusBadge status={o.status} />
      </div>
      <div className="mini-card-meta">
        {o.orderDate && <span>🗓 {o.orderDate}</span>}
        {o.totalAmount != null && <span>💰 ₹{o.totalAmount}</span>}
        {o.customer?.id && <span>👤 Customer #{o.customer.id}</span>}
      </div>
      {o.payment && (
        <div className="mini-card-sub" style={{ display: "flex", gap: 8, alignItems: "center", flexWrap: "wrap" }}>
          <PaymentMethodBadge method={o.payment.paymentMethod} />
          <StatusBadge status={o.payment.status} kind="payment" />
          <span>₹{o.payment.amount}</span>
        </div>
      )}
      {Array.isArray(o.orderItems) && o.orderItems.length > 0 && (
        <div className="order-card-items">
          {o.orderItems.map((oi) => (
            <div key={oi.orderItemId ?? Math.random()} className="order-card-item-row">
              <span>{oi.menuItem?.itemName || `Item #${oi.menuItem?.itemId ?? "?"}`}</span>
              <span>× {oi.quantity}</span>
              <span>₹{oi.subTotal}</span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

function PaymentCard({ p }) {
  return (
    <div className="mini-card">
      <div className="mini-card-row">
        <strong>Payment #{p.paymentId}</strong>
        <StatusBadge status={p.status} kind="payment" />
      </div>
      <div className="mini-card-meta">
        <PaymentMethodBadge method={p.paymentMethod} />
        <span>₹{p.amount}</span>
        {p.order?.orderId && <span>📦 Order #{p.order.orderId}</span>}
      </div>
    </div>
  );
}

const CARD_BY_KIND = {
  customer: (item) => <CustomerCard c={item} />,
  restaurant: (item) => <RestaurantCard r={item} />,
  menuitem: (item) => <MenuItemCard m={item} />,
  order: (item) => <OrderCard o={item} />,
  orderitem: (item) => <OrderItemLine oi={item} />,
  payment: (item) => <PaymentCard p={item} />,
};

function GenericCard({ item }) {
  return (
    <div className="mini-card">
      {Object.entries(item).map(([k, v]) => {
        if (v && typeof v === "object") return null;
        return (
          <div key={k} className="mini-card-kv">
            <span>{k}</span>
            <strong>{String(v)}</strong>
          </div>
        );
      })}
    </div>
  );
}

export default function ResultRenderer({ data, kind = "generic" }) {
  if (data === null || data === undefined) return null;

  if (typeof data === "string" || typeof data === "number" || typeof data === "boolean") {
    return <div className="mini-card">{String(data)}</div>;
  }

  const renderItem = CARD_BY_KIND[kind] || ((item) => <GenericCard item={item} />);

  if (Array.isArray(data)) {
    if (data.length === 0) return <EmptyState title="No records found" hint="Try a different value." />;
    return (
      <div className={`result-grid result-grid-${kind}`}>
        {data.map((item, i) => (
          <div key={item?.id ?? item?.orderId ?? item?.itemId ?? item?.orderItemId ?? item?.paymentId ?? i}>
            {renderItem(item)}
          </div>
        ))}
      </div>
    );
  }

  return <div className="result-grid single">{renderItem(data)}</div>;
}
