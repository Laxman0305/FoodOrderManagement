import client from "../api/axiosClient";

// Backend base: /order (OrderController)
// NOTE: there is no single-order create or hard-delete endpoint exposed.
// "Create Order" is adapted to POST an array of one order to /orders/multiple.
// "Delete Order" is adapted to the real Cancel Order endpoint (soft-delete by status).
const BASE = "/order";

const unwrap = (res) => res.data;

const orderService = {
  create: (order) =>
    client.post(`${BASE}/orders/multiple`, [order]).then((res) => ({
      ...res.data,
      data: Array.isArray(res.data?.data) ? res.data.data[0] : res.data?.data,
    })),
  calculateTotal: (order) =>
    client.post(`${BASE}/orders/calculate-total`, order).then(unwrap),
  cancel: (orderId) =>
    client.put(`${BASE}/orders/${orderId}/cancel`).then(unwrap),
  checkPaymentAmount: (orderId) =>
    client.get(`${BASE}/orders/${orderId}/check-payment`).then(unwrap),
  getAll: () => client.get(`${BASE}/orders`).then(unwrap),
  getById: (orderId) => client.get(`${BASE}/orders/${orderId}`).then(unwrap),
  getByCustomer: (customerId) =>
    client.get(`${BASE}/orders/customer/${customerId}`).then(unwrap),
  getByRestaurant: (restaurantId) =>
    client.get(`${BASE}/orders/restaurant/${restaurantId}`).then(unwrap),
  getByStatus: (status) =>
    client.get(`${BASE}/orders/status/${status}`).then(unwrap),
  getByDate: (orderDate) =>
    client.get(`${BASE}/orders/date/${orderDate}`).then(unwrap),
};

export default orderService;
