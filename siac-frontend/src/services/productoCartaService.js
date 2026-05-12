import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/productos-carta";

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

export const obtenerProductosCarta = async () => {
  const response = await fetch(API_URL, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return await response.json();
};

export const obtenerProductosCartaInactivos = async () => {
  const response = await fetch(`${API_URL}/inactivos`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return await response.json();
};

export const crearProductoCarta = async (productoCarta) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(productoCarta),
  });

  if (!response.ok) await manejarError(response);

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const actualizarProductoCarta = async (id, productoCarta) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: getHeaders(),
    body: JSON.stringify(productoCarta),
  });

  if (!response.ok) await manejarError(response);

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const desactivarProductoCarta = async (id) => {
  const response = await fetch(`${API_URL}/${id}/deactivate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return true;
};

export const activarProductoCarta = async (id) => {
  const response = await fetch(`${API_URL}/${id}/activate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return true;
};