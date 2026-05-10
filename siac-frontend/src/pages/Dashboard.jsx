import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import getDashboardData from "../services/dashboardService";

function Dashboard() {
  const navigate = useNavigate();

  const correo = localStorage.getItem("correo");
  const [dashboardData, setDashboardData] = useState(null);
  const [error, setError] = useState("");

  const handleLogout = () => {
    localStorage.removeItem("isAuthenticated");
    localStorage.removeItem("correo");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("tokenType");

    navigate("/login");
  };

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const data = await getDashboardData();
        setDashboardData(data);
      } catch (error) {
        setError(error.message);

        if (
          error.message.includes("401") ||
          error.message.includes("403") ||
          error.message.includes("Error al consumir la API")
        ) {
          handleLogout();
        }
      }
    };

    fetchDashboardData();
  }, []);

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div>
          <h1>SIAC Dashboard</h1>
          <p>Bienvenida, {correo || "usuario"}</p>
        </div>

        <button className="logout-button" onClick={handleLogout}>
          Cerrar sesión
        </button>
      </header>

      {error && <p className="error-message">{error}</p>}

      <main className="dashboard-content">
        <section className="dashboard-welcome">
          <h2>
            {dashboardData
              ? dashboardData.mensaje
              : "Cargando dashboard..."}
          </h2>
          <p>Sistema de Inventario de Artículos Coleccionables</p>
        </section>

        <section className="cards-container">
          <div className="dashboard-card">
            <span>Usuarios: </span>
            <strong>{dashboardData?.totalUsuarios || 0}</strong>
          </div>

          <div className="dashboard-card">
            <span>Productos: </span>
            <strong>{dashboardData?.totalProductos || 0}</strong>
          </div>

          <div className="dashboard-card">
            <span>Movimientos: </span>
            <strong>{dashboardData?.totalMovimientos || 0}</strong>
          </div>

          <div className="dashboard-card">
            <span>Reportes: </span>
            <strong>{dashboardData?.totalReportes || 0}</strong>
          </div>
        </section>
      </main>
    </div>
  );
}

export default Dashboard;