import { useNavigate } from "react-router-dom";

import {
  canViewProductos,
  canViewCategorias,
  canManageMovimientos,
  canManageUsuarios,
  canViewBitacora,
  canViewReportes,
  canManageTiposMovimiento,
  canManageTiposCarta,
  canManageProductoCarta,
  canManageImagenesProducto,
  canManageUsuarioRoles,
  canManageRoles,
  canViewPermisos,
  canViewRolPermisos
} from "../auth/roleUtils";

import "../styles/siac.css";

function Dashboard() {

  const navigate = useNavigate();

  const cerrarSesion = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="dashboard-container">

      <header className="dashboard-header">
        <div>
          <h1>SIAC</h1>
          <p>Sistema de Inventario de Artículos Coleccionables</p>
        </div>

        <button
          className="logout-button"
          onClick={cerrarSesion}
        >
          Cerrar sesión
        </button>
      </header>

      <main className="dashboard-content">

        <section className="dashboard-welcome">
          <h2>Bienvenida al Dashboard</h2>
          <p>Selecciona un módulo del sistema.</p>
        </section>

        {/* MÓDULOS PRINCIPALES */}
        <section className="dashboard-grid">

          {canViewProductos() && (
            <button onClick={() => navigate("/productos")}>
              Productos
            </button>
          )}

          {canViewCategorias() && (
            <button onClick={() => navigate("/categorias")}>
              Categorías
            </button>
          )}

          {canManageMovimientos() && (
            <button onClick={() => navigate("/movimientos")}>
              Movimientos
            </button>
          )}

          {canManageUsuarios() && (
            <button onClick={() => navigate("/usuarios")}>
              Usuarios
            </button>
          )}

          {canViewBitacora() && (
            <button onClick={() => navigate("/bitacora")}>
              Bitácora
            </button>
          )}

          {canViewReportes() && (
            <button onClick={() => navigate("/reportes")}>
              Reportes
            </button>
          )}

          <button onClick={() => navigate("/cambiar-contrasena")}>
            Cambiar contraseña
          </button>

          <button onClick={() => navigate("/busqueda-inteligente")}>
            Búsqueda Inteligente
          </button>

        </section>

        {/* CATÁLOGOS ADMINISTRATIVOS */}
        {canManageTiposMovimiento() && (
          <>
            <section className="dashboard-section-title">
              <h2>Catálogos administrativos</h2>
            </section>

            <section className="dashboard-grid">

              <button onClick={() => navigate("/tipos-movimiento")}>
                Tipos de movimiento
              </button>

               {canManageTiposCarta() && (
                   <button onClick={() => navigate("/tipos-carta")}>
                       Tipos de carta
                   </button>
               )}

               {canManageProductoCarta() && (
                 <button onClick={() => navigate("/productos-carta")}>
                   Productos carta
                 </button>
               )}

               {canManageImagenesProducto() && (
                 <button onClick={() => navigate("/imagenes-producto")}>
                   Imágenes producto
                 </button>
               )}

               {canManageUsuarioRoles() && (
                 <button onClick={() => navigate("/usuario-roles")}>
                   Usuario roles
                 </button>
               )}

               {canManageRoles() && (
                 <button onClick={() => navigate("/roles")}>
                   Roles
                 </button>
               )}

               {canViewPermisos() && (
                 <button onClick={() => navigate("/permisos")}>
                   Permisos
                 </button>
               )}

               {canViewRolPermisos() && (
                 <button onClick={() => navigate("/rol-permisos")}>
                   Rol permisos
                 </button>
               )}

            </section>
          </>
        )}


      </main>

    </div>
  );
}

export default Dashboard;