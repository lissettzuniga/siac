const API_BASE_URL = "http://localhost:8080/api";

const refreshAccessToken = async () => {
  const refreshToken = localStorage.getItem("refreshToken");

  if (!refreshToken) {
    throw new Error("No hay refresh token");
  }

  const response = await fetch(`${API_BASE_URL}/auth/refresh`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ refreshToken })
  });

  if (!response.ok) {
    throw new Error("No se pudo renovar la sesión");
  }

  const data = await response.json();

  localStorage.setItem("accessToken", data.accessToken);
  localStorage.setItem("refreshToken", data.refreshToken);
  localStorage.setItem("tokenType", data.type);

  return data.accessToken;
};

export const apiRequest = async (endpoint, options = {}) => {
  let accessToken = localStorage.getItem("accessToken");

  let response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
      Authorization: `Bearer ${accessToken}`
    }
  });

  if (response.status === 401 || response.status === 403) {
    try {
      accessToken = await refreshAccessToken();

      response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: {
          "Content-Type": "application/json",
          ...(options.headers || {}),
          Authorization: `Bearer ${accessToken}`
        }
      });
    } catch (error) {
      localStorage.removeItem("isAuthenticated");
      localStorage.removeItem("correo");
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("tokenType");

      window.location.href = "/login";

      throw error;
    }
  }

  if (!response.ok) {
    throw new Error("Error al consumir la API");
  }

  return response.json();
};