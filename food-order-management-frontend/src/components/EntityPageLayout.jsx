import { useNavigate } from "react-router-dom";

export default function EntityPageLayout({ entity, children }) {
  const navigate = useNavigate();
  return (
    <div className="scene" style={{ "--accent": entity.accent, "--accent-2": entity.accent2 }}>
      <div className="scene-bg" style={{ backgroundImage: `url(${entity.background})` }} />
      <div className="scene-overlay" style={{
        background: `linear-gradient(180deg, rgba(20,16,13,0.68) 0%, rgba(20,16,13,0.88) 45%, rgba(20,16,13,0.97) 100%)`
      }} />
      <div className="scene-content entity-page">
        <button className="btn btn-ghost btn-sm back-btn" onClick={() => navigate("/app")}>
          ← Back to Dashboard
        </button>

        <header className="entity-header glass-strong">
          <div className="entity-header-icon" style={{ background: entity.accent }}>{entity.icon}</div>
          <div>
            <span className="eyebrow" style={{ color: entity.accent2 }}>Food Order Management</span>
            <h1 className="entity-header-title">{entity.title}</h1>
            <p className="entity-header-desc">{entity.description}</p>
          </div>
        </header>

        <div className="operation-grid">{children}</div>
      </div>
    </div>
  );
}
