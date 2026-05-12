const API_URL = "http://localhost:8080/api/auth";

export const login = async (correo, contrasena) => {
  const response = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ correo, contrasena })
  });

  if (!response.ok) {
    throw new Error("Correo o contraseña incorrectos");
  }

  const data = await response.json();

  localStorage.setItem("accessToken", data.accessToken);
  localStorage.setItem("refreshToken", data.refreshToken);
  localStorage.setItem("tokenType", data.type);

  localStorage.setItem("rol", data.rol);

  return data;
};

export const logout = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("tokenType");

  localStorage.removeItem("rol");
};

export const getAccessToken = () => {
  return localStorage.getItem("accessToken");
};


export const getRol = () => {
  return localStorage.getItem("rol");
};