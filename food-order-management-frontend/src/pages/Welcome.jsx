import { useNavigate } from "react-router-dom";
import { welcomeBackground } from "../config/entityConfig";

export default function Welcome() {
  const navigate = useNavigate();
  return (
    <div className="scene">
      <div className="scene-bg" style={{ backgroundImage: `url(${welcomeBackground})` }} />
      <div className="scene-overlay" />
      <div className="scene-content welcome-content">
        <span className="welcome-eyebrow">🍴 Full-stack restaurant operations</span>
        <h1 className="welcome-title">Welcome to Food Order Management</h1>
        <p className="welcome-subtitle">
          Manage Customers, Restaurants, Menus, Orders and Payments in one place.
        </p>
        <button className="btn btn-primary welcome-cta" onClick={() => navigate("/app")}>
          Explore Management System <span aria-hidden>🍽️</span>
        </button>
        <div className="welcome-spices" aria-hidden>
          <span>👤</span><span>🏪</span><span>🍔</span><span>📦</span><span>🛒</span><span>💳</span>
        </div>
      </div>
    </div>
  );
}
