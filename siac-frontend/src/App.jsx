import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./components/ProtectedRoute";

import ProductosPage from "./pages/ProductosPage";
import CategoriasPage from "./pages/CategoriasPage";
import MovimientosPage from "./pages/MovimientosPage";
import UsuariosPage from "./pages/UsuariosPage";
import BitacoraPage from "./pages/BitacoraPage";
import ReportesPage from "./pages/ReportesPage";
import BusquedaInteligentePage from "./pages/BusquedaInteligentePage";
import CambiarContrasenaPage from "./pages/CambiarContrasenaPage";
import TipoMovimientoPage from "./pages/TipoMovimientoPage";
import TipoCartaPage from "./pages/TipoCartaPage";
import ProductoCartaPage from "./pages/ProductoCartaPage";
import ImagenesProductoPage from "./pages/ImagenesProductoPage";
import UsuarioRolPage from "./pages/UsuarioRolPage";
import RolesPage from "./pages/RolesPage";
import PermisosPage from "./pages/PermisosPage";
import RolPermisoPage from "./pages/RolPermisoPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route path="/productos" element={<ProductosPage />} />
        <Route path="/categorias" element={<CategoriasPage />} />
        <Route path="/movimientos" element={<MovimientosPage />} />
        <Route path="/usuarios" element={<UsuariosPage />} />
        <Route path="/bitacora" element={<BitacoraPage />} />
        <Route path="/reportes" element={<ReportesPage />} />
        <Route path="/cambiar-contrasena" element={<CambiarContrasenaPage />} />
        <Route path="/busqueda-inteligente" element={<BusquedaInteligentePage />} />
        <Route path="/tipos-movimiento" element={<TipoMovimientoPage />} />
        <Route path="/tipos-carta" element={<TipoCartaPage />} />
        <Route path="/productos-carta" element={<ProductoCartaPage />} />
        <Route path="/imagenes-producto" element={<ImagenesProductoPage />} />
        <Route path="/usuario-roles" element={<UsuarioRolPage />} />
        <Route path="/roles" element={<RolesPage />} />
        <Route path="/permisos" element={<PermisosPage />} />
        <Route path="/rol-permisos" element={<RolPermisoPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;