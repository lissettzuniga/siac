import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/tipos-carta";

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

export const obtenerTiposCarta = async () => {
  const response = await fetch(API_URL, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const obtenerTiposCartaInactivos = async () => {
  const response = await fetch(`${API_URL}/inactivos`, {
    method: "GET",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};

export const crearTipoCarta = async (tipoCarta) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify(tipoCarta),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const actualizarTipoCarta = async (id, tipoCarta) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: getHeaders(),
    body: JSON.stringify(tipoCarta),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

export const desactivarTipoCarta = async (id) => {
  const response = await fetch(`${API_URL}/${id}/deactivate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};

export const activarTipoCarta = async (id) => {
  const response = await fetch(`${API_URL}/${id}/activate`, {
    method: "PATCH",
    headers: getHeaders(),
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return true;
};