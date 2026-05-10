import { apiRequest } from "./apiClient";

export const getCategorias = async () => {
  return apiRequest("/categorias");
};