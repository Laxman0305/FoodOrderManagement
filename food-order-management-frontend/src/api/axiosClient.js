import axios from "axios";

// The Spring Boot backend has no shared root path — each controller
// mounts its own base (/controller, /order, /item, /payment). We keep
// one HOST constant here so switching environments never touches the UI.
export const API_HOST =
  import.meta.env.VITE_API_HOST || "http://localhost:8080";

const client = axios.create({
  baseURL: API_HOST,
  headers: { "Content-Type": "application/json" },
  timeout: 15000,
});

// Normalize every failure into a plain, readable message so pages
// never have to know about axios/HTTP internals.
client.interceptors.response.use(
  (res) => res,
  (error) => {
    let message = "Something went wrong. Please try again.";
    if (error.response) {
      const data = error.response.data;
      message =
        (data && (data.message || data.error)) ||
        `Request failed (${error.response.status})`;
    } else if (error.request) {
      message =
        "Couldn't reach the backend. Confirm the Spring Boot server is running on " +
        API_HOST;
    }
    return Promise.reject(new Error(message));
  }
);

export default client;
