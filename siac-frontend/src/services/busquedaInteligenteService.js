import { getAccessToken } from "./authService";

const API_URL = "http://localhost:8080/api/busqueda-imagen";

const getHeaders = () => {
  const token = getAccessToken();

  return {
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

export const buscarPorImagen = async (imagen) => {
  const formData = new FormData();
  formData.append("imagen", imagen);

  const response = await fetch(API_URL, {
    method: "POST",
    headers: getHeaders(),
    body: formData,
  });

  if (!response.ok) {
    await manejarError(response);
  }

  return await response.json();
};