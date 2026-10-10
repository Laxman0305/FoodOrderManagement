import client from "../api/axiosClient";

// Backend base: /item (OrderItemController) + reuses /order for reads,
// since OrderItem has no standalone GET endpoints of its own — the order
// itself carries its orderItems array, so "Get By Order" reads it from there.
const ITEM_BASE = "/item";
const ORDER_BASE = "/order";

const unwrap = (res) => res.data;

const orderItemService = {
  // Adapted: wraps a single item into the "add multiple items" endpoint.
  addItemToOrder: (orderId, orderItem) =>
    client
      .post(`${ITEM_BASE}/orders/${orderId}/multiple-items`, [orderItem])
      .then((res) => ({
        ...res.data,
        data: Array.isArray(res.data?.data) ? res.data.data[0] : res.data?.data,
      })),
  addMultipleItemsToOrder: (orderId, orderItems) =>
    client
      .post(`${ITEM_BASE}/orders/${orderId}/multiple-items`, orderItems)
      .then(unwrap),
  updateQuantity: (orderItemId, quantity) =>
    client
      .put(`${ITEM_BASE}/order-items/${orderItemId}/quantity/${quantity}`)
      .then(unwrap),
  remove: (orderItemId) =>
    client.delete(`${ITEM_BASE}/order-items/${orderItemId}`).then(unwrap),
  // Adapted: fetch the parent order and read its orderItems collection.
  getByOrder: (orderId) =>
    client.get(`${ORDER_BASE}/orders/${orderId}`).then((res) => ({
      ...res.data,
      data: res.data?.data?.orderItems || [],
    })),
};

export default orderItemService;
