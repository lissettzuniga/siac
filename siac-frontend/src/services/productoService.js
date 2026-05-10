import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/productos";

const getHeaders = () => {
  const token = getAccessToken();

  return token
    ? {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      }
    : {
        "Content-Type": "application/json",
      };
};

export const obtenerProductos = async () => {
  const response = await fetch(API_URL, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    throw new Error("Error al obtener productos");
  }

  return await response.json();
};

export const obtenerProductoPorId = async (id) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    throw new Error("Error al obtener el producto");
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
    throw new Error("Error al crear producto");
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
    throw new Error("Error al actualizar producto");
  }

  return await response.json();
};

export const eliminarProductoPorId = async (id) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
    headers: getHeaders(),
  });

  if (!response.ok) {
    throw new Error("Error al eliminar producto");
  }
};