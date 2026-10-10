import { useState } from "react";
import Loading from "./Loading";
import ConfirmDialog from "./ConfirmDialog";
import ResultRenderer from "./ResultRenderer";
import { useToast } from "./Toast";

/**
 * op shape:
 * {
 *   key, label, method ('GET'|'POST'|'PUT'|'DELETE'), description,
 *   fields: [{ name, label, type: 'text'|'number'|'select'|'date', options, placeholder, required }],
 *   customForm: (values, setValue) => JSX   // overrides `fields` for complex/nested payloads
 *   initialValues: {},
 *   submitLabel,
 *   dangerous: bool,               // renders red button + requires confirmation
 *   confirmMessage,
 *   run: async (values) => ({ message, data }),   // returns unwrapped ResponseStructure
 *   resultKind: 'customer'|'restaurant'|'menuitem'|'order'|'orderitem'|'payment'
 *   unavailable: { reason }        // renders a disabled explanatory card instead of a form
 *   adapted: string                // short note when the call is an adaptation, shown as a chip
 * }
 */
export default function OperationPanel({ op }) {
  const [values, setValues] = useState(op.initialValues || {});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [result, setResult] = useState(null);
  const [confirmOpen, setConfirmOpen] = useState(false);
  const toast = useToast();

  const setValue = (name, val) => setValues((v) => ({ ...v, [name]: val }));

  const execute = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await op.run(values);
      setResult(res);
      toast.success(res?.message || `${op.label} succeeded`);
    } catch (e) {
      setError(e.message);
      toast.error(e.message);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const missing = (op.fields || []).find((f) => f.required && !values[f.name] && values[f.name] !== 0);
    if (missing) {
      setError(`"${missing.label}" is required.`);
      toast.error(`"${missing.label}" is required.`);
      return;
    }
    if (op.dangerous) {
      setConfirmOpen(true);
      return;
    }
    execute();
  };

  if (op.unavailable) {
    return (
      <div className="glass op-card op-card-disabled">
        <OpHeader op={op} />
        <div className="op-unavailable">
          <span style={{ fontSize: "1.1rem" }}>🚧</span>
          <p>{op.unavailable.reason}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="glass op-card">
      <OpHeader op={op} />

      <form onSubmit={handleSubmit}>
        {op.customForm
          ? op.customForm(values, setValue)
          : (op.fields || []).map((f) => (
              <div className="field" key={f.name}>
                <label htmlFor={`${op.key}-${f.name}`}>
                  {f.label}
                  {f.required && " *"}
                </label>
                {f.type === "select" ? (
                  <select
                    id={`${op.key}-${f.name}`}
                    value={values[f.name] ?? ""}
                    onChange={(e) => setValue(f.name, e.target.value)}
                  >
                    <option value="" disabled>
                      Select {f.label.toLowerCase()}
                    </option>
                    {f.options.map((o) => (
                      <option key={o} value={o}>
                        {o}
                      </option>
                    ))}
                  </select>
                ) : (
                  <input
                    id={`${op.key}-${f.name}`}
                    type={f.type || "text"}
                    step={f.type === "number" ? "any" : undefined}
                    placeholder={f.placeholder}
                    value={values[f.name] ?? ""}
                    onChange={(e) => setValue(f.name, e.target.value)}
                  />
                )}
                {f.hint && <span className="field-hint">{f.hint}</span>}
              </div>
            ))}

        <div className="op-actions">
          <button
            type="submit"
            className={`btn btn-sm ${op.dangerous ? "btn-danger" : "btn-primary"}`}
            disabled={loading}
          >
            {loading ? <Loading label="Working" /> : op.submitLabel || op.label}
          </button>
          {op.adapted && <span className="op-chip">{op.adapted}</span>}
        </div>
      </form>

      {error && <div className="op-error">⚠ {error}</div>}

      {result && !error && (
        <div className="op-result">
          <div className="op-result-message">{result.message}</div>
          <ResultRenderer data={result.data} kind={op.resultKind} />
        </div>
      )}

      <ConfirmDialog
        open={confirmOpen}
        title={op.confirmTitle || "Are you sure?"}
        message={op.confirmMessage || "This action cannot be undone."}
        confirmLabel={op.submitLabel || "Confirm"}
        onCancel={() => setConfirmOpen(false)}
        onConfirm={() => {
          setConfirmOpen(false);
          execute();
        }}
      />
    </div>
  );
}

function OpHeader({ op }) {
  return (
    <div className="op-card-header">
      <span className={`op-method op-method-${(op.method || "GET").toLowerCase()}`}>{op.method}</span>
      <div>
        <h4>{op.label}</h4>
        {op.description && <p>{op.description}</p>}
      </div>
    </div>
  );
}
