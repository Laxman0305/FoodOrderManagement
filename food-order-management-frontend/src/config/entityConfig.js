// ============================================================
// CENTRALIZED ENTITY CONFIGURATION
// Single source of truth for every entity's identity: title,
// description, spice-accent color, icon and background image.
// Pages and cards read from here — never hardcode per-page.
// ============================================================

export const entityConfig = {
  customer: {
    key: "customer",
    title: "Customer Management",
    shortTitle: "Customer",
    icon: "👤",
    description:
      "Manage diner profiles — create accounts, look people up by contact or name, and keep records current.",
    accent: "var(--turmeric)",
    accent2: "var(--turmeric-soft)",
    accentVar: "--turmeric",
    background:
      "https://images.unsplash.com/photo-1552566626-52f8b828add9?auto=format&fit=crop&w=1920&q=80",
    route: "/app/customer",
  },
  restaurant: {
    key: "restaurant",
    title: "Restaurant Management",
    shortTitle: "Restaurant",
    icon: "🏪",
    description:
      "Onboard restaurant partners, keep locations and ratings accurate, and browse the partner network.",
    accent: "var(--paprika)",
    accent2: "var(--paprika-soft)",
    accentVar: "--paprika",
    background:
      "https://images.unsplash.com/photo-1577219491135-ce391730fb2c?auto=format&fit=crop&w=1920&q=80",
    route: "/app/restaurant",
  },
  menuitem: {
    key: "menuitem",
    title: "Menu Management",
    shortTitle: "Menu Item",
    icon: "🍔",
    description:
      "Build out restaurant menus — add dishes, set prices and availability, and browse by kitchen.",
    accent: "var(--basil)",
    accent2: "var(--basil-soft)",
    accentVar: "--basil",
    background:
      "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=1920&q=80",
    route: "/app/menuitem",
  },
  order: {
    key: "order",
    title: "Order Management",
    shortTitle: "Order",
    icon: "📦",
    description:
      "Place and track orders end to end — status, totals, delivery details, and full order history.",
    accent: "var(--saffron)",
    accent2: "var(--saffron-soft)",
    accentVar: "--saffron",
    background:
      "https://images.unsplash.com/photo-1526367790999-0150786686a2?auto=format&fit=crop&w=1920&q=80",
    route: "/app/order",
  },
  orderitem: {
    key: "orderitem",
    title: "Order Item Management",
    shortTitle: "Order Item",
    icon: "🛒",
    description:
      "Manage the line items inside an order — add dishes, adjust quantity, and remove items before dispatch.",
    accent: "var(--chili)",
    accent2: "var(--chili-soft)",
    accentVar: "--chili",
    background:
      "https://images.unsplash.com/photo-1600628421066-f6bda6a7b976?auto=format&fit=crop&w=1920&q=80",
    route: "/app/orderitem",
  },
  payment: {
    key: "payment",
    title: "Payment Management",
    shortTitle: "Payment",
    icon: "💳",
    description:
      "Track billing for every order — look up payments by method, status or order, and settle balances.",
    accent: "var(--cardamom)",
    accent2: "var(--cardamom-soft)",
    accentVar: "--cardamom",
    background:
      "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?auto=format&fit=crop&w=1920&q=80",
    route: "/app/payment",
  },
};

export const entityList = Object.values(entityConfig);

export const orderStatuses = [
  "PENDING",
  "CONFIRMED",
  "PREPARING",
  "OUT_FOR_DELIVERY",
  "DELIVERED",
  "CANCELLED",
];

export const paymentMethods = ["UPI", "CARD", "CASH", "NET_BANKING", "WALLET"];
export const paymentStatuses = ["PENDING", "SUCCESS", "FAILED", "REFUNDED"];
export const availabilityOptions = ["AVAILABLE", "NOT_AVAILABLE"];

export const welcomeBackground =
  "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=1920&q=80";
