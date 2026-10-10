import EntityPageLayout from "../components/EntityPageLayout";
import OperationPanel from "../components/OperationPanel";
import paymentService from "../services/paymentService";
import { entityConfig, paymentMethods, paymentStatuses } from "../config/entityConfig";

const entity = entityConfig.payment;
const KIND = "payment";

export default function PaymentPage() {
  const ops = [
   /* {
      key: "create",
      label: "Create Payment",
      method: "POST",
      resultKind: KIND,
      unavailable: {
        reason:
          "There's no standalone create endpoint — a Payment record is created automatically as part of Create Order, in the Order Management page, since it's cascaded from the order.",
      },
    },
    {
      key: "getAll",
      label: "Get All Payments",
      method: "GET",
      resultKind: KIND,
      unavailable: {
        reason:
          "The API has no list-all endpoint for payments. Use \"Get Payments By Status\" or \"Get Payments By Method\" to browse them instead.",
      },
    },*/
    {
      key: "getById",
      label: "Get Payment By ID",
      method: "GET",
      description: "Look up a single payment by its ID.",
      resultKind: KIND,
      fields: [{ name: "paymentId", label: "Payment ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch payment",
      run: (v) => paymentService.getById(v.paymentId),
    },
    {
      key: "byOrder",
      label: "Get Payment By Order",
      method: "GET",
      description: "Find the payment attached to a specific order.",
      resultKind: KIND,
      fields: [{ name: "orderId", label: "Order ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch by order",
      run: (v) => paymentService.getByOrder(v.orderId),
    },
    {
      key: "byStatus",
      label: "Get Payments By Status",
      method: "GET",
      description: "Filter payments by their current status.",
      resultKind: KIND,
      fields: [{ name: "status", label: "Status", type: "select", options: paymentStatuses, required: true }],
      submitLabel: "Filter by status",
      run: (v) => paymentService.getByStatus(v.status),
    },
    {
      key: "byMethod",
      label: "Get Payments By Method",
      method: "GET",
      description: "Filter payments by how they were paid.",
      resultKind: KIND,
      fields: [{ name: "paymentMethod", label: "Method", type: "select", options: paymentMethods, required: true }],
      submitLabel: "Filter by method",
      run: (v) => paymentService.getByMethod(v.paymentMethod),
    },
    {
      key: "updateStatus",
      label: "Update Payment Status",
      method: "PUT",
      description: "Move a payment to a new status — e.g. mark it SUCCESS once it clears.",
      resultKind: KIND,
      fields: [
        { name: "paymentId", label: "Payment ID", type: "number", required: true, placeholder: "1" },
        { name: "status", label: "New status", type: "select", options: paymentStatuses, required: true },
      ],
      submitLabel: "Update status",
      run: (v) => paymentService.updateStatus(v.paymentId, v.status),
    },
   /* {
      key: "delete",
      label: "Delete Payment",
      method: "DELETE",
      resultKind: KIND,
      unavailable: {
        reason:
          "The API doesn't expose a delete endpoint for payments — a payment's lifecycle is managed through its status instead (see Update Payment Status above).",
      },
    },*/
  ];

  return (
    <EntityPageLayout entity={entity}>
      {ops.map((op) => (
        <OperationPanel key={op.key} op={op} />
      ))}
    </EntityPageLayout>
  );
}
