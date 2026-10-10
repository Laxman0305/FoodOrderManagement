import EntityCard from "../components/EntityCard";
import { entityList, welcomeBackground } from "../config/entityConfig";

export default function Dashboard() {
  return (
    <div className="scene">
      <div className="scene-bg" style={{ backgroundImage: `url(${welcomeBackground})`, opacity: 0.5 }} />
      <div className="scene-overlay" />
      <div className="scene-content dashboard-content">
        <div className="dashboard-header">
          <span className="eyebrow">Admin dashboard</span>
          <h2 className="dashboard-title">Choose a management area</h2>
          <p className="dashboard-subtitle">
            Every card opens a live view onto the Spring Boot API for that entity — create records, look
            them up, and manage the full order lifecycle.
          </p>
        </div>
        <div className="entity-grid">
          {entityList.map((entity, i) => (
            <EntityCard key={entity.key} entity={entity} index={i} />
          ))}
        </div>
      </div>
    </div>
  );
}
