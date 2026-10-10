import client from "../api/axiosClient";

// Backend base: /controller  (RestaurantController)
const BASE = "/controller";

const unwrap = (res) => res.data;

const restaurantService = {
  create: (restaurant) =>
    client.post(`${BASE}/restaurants`, restaurant).then(unwrap),
  getAll: () => client.get(`${BASE}/restaurants`).then(unwrap),
  getById: (id) => client.get(`${BASE}/restaurants/${id}`).then(unwrap),
  update: (id, restaurant) =>
    client.put(`${BASE}/restaurants/${id}`, restaurant).then(unwrap),
  remove: (id) => client.delete(`${BASE}/restaurants/${id}`).then(unwrap),
  getByLocation: (location) =>
    client
      .get(`${BASE}/restaurants/location/${encodeURIComponent(location)}`)
      .then(unwrap),
  getByName: (name) =>
    client.get(`${BASE}/restaurants/name/${encodeURIComponent(name)}`).then(unwrap),
  getByRatingAbove: (rating) =>
    client.get(`${BASE}/restaurants/rating/${rating}`).then(unwrap),
};

export default restaurantService;
