import EntityPageLayout from "../components/EntityPageLayout";
import OperationPanel from "../components/OperationPanel";
import restaurantService from "../services/restaurantService";
import { entityConfig } from "../config/entityConfig";

const entity = entityConfig.restaurant;
const KIND = "restaurant";

export default function RestaurantPage() {
  const ops = [
    {
      key: "create",
      label: "Create Restaurant",
      method: "POST",
      description: "Onboard a new restaurant partner.",
      resultKind: KIND,
      fields: [
        { name: "name", label: "Restaurant name", required: true, placeholder: "Spice Route Kitchen" },
        { name: "location", label: "Location", required: true, placeholder: "Indiranagar, Bengaluru" },
        { name: "rating", label: "Rating (0–5)", type: "number", placeholder: "4.5" },
      ],
      submitLabel: "Create restaurant",
      run: (v) =>
        restaurantService.create({
          name: v.name,
          location: v.location,
          rating: v.rating ? Number(v.rating) : null,
        }),
    },
    {
      key: "getAll",
      label: "Get All Restaurants",
      method: "GET",
      description: "Fetch every partner restaurant.",
      resultKind: KIND,
      fields: [],
      submitLabel: "Fetch all restaurants",
      run: () => restaurantService.getAll(),
    },
    {
      key: "getById",
      label: "Get Restaurant By ID",
      method: "GET",
      description: "Look up a single restaurant by ID.",
      resultKind: KIND,
      fields: [{ name: "id", label: "Restaurant ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch restaurant",
      run: (v) => restaurantService.getById(v.id),
    },
    {
      key: "update",
      label: "Update Restaurant",
      method: "PUT",
      description: "Replace a restaurant's details.",
      resultKind: KIND,
      fields: [
        { name: "id", label: "Restaurant ID", type: "number", required: true, placeholder: "1" },
        { name: "name", label: "Restaurant name", required: true },
        { name: "location", label: "Location", required: true },
        { name: "rating", label: "Rating (0–5)", type: "number" },
      ],
      submitLabel: "Update restaurant",
      run: (v) =>
        restaurantService.update(v.id, {
          name: v.name,
          location: v.location,
          rating: v.rating ? Number(v.rating) : null,
        }),
    },
    {
      key: "delete",
      label: "Delete Restaurant",
      method: "DELETE",
      description: "Permanently remove a restaurant partner.",
      resultKind: KIND,
      fields: [{ name: "id", label: "Restaurant ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Delete restaurant",
      dangerous: true,
      confirmTitle: "Delete this restaurant?",
      confirmMessage: "This permanently removes the restaurant record. This cannot be undone.",
      run: (v) => restaurantService.remove(v.id),
    },
    {
      key: "byLocation",
      label: "Get Restaurants By Location",
      method: "GET",
      description: "Find every restaurant registered at a location.",
      resultKind: KIND,
      fields: [{ name: "location", label: "Location", required: true, placeholder: "Indiranagar, Bengaluru" }],
      submitLabel: "Search by location",
      run: (v) => restaurantService.getByLocation(v.location),
    },
    {
      key: "byName",
      label: "Get Restaurant By Name",
      method: "GET",
      description: "Find a restaurant using its exact name.",
      resultKind: KIND,
      fields: [{ name: "name", label: "Restaurant name", required: true, placeholder: "Spice Route Kitchen" }],
      submitLabel: "Search by name",
      run: (v) => restaurantService.getByName(v.name),
    },
    {
      key: "byRating",
      label: "Get Restaurants By Rating",
      method: "GET",
      description: "Find every restaurant rated above a given score.",
      resultKind: KIND,
      fields: [{ name: "rating", label: "Rating greater than", type: "number", required: true, placeholder: "4.0" }],
      submitLabel: "Search by rating",
      run: (v) => restaurantService.getByRatingAbove(v.rating),
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
