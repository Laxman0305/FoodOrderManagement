import EntityPageLayout from "../components/EntityPageLayout";
import OperationPanel from "../components/OperationPanel";
import orderService from "../services/orderService";
import { entityConfig, orderStatuses, paymentMethods, paymentStatuses } from "../config/entityConfig";

const entity = entityConfig.order;
const KIND = "order";

function OrderItemRows({ items, onChange }) {
  const rows = items && items.length ? items : [{ menuItemId: "", quantity: 1 }];
  const update = (idx, key, val) => onChange(rows.map((r, i) => (i === idx ? { ...r, [key]: val } : r)));
  const add = () => onChange([...rows, { menuItemId: "", quantity: 1 }]);
  const remove = (idx) => onChange(rows.filter((_, i) => i !== idx));
  return (
    <div className="field">
      <label>Dishes in this order *</label>
      {rows.map((r, i) => (
        <div className="repeat-row" key={i}>
          <input
            type="number"
            placeholder="MenuItem ID"
            value={r.menuItemId}
            onChange={(e) => update(i, "menuItemId", e.target.value)}
          />
          <input
            type="number"
            placeholder="Quantity"
            min="1"
            value={r.quantity}
            onChange={(e) => update(i, "quantity", e.target.value)}
          />
          {rows.length > 1 && (
            <button type="button" className="repeat-row-remove" onClick={() => remove(i)}>
              ✕
            </button>
          )}
        </div>
      ))}
      <button type="button" className="repeat-row-add" onClick={add}>
        + Add another dish
      </button>
    </div>
  );
}

function toOrderItems(items) {
  const rows = (items && items.length ? items : []).filter((r) => r.menuItemId);
  if (rows.length === 0) throw new Error("Add at least one dish (MenuItem ID + quantity).");
  return rows.map((r) => ({
    menuItem: { itemId: Number(r.menuItemId) },
    quantity: Number(r.quantity || 1),
  }));
}

export default function OrderPage() {
  const ops = [
    {
      key: "calcTotal",
      label: "Calculate Order Total",
      method: "POST",
      description:
        "Preview the total for a set of dishes before placing the order — useful because the payment amount below must match exactly.",
      resultKind: KIND,
      customForm: (values, setValue) => (
        <OrderItemRows items={values.items} onChange={(items) => setValue("items", items)} />
      ),
      submitLabel: "Calculate total",
      run: (v) => orderService.calculateTotal({ orderItems: toOrderItems(v.items) }),
    },
    {
      key: "create",
      label: "Create Order",
      method: "POST",
      adapted: "Adapted: uses the backend's bulk /orders/multiple endpoint with a single order",
      description:
        "Place a new order for a customer. The backend requires the payment amount to exactly equal the calculated total — use the Calculate Order Total card above first.",
      resultKind: KIND,
      customForm: (values, setValue) => (
        <>
          <div className="field">
            <label>Customer ID *</label>
            <input
              type="number"
              placeholder="1"
              value={values.customerId || ""}
              onChange={(e) => setValue("customerId", e.target.value)}
            />
          </div>
          <div className="field">
            <label>Order date</label>
            <input type="date" value={values.orderDate || ""} onChange={(e) => setValue("orderDate", e.target.value)} />
          </div>
          <div className="field">
            <label>Status</label>
            <select value={values.status || "PENDING"} onChange={(e) => setValue("status", e.target.value)}>
              {orderStatuses.map((s) => (
                <option key={s} value={s}>{s}</option>
              ))}
            </select>
          </div>
          <OrderItemRows items={values.items} onChange={(items) => setValue("items", items)} />
          <hr className="hairline" />
          <span className="eyebrow">Payment (created together with the order)</span>
          <div className="field" style={{ marginTop: 10 }}>
            <label>Payment method *</label>
            <select value={values.paymentMethod || ""} onChange={(e) => setValue("paymentMethod", e.target.value)}>
              <option value="" disabled>Select method</option>
              {paymentMethods.map((m) => (
                <option key={m} value={m}>{m}</option>
              ))}
            </select>
          </div>
          <div className="field">
            <label>Payment status</label>
            <select value={values.paymentStatus || "PENDING"} onChange={(e) => setValue("paymentStatus", e.target.value)}>
              {paymentStatuses.map((s) => (
                <option key={s} value={s}>{s}</option>
              ))}
            </select>
          </div>
          <div className="field">
            <label>Payment amount *</label>
            <input
              type="number"
              placeholder="Must equal the calculated total"
              value={values.paymentAmount || ""}
              onChange={(e) => setValue("paymentAmount", e.target.value)}
            />
            <span className="field-hint">Run "Calculate Order Total" first to get this exact number.</span>
          </div>
        </>
      ),
      submitLabel: "Create order",
      run: (v) => {
        if (!v.customerId) throw new Error("Customer ID is required.");
        if (!v.paymentMethod) throw new Error("Payment method is required.");
        if (!v.paymentAmount) throw new Error("Payment amount is required.");
        return orderService.create({
          orderDate: v.orderDate || new Date().toISOString().slice(0, 10),
          status: v.status || "PENDING",
          customer: { id: Number(v.customerId) },
          payment: {
            paymentMethod: v.paymentMethod,
            status: v.paymentStatus || "PENDING",
            amount: Number(v.paymentAmount),
          },
          orderItems: toOrderItems(v.items),
        });
      },
    },
    {
      key: "cancel",
      label: "Cancel Order",
      method: "PUT",
      adapted: "Adapts spec's Delete Order — the API only supports a status-based cancel, not a hard delete",
      description: "Cancels an order by setting its status to CANCELLED. Blocked once an order is PREPARING.",
      resultKind: KIND,
      fields: [{ name: "orderId", label: "Order ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Cancel order",
      dangerous: true,
      confirmTitle: "Cancel this order?",
      confirmMessage: "The order's status will be set to CANCELLED. This cannot be undone.",
      run: (v) => orderService.cancel(v.orderId),
    },
    {
      key: "checkPayment",
      label: "Check Payment Amount",
      method: "GET",
      description: "Verifies the order's total amount matches its payment amount.",
      resultKind: KIND,
      fields: [{ name: "orderId", label: "Order ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Check payment",
      run: (v) => orderService.checkPaymentAmount(v.orderId),
    },
    {
      key: "getAll",
      label: "Get All Orders",
      method: "GET",
      description: "Fetch every order in the system.",
      resultKind: KIND,
      fields: [],
      submitLabel: "Fetch all orders",
      run: () => orderService.getAll(),
    },
    {
      key: "getById",
      label: "Get Order By ID",
      method: "GET",
      description: "Look up a single order and its dishes, customer and payment.",
      resultKind: KIND,
      fields: [{ name: "orderId", label: "Order ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch order",
      run: (v) => orderService.getById(v.orderId),
    },
    {
      key: "byCustomer",
      label: "Get Orders By Customer",
      method: "GET",
      description: "Fetch every order placed by a customer.",
      resultKind: KIND,
      fields: [{ name: "customerId", label: "Customer ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch customer orders",
      run: (v) => orderService.getByCustomer(v.customerId),
    },
    {
      key: "byRestaurant",
      label: "Get Orders By Restaurant",
      method: "GET",
      description: "Fetch every order that includes a dish from a restaurant.",
      resultKind: KIND,
      fields: [{ name: "restaurantId", label: "Restaurant ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch restaurant orders",
      run: (v) => orderService.getByRestaurant(v.restaurantId),
    },
    {
      key: "byStatus",
      label: "Get Orders By Status",
      method: "GET",
      description: "Filter orders by their current status.",
      resultKind: KIND,
      fields: [{ name: "status", label: "Status", type: "select", options: orderStatuses, required: true }],
      submitLabel: "Filter by status",
      run: (v) => orderService.getByStatus(v.status),
    },
    {
      key: "byDate",
      label: "Get Orders By Date",
      method: "GET",
      description: "Filter orders by the date they were placed.",
      resultKind: KIND,
      fields: [{ name: "orderDate", label: "Order date", type: "date", required: true }],
      submitLabel: "Filter by date",
      run: (v) => orderService.getByDate(v.orderDate),
    },
  ];

  return (
    <EntityPageLayout entity={entity}>
      {ops.map((op) => (
        <OperationPanel key={op.key} op={op} />
      ))}
    </EntityPageLayout>
  );
}
