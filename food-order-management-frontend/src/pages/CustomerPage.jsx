import EntityPageLayout from "../components/EntityPageLayout";
import OperationPanel from "../components/OperationPanel";
import customerService from "../services/customerService";
import { entityConfig } from "../config/entityConfig";

const entity = entityConfig.customer;
const KIND = "customer";

export default function CustomerPage() {
  const ops = [
    {
      key: "create",
      label: "Create Customer",
      method: "POST",
      description: "Register a new diner. Email and contact number must be unique.",
      resultKind: KIND,
      fields: [
        { name: "name", label: "Full name", required: true, placeholder: "Laxman B B" },
        { name: "email", label: "Email", required: true, placeholder: "laxman03052@gmail.com" },
        { name: "contact", label: "Contact number", type: "number", required: true, placeholder: "6364123744" },
        { name: "address", label: "Address", placeholder: "demali plazza, chikkodi, Belagavi" },
      ],
      submitLabel: "Create customer",
      run: (v) =>
        customerService.create({
          name: v.name,
          email: v.email,
          contact: Number(v.contact),
          address: v.address || null,
        }),
    },
    {
      key: "getAll",
      label: "Get All Customers",
      method: "GET",
      description: "Fetch every registered customer.",
      resultKind: KIND,
      fields: [],
      submitLabel: "Fetch all customers",
      run: () => customerService.getAll(),
    },
    {
      key: "getById",
      label: "Get Customer By ID",
      method: "GET",
      description: "Look up a single customer by their numeric ID.",
      resultKind: KIND,
      fields: [{ name: "id", label: "Customer ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Fetch customer",
      run: (v) => customerService.getById(v.id),
    },
    {
      key: "update",
      label: "Update Customer",
      method: "PUT",
      description: "Replace a customer's details. All fields are sent, so provide the full record.",
      resultKind: KIND,
      fields: [
        { name: "id", label: "Customer ID", type: "number", required: true, placeholder: "1" },
        { name: "name", label: "Full name", required: true },
        { name: "email", label: "Email", required: true },
        { name: "contact", label: "Contact number", type: "number", required: true },
        { name: "address", label: "Address" },
      ],
      submitLabel: "Update customer",
      run: (v) =>
        customerService.update(v.id, {
          name: v.name,
          email: v.email,
          contact: Number(v.contact),
          address: v.address || null,
        }),
    },
    {
      key: "delete",
      label: "Delete Customer",
      method: "DELETE",
      description:
        "Permanently remove a customer. The backend enforces its own deletion rule based on order history.",
      resultKind: KIND,
      fields: [{ name: "id", label: "Customer ID", type: "number", required: true, placeholder: "1" }],
      submitLabel: "Delete customer",
      dangerous: true,
      confirmTitle: "Delete this customer?",
      confirmMessage: "This permanently removes the customer record. This cannot be undone.",
      run: (v) => customerService.remove(v.id),
    },
    {
      key: "byContact",
      label: "Get Customer By Contact",
      method: "GET",
      description: "Find a customer using their exact contact number.",
      resultKind: KIND,
      fields: [{ name: "contact", label: "Contact number", type: "number", required: true, placeholder: "9876543210" }],
      submitLabel: "Search by contact",
      run: (v) => customerService.getByContact(v.contact),
    },
    {
      key: "byName",
      label: "Get Customer By Name",
      method: "GET",
      description: "Find a customer using their exact registered name.",
      resultKind: KIND,
      fields: [{ name: "name", label: "Full name", required: true, placeholder: "Aarav Sharma" }],
      submitLabel: "Search by name",
      run: (v) => customerService.getByName(v.name),
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
