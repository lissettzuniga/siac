import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { obtenerUsuarios } from "../services/usuarioService";
import { obtenerRoles } from "../services/rolService";

import {
  obtenerUsuarioRoles,
  obtenerUsuarioRolesInactivos,
  crearUsuarioRol,
  actualizarUsuarioRol,
  desactivarUsuarioRol,
  activarUsuarioRol,
} from "../services/usuarioRolService";

import { canManageUsuarioRoles } from "../auth/roleUtils";

import "../styles/usuarioRolPage.css";

function UsuarioRolPage() {
  const navigate = useNavigate();

  const [usuarioRoles, setUsuarioRoles] = useState([]);
  const [usuarios, setUsuarios] = useState([]);
  const [roles, setRoles] = useState([]);

  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [seleccionado, setSeleccionado] = useState(null);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);

  const size = 5;

  const [form, setForm] = useState({
    usuarioId: "",
    rolId: "",
  });

  useEffect(() => {
    cargarUsuarios();
    cargarRoles();
  }, []);

  useEffect(() => {
    cargarUsuarioRoles();
  }, [pagina, mostrarInactivos]);

  const cargarUsuarioRoles = async () => {
    try {
      const data = mostrarInactivos
        ? await obtenerUsuarioRolesInactivos(pagina, size)
        : await obtenerUsuarioRoles(pagina, size);

      setUsuarioRoles(data.content || []);
      setTotalPaginas(data.totalPages || 0);

    } catch (error) {
      alert(error.message);
    }
  };

  const cargarUsuarios = async () => {
    try {
      const data = await obtenerUsuarios();
      setUsuarios(data.content || data || []);
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

  const cambiarVista = () => {
    setPagina(0);
    setMostrarInactivos(!mostrarInactivos);
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setSeleccionado(null);

    setForm({
      usuarioId: "",
      rolId: "",
    });

    setMostrarFormulario(true);
  };

  const abrirEditar = (item) => {
    setModoEdicion(true);
    setSeleccionado(item);

    setForm({
      usuarioId: item.usuarioId || "",
      rolId: item.rolId || "",
    });

    setMostrarFormulario(true);
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
      usuarioId: Number(form.usuarioId),
      rolId: Number(form.rolId),
    };

    try {
      if (modoEdicion) {
        await actualizarUsuarioRol(seleccionado.id, request);
        alert("Rol de usuario actualizado correctamente");
      } else {
        await crearUsuarioRol(request);
        alert("Rol asignado correctamente");
      }

      setMostrarFormulario(false);
      setModoEdicion(false);
      setSeleccionado(null);

      setForm({
        usuarioId: "",
        rolId: "",
      });

      await cargarUsuarioRoles();

    } catch (error) {
      alert(error.message);
    }
  };

  const desactivar = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar esta asignación de rol?"
    );

    if (!confirmar) return;

    try {
      await desactivarUsuarioRol(id);

      alert("Asignación desactivada correctamente");

      await cargarUsuarioRoles();

    } catch (error) {
      alert(error.message);
    }
  };

  const activar = async (id) => {
    try {
      await activarUsuarioRol(id);

      alert("Asignación activada correctamente");

      await cargarUsuarioRoles();

    } catch (error) {
      alert(error.message);
    }
  };

  if (!canManageUsuarioRoles()) {
    return (
      <div className="usuario-rol-page">
        <header className="usuario-rol-header">
          <div>
            <h1>Acceso restringido</h1>
            <p>Solo ADMIN puede administrar roles de usuario.</p>
          </div>

          <button onClick={() => navigate("/dashboard")}>
            Volver al dashboard
          </button>
        </header>
      </div>
    );
  }

  return (
    <div className="usuario-rol-page">
      <header className="usuario-rol-header">
        <div>
          <h1>Usuario - Roles</h1>
          <p>Asigna roles a los usuarios del sistema.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <section className="usuario-rol-actions">
        <button className="btn-primary" onClick={abrirAgregar}>
          Asignar rol
        </button>

        <button className="btn-secondary" onClick={cambiarVista}>
          {mostrarInactivos ? "Ver activos" : "Ver inactivos"}
        </button>
      </section>

      {mostrarFormulario && (
        <section className="usuario-rol-form-card">
          <h2>{modoEdicion ? "Editar asignación" : "Asignar rol"}</h2>

          <form className="usuario-rol-form" onSubmit={guardar}>
            <select
              name="usuarioId"
              value={form.usuarioId}
              onChange={manejarCambio}
              required
            >
              <option value="">Selecciona usuario</option>

              {usuarios.map((usuario) => (
                <option key={usuario.id} value={usuario.id}>
                  {usuario.nombre} {usuario.apPaterno}{" "}
                  {usuario.apMaterno} - {usuario.correo}
                </option>
              ))}
            </select>

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

      <section className="usuario-rol-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Usuario</th>
              <th>Rol</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {usuarioRoles.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>

                <td>
                  {item.nombreUsuario}
                  <br />
                  <span>ID: {item.usuarioId}</span>
                </td>

                <td>{item.nombreRol}</td>

                <td>
                  {item.activo ? "Activo" : "Inactivo"}
                </td>

                <td>
                  {item.activo ? (
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
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {usuarioRoles.length === 0 && (
          <p className="sin-registros">
            No hay asignaciones de roles.
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
    </div>
  );
}

export default UsuarioRolPage;