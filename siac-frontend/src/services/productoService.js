import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/productos";

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
    const error = await response.json();
    mensaje = error.message || error.error || JSON.stringify(error);
  } catch {
    try {
      mensaje = await response.text();
    } catch {
      mensaje = `Error ${response.status}`;
    }
  }

  throw new Error(mensaje);
};



export const obtenerProductoPorId = async (id) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const crearProducto = async (producto) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(producto),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const actualizarProducto = async (id, producto) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: getHeaders(),
    body: JSON.stringify(producto),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const eliminarProductoPorId = async (id) => {
  const response = await fetch(`${API_URL}/${id}/deactivate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};
export const obtenerProductos = async (page = 0, size = 8) => {
  const response = await fetch(`${API_URL}?page=${page}&size=${size}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return await response.json();
};

export const obtenerProductosInactivos = async (page = 0, size = 8) => {
  const response = await fetch(`${API_URL}/inactivos?page=${page}&size=${size}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) await manejarError(response);

  return await response.json();
};
export const activarProductoPorId = async (id) => {
  const response = await fetch(`${API_URL}/${id}/activate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};