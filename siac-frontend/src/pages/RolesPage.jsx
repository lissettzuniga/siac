import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  obtenerRoles,
  obtenerRolesInactivos,
  crearRol,
  actualizarRol,
  desactivarRol,
  activarRol,
} from "../services/rolService";
import { canManageRoles } from "../auth/roleUtils";
import "../styles/rolesPage.css";

function RolesPage() {
  const navigate = useNavigate();

  const [roles, setRoles] = useState([]);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [rolSeleccionado, setRolSeleccionado] = useState(null);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [formRol, setFormRol] = useState({
    nombre: "",
    descripcion: "",
  });

  useEffect(() => {
    cargarRolesActivos();
  }, []);

  const cargarRolesActivos = async () => {
    try {
      const data = await obtenerRoles();
      setRoles(data.content || data || []);
      setMostrarInactivos(false);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarRolesInactivos = async () => {
    try {
      const data = await obtenerRolesInactivos();
      setRoles(data.content || data || []);
      setMostrarInactivos(true);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setRolSeleccionado(null);
    setFormRol({
      nombre: "",
      descripcion: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (rol) => {
    setModoEdicion(true);
    setRolSeleccionado(rol);

    setFormRol({
      nombre: rol.nombre || "",
      descripcion: rol.descripcion || "",
    });

    setMostrarFormulario(true);
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setFormRol({
      ...formRol,
      [name]: value,
    });
  };

  const guardarRol = async (e) => {
    e.preventDefault();

    try {
      if (modoEdicion) {
        await actualizarRol(rolSeleccionado.id, formRol);
        alert("Rol actualizado correctamente");
      } else {
        await crearRol(formRol);
        alert("Rol creado correctamente");
      }

      setMostrarFormulario(false);
      cargarRolesActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const eliminarRol = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar este rol?"
    );

    if (!confirmar) return;

    try {
      await desactivarRol(id);
      alert("Rol desactivado correctamente");
      cargarRolesActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const activar = async (id) => {
    try {
      await activarRol(id);
      alert("Rol activado correctamente");
      cargarRolesInactivos();
    } catch (error) {
      alert(error.message);
    }
  };

  if (!canManageRoles()) {
    return (
      <div className="roles-page">
        <section className="roles-header">
          <div>
            <h1>Acceso restringido</h1>
            <p>Solo ADMIN puede administrar roles.</p>
          </div>

          <button onClick={() => navigate("/dashboard")}>
            Volver al dashboard
          </button>
        </section>
      </div>
    );
  }

  return (
    <div className="roles-page">
      <header className="roles-header">
        <div>
          <h1>Roles</h1>
          <p>Administra los roles del sistema.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <section className="roles-actions">
        <button className="btn-primary" onClick={abrirAgregar}>
          Agregar rol
        </button>

        {!mostrarInactivos ? (
          <button
            className="btn-secondary"
            onClick={cargarRolesInactivos}
          >
            Ver inactivos
          </button>
        ) : (
          <button
            className="btn-secondary"
            onClick={cargarRolesActivos}
          >
            Ver activos
          </button>
        )}
      </section>

      <section className="roles-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Descripción</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {roles.map((rol) => (
              <tr key={rol.id}>
                <td>{rol.id}</td>
                <td>{rol.nombre}</td>
                <td>{rol.descripcion}</td>
                <td>{rol.activo ? "Activo" : "Inactivo"}</td>

                <td>
                  {rol.activo ? (
                    <>
                      <button
                        className="btn-edit"
                        onClick={() => abrirEditar(rol)}
                      >
                        Editar
                      </button>

                      <button
                        className="btn-delete"
                        onClick={() => eliminarRol(rol.id)}
                      >
                        Desactivar
                      </button>
                    </>
                  ) : (
                    <button
                      className="btn-edit"
                      onClick={() => activar(rol.id)}
                    >
                      Activar
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {roles.length === 0 && (
          <p className="sin-registros">No hay roles registrados.</p>
        )}
      </section>

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar rol" : "Agregar rol"}</h2>

            <form className="roles-form" onSubmit={guardarRol}>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre del rol"
                value={formRol.nombre}
                onChange={manejarCambio}
                required
              />

              <textarea
                name="descripcion"
                placeholder="Descripción"
                value={formRol.descripcion}
                onChange={manejarCambio}
                required
              />

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear rol"}
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

export default RolesPage;