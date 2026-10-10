import EntityPageLayout from "../components/EntityPageLayout";
import OperationPanel from "../components/OperationPanel";
import menuItemService from "../services/menuItemService";
import { entityConfig, availabilityOptions } from "../config/entityConfig";

const entity = entityConfig.menuitem;
const KIND = "menuitem";

export default function MenuItemPage() {
  const ops = [
    {
      key: "create",
      label: "Create MenuItem",
      method: "POST",
      description: "Add a dish to a restaurant's menu.",
      resultKind: KIND,
      fields: [
        { name: "itemName", label: "Dish name", required: true, placeholder: "Paneer Tikka" },
        { name: "price", label: "Price", type: "number", required: true, placeholder: "249" },
        { name: "availability", label: "Availability", type: "select", options: availabilityOptions, required: true },
        { name: "restaurantId", label: "Restaurant ID", type: "number", required: true, placeholder: "1" },
      ],
      submitLabel: "Create menu item",
      run: (v) =>
        menuItemService.create({
          itemName: v.itemName,
          price: Number(v.price),
          availability: v.availability,
          restaurant: { id: Number(v.restaurantId) },
        }),
    },
    {
      key: "getAll",
      label: "Get All MenuItems",
      method: "GET",
      description: "Fetch every dish across every restaurant.",
      resultKind: KIND,
      fields: [],
      submitLabel: "Fetch all menu items",
      run: () => menuItemService.getAll(),
    },
    {
      key: "getById",
      label: "Get MenuItem By ID",
      method: "GET",
      description: "Look up a single dish by its ID.",
      resultKind: KIND,
      fields: [{ name: "id", label: "MenuItem ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch menu item",
      run: (v) => menuItemService.getById(v.id),
    },
    {
      key: "update",
      label: "Update Price & Availability",
      method: "PUT",
      description: "The backend only allows updating a dish's price and availability, not its name.",
      resultKind: KIND,
      fields: [
        { name: "id", label: "MenuItem ID", type: "number", required: true, placeholder: "1" },
        { name: "price", label: "New price", type: "number", required: true, placeholder: "279" },
        { name: "availability", label: "Availability", type: "select", options: availabilityOptions, required: true },
      ],
      submitLabel: "Update menu item",
      run: (v) =>
        menuItemService.update(v.id, { price: Number(v.price), availability: v.availability }),
    },
   /* {
      key: "delete",
      label: "Delete MenuItem",
      method: "DELETE",
      resultKind: KIND,
      unavailable: {
        reason:
          "The Spring Boot API doesn't expose a delete endpoint for MenuItem — only create, read and a price/availability update. Set a dish to NOT_AVAILABLE instead using the update operation above.",
      },
    },*/
    {
      key: "byRestaurant",
      label: "Get MenuItems By Restaurant",
      method: "GET",
      description: "Fetch the full menu for one restaurant.",
      resultKind: KIND,
      fields: [{ name: "restaurantId", label: "Restaurant ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch restaurant menu",
      run: (v) => menuItemService.getByRestaurant(v.restaurantId),
    },
    {
      key: "allItemsInRestaurant",
      label: "Get All Items In Restaurant",
      method: "GET",
      description: "An alternate endpoint that also lists every dish belonging to a restaurant.",
      resultKind: KIND,
      fields: [{ name: "restaurantId", label: "Restaurant ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch items",
      run: (v) => menuItemService.getAllItemsInRestaurant(v.restaurantId),
    },
    {
      key: "byName",
      label: "Get MenuItem By Name",
      method: "GET",
      description: "Find a dish using its exact name.",
      resultKind: KIND,
      fields: [{ name: "itemName", label: "Dish name", required: true, placeholder: "Paneer Tikka" }],
      submitLabel: "Search by name",
      run: (v) => menuItemService.getByName(v.itemName),
    },
    {
      key: "priceAsc",
      label: "Get MenuItems Sorted By Price",
      method: "GET",
      description: "Fetch every dish across all restaurants, cheapest first.",
      resultKind: KIND,
      fields: [],
      submitLabel: "Fetch sorted menu",
      run: () => menuItemService.getSortedByPriceAsc(),
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
