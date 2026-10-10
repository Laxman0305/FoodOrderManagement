import { Link } from "react-router-dom";

export default function EntityCard({ entity, index }) {
  return (
    <Link
      to={entity.route}
      className="glass entity-card"
      style={{ "--accent": entity.accent, "--accent-2": entity.accent2, "--delay": `${index * 70}ms` }}
    >
      <div className="entity-card-icon">{entity.icon}</div>
      <h3 className="entity-card-title">{entity.shortTitle}</h3>
      <p className="entity-card-desc">{entity.description}</p>
      <span className="entity-card-cta">
        Open {entity.shortTitle} API <span aria-hidden>→</span>
      </span>
    </Link>
  );
}
