import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { obtenerRoles } from "../services/rolService";
import { obtenerPermisos } from "../services/permisoService";

import {
  obtenerRolPermisos,
  obtenerRolPermisosInactivos,
  crearRolPermiso,
  actualizarRolPermiso,
  desactivarRolPermiso,
  activarRolPermiso,
} from "../services/rolPermisoService";

import {
  canManageRolPermisos,
  canViewRolPermisos,
} from "../auth/roleUtils";

import "../styles/rolPermisoPage.css";

function RolPermisoPage() {
  const navigate = useNavigate();

  const [rolPermisos, setRolPermisos] = useState([]);
  const [roles, setRoles] = useState([]);
  const [permisos, setPermisos] = useState([]);

  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const size = 5;

  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [seleccionado, setSeleccionado] = useState(null);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [form, setForm] = useState({
    rolId: "",
    permisoId: "",
  });

  useEffect(() => {
    cargarRoles();
    cargarPermisos();
  }, []);

  useEffect(() => {
    cargarRolPermisos();
  }, [pagina, mostrarInactivos]);

  const cargarRolPermisos = async () => {
    try {
      const data = mostrarInactivos
        ? await obtenerRolPermisosInactivos(pagina, size)
        : await obtenerRolPermisos(pagina, size);

      setRolPermisos(data.content || []);
      setTotalPaginas(data.totalPages || 0);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarRoles = async () => {
    try {
      const data = await obtenerRoles();
      setRoles(data.content || data || []);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarPermisos = async () => {
    try {
      const data = await obtenerPermisos();
      setPermisos(data.content || data || []);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setSeleccionado(null);
    setForm({
      rolId: "",
      permisoId: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (item) => {
    setModoEdicion(true);
    setSeleccionado(item);

    setForm({
      rolId: item.rolId || "",
      permisoId: item.permisoId || "",
    });

    setMostrarFormulario(true);

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setForm({
      ...form,
      [name]: value,
    });
  };

  const guardar = async (e) => {
    e.preventDefault();

    const request = {
      rolId: Number(form.rolId),
      permisoId: Number(form.permisoId),
    };

    try {
      if (modoEdicion) {
        await actualizarRolPermiso(seleccionado.id, request);
        alert("Permiso del rol actualizado correctamente");
      } else {
        await crearRolPermiso(request);
        alert("Permiso asignado al rol correctamente");
      }

      setMostrarFormulario(false);
      setModoEdicion(false);
      setSeleccionado(null);
      setForm({
        rolId: "",
        permisoId: "",
      });

      await cargarRolPermisos();
    } catch (error) {
      console.error(error);
      alert(error.message);
    }
  };

  const desactivar = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar esta asignación de permiso?"
    );

    if (!confirmar) return;

    try {
      await desactivarRolPermiso(id);
      alert("Asignación desactivada correctamente");
      await cargarRolPermisos();
    } catch (error) {
      alert(error.message);
    }
  };

  const activar = async (id) => {
    try {
      await activarRolPermiso(id);
      alert("Asignación activada correctamente");
      await cargarRolPermisos();
    } catch (error) {
      alert(error.message);
    }
  };

  const cambiarVista = () => {
    setPagina(0);
    setMostrarInactivos(!mostrarInactivos);
  };

  if (!canViewRolPermisos()) {
    return (
      <div className="rol-permiso-page">
        <header className="rol-permiso-header">
          <div>
            <h1>Acceso restringido</h1>
            <p>No tienes permiso para consultar rol-permisos.</p>
          </div>

          <button onClick={() => navigate("/dashboard")}>
            Volver al dashboard
          </button>
        </header>
      </div>
    );
  }

  return (
    <div className="rol-permiso-page">
      <header className="rol-permiso-header">
        <div>
          <h1>Rol - Permisos</h1>
          <p>Asigna permisos a los roles del sistema.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {canManageRolPermisos() && (
        <section className="rol-permiso-actions">
          <button className="btn-primary" onClick={abrirAgregar}>
            Asignar permiso
          </button>

          <button className="btn-secondary" onClick={cambiarVista}>
            {mostrarInactivos ? "Ver activos" : "Ver inactivos"}
          </button>
        </section>
      )}

      <section className="rol-permiso-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Rol</th>
              <th>Permiso</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {rolPermisos.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>

                <td>
                  {item.nombreRol}
                  <br />
                  <span>ID: {item.rolId}</span>
                </td>

                <td>
                  {item.accionPermiso}
                  <br />
                  <span>ID: {item.permisoId}</span>
                </td>

                <td>{item.activo ? "Activo" : "Inactivo"}</td>

                <td>
                  {canManageRolPermisos() ? (
                    item.activo ? (
                      <>
                        <button
                          className="btn-edit"
                          onClick={() => abrirEditar(item)}
                        >
                          Editar
                        </button>

                        <button
                          className="btn-delete"
                          onClick={() => desactivar(item.id)}
                        >
                          Desactivar
                        </button>
                      </>
                    ) : (
                      <button
                        className="btn-edit"
                        onClick={() => activar(item.id)}
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

        {rolPermisos.length === 0 && (
          <p className="sin-registros">
            No hay permisos asignados a roles.
          </p>
        )}

        <div className="pagination">
          <button
            disabled={pagina === 0}
            onClick={() => setPagina(pagina - 1)}
          >
            Anterior
          </button>

          <span>
            Página {pagina + 1} de {totalPaginas || 1}
          </span>

          <button
            disabled={pagina + 1 >= totalPaginas}
            onClick={() => setPagina(pagina + 1)}
          >
            Siguiente
          </button>
        </div>
      </section>

      {mostrarFormulario && canManageRolPermisos() && (
        <section className="rol-permiso-form-card">
          <h2>{modoEdicion ? "Editar asignación" : "Asignar permiso"}</h2>

          <form className="rol-permiso-form" onSubmit={guardar}>
            <select
              name="rolId"
              value={form.rolId}
              onChange={manejarCambio}
              required
            >
              <option value="">Selecciona rol</option>

              {roles.map((rol) => (
                <option key={rol.id} value={rol.id}>
                  {rol.nombre}
                </option>
              ))}
            </select>

            <select
              name="permisoId"
              value={form.permisoId}
              onChange={manejarCambio}
              required
            >
              <option value="">Selecciona permiso</option>

              {permisos.map((permiso) => (
                <option key={permiso.id} value={permiso.id}>
                  {permiso.accion} - {permiso.recurso}
                </option>
              ))}
            </select>

            <div className="acciones">
              <button className="btn-primary" type="submit">
                {modoEdicion ? "Guardar cambios" : "Asignar"}
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
        </section>
      )}
    </div>
  );
}

export default RolPermisoPage;