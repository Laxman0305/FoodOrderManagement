import EntityPageLayout from "../components/EntityPageLayout";
import OperationPanel from "../components/OperationPanel";
import orderItemService from "../services/orderItemService";
import { entityConfig } from "../config/entityConfig";

const entity = entityConfig.orderitem;
const KIND = "orderitem";

export default function OrderItemPage() {
  const ops = [
    {
      key: "create",
      label: "Create OrderItem",
      method: "POST",
      adapted: "Adapted: wraps a single line in the backend's multi-item endpoint",
      description: "Add one dish to an order that already exists. Subtotal and the order's total are recalculated automatically.",
      resultKind: KIND,
      fields: [
        { name: "orderId", label: "Order ID", type: "number", required: true, placeholder: "1" },
        { name: "menuItemId", label: "MenuItem ID", type: "number", required: true, placeholder: "3" },
        { name: "quantity", label: "Quantity", type: "number", required: true, placeholder: "2" },
      ],
      submitLabel: "Add item to order",
      run: (v) =>
        orderItemService.addItemToOrder(v.orderId, {
          menuItem: { itemId: Number(v.menuItemId) },
          quantity: Number(v.quantity),
        }),
    },
   /* {
      key: "getAll",
      label: "Get All OrderItems",
      method: "GET",
      resultKind: KIND,
      unavailable: {
        reason:
          "The API has no endpoint that lists every order item across all orders. Use \"Get OrderItems By Order\" below to view the items inside one order.",
      },
    },
    {
      key: "getById",
      label: "Get OrderItem By ID",
      method: "GET",
      resultKind: KIND,
      unavailable: {
        reason:
          "The API doesn't expose a single-item lookup by ID (only update-quantity and delete take an order item ID). Use \"Get OrderItems By Order\" to find it.",
      },
    },*/
    {
      key: "byOrder",
      label: "Get OrderItems By Order",
      method: "GET",
      adapted: "Adapted: reads the orderItems list from the parent order",
      description: "Fetches the order and lists the dishes inside it, since order items don't have their own list endpoint.",
      resultKind: KIND,
      fields: [{ name: "orderId", label: "Order ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch order items",
      run: (v) => orderItemService.getByOrder(v.orderId),
    },
    {
      key: "update",
      label: "Update OrderItem Quantity",
      method: "PUT",
      description: "Change how many of a dish are in the order. Subtotal and order total update automatically.",
      resultKind: KIND,
      fields: [
        { name: "orderItemId", label: "OrderItem ID", type: "number", required: true, placeholder: "1" },
        { name: "quantity", label: "New quantity", type: "number", required: true, placeholder: "3" },
      ],
      submitLabel: "Update quantity",
      run: (v) => orderItemService.updateQuantity(v.orderItemId, Number(v.quantity)),
    },
    {
      key: "delete",
      label: "Delete OrderItem",
      method: "DELETE",
      description: "Remove a dish from its order. Blocked once the order is OUT_FOR_DELIVERY or DELIVERED.",
      resultKind: "order",
      fields: [{ name: "orderItemId", label: "OrderItem ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Remove item",
      dangerous: true,
      confirmTitle: "Remove this item?",
      confirmMessage: "The dish will be removed from its order and totals recalculated.",
      run: (v) => orderItemService.remove(v.orderItemId),
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
