import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/imagenes-producto";

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

export const obtenerImagenesProductoActivas = async (page = 0, size = 10) => {
  const response = await fetch(`${API_URL}?page=${page}&size=${size}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const obtenerImagenesProductoInactivas = async (page = 0, size = 10) => {
  const response = await fetch(`${API_URL}/inactivas?page=${page}&size=${size}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const crearImagenProducto = async (imagen) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(imagen),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const actualizarImagenProducto = async (id, imagen) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: getHeaders(),
    body: JSON.stringify(imagen),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const desactivarImagenProducto = async (id) => {
  const response = await fetch(`${API_URL}/${id}/deactivate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};

export const activarImagenProducto = async (id) => {
  const response = await fetch(`${API_URL}/${id}/activate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};