# Food Order Management — Frontend

A React + Vite admin frontend for the `FoodOrderManagement` Spring Boot API you uploaded.
It's built against your **actual backend controllers** (not just the blueprint), so every
button really calls a real endpoint.

## Run it

```bash
npm install
npm run dev
```

Opens on `http://localhost:5173`. It expects the Spring Boot API on `http://localhost:8080`
(change this by copying `.env.example` to `.env` and editing `VITE_API_HOST`).

### Backend

1. This app also adds one new file to your backend:
   `src/main/java/.../config/CorsConfig.java` — it wasn't there before, and without it the
   browser will block every request from `localhost:5173` to `localhost:8080`.
2. Start Postgres, make sure `application.properties` points at a real database, then run
   the Spring Boot app as usual (`mvnw spring-boot:run` or from your IDE).

## Structure

```
src/
├── api/axiosClient.js       # one axios instance, error messages normalized
├── config/entityConfig.js   # single source of truth: titles, colors, backgrounds
├── services/                # one file per entity, maps 1:1 to real controller endpoints
├── components/              # OperationPanel (generic form+call+result), ResultRenderer,
│                             # EntityCard, EntityPageLayout, StatusBadge, Toast, etc.
└── pages/                   # Welcome, Dashboard, and one page per entity
```

Every entity page is a declarative list of "operations" (`{ label, method, fields, run }`)
rendered by the shared `<OperationPanel>` — adding a new operation to any entity means
adding one object to that array, not writing new UI.

## Where this differs from the original blueprint

The blueprint document describes an idealized API. The actual Spring Boot controllers in
your zip are narrower in a few places. Rather than silently pretending those endpoints
exist, the frontend calls out each gap directly in the UI:

| Entity | Spec asked for | What the backend actually exposes | What the UI does |
|---|---|---|---|
| MenuItem | Delete | No delete endpoint | Card is shown, disabled, with an explanation; use "Update" to set `NOT_AVAILABLE` instead |
| Order | Create (single) | Only a bulk `/orders/multiple` endpoint | "Create Order" wraps your one order in a list automatically |
| Order | Delete | Only `PUT /orders/{id}/cancel` | "Cancel Order" — a real, working soft-delete |
| Order | `deliveryAddress` field | Not on the `Orders` entity | Field omitted from the form |
| OrderItem | Create (single) | Only `POST /orders/{id}/multiple-items` | Wraps your one item in a list automatically |
| OrderItem | Get All / Get By ID | No such endpoints | Disabled with explanation |
| OrderItem | Get By Order | No such endpoint | Fetches the parent order and reads its `orderItems` array instead |
| Payment | Create | No such endpoint — `Orders.payment` cascades on save | Disabled; create a payment via Order Management → Create Order |
| Payment | Get All | No such endpoint | Disabled; use Get By Status / Get By Method |
| Payment | Delete | No such endpoint | Disabled; use Update Status instead |

Every disabled card still appears on its entity's page (nothing was silently dropped),
and every adapted call is labeled with a small "Adapted" chip so it's clear it's not a
1:1 passthrough.

Two backend business rules worth knowing before you demo:
- **Create Order** requires `payment.amount` to exactly equal the sum of `price × quantity`
  across all dishes — use the "Calculate Order Total" card first to get that number.
- **Delete Customer** actually throws unless the customer *has* orders (the check in
  `ControllerService` looks inverted) — that's backend behavior, not a frontend bug.
