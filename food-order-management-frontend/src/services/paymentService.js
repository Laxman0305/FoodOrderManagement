import client from "../api/axiosClient";

// Backend base: /payment (PaymentController)
// NOTE: there is no create/delete/get-all endpoint for Payment — a payment
// is created automatically as part of Order creation (cascaded from Orders.payment).
const BASE = "/payment";

const unwrap = (res) => res.data;

const paymentService = {
  getById: (paymentId) => client.get(`${BASE}/${paymentId}`).then(unwrap),
  getByOrder: (orderId) => client.get(`${BASE}/order/${orderId}`).then(unwrap),
  getByMethod: (method) => client.get(`${BASE}/method/${method}`).then(unwrap),
  getByStatus: (status) => client.get(`${BASE}/status/${status}`).then(unwrap),
  updateStatus: (paymentId, status) =>
    client.put(`${BASE}/${paymentId}/status/${status}`).then(unwrap),
};

export default paymentService;
