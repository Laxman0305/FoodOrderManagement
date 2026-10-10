import client from "../api/axiosClient";

// Backend base: /controller  (CustomerController)
const BASE = "/controller";

const unwrap = (res) => res.data; // ResponseStructure<T> { statusCode, message, data }

const customerService = {
  create: (customer) => client.post(`${BASE}/customer`, customer).then(unwrap),
  getAll: () => client.get(`${BASE}/customers`).then(unwrap),
  getById: (id) => client.get(`${BASE}/customers/${id}`).then(unwrap),
  update: (id, customer) =>
    client.put(`${BASE}/customers/${id}`, customer).then(unwrap),
  remove: (id) => client.delete(`${BASE}/customers/${id}`).then(unwrap),
  getByContact: (contact) =>
    client.get(`${BASE}/customers/contact/${contact}`).then(unwrap),
  getByName: (name) =>
    client.get(`${BASE}/customers/name/${encodeURIComponent(name)}`).then(unwrap),
};

export default customerService;
