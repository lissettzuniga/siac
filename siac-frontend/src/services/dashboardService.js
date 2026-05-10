import { apiRequest } from "./apiClient";

const getDashboardData = async () => {
  return apiRequest("/dashboard/data");
};

export default getDashboardData;