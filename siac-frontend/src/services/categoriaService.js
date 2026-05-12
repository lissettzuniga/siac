import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/categorias";

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

export const obtenerCategorias = async () => {
  const response = await fetch(API_URL, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const obtenerCategoriasInactivas = async () => {
  const response = await fetch(`${API_URL}/inactivas`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const crearCategoria = async (categoria) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(categoria),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  if (response.status === 204) {
    return null;
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const actualizarCategoria = async (id, categoria) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: getHeaders(),
    body: JSON.stringify(categoria),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  if (response.status === 204) {
    return null;
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const desactivarCategoria = async (id) => {
  const response = await fetch(`${API_URL}/${id}/deactivate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};

export const activarCategoria = async (id) => {
  const response = await fetch(`${API_URL}/${id}/activate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};