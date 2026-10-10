import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastProvider } from "./components/Toast";
import Welcome from "./pages/Welcome";
import Dashboard from "./pages/Dashboard";
import CustomerPage from "./pages/CustomerPage";
import RestaurantPage from "./pages/RestaurantPage";
import MenuItemPage from "./pages/MenuItemPage";
import OrderPage from "./pages/OrderPage";
import OrderItemPage from "./pages/OrderItemPage";
import PaymentPage from "./pages/PaymentPage";

export default function App() {
  return (
    <ToastProvider>
      <BrowserRouter basename={import.meta.env.BASE_URL}>
        <Routes>
          <Route path="/" element={<Welcome />} />
          <Route path="/app" element={<Dashboard />} />
          <Route path="/app/customer" element={<CustomerPage />} />
          <Route path="/app/restaurant" element={<RestaurantPage />} />
          <Route path="/app/menuitem" element={<MenuItemPage />} />
          <Route path="/app/order" element={<OrderPage />} />
          <Route path="/app/orderitem" element={<OrderItemPage />} />
          <Route path="/app/payment" element={<PaymentPage />} />
          <Route path="*" element={<Welcome />} />
        </Routes>
      </BrowserRouter>
    </ToastProvider>
  );
}
