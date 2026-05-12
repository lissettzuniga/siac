import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  obtenerPermisos,
  obtenerPermisosInactivos,
  crearPermiso,
  actualizarPermiso,
  desactivarPermiso,
  activarPermiso,
} from "../services/permisoService";
import {
  canManagePermisos,
  canViewPermisos,
} from "../auth/roleUtils";
import "../styles/permisosPage.css";

function PermisosPage() {
  const navigate = useNavigate();

  const [permisos, setPermisos] = useState([]);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [permisoSeleccionado, setPermisoSeleccionado] = useState(null);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [formPermiso, setFormPermiso] = useState({
    accion: "",
    recurso: "",
  });

  useEffect(() => {
    cargarActivos();
  }, []);

  const cargarActivos = async () => {
    try {
      const data = await obtenerPermisos();
      setPermisos(data.content || data || []);
      setMostrarInactivos(false);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarInactivos = async () => {
    try {
      const data = await obtenerPermisosInactivos();
      setPermisos(data.content || data || []);
      setMostrarInactivos(true);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setPermisoSeleccionado(null);

    setFormPermiso({
      accion: "",
      recurso: "",
    });

    setMostrarFormulario(true);
  };

  const abrirEditar = (permiso) => {
    setModoEdicion(true);
    setPermisoSeleccionado(permiso);

    setFormPermiso({
      accion: permiso.accion || "",
      recurso: permiso.recurso || "",
    });

    setMostrarFormulario(true);
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setFormPermiso({
      ...formPermiso,
      [name]: value,
    });
  };

  const guardarPermiso = async (e) => {
    e.preventDefault();

    try {
      if (modoEdicion) {
        await actualizarPermiso(permisoSeleccionado.id, formPermiso);
        alert("Permiso actualizado correctamente");
      } else {
        await crearPermiso(formPermiso);
        alert("Permiso creado correctamente");
      }

      setMostrarFormulario(false);
      cargarActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const eliminar = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar este permiso?"
    );

    if (!confirmar) return;

    try {
      await desactivarPermiso(id);
      alert("Permiso desactivado correctamente");
      cargarActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const activar = async (id) => {
    try {
      await activarPermiso(id);
      alert("Permiso activado correctamente");
      cargarInactivos();
    } catch (error) {
      alert(error.message);
    }
  };

  if (!canViewPermisos()) {
    return (
      <div className="permisos-page">
        <section className="permisos-header">
          <div>
            <h1>Acceso restringido</h1>
            <p>No tienes permiso para consultar permisos.</p>
          </div>

          <button onClick={() => navigate("/dashboard")}>
            Volver al dashboard
          </button>
        </section>
      </div>
    );
  }

  return (
    <div className="permisos-page">
      <header className="permisos-header">
        <div>
          <h1>Permisos</h1>
          <p>Administra los permisos del sistema.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {canManagePermisos() && (
        <section className="permisos-actions">
          <button className="btn-primary" onClick={abrirAgregar}>
            Agregar permiso
          </button>

          {!mostrarInactivos ? (
            <button
              className="btn-secondary"
              onClick={cargarInactivos}
            >
              Ver inactivos
            </button>
          ) : (
            <button
              className="btn-secondary"
              onClick={cargarActivos}
            >
              Ver activos
            </button>
          )}
        </section>
      )}

      <section className="permisos-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Acción</th>
              <th>Recurso</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {permisos.map((permiso) => (
              <tr key={permiso.id}>
                <td>{permiso.id}</td>
                <td>{permiso.accion}</td>
                <td>{permiso.recurso}</td>
                <td>{permiso.activo ? "Activo" : "Inactivo"}</td>

                <td>
                  {canManagePermisos() ? (
                    permiso.activo ? (
                      <>
                        <button
                          className="btn-edit"
                          onClick={() => abrirEditar(permiso)}
                        >
                          Editar
                        </button>

                        <button
                          className="btn-delete"
                          onClick={() => eliminar(permiso.id)}
                        >
                          Desactivar
                        </button>
                      </>
                    ) : (
                      <button
                        className="btn-edit"
                        onClick={() => activar(permiso.id)}
                      >
                        Activar
                      </button>
                    )
                  ) : (
                    <span>Solo lectura</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {permisos.length === 0 && (
          <p className="sin-registros">No hay permisos registrados.</p>
        )}
      </section>

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar permiso" : "Agregar permiso"}</h2>

            <form className="permisos-form" onSubmit={guardarPermiso}>
              <input
                type="text"
                name="accion"
                placeholder="Acción"
                value={formPermiso.accion}
                onChange={manejarCambio}
                required
              />

              <input
                type="text"
                name="recurso"
                placeholder="Recurso"
                value={formPermiso.recurso}
                onChange={manejarCambio}
                required
              />

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear permiso"}
                </button>

                <button
                  className="btn-secondary"
                  type="button"
                  onClick={() => setMostrarFormulario(false)}
                >
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default PermisosPage;