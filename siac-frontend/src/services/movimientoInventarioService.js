import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/movimientos-inventario";

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

export const obtenerMovimientos = async (page = 0, size = 10) => {
  const response = await fetch(`${API_URL}?page=${page}&size=${size}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return await response.json();
};

export const crearMovimiento = async (movimiento) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(movimiento),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};