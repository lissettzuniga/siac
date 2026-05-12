import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/tipos-movimiento";

const getHeaders = () => {
  const token = getAccessToken();

  return {
    "Content-Type": "application/json",
    ...(token && { Authorization: `Bearer ${token}` }),
  };
};

const manejarError = async (response) => {
  let mensaje = `Error ${response.status}`;

  try {
    const contentType = response.headers.get("content-type");

    if (contentType && contentType.includes("application/json")) {
      const error = await response.json();
      mensaje = error.message || error.error || mensaje;
    } else {
      const texto = await response.text();
      mensaje = texto || mensaje;
    }
  } catch {
    mensaje = `Error ${response.status}`;
  }

  throw new Error(mensaje);
};

export const obtenerTiposMovimiento = async () => {
  const response = await fetch(API_URL, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return await response.json();
};

export const crearTipoMovimiento = async (tipo) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(tipo),
  });

  if (!response.ok) await manejarError(response);

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const actualizarTipoMovimiento = async (id, tipo) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: getHeaders(),
    body: JSON.stringify(tipo),
  });

  if (!response.ok) await manejarError(response);

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};