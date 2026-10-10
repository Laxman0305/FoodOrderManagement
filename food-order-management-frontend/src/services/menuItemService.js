import client from "../api/axiosClient";

// Backend base: /controller  (MenuItemController)
// NOTE: the backend does not expose a delete-menu-item endpoint.
const BASE = "/controller";

const unwrap = (res) => res.data;

const menuItemService = {
  create: (menuItem) => client.post(`${BASE}/menu-item`, menuItem).then(unwrap),
  getAll: () => client.get(`${BASE}/menu-items`).then(unwrap),
  getById: (id) => client.get(`${BASE}/menu-items/${id}`).then(unwrap),
  update: (id, menuItem) =>
    client.put(`${BASE}/menu-items/${id}`, menuItem).then(unwrap),
  getByRestaurant: (restaurantId) =>
    client.get(`${BASE}/restaurants/${restaurantId}/menu`).then(unwrap),
  getAllItemsInRestaurant: (restaurantId) =>
    client.get(`${BASE}/restaurants/${restaurantId}/items`).then(unwrap),
  getByName: (itemName) =>
    client
      .get(`${BASE}/menu-items/name/${encodeURIComponent(itemName)}`)
      .then(unwrap),
  getSortedByPriceAsc: () =>
    client.get(`${BASE}/menu-items/price/ascending`).then(unwrap),
};

export default menuItemService;
